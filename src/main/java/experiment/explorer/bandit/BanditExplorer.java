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

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
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
//            if (this.lap % 2 == 0) {
//                System.out.println(this.outStateBandit());
//                System.exit(23);
//            }
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
                    System.err.println("FAIL");
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
            if (e.getMessage().endsWith("(Too many open files)")) {
                System.out.println(outStateBandit());
                System.exit(23);
            }
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
//                if (result[i][0][0][0].get(4) != 0) {
                writer.write(String.format(format, this.arms.get(i).getLocationIndex(), result[i][0][0][0].get(4),
                        result[i][0][0][0].get(0), result[i][0][0][0].get(1), result[i][0][0][0].get(2),
                        result[i][0][0][0].get(3),
                        result[i][0][0][0].get(4) == 0 ? "NaN" : Util.getStringPerc(result[i][0][0][0].get(0), result[i][0][0][0].total(3))) + "\n");
//                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print on the stderr the current state of the bandit to reload it.
     * This method leave the current program with 23 as exit code.
     * It means that the bandit did not  end its exploration.
     */
    String outStateBandit() {
        String outErr = "";
        /* lap */
        outErr += this.lap + " ";
        /* policy state */
        outErr += this.policyLocation.outStateAsString() + " ";
        /* budget state */
        outErr += this.budget.outStateAsString() + " ";
        /* inner logger */
        Tuple[][][][] results = this.logger.getResults();
        for (Tuple[][][] result : results)
            outErr += result[0][0][0].toString();
        return outErr;
    }

    /**
     * Build a bandit from the given String.
     *
     * @param states
     * @return
     */
    static BanditExplorer buildBanditFromString(int position, String[] states) {
        int numberOfLocations = Main.manager.getLocations().size();
        int lap = Integer.parseInt(states[position]);
        position++;
        /* Policy UCB */
        Policy policy = UCBPolicy.buildFromString(states, numberOfLocations, position);
        position += 1 + 3 * numberOfLocations;
        /* Budget */
        Budget budget = TimeBudget.buildFromString(states[position]);
        position++;
        BanditExplorer explorer = new BanditExplorer(Main.exploration, Main.manager, policy, budget);
        /* Lap */
        explorer.lap = lap;
        int sizeOfTuple = explorer.logger.getResults()[0][0][0][0].length();
        /* explorer logger */
        for (int i = 0; i < numberOfLocations; i++) {
            Tuple current = new Tuple(sizeOfTuple);
            for (int indexTuple = 0; indexTuple < sizeOfTuple; indexTuple++)
                current.set(indexTuple, Integer.parseInt(states[position + (i * sizeOfTuple) + indexTuple]));
            explorer.logger.getResults()[i][0][0][0] = current;
        }
        return explorer;
    }

    public static void main(String[] args) {
        Main.exploration = new IntegerExplorationPlusOne();
        int index = (Main.getIndexOfOption("-bandit", args) + 1);
        Main.numberOfTask = Integer.parseInt(args[index]);
        Main.buildSubject(Main.getIndexOfOption("-s", args) + 1, args);
        Main.manager.getLocations(Main.exploration.getType());
        BanditExplorer bandit = buildBanditFromString(index, args);
        bandit.run();
    }

    public static void run(String[] args) {
        int currentIndex;
        Budget budget = null;
        Policy policy = null;

        int indexSubject = Main.getIndexOfOption("-s", args);

        Main.manager.getLocations(Main.exploration.getType());

        if ((currentIndex = Main.getIndexOfOption("-budget", args)) != -1) {
            switch (args[currentIndex + 1]) {
                case "time":
                    budget = new TimeBudget(Integer.parseInt(args[currentIndex + 2]));
                    break;
                case "lap":
                    budget = new LapBudget(Integer.parseInt(args[currentIndex + 2]));
                    break;
            }
        } else
            budget = new TimeBudget(5000 * 60);

        if ((currentIndex = Main.getIndexOfOption("-policy", args)) != -1) {
            switch (args[currentIndex + 1]) {
                case "eps":
                    policy = new EpsilonGreedyPolicy(Main.manager.getLocations().size(), 0.80D);
                    break;
            }
        } else
            policy = new UCBPolicy(Main.manager.getLocations().size(), 23);

        BanditExplorer explorer = new BanditExplorer(Main.exploration, Main.manager, policy, budget);

        if (Main.getIndexOfOption("-f", args) == -1) {
            explorer.run();
        } else {
            String bandit = buildArgsFromArray(explorer.outStateBandit().split("\n"));
            long time = System.currentTimeMillis();
            int code = 23;
            while (code == 23) {
                try {
                    System.out.println(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
                    Process p = Runtime.getRuntime().exec(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
                    p.waitFor();
                    code = p.exitValue();
                    System.out.println(code);
                    System.out.println(readInput(p.getErrorStream()));
                    bandit = buildArgsFromArray(readInput(p.getInputStream()).split(" "));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() - time);
        }
        Main.manager.stop();
        System.exit(32);
    }

    private static final String CMD_EXEC_BANDIT;

    static {
        URL[] urls = ((URLClassLoader) BanditExplorer.class.getClassLoader()).getURLs();
        String classpath = urls[0].getPath();
        for (int i = 1; i < urls.length; i++)
            classpath += ":" + urls[i].getPath();
        CMD_EXEC_BANDIT = "java -cp " + classpath + " experiment.explorer.bandit.BanditExplorer ";
    }

    /**
     * Convert an array of String into a single String
     *
     * @param array
     * @return
     */
    private static String buildArgsFromArray(String[] array) {
        String args = "";
        for (String s : array)
            args += s + " ";
        return args;
    }

    /**
     * return the content of the the given input stream
     *
     * @param in
     * @return
     */
    private static String readInput(InputStream in) {
        String out = "";
        try {
            while (in.available() != 0)
                out += (char) in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

}
