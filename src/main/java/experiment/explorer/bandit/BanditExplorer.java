package experiment.explorer.bandit;

import experiment.Main;
import experiment.Manager;
import experiment.Tuple;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.Explorer;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.NothingPerturbatorImpl;
import quicksort.QuickSortManager;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by bdanglot on 06/06/16.
 */
public class BanditExplorer implements Explorer {

    private Exploration exploration;

    private List<PerturbationLocation> arms;

    private Politic politic;

    private Budget budget;

    private Manager manager;

    private RandomEnactorImpl[] enactorByArm;

    public BanditExplorer(Exploration exploration, Manager manager, Politic politic, Budget budget) {
        this.manager = manager;
        this.exploration = exploration;
        this.arms = manager.getLocations();
        this.politic = politic;
        this.budget = budget;
        this.enactorByArm = new RandomEnactorImpl[manager.getIndexTask().size()];
        for (int i = 0; i < this.enactorByArm.length; i++)
            this.enactorByArm[i] = new RandomEnactorImpl(0.3f);
    }

    @Override
    public void run() {
        while (this.budget.shouldRun()) {
            int armSelected = this.politic.selectArm();
            this.pullArm(armSelected);
            this.politic.update();
        }
        this.log();
    }

    private void pullArm(int indexArm) {
        this.arms.get(indexArm).setPerturbator(this.exploration.getPerturbators().get(0));
        this.arms.get(indexArm).setEnactor( this.enactorByArm[indexArm]);
        for (int i = 0; i < this.manager.getIndexTask().size() ; i++) {
            Tuple result = run((Integer) this.manager.getIndexTask().get(i));
            this.politic.armPulled(indexArm);
            if (result.get(0) == 1)
                this.politic.successOnArm(indexArm);
        }
        this.arms.get(indexArm).setEnactor(new NeverEnactorImpl());
        this.arms.get(indexArm).setPerturbator(new NothingPerturbatorImpl());
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

    }

    @Override
    public void initLogger() {

    }

    @Override
    public void log() {
        this.politic.log();
    }

    public static void main(String[] args) {
        Manager manager = new QuickSortManager(100,25);
        Budget budget = new TimeBudget(7200000);//2hour of budget
        Politic politic = new DecreasingEpsilonGreedyPolitic(manager.getLocations().size(), 0.9d, 23);
        Exploration exploration = new IntegerExplorationPlusOne();
        Explorer explorer = new BanditExplorer(exploration, manager, politic, budget);
        explorer.run();
    }

}
