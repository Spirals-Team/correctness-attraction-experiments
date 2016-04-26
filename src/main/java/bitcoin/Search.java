package bitcoin;

import experiment.Runner;
import org.bitcoinj.crypto.PBKDF2SHA512;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

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

    public static void iterateFolders(String path, String currentPackage) {
        File root = new File(path);
        assert root.listFiles() != null;
        for (File subFile : root.listFiles()) {
            if (subFile.isDirectory())
                iterateFolders(path + subFile.getName() + "/", currentPackage + "." + subFile.getName());
            else if (isJava(subFile.getName())) {
                try {
                    Class<?> clazz = loader.loadClass(currentPackage + "." + removeExt(subFile.getName()));
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                   continue;
                }
            }
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

            Runner.numberOfSecondsToWait = 60;
            Runner.sizeOfEachTask = 3;
            Runner.numberOfTask = 2;
            Runner.verbose = true;

//            String type = "Numerical";
            String type = "Boolean";

            getAllClasses("./core/src/main/java/org/", "org");

            final List<PerturbationLocation> locations = new ArrayList<>();

            classes.stream().forEach(clazz ->
                    locations.addAll(PerturbationLocationImpl.getLocationFromClass(clazz))
            );

            BitcoinManager manager = new BitcoinManager();
            BitcoinCallable callable = new BitcoinCallable(manager.get(0));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            locations.stream()
                    .filter(location -> location.getType().equals(type))
                    .forEach(location -> PerturbationEngine.logger.logOn(location));
            Future future = executor.submit(callable);
            long time = System.currentTimeMillis();
            future.get(Runner.numberOfSecondsToWait, TimeUnit.SECONDS);
            System.out.println(System.currentTimeMillis() - time + " ms");

            final FileWriter writer = new FileWriter("output_search_bitcoin_"+type+".txt", false);

            locations.stream()
                    .filter(location -> location.getType().equals(type))
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
