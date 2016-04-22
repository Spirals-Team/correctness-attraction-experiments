package torrent;

import com.turn.ttorrent.common.Peer;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by spirals on 22/04/16.
 */
public class Search {

    public static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public static List<Class> classes = new ArrayList<>();

    public static void iterateFolders(String path, String currentPackage) throws ClassNotFoundException {
        File root = new File(path);
        assert root.listFiles() != null;
        for (File subFile : root.listFiles()) {
            if (subFile.isDirectory())
                iterateFolders(path + subFile.getName() + "/", currentPackage + "." + subFile.getName());
            else if (isJava(subFile.getName()))
                classes.add(loader.loadClass(currentPackage + "." + removeExt(subFile.getName())));
        }
    }

    public static String removeExt(String name) {
        return name.substring(0, name.length() - ".java".length());
    }

    public static boolean isJava(String name) {
        return name.endsWith(".java");
    }

    public static void getAllClasses(String project, String packagaPath) throws ClassNotFoundException {
        iterateFolders(project, packagaPath);
    }

    public static void main(String[] args) {
        try {
            getAllClasses("./src/main/java/com/turn/ttorrent/", "com.turn.ttorrent");

            final List<PerturbationLocation> locations = new ArrayList<>();

            classes.stream().forEach(clazz ->
                    locations.addAll(PerturbationLocationImpl.getLocationFromClass(clazz))
            );

            locations.stream()
                    .filter(location -> location.getType().equals("Numerical"))
                    .forEach(location -> PerturbationEngine.logger.logOn(location));

            TorrentManager manager = new TorrentManager();
            TorrentCallable callable = new TorrentCallable(manager.get(0));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future future = executor.submit(callable);
            future.get(60, TimeUnit.SECONDS);

            final FileWriter writer = new FileWriter("output_seach.txt", false);

            locations.stream()
                    .filter(location -> location.getType().equals("Numerical"))
                    .sorted((l1, l2) -> - (PerturbationEngine.logger.getCalls(l1) - PerturbationEngine.logger.getCalls(l2)))
                    .forEach(location -> {
                        try {
                            writer.write(location + "\t" + PerturbationEngine.logger.getCalls(location) + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            writer.close();
            manager.stop();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
