package experiment;

import experiment.explorer.*;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by spirals on 05/04/16.
 */
public class Runner {

    public static Oracle oracle;
    public static OracleManagerImpl manager;
    public static Class<?> CUP;// Class Under Perturbation
    public static Class<?> classCallable;
    public static Constructor constructorRunner;
    public static List<PerturbationLocation> locations;
    public static List<Explorer> explorers = new ArrayList<>();
    public static Explorer explorer;
    public static int numberOfSecondsToWait = 1;
    public static int sizeOfEachTask = 100;
    public static int numberOfTask = 20;
    public static Class [] inputType;

    public static boolean verbose = false;

    public static void run(Explorer explorerUnderPerturbation) {
        explorer = explorerUnderPerturbation;
        System.out.println("Run " + explorer + " on " + CUP.getSimpleName() + " ...");
        filterLocation(explorer.getTypeOfExploration());
        explorer.initLogger();
        for (int indexOfTask = 0 ; indexOfTask < numberOfTask ; indexOfTask++) {
            runLocations(indexOfTask);
        }
        explorer.log();
    }

    public static void runLocations(int indexOfTask) {
        for (PerturbationLocation location : locations) {
            if (verbose)
                System.out.println(location.getLocationIndex()+" \t "+Util.getStringPerc(locations.indexOf(location) , locations.size()));
            explorer.run(indexOfTask, location);
        }
    }


    public static Tuple runPerturbation(int indexOfTask) {
        Tuple result = new Tuple(3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = (Callable)constructorRunner.newInstance(manager.get(indexOfTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object output = (future.get(numberOfSecondsToWait, TimeUnit.SECONDS));
                boolean assertion = oracle.assertPerturbation(manager.get(indexOfTask), output);
                if (assertion)
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

    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable,
                             OracleManagerImpl manager, float percentage, int indexPercentage, String locationType, Class<?>... inputTypes) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        Runner.manager = manager;
        try {
            Runner.constructorRunner = classCallable.getConstructor(inputTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterLocation(locationType);
        int sizeOfSlice = (int)(locations.size() * percentage);
        locations = locations.subList(indexPercentage * sizeOfSlice, (indexPercentage+1) * sizeOfSlice);
        inputType = inputTypes;
        oracle = Runner.manager.getOracle();
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     * @param classUnderPerturbation
     * @param classCallable
     * @param manager
     * @param inputTypes
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable, OracleManagerImpl manager, String locationType, Class<?>... inputTypes) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        Runner.manager = manager;
        try {
            Runner.constructorRunner = classCallable.getConstructor(inputTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterLocation(locationType);
        inputType = inputTypes;
        oracle = Runner.manager.getOracle();
    }

    public static void filterLocation(String locationType) {
        locations = PerturbationLocationImpl.getLocationFromClass(CUP).stream()
                .filter(location -> location.getType().equals(locationType))
                .collect(Collectors.toList()
        );
    }

    public static void runExplorers() {
        explorers.forEach(Runner::run);
    }

    public static void main(String[] args) {
        ParserArgs.parseArgs(args);
        runExplorers();
    }
}
