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
import experiment.explorer.bandit.policy.RandomPolicy;
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
import java.util.Date;
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

	public static final String name = "Bandit";

	private int initLap;

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
		this.initLap = this.lap;
		do {
			int[] nbCallRef;
			while ((nbCallRef = this.filterLocation()) == null) ;
			int armSelected = this.policyLocation.selectArm();
			this.pullArm(armSelected, nbCallRef[armSelected]);
		} while (this.budget.shouldRun());
		this.exit(32);
	}

	private void exit(int code) {
		System.err.println(this.lap - initLap + " laps elapsed");
		this.log();
		if (code != 42)
			this.manager.stop();
		// System.exit(code);
	}

	private int[] filterLocation() {
		this.arms.forEach(PerturbationEngine.loggers.get("filterLocation")::logOn);
		int[] nbCallRef = new int[this.arms.size()];
		Tuple resultRunReference = this.run(this.lap);
		if (resultRunReference.get(0) != 1) {
			System.err.println("Error during the reference run");
			if (!this.budget.shouldRun())
				this.exit(32);
			this.manager.recover();
			return null;
		}

		this.filter.clear();

		for (int i = 0; i < this.arms.size(); i++) {
			if (PerturbationEngine.loggers.get("filterLocation").getCalls(this.arms.get(i)) == 0)
				this.filter.add(i);
			else
				nbCallRef[i] = PerturbationEngine.loggers.get("filterLocation").getCalls(this.arms.get(i));
		}

		PerturbationEngine.loggers.get("filterLocation").reset();
		this.policyLocation.filter(this.filter);
		System.err.println((this.arms.size() - this.filter.size()) + " : " + this.filter.size() + " : " + this.arms.size());

		if (this.arms.size() == this.filter.size()) {
			System.err.println("Error, no pp is active");
			System.err.println(new Date());
			// this.exit(42);
		}
		return nbCallRef;
	}

	private void pullArm(int indexArm, int nbCallRef) {
		this.arms.get(indexArm).setEnactor(new NCallEnactorImpl(this.random.nextInt(nbCallRef + 1), this.arms.get(indexArm)));
		PerturbationEngine.loggers.get(name).logOn(this.arms.get(indexArm));
		Tuple result = run(this.lap);
		System.err.println(result);
		if (PerturbationEngine.loggers.get(name).getEnactions(this.arms.get(indexArm)) == 1) {
			System.err.println(this.arms.get(indexArm).getLocationIndex() + " has been pulled");
			this.logger.log(indexArm, 0, 0, 0, result, name);
			this.policyLocation.update(indexArm, (int) result.get(0));
			this.lap++;
			this.arms.get(indexArm).setEnactor(new NeverEnactorImpl());
			PerturbationEngine.loggers.get(name).reset();
		}
	}

	private Tuple run(int indexOfTask) {
		Tuple result = new Tuple(3);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			Callable instanceRunner = this.manager.getCallable(this.manager.getTask(indexOfTask));
			Future future = executor.submit(instanceRunner);
			try {
				executor.shutdown();
				Object output = (future.get(Main.numberOfSecondsToWait, TimeUnit.SECONDS));
				boolean assertion = this.manager.getOracle().assertPerturbation(this.manager.getTask(indexOfTask), output);
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
				this.manager.recover();
				return result;
			}
		} catch (Exception | Error e) {
			e.printStackTrace();
			result.set(2, 1);
			this.manager.recover();
			return result;
		} finally {
			try {
				executor.awaitTermination(5, TimeUnit.SECONDS);
				executor.shutdownNow();
				executor.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException ignored) {

			}
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
		String path = "results/" + this.manager.getName() + "/" + this.exploration.getName() + "_" + name;
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
		FileWriter writer = new FileWriter(path + "_state.txt", false);
		writer.write(this.outStateBandit());
		writer.close();
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
	public static BanditExplorer buildBanditFromString(int position, String[] states, Policy policy) {
		int numberOfLocations = Main.manager.getLocations().size();
		int lap = Integer.parseInt(states[position]);
		position++;
		/* Budget */
		Budget budget = TimeBudget.buildFromString(states[position]);
		position++;
		if (policy instanceof UCBPolicy) {
			/* Policy UCB */
			policy = UCBPolicy.buildFromString(states, numberOfLocations, position);
			position += 1 + 3 * numberOfLocations;
		} else {
			/* random policy */
			policy = RandomPolicy.buildFromString(states, numberOfLocations, position);
			position += numberOfLocations;
		}
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
	 * Run called by the Main class
	 *
	 * @param args
	 */
	public static void run(String[] args) {
		Main.manager.getLocations(Main.exploration.getType());

		Budget budget = getBudgetArgs(args);
		Policy policy = getPolicyArgs(args);

		BanditExplorer explorer;

		if (Main.getIndexOfOption("-state", args) != -1) {
			explorer = buildBanditArgs(budget, policy);
		} else
			explorer = new BanditExplorer(Main.exploration, Main.manager, policy, budget);

		explorer.run();

		Main.manager.stop();
	}

	private static BanditExplorer buildBanditArgs(Budget budget, Policy policy) {
		String bandit;
		try {
			String path = "results/" + Main.manager.getName() + "/" + Main.exploration.getName() + "_" + BanditExplorer.name + "_state.txt";
//				System.err.println(path);
			BufferedReader buffer = new BufferedReader(new FileReader(path));
			bandit = buffer.lines().reduce("", (acc, l) -> acc + l);
			buffer.close();
//				System.err.println(bandit);
			String[] banditAsArray = bandit.split(" ");
			banditAsArray[1] = budget.outStateAsString();//Change the state of the budget to reallocate some times.
			bandit = Arrays.stream(banditAsArray).reduce("", (acc, cell) -> acc + cell + " ");
//				System.err.println(bandit);
			return BanditExplorer.buildBanditFromString(0, bandit.split(" "), policy);
		} catch (Exception e) {
			e.printStackTrace();
			// System.exit(-1);
		}
		return null;
	}

	private static Budget getBudgetArgs(String[] args) {
		int currentIndex;
		if ((currentIndex = Main.getIndexOfOption("-budget", args)) != -1) {
			switch (args[currentIndex + 1]) {
				case "time":
					return new TimeBudget(Integer.parseInt(args[currentIndex + 2]) * 1000 * 60);
				case "lap":
					return new LapBudget(Integer.parseInt(args[currentIndex + 2]));
				default:
					return new TimeBudget(5000 * 60);
			}
		} else
			return new TimeBudget(5000 * 60);
	}

	private static Policy getPolicyArgs(String[] args) {
		int currentIndex;
		if ((currentIndex = Main.getIndexOfOption("-policy", args)) != -1) {
			switch (args[currentIndex + 1]) {
				case "rnd":
					return new RandomPolicy(Main.manager.getLocations().size());
				case "eps":
					return new EpsilonGreedyPolicy(Main.manager.getLocations().size(), 0.80D);
				default:
					return new UCBPolicy(Main.manager.getLocations().size(), 23);
			}
		} else
			return new UCBPolicy(Main.manager.getLocations().size(), 23);
	}


	/**
	 * NOT USED ANYMORE
	 */

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
	@Deprecated
	private static void runInFork(String bandit, String[] args) {
		int indexSubject = Main.getIndexOfOption("-s", args);
		long time = System.currentTimeMillis();
		int code = 23;
		while (code == 23) {
			try {
//				System.err.println(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
				Process p = Runtime.getRuntime().exec(CMD_EXEC_BANDIT + "-s " + args[indexSubject + 1] + " -bandit " + bandit);
				new Thread(() -> {
					HelperBandit.readStream(p.getErrorStream());
				}).run();
				p.waitFor();
				code = p.exitValue();
				System.err.println(code);
				bandit = HelperBandit.readInput(p.getInputStream());
//				System.err.println(HelperBandit.readInput(p.getErrorStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println(System.currentTimeMillis() - time);
	}

	/**
	 * Main used in the fork
	 *
	 * @param args
	 */
	@Deprecated
	public static void main(String[] args) {
		Main.exploration = new IntegerExplorationPlusOne();
		int index = (Main.getIndexOfOption("-bandit", args) + 1);
		Main.numberOfTask = Integer.parseInt(args[index]);
		Main.buildSubject(Main.getIndexOfOption("-s", args) + 1, args);
		Main.manager.getLocations(Main.exploration.getType());
		BanditExplorer bandit = buildBanditFromString(index, args, null);//TODO
		bandit.run();
	}


}
