package experiment.explorer.bandit;

import experiment.Logger;
import experiment.Main;
import experiment.Manager;
import experiment.Tuple;
import experiment.Util;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.Explorer;
import experiment.explorer.bandit.budget.Budget;
import experiment.explorer.bandit.budget.LapBudget;
import experiment.explorer.bandit.budget.TimeBudget;
import experiment.explorer.bandit.policy.EpsilonGreedyPolicy;
import experiment.explorer.bandit.policy.Policy;
import experiment.explorer.bandit.policy.UCBPolicy;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static experiment.explorer.bandit.HelperBandit.CMD_EXEC_BANDIT;

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
		if (PerturbationEngine.loggers.get(this.name).getEnactions(this.arms.get(indexArm)) > 1)
			System.out.println("PERTURBED MORE THAN ONE TIME");
		if (PerturbationEngine.loggers.get(this.name).getEnactions(this.arms.get(indexArm)) == 1 && result.total() != 0) {
			this.logger.log(indexArm, 0, 0, 0, result, this.name);
			this.policyLocation.update(indexArm, (int) result.get(0));
			this.lap++;
		}
		this.arms.get(indexArm).setEnactor(new NeverEnactorImpl());
		PerturbationEngine.loggers.get(this.name).reset();
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
//                    System.err.println("FAIL");
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
			if (e.getMessage() != null && e.getMessage().endsWith("(Too many open files)")) {
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
				if (result[i][0][0][0].get(4) != 0) {
					writer.write(String.format(format, this.arms.get(i).getLocationIndex(), result[i][0][0][0].get(4),
							result[i][0][0][0].get(0), result[i][0][0][0].get(1), result[i][0][0][0].get(2),
							result[i][0][0][0].get(3),
							result[i][0][0][0].get(4) == 0 ? "NaN" : Util.getStringPerc(result[i][0][0][0].get(0), result[i][0][0][0].total(3))) + "\n");
				}
			}
			writer.close();

			logStateBandit(path);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logStateBandit(String path) throws IOException {
		if (this.budget instanceof TimeBudget && this.policyLocation instanceof UCBPolicy) {
			FileWriter writer = new FileWriter(path + "_state.txt", false);
			writer.write(this.outStateBandit());
			writer.close();
		}
	}

	/**
	 * this method return a String of the state of the bandit exploration
	 */
	public String outStateBandit() {
		String outErr = "";
		/* lap */
		outErr += this.lap + " ";
		/* budget state */
		outErr += this.budget.outStateAsString() + " ";
		/* policy state */
		outErr += this.policyLocation.outStateAsString() + " ";
        /* inner logger */
		Tuple[][][][] results = this.logger.getResults();
		for (Tuple[][][] result : results)
			outErr += result[0][0][0].toString();
		return outErr;
	}

	/**
	 * Build a bandit from the given String.
	 */
	public static BanditExplorer buildBanditFromString(int position, String[] states) {
		int numberOfLocations = Main.manager.getLocations().size();
		int lap = Integer.parseInt(states[position]);
		position++;
		/* Budget */
		Budget budget = TimeBudget.buildFromString(states[position]);
		position++;
        /* Policy UCB */
		Policy policy = UCBPolicy.buildFromString(states, numberOfLocations, position);
		position += 1 + 3 * numberOfLocations;
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

	/**
	 * Main used in the fork
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Main.exploration = new IntegerExplorationPlusOne();
		int index = (Main.getIndexOfOption("-bandit", args) + 1);
		Main.numberOfTask = Integer.parseInt(args[index]);
		Main.buildSubject(Main.getIndexOfOption("-s", args) + 1, args);
		Main.manager.getLocations(Main.exploration.getType());
		BanditExplorer bandit = buildBanditFromString(index, args);
		bandit.run();
	}

	/**
	 * Run called by the Main class
	 *
	 * @param args
	 */
	public static void run(String[] args) {
		int currentIndex;

		Budget budget = null;
		Policy policy = null;

		Main.manager.getLocations(Main.exploration.getType());

		if ((currentIndex = Main.getIndexOfOption("-budget", args)) != -1) {
			switch (args[currentIndex + 1]) {
				case "time":
					budget = new TimeBudget(Integer.parseInt(args[currentIndex + 2]) * 1000 * 60);
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
		String bandit = explorer.outStateBandit();

		if ((currentIndex = Main.getIndexOfOption("-state", args)) != -1) {
			try {
				String path = "results/" + Main.manager.getName() + "/" + Main.exploration.getName() + "_" + explorer.name + "_state.txt";
				System.out.println(path);
				BufferedReader buffer = new BufferedReader(new FileReader(path));
				bandit = buffer.lines().reduce("", (acc, l) -> acc + l);
				buffer.close();
				System.out.println(bandit);
				String[] banditAsArray = bandit.split(" ");
				banditAsArray[1] = budget.outStateAsString();//Change the state of the budget to reallocate some times.
				bandit = Arrays.stream(banditAsArray).reduce("", (acc, cell) -> acc + cell + " ");
				System.out.println(bandit);
				explorer = BanditExplorer.buildBanditFromString(0, bandit.split(" "));
			} catch (Exception e) {
				System.exit(-23);
			}
		}

		if (Main.getIndexOfOption("-f", args) == -1)
			explorer.run();
		else
			runInFork(bandit, args);

		Main.manager.stop();
		System.exit(32);
	}

	/**
	 * This method will run the bandit exploration in a new processes java in order to allow the system to
	 * clear the number of fd opened by java.
	 * <p>
	 * This works only with UCBPolicy and TimeBudget.
	 * <p>
	 * JVM called will gives the state of the bandit exploration on his std out in order to keep it.
	 *
	 * @param bandit
	 * @param args
	 */
	private static void runInFork(String bandit, String[] args) {
		int indexSubject = Main.getIndexOfOption("-s", args);
		long time = System.currentTimeMillis();
		int code = 23;
		while (code == 23) {
			try {
				System.out.println(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
				Process p = Runtime.getRuntime().exec(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
				p.waitFor();
				code = p.exitValue();
				System.out.println(code);
				System.out.println(HelperBandit.readInput(p.getErrorStream()));
				bandit = HelperBandit.readInput(p.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() - time);
	}


}
