package experiment;

import perturbation.location.PerturbationLocation;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by spirals on 05/04/16.
 */
public class Runner {

    public static Oracle oracle;
    public static Class<?> CUP;// Class Under Perturbation
    public static Class<?> classCallable;// Class to run exp have to be static
    public static Constructor constructorRunner;
    public static List<PerturbationLocation> locations;
    public static Explorer explorer;
    public static int numberOfSecondsToWait = 1;
    public static int sizeOfTopLocations = 10;
    public static boolean verbose = false;

    public static void run(Explorer explorerUnderPerturbation) {
        explorer = explorerUnderPerturbation;
        for (int indexOfTask = 0 ; indexOfTask < oracle.getNumberOfTask() ; indexOfTask++) {
            runLocations(indexOfTask);
        }
    }

    public static void runLocations(int indexOfTask) {
        for (PerturbationLocation location : locations) {
            if (verbose)
                System.out.println(location.getLocationIndex()+" \t "+Util.getStringPerc(locations.indexOf(location) , locations.size()));
            explorer.run(indexOfTask, location);
        }
        explorer.log();
    }

    public static Tuple runPerturbation(int indexOfTask) {
        Tuple result = new Tuple(3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = (Callable)constructorRunner.newInstance(oracle.get(indexOfTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object perturbedValue = (future.get(numberOfSecondsToWait, TimeUnit.SECONDS));
                boolean checked = oracle.check(perturbedValue, indexOfTask);
                if (checked)
                    result.set(0, 1); // success
                else
                    result.set(1, 1); // failures
                executor.shutdownNow();
                return result;

            } catch (TimeoutException e) {
                future.cancel(true);
                result.set(2, 1); // error computation time
                System.err.println("Time out!");
                executor.shutdownNow();
                return result;
            }

        } catch (Exception | Error e) {
            result.set(2, 1);
            executor.shutdownNow();
            return result;
        }
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     * @param classUnderPerturbation
     * @param classCallable Class which contains the entry method nameOfEntryMethod
     * @param oracleImpl
     * @param argsOfMethods
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable, Oracle oracleImpl, Class<?>... argsOfMethods) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        try {
            Runner.constructorRunner = classCallable.getConstructor(argsOfMethods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        locations = PerturbationLocation.getLocationFromClass(classUnderPerturbation).stream().filter(location ->
                location.getType().equals("Numerical")).collect(Collectors.toList()
        );
        oracle = oracleImpl;
    }

    public static void runAllCampaign() {
        run(new AddOneExplorerImpl());
        run(new AddNExplorerImpl());
        run(new IntegerAdd1RndEnactorExplorerImpl());
    }

    public static void main(String[] args) {
        quicksort.Main.main(args);
        zip.Main.main(args);
        md5.Main.main(args);
        sudoku.Main.main(args);
    }
}
