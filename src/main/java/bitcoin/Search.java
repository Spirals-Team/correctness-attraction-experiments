package bitcoin;

import experiment.Oracle;
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

    public static void iterateFolders(String path, String currentPackage) {
        File root = new File(path);
        assert root.listFiles() != null;
        for (File subFile : root.listFiles()) {
            if (subFile.isDirectory())
                iterateFolders(path + subFile.getName() + "/", currentPackage + "." + subFile.getName());
            else if (isJava(subFile.getName())) {
                try {
                    Class<?> clazz = loader.loadClass(currentPackage + "." + removeExt(subFile.getName()));
                    System.out.println(currentPackage + "." + removeExt(subFile.getName()));
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

    private static final String pathToBicoinJDirectory = "./bitcoinj/core/src/main/java/org/";

    public static void main(String[] args) {
        try {

            int numberOfSecondsToWait = 60;
            int sizeOfEachTask = 3;
            int numberOfTask = 1;

            PerturbationEngine.loggers.put("Bitcoin", new LoggerImpl());

            String type = "Numerical";
//            String type = "Boolean";

            getAllClasses(pathToBicoinJDirectory, "org");

            final List<PerturbationLocation> locations = new ArrayList<>();

            classes.stream().forEach(clazz -> {
                        PerturbationLocationImpl.getLocationFromClass(clazz).forEach(location -> {
                            if (!locations.contains(location))
                                locations.add(location);
                        });
                    }
            );

            BitcoinManager manager = new BitcoinManager(numberOfTask, sizeOfEachTask, type);

            locations.stream()
                    .filter(location -> location.getType().equals(type))
                    .forEach(location -> PerturbationEngine.loggers.get("Bitcoin").logOn(location));

            boolean totalAssertion = true;
            long accTime = 0;

            for (int i = 0; i < numberOfTask ; i++) {
                BitcoinCallable callable = (BitcoinCallable) manager.getCallable(manager.getTask(i));
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future future = executor.submit(callable);
                long time = System.currentTimeMillis();
                boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(i), (int) future.get(numberOfSecondsToWait, TimeUnit.SECONDS));
                totalAssertion &= assertion;
                accTime += System.currentTimeMillis() - time;
            }

            System.out.println(totalAssertion);
            System.out.println(accTime / numberOfTask + " ms");

            final FileWriter writer = new FileWriter("results/bitcoin/output_search_bitcoin_" + type + ".txt", false);

            locations.stream()
                    .filter(location -> location.getType().equals(type))
                    .sorted((l1, l2) -> -(PerturbationEngine.loggers.get("Bitcoin").getCalls(l1) - PerturbationEngine.loggers.get("Bitcoin").getCalls(l2)))
                    .forEach(location -> {
                        try {
                            writer.write(location + "\t" + PerturbationEngine.loggers.get("Bitcoin").getCalls(location) + "\n");
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
