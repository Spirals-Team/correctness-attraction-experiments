package torrent;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.log.LoggerImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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

            int numberOfSecondsToWait = 60;
            int sizeOfEachTask = 100;
            int numberOfTask = 10;

            getAllClasses("ttorrent/core/src/main/java/com/turn/ttorrent/", "com.turn.ttorrent");

            final List<PerturbationLocation> locations = new ArrayList<>();

            classes.stream().forEach(clazz -> {
                        PerturbationLocationImpl.getLocationFromClass(clazz).forEach(location -> {
                            if (!locations.contains(location))
                                locations.add(location);
                        });
                    }
            );

            PerturbationEngine.loggers.put("Torrent", new LoggerImpl());

            String type = "Numerical";
//            String type = "Boolean";

            locations.stream()
                    .filter(location -> location.getType().equals(type))
                    .forEach(location -> PerturbationEngine.loggers.get("Torrent").logOn(location));

            TorrentManager manager = new TorrentManager(numberOfTask, sizeOfEachTask);
            for (int i = 0; i < numberOfTask ; i++) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future future = executor.submit(manager.getCallable(manager.getTask(i)));
                long time = System.currentTimeMillis();
                System.out.println(manager.getOracle().assertPerturbation(manager.getTask(i), (String) future.get(numberOfSecondsToWait, TimeUnit.SECONDS)));
                System.out.println(System.currentTimeMillis() - time + " ms");
            }

            final FileWriter writer = new FileWriter("results/torrent/output_search_torrent_"+type+".txt", false);

            locations.stream()
                    .filter(location -> location.getType().equals(type))
                    .sorted((l1, l2) -> - (PerturbationEngine.loggers.get("Torrent").getCalls(l1) - PerturbationEngine.loggers.get("Torrent").getCalls(l2)))
                    .forEach(location -> {
                        try {
                            writer.write(location + "\t" + PerturbationEngine.loggers.get("Torrent").getCalls(location) + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            writer.close();
            manager.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
