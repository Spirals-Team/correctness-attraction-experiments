package experiment;

import bitcoin.BitcoinManager;
import com.google.common.annotations.VisibleForTesting;
import experiment.explorer.*;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by spirals on 05/04/16.
 */
public class Runner {

    public static Oracle oracle;
    public static OracleManager manager;
    public static Class<?> CUP;// Class Under Perturbation
    public static Class<?> classCallable;
    public static Constructor constructorRunner;
    public static List<PerturbationLocation> locations;
    public static List<Explorer> explorers = new ArrayList<>();
    public static Explorer explorer;
    public static int numberOfSecondsToWait = 1;
    public static int sizeOfEachTask = 100;
    public static List<Integer> task;

    public static int numberOfTask = -1;

    static {
        task = new ArrayList<>();
        for (int i = 0; i < (numberOfTask == -1 ? 20 : numberOfTask) ; i++) {
            task.add(i);
        }
        numberOfTask = task.size();
    }
    public static Class[] inputType;

    private static List<Object> outputs = new ArrayList<>();

    public static boolean verbose = false;

    public static void run(Explorer explorerToBeRun) {
        explorer = explorerToBeRun;
        System.out.println("Run " + explorer + " on " + CUP.getSimpleName() + " ...");
        filterLocation(explorer.getTypeOfExploration());
        explorer.initLogger();
        IntStream.range(0, numberOfTask)
                .forEach(index -> Runner.runLocations(task.get(index)));
        explorer.log();
    }

    public static void run(Explorer explorerToBeRun, List<PerturbationLocation> customLocations) {
        explorer = explorerToBeRun;
        System.out.println("Run " + explorer + " on " + CUP.getSimpleName() + " ...");
        locations = customLocations;
        explorer.initLogger();
        IntStream.range(0, numberOfTask)
                .forEach(index -> Runner.runLocations(task.get(index)));
        explorer.log();
    }

    public static void runLocations(int indexOfTask) {
        for (PerturbationLocation location : locations) {
            if (verbose)
                System.out.println(location.getLocationIndex() + " \t " + Util.getStringPerc(locations.indexOf(location), locations.size()));
            explorer.runReference(indexOfTask, location);
            explorer.run(indexOfTask, location);
            if (manager instanceof BitcoinManager)
                ((BitcoinManager) manager).initWallets();
        }
    }

    public static Object run(int indexOfTask, ExecutorService executor, Future future) throws Exception {
        Object output = (future.get(numberOfSecondsToWait, TimeUnit.SECONDS));
        future.cancel(true);
        executor.shutdownNow();
        return output;
    }

    public static Tuple runPerturbation(int indexOfTask) {
        Tuple result = new Tuple(3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = (Callable) constructorRunner.newInstance(manager.get(indexOfTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object output = (future.get(numberOfSecondsToWait, TimeUnit.SECONDS));
                outputs.add(output);
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
                if (manager instanceof BitcoinManager)
                    ((BitcoinManager) manager).initWallets();
                return result;
            }

        } catch (Exception | Error e) {
            result.set(2, 1);
            executor.shutdownNow();
            if (manager instanceof BitcoinManager)
                ((BitcoinManager) manager).initWallets();
            return result;
        }
    }

    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable,
                             OracleManager manager, float percentage, int indexPercentage, String locationType, Class<?>... inputTypes) {
        outputs.clear();
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        Runner.manager = manager;
        try {
            Runner.constructorRunner = classCallable.getConstructor(inputTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterLocation(locationType);
        int sizeOfSlice = (int) (locations.size() * percentage);
        locations = locations.subList(indexPercentage * sizeOfSlice, (indexPercentage + 1) * sizeOfSlice);
        inputType = inputTypes;
        oracle = Runner.manager.getOracle();
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     *
     * @param classUnderPerturbation
     * @param classCallable
     * @param manager
     * @param inputTypes
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable, OracleManager manager, String locationType, Class<?>... inputTypes) {
        outputs.clear();
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
        if (ParserArgs.locations.isEmpty())
            runExplorers();
        else
            run(explorers.get(0), ParserArgs.locations);
    }
}
