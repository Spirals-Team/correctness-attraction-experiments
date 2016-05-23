package gui;

import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
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
public class Model {

    private float rnd;

    private QuickSortManager manager;

    private QuickSortOracle oracle;

    private List<PerturbationLocation> locations;

    private float percentageOfSuccess;

    private int numberOfTask;
    private int size;

    public Model() {
        this.size = 100;
        this.numberOfTask = 40;
        this.manager = new QuickSortManager(numberOfTask, size);
        oracle = new QuickSortOracle();
        locations = PerturbationLocationImpl.getLocationFromClass(QuickSortInstr.class)
                .stream()
                .filter(location -> location.getType().equals("Numerical"))
                .collect(Collectors.toList());
        locations.forEach(location -> location.setPerturbator(new AddNPerturbatorImpl(1)));
    }

    public void setRnd(float rnd) {
        this.rnd = rnd;
        this.percentageOfSuccess = this.runAllTask();
    }


    public float runAllTask() {
        return (float) IntStream.range(0, numberOfTask).reduce(0, (acc, indexTask) -> acc + runLocations(indexTask))
                / (float)(locations.size() * numberOfTask) * 100;
    }

    private int runLocations(int indexTask) {
        return IntStream.range(0, locations.size())
                        .reduce(0, (acc, indexLocation) ->
                                acc + runLocation(indexTask, locations.get(indexLocation))
                        );
    }

    private int runLocation(int indexTask, PerturbationLocation location) {
        boolean success;
        location.setEnactor(new RandomEnactorImpl(rnd));
        success = runPerturbation(indexTask);
        location.setEnactor(new NeverEnactorImpl());
        return success ? 1 : 0;
    }

    private boolean runPerturbation(int indexTask) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = manager.getCallable(manager.getTask(indexTask));
            Future future = executor.submit(instanceRunner);
            try {
                List<Integer> output = (List<Integer>)(future.get(15, TimeUnit.SECONDS));
                boolean assertion = oracle.assertPerturbation(manager.getTask(indexTask), output);
                executor.shutdownNow();
                return assertion;
            } catch (TimeoutException e) {
                future.cancel(true);
                System.err.println("Time out!");
                executor.shutdownNow();
                return false;
            }
        } catch (Exception | Error e) {
            executor.shutdownNow();
            return false;
        }
    }

}
