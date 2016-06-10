package experiment.explorer.bandit;

import experiment.*;
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
import rsa.RSAManager;
import torrent.TorrentManager;
import zip.ZipManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private int lap;

    private Random random;

    private List<Integer> filter;

    public BanditExplorer(Exploration exploration, Manager manager, Policy policyLocation, Budget budget) {
        this.manager = manager;
        this.random = new Random(23);
        this.exploration = exploration;
        this.arms = manager.getLocations();
        this.policyLocation = policyLocation;
        this.budget = budget;
        this.enactorByArm = new RandomEnactorImpl[manager.getIndexTask().size()];
        for (int i = 0; i < this.enactorByArm.length; i++)
            this.enactorByArm[i] = new RandomEnactorImpl(0.3f);
        this.lap = 0;
        this.filter = new ArrayList<>();
        this.initLogger();
    }

    @Override
    public void run() {
        this.arms.forEach(location -> location.setPerturbator(this.exploration.getPerturbators().get(0)));
        while (this.budget.shouldRun()) {
            int armSelected = this.policyLocation.selectArm();
            this.pullArm(armSelected);
            this.filterLocation();
        }
        this.log();
    }

    private void filterLocation() {
        for (int i = 0; i < this.arms.size(); i++) {
            if (PerturbationEngine.loggers.get("filterLocation").getCalls(this.arms.get(i)) == 0)
                this.filter.add(i);
        }
        PerturbationEngine.loggers.get("filterLocation").reset();
        this.policyLocation.filter(filter);
        this.arms.forEach(PerturbationEngine.loggers.get("filterLocation")::logOn);
    }

    private void pullArm(int indexArm) {
        int nbCallRef = this.runReference(this.lap, this.arms.get(indexArm));
        if (nbCallRef == 0) {
            this.filter.add(indexArm);
            this.policyLocation.filter(filter);//TODO The Time Budget should not take this reference run in account imo.
        } else {
            this.arms.get(indexArm).setEnactor(new NCallEnactorImpl(this.random.nextInt(nbCallRef), this.arms.get(indexArm)));
            this.policyLocation.armPulled(indexArm);
            PerturbationEngine.loggers.get(this.name).logOn(this.arms.get(indexArm));
            Tuple result = run(this.lap);
            this.logger.log(indexArm, 0, 0, 0, result, this.name);
            PerturbationEngine.loggers.get(this.name).reset();
            if (result.get(0) == 1)
                this.policyLocation.successOnArm(indexArm);
            this.lap++;
        }
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
                if (this.manager instanceof TorrentManager)
                    ((TorrentManager) this.manager).reinit();
                return result;
            }
        } catch (Exception | Error e) {
            result.set(2, 1);
            executor.shutdownNow();
            if (this.manager instanceof TorrentManager)
                ((TorrentManager) this.manager).reinit();
            return result;
        }
    }

    @Override
    public int runReference(int indexOfTask, PerturbationLocation location) {
        PerturbationEngine.loggers.get(this.name).logOn(location);
        this.run(indexOfTask);
        int currentNbCall = PerturbationEngine.loggers.get(this.name).getCalls(location);
        PerturbationEngine.loggers.get(this.name).reset();
        return currentNbCall;
    }

    @Override
    public void initLogger() {
        this.logger = new Logger(this.manager, this.manager.getLocations().size(), this.manager.getIndexTask().size(), this.exploration.getPerturbators().size());
        PerturbationEngine.loggers.put(name, new LoggerImpl());
        PerturbationEngine.loggers.put("filterLocation", new LoggerImpl());
        this.logger = new Logger(this.manager, this.manager.getLocations().size(), 1, 1);
        this.arms.forEach(PerturbationEngine.loggers.get("filterLocation")::logOn);
    }

    @Override
    public void log() {
        String path = "results/" + this.manager.getName() + "/" + this.exploration.getName() + "_" + this.name;
        Tuple[][][][] result = this.logger.getResults();
        try {
            FileWriter writer = new FileWriter(path + "_policy.txt", false);
            writer.write(this.policyLocation.log());
            writer.close();

            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s";
            writer = new FileWriter(path + "_data_graph_analysis.txt", false);

            for (int i = 0; i < result.length; i++) {
                writer.write(String.format(format, this.arms.get(i).getLocationIndex(), result[i][0][0][0].get(4),
                        result[i][0][0][0].get(0), result[i][0][0][0].get(1), result[i][0][0][0].get(2),
                        result[i][0][0][0].get(3),
                        result[i][0][0][0].get(4) == 0 ? "NaN" : Util.getStringPerc(result[i][0][0][0].get(0), result[i][0][0][0].total(3))) + "\n");
            }
            writer.close();

        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        Main.numberOfSecondsToWait = 30;
        Main.verbose = true;
        Manager manager = new QuickSortManager(100, 25);
        Budget budget = new LapBudget(1000);
        Policy policy = new UCBPolicy(manager.getLocations().size(), 23);
        Exploration exploration = new IntegerExplorationPlusOne();
        Explorer explorer = new BanditExplorer(exploration, manager, policy, budget);
        explorer.run();


    }

}
