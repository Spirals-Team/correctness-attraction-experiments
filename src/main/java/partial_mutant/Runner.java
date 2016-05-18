package partial_mutant;

import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import perturbation.enactor.AlwaysEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;
import perturbation.perturbator.Perturbator;
import quicksort.*;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 18/05/16.
 */
public class Runner {

    /**
     * On QS ATM
     */

    private static int numberOfTask = 2;

    private static Perturbator perturbator = new AddNPerturbatorImpl(1);

    private static QuickSortManager manager = new QuickSortManager();

    private static QuickSortOracle oracle = (QuickSortOracle) manager.getOracle();

    public static void run() {
        List<PerturbationLocation> locations = PerturbationLocationImpl.getLocationFromClass(QuickSortInstr.class);
        locations.forEach(Runner::run);
    }

    private static void run(PerturbationLocation location) {
        System.out.println(isPartialMutant(location));
//        if (!isPartialMutant(location)) {
//            return;
//        } else {
//            experiment.Runner.setup(QuickSortInstr.class, QuickSortCallableImpl.class, new QuickSortManager(), "Numerical", List.class);
//            experiment.Runner.locations.remove(location);
//            experiment.Runner.explorers.add(new CallExplorer(new IntegerExplorationPlusOne()));
//            manager.setPath(manager.getPath()+"_partialmutant");
//            experiment.Runner.runExplorers();
//        }
    }

    /**
     * Side effect : let the AlwaysEnactor and perturbation if the location is a partial mutant.
     * @param location
     * @return true if the location is a partial mutant, false otherwise
     */
    private static boolean isPartialMutant(PerturbationLocation location) {
        location.setEnactor(new AlwaysEnactorImpl());
        location.setPerturbator(perturbator);
        double perc = runTasks();
        if (perc > 0.0d && perc < 1.0d)
            return true;
        else {
            location.setEnactor(new NeverEnactorImpl());
            location.setPerturbator(new NothingPerturbatorImpl());
            return false;
        }
    }

    public static double runTasks() {
        int nbSuccess = (IntStream.range(0 , numberOfTask)
                .reduce(0, (acc, indexOfTask) ->
                        acc + runPerturbation(indexOfTask))
        );
        if (nbSuccess > 0) {
        System.out.println(nbSuccess);
            System.out.println(numberOfTask);
        System.out.println((double)nbSuccess  / (double) numberOfTask) ;}
        return ((double) nbSuccess / (double) numberOfTask);
    }

    private static int runPerturbation(int indexTask) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = new QuickSortCallableImpl(manager.get(indexTask));
            Future future = executor.submit(instanceRunner);
            try {
                List<Integer> output = (List<Integer>)(future.get(15, TimeUnit.SECONDS));
                boolean assertion = oracle.assertPerturbation(manager.get(indexTask), output);
                executor.shutdownNow();
                return assertion ? 1 : 0;
            } catch (TimeoutException e) {
                future.cancel(true);
                System.err.println("Time out!");
                executor.shutdownNow();
                return 0;
            }
        } catch (Exception | Error e) {
            executor.shutdownNow();
            return 0;
        }
    }

    public static void main(String[] args) {
        run();
    }


}
