package experiment.explorer.bandit;

import experiment.*;
import experiment.Logger;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.Explorer;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.*;
import quicksort.QuickSortManager;

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

    private int lap;

    private Random random;

    private List<Integer> filter;

    public BanditExplorer(Exploration exploration, Manager manager, Policy policyLocation, Budget budget) {
        this.manager = manager;
        this.random = new Random(23);
        this.exploration = exploration;
        this.arms = this.manager.getLocations();
        this.policyLocation = policyLocation;
        this.budget = budget;
        this.lap = 0;
        this.filter = new ArrayList<>();
        this.initLogger();
    }

    @Override
    public void run() {
        this.arms.forEach(location -> location.setPerturbator(this.exploration.getPerturbators().get(0)));
        int[] nbCallRef = this.filterLocation();
        while (this.budget.shouldRun()) {
            int armSelected = this.policyLocation.selectArm();
            this.pullArm(armSelected, nbCallRef[armSelected]);
            nbCallRef = this.filterLocation();
        }
        this.log();
    }

    private int[] filterLocation() {
        this.arms.forEach(PerturbationEngine.loggers.get("filterLocation")::logOn);
        int[] nbCallRef = new int[this.arms.size()];
        this.run(this.lap);
        for (int i = 0; i < this.arms.size(); i++) {
            if (PerturbationEngine.loggers.get("filterLocation").getCalls(this.arms.get(i)) == 0)
                this.filter.add(i);
            else
                nbCallRef[i] = PerturbationEngine.loggers.get("filterLocation").getCalls(this.arms.get(i));
        }
        PerturbationEngine.loggers.get("filterLocation").reset();
        this.policyLocation.filter(filter);
        this.arms.forEach(PerturbationEngine.loggers.get("filterLocation")::logOn);
        return nbCallRef;
    }

    private void pullArm(int indexArm, int nbCallRef) {
        this.arms.get(indexArm).setEnactor(new NCallEnactorImpl(this.random.nextInt(nbCallRef + 1), this.arms.get(indexArm)));
        PerturbationEngine.loggers.get(this.name).logOn(this.arms.get(indexArm));
        Tuple result = run(this.lap);
        this.logger.log(indexArm, 0, 0, 0, result, this.name);
        PerturbationEngine.loggers.get(this.name).reset();
        this.policyLocation.update(indexArm, (int) result.get(0));
        this.lap++;
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
                else {
                    result.set(1, 1); // failures
                    this.manager.recover();
                }
                return result;
            } catch (TimeoutException e) {
                future.cancel(true);
                result.set(2, 1); // error computation time
                System.err.println("Time out!");
                executor.shutdownNow();
                this.manager.recover();
                return result;
            }
        } catch (Exception | Error e) {
            if (e.getMessage().endsWith("(Too many open files)"))
                outStateBandit();
            result.set(2, 1);
            executor.shutdownNow();
            this.manager.recover();
            return result;
        }
    }

    @Override
    public int runReference(int indexOfTask, PerturbationLocation location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initLogger() {
        this.logger = new Logger(this.manager, this.manager.getLocations().size(), 1, 1, 1);
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

            String format = "%-15s %-15s %-15s %-15s %-15s %-15s %-20s";
            writer = new FileWriter(path + "_data_graph_analysis.txt", false);

            writer.write("Bandit exploration\n");
            writer.write(this.policyLocation.toString());
            writer.write(this.budget.toString());
            writer.write(this.manager.getHeader());

            writer.write(String.format(format, "IndexLoc", "#Perturbations",
                    "#Success", "#Failure", "#Exception",
                    "#Call",
                    "%Success") + "\n");
            for (int i = 0; i < result.length; i++) {
                if (result[i][0][0][0].get(4) != 0) {
                    writer.write(String.format(format, this.arms.get(i).getLocationIndex(), result[i][0][0][0].get(4),
                            result[i][0][0][0].get(0), result[i][0][0][0].get(1), result[i][0][0][0].get(2),
                            result[i][0][0][0].get(3),
                            result[i][0][0][0].get(4) == 0 ? "NaN" : Util.getStringPerc(result[i][0][0][0].get(0), result[i][0][0][0].total(3))) + "\n");
                }
            }
            writer.close();

        } catch (IOException e) {

        }
    }

    /**
     * Print on the stderr the current state of the bandit to reload it.
     * This method leave the current program with 23 as exit code.
     * It means that the bandit did not  end its exploration.
     */
    public void outStateBandit() {
        String outErr = "";
        /* policy state */
        outErr += this.policyLocation.outStateAsString();

        /* budget state */
        outErr += this.budget.outStateAsString();

        /* lap */
        outErr += lap + "\n";

        /* inner logger */
        Tuple[][][][] results = this.logger.getResults();
        for (Tuple[][][] result : results)
            outErr += result[0][0][0].toString() + "\n";

        buildBanditFromString(outErr);

//        Main.manager.stop();
        System.exit(23);
    }

    /**
     * Build a bandit from the given String.
     *
     * @param state
     * @return
     */
    public static BanditExplorer buildBanditFromString(String state) {
        int numberOfLocations = Main.manager.getLocations().size();
        int position = 0;

        String[] states = state.split("\n");

        Policy policy;
        if (states[position].split(" ").length > 2) {
            position += 1 + numberOfLocations;
            policy = UCBPolicy.buildFromString(states, numberOfLocations);
        } else {
            position += numberOfLocations;
            policy = EpsilonGreedyPolicy.buildFromString(states, numberOfLocations);
        }

        Budget budget;
        if (states[position].split(" ").length > 1) {
            budget = LapBudget.buildFromString(states[position]);
        } else {
            budget = TimeBudget.buildFromString(states[position]);
        }
        position++;

        BanditExplorer explorer = new BanditExplorer(Main.exploration, Main.manager, policy, budget);

        explorer.lap = Integer.parseInt(states[position]);
        position++;

        for (int i = 0; i < numberOfLocations; i++) {
            String[] tupleAsStr = states[position + i].split(" ");
            Tuple current = new Tuple(tupleAsStr.length);
            for (int indexTuple = 0; indexTuple < tupleAsStr.length; indexTuple++)
                current.set(0, Integer.parseInt(tupleAsStr[indexTuple]));
            explorer.logger.getResults()[i][0][0][0].add(current);
        }

        return explorer;
    }

    public static void main(String[] args) {
        Main.manager = new QuickSortManager(1, 20);
        BanditExplorer banditExplorer = new BanditExplorer(new IntegerExplorationPlusOne(), Main.manager, new UCBPolicy(Main.manager.getLocations().size()), new TimeBudget(5000 * 60));
        banditExplorer.outStateBandit();
//        BanditExplorer bandit = buildBanditFromString(args[0]);
//        bandit.run();
//        Main.manager.stop();
//        System.exit(1);
    }

    public static void run(String[] args) {

        int currentIndex;
        Budget budget = null;
        Policy policy = null;

//        Exploration exploration = new IntegerExplorationPlusOne();
        Main.manager.getLocations(Main.exploration.getType());

        if ((currentIndex = Main.getIndexOfOption("-budget", args)) != -1) {
            switch (args[currentIndex + 1]) {
                case "time":
                    budget = new TimeBudget(Integer.parseInt(args[currentIndex + 2]));
                    break;
                case "lap":
                    budget = new LapBudget(Integer.parseInt(args[currentIndex + 2]));
                    break;
                default:
                    budget = new TimeBudget(5000 * 60);
            }
        }

        if ((currentIndex = Main.getIndexOfOption("-policy", args)) != -1) {
            switch (args[currentIndex + 1]) {
                case "eps":
                    policy = new EpsilonGreedyPolicy(Main.manager.getLocations().size(), 0.80D);
                    break;
                case "ucb":
                default:
                    policy = new UCBPolicy(Main.manager.getLocations().size(), 23);
                    break;
            }
        }

        Explorer explorer = new BanditExplorer(Main.exploration, Main.manager, policy, budget);
        explorer.run();
        Main.manager.stop();
        System.exit(1);
    }
}
