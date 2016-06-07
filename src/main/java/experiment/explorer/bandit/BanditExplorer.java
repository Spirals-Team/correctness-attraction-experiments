package experiment.explorer.bandit;

import experiment.Logger;
import experiment.Main;
import experiment.Manager;
import experiment.Tuple;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.Explorer;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import quicksort.QuickSortManager;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by bdanglot on 06/06/16.
 */
public class BanditExplorer implements Explorer {

    private final String name = "Bandit";

    private Logger logger;

    private Exploration exploration;

    private List<PerturbationLocation> arms;

    private Policy policyLocation;

    private Budget budget;

    private Manager manager;

    private RandomEnactorImpl[] enactorByArm;

    private int[][] nbCallReferencePerLocationPerTask;

    public BanditExplorer(Exploration exploration, Manager manager, Policy policyLocation, Budget budget) {
        this.manager = manager;
        this.exploration = exploration;
        this.arms = manager.getLocations();
        this.policyLocation = policyLocation;
        this.budget = budget;
        this.enactorByArm = new RandomEnactorImpl[manager.getIndexTask().size()];
        for (int i = 0; i < this.enactorByArm.length; i++)
            this.enactorByArm[i] = new RandomEnactorImpl(0.3f);
        this.nbCallReferencePerLocationPerTask = new int[this.arms.size()][this.manager.getIndexTask().size()];
    }

    @Override
    public void run() {
        //Getting the bound of call of references
        for (int location = 0; location < this.arms.size(); location++) {
            int max = Integer.MIN_VALUE;
            for (int task = 0; task < this.manager.getIndexTask().size(); task++) {
                this.runReference(task, this.arms.get(location));
                if (this.nbCallReferencePerLocationPerTask[location][task] > max)
                    max = this.nbCallReferencePerLocationPerTask[location][task];
            }
            this.arms.get(location).setPerturbator(this.exploration.getPerturbators().get(0));
        }

        while (this.budget.shouldRun()) {
            int armSelected = this.policyLocation.selectArm();
            this.pullArm(armSelected);
        }
        this.log();
    }

    private void pullArm(int indexArm) {

    }

    private Tuple run(int indexOfTask) {
        Tuple result = new Tuple(3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = this.manager.getCallable(this.manager.getTask(indexOfTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object output = (future.get(Main.numberOfSecondsToWait, TimeUnit.SECONDS));
                boolean assertion = this.manager.getOracle().assertPerturbation(this.manager.getTask(indexOfTask), output);
                executor.shutdownNow();
                if (assertion)
                    result.set(0, 1); // success
                else
                    result.set(1, 1); // failures
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

    @Override
    public void runReference(int indexOfTask, PerturbationLocation location) {
        PerturbationEngine.loggers.get(this.name).logOn(location);
        this.run(indexOfTask);
        int currentNbCall = PerturbationEngine.loggers.get(this.name).getCalls(location);
        this.nbCallReferencePerLocationPerTask[this.manager.getLocations().indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.loggers.get(this.name).reset();
    }

    @Override
    public void initLogger() {
        this.logger = new Logger(this.manager, this.manager.getLocations().size(), this.manager.getIndexTask().size(), this.exploration.getPerturbators().size());
        PerturbationEngine.loggers.put(name, new LoggerImpl());
    }

    @Override
    public void log() {
        this.policyLocation.log();
    }

    public static void main(String[] args) {
        Manager manager = new QuickSortManager(100, 25);
        Budget budget = new LapBudget(manager.getLocations().size() * 2);
        Policy policy = new UCBPolicy(manager.getLocations().size(), 23);
        Exploration exploration = new IntegerExplorationPlusOne();
        Explorer explorer = new BanditExplorer(exploration, manager, policy, budget);
        explorer.run();
    }

}
