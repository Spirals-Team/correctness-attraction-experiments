package experiment;

import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.InvPerturbatorImpl;

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
    public static OracleManager manager;
    public static Class<?> CUP;// Class Under Perturbation
    public static Class<?> classCallable;
    public static Constructor constructorRunner;
    public static List<PerturbationLocation> locations;
    public static Explorer explorer;
    public static int numberOfSecondsToWait = 1;
    public static int sizeOfEachTask = 100;
    public static int numberOfTask = 20;

    public static List<String> explorers = new ArrayList<>();

    public static boolean verbose = false;

    public static void run(Explorer explorerUnderPerturbation) {
        explorer = explorerUnderPerturbation;
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


    //@TODO Add location to the signature And logger
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
                             OracleManager manager, float percentage, int indexPercentage,String locationType, Class<?>... inputTypes) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        Runner.manager = manager;
        try {
            Runner.constructorRunner = classCallable.getConstructor(inputTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLocation(locationType);
        int sizeOfSlice = (int)(locations.size() * percentage);
        locations = locations.subList(indexPercentage * sizeOfSlice, (indexPercentage+1) * sizeOfSlice);

        oracle = Runner.manager.getOracle();
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     * @param classUnderPerturbation
     * @param classCallable
     * @param manager
     * @param inputTypes
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable, OracleManager manager, String locationType, Class<?>... inputTypes) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        Runner.manager = manager;
        try {
            Runner.constructorRunner = classCallable.getConstructor(inputTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLocation(locationType);
        oracle = Runner.manager.getOracle();
    }

    public static void getLocation(String locationType) {
        locations = PerturbationLocationImpl.getLocationFromClass(CUP).stream()
                .filter(location -> location.getType().equals(locationType))
                .collect(Collectors.toList()
        );
    }

    public static void runAllCampaign() {
        System.out.println("Run AddOne Campaign...");
        run(new AddOneExplorerImpl());
        System.out.println("Run AddN Campaign...");
        run(new AddNExplorerImpl());
        System.out.println("Run IntegerAdd1RndEnactor Campaign...");
        run(new IntegerAdd1RndEnactorExplorerImpl(new AddNPerturbatorImpl(1)));
        System.out.println("Run BooleanInvRndEnactor Campaign...");
        getLocation("Boolean");
        run(new BooleanInvRndEnactorExplorerImpl());
    }

    public static void runExplorers() {
        if (explorers.isEmpty())
            runAllCampaign();
        else {
            for (String explorer : explorers) {
                switch (explorer) {
                    case "addOne":
                        System.out.println("run " + explorer+"...");
                        run(new AddOneExplorerImpl());
                        break;
                    case "addN":
                        System.out.println("run " + explorer+"...");
                        run(new AddNExplorerImpl());
                        break;
                    case "BoolCall":
                        System.out.println("run " + explorer+"...");
                        getLocation("Boolean");
                        run(new BoolInvCallExplorerImpl(new InvPerturbatorImpl()));
                        break;
                    case "IntRnd":
                        System.out.println("run " + explorer+"...");
                        run(new IntegerAdd1RndEnactorExplorerImpl(new AddNPerturbatorImpl(1)));
                        break;
                    case "BoolRnd":
                        System.out.println("run " + explorer+"...");
                        getLocation("Boolean");
                        run(new BooleanInvRndEnactorExplorerImpl());
                        break;
                    default:
                        Util.usage();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        quicksort.Main.run();
        md5.Main.run();
        mersenne.Main.run();
        optimizer.Main.run();
        sudoku.Main.run();
        zip.Main.run();
    }
}
