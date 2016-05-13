package gui;

import experiment.Runner;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import quicksort.QuickSortCallableImpl;
import quicksort.QuickSortInstr;
import quicksort.QuickSortManager;
import quicksort.QuickSortOracle;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Main {

    private static float rnd = 0.001f;

    private static QuickSortManager manager = new QuickSortManager();

    private static QuickSortOracle oracle = new QuickSortOracle();

    private static List<PerturbationLocation> locations;

    public static void init() {
        locations = PerturbationLocationImpl.getLocationFromClass(QuickSortInstr.class)
                    .stream()
                    .filter(location -> location.getType() == "Numerical")
                    .collect(Collectors.toList());
        locations.forEach(Main::setPerturbator);
    }

    public static double runAllTask() {
        return (double) IntStream.range(0, Runner.numberOfTask).reduce(0, (acc, indexTask) -> acc + runLocations(indexTask))
                / (double)(locations.size() * Runner.numberOfTask) * 100;
    }

    private static int runLocations(int indexTask) {
        return IntStream.range(0, locations.size())
                        .reduce(0, (acc, indexLocation) ->
                                acc + runLocation(indexTask, locations.get(indexLocation))
                        );
    }

    public static int runLocation(int indexTask, PerturbationLocation location) {
        boolean success;
        location.setEnactor(new RandomEnactorImpl(rnd));
        success = run(indexTask);
        location.setEnactor(new NeverEnactorImpl());
        return success ? 1 : 0;
    }

    private static void setPerturbator(PerturbationLocation location) {
        location.setPerturbator(new AddNPerturbatorImpl(1));
    }

    private static boolean run(int indexTask) {
        boolean result = false;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = new QuickSortCallableImpl(manager.get(indexTask));
            Future future = executor.submit(instanceRunner);
            try {
                List<Integer> output = (List<Integer>)(future.get(15, TimeUnit.SECONDS));
                boolean assertion = oracle.assertPerturbation(manager.get(indexTask), output);
                if (assertion)
                    result = true ;// success
                executor.shutdownNow();
                return result;

            } catch (TimeoutException e) {
                future.cancel(true);
                System.err.println("Time out!");
                executor.shutdownNow();
                return result;
            }
        } catch (Exception | Error e) {
            executor.shutdownNow();
            return result;
        }
    }

    public static void main(String[] args) {
        Runner.numberOfTask = 20;
        init();
        long time = System.currentTimeMillis();
        System.out.println(String.format("%.2f", runAllTask())  + " % of success");
        System.err.println(System.currentTimeMillis() - time);
    }


}
