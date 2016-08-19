package laguerre;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.InvPerturbatorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 26/05/16.
 */
public class LaguerreManager extends ManagerImpl<double[], Double> {

	private final double EPSILON = 10e-6;

	private final int MaxEval = 1000;

	private final double bound = 10.0;

	public LaguerreManager(int nbtask, int size) {
		this(nbtask, size, 23);
	}

	public LaguerreManager(int nbtask, int size, int seed) {
		super(seed);
		super.CUP = LaguerreSolver.class;
		super.initialize(nbtask, size);
	}

	@Override
	protected double[] generateOneTask() {
		double[] task = new double[super.sizeOfTask];
		for (int i = 0; i < super.sizeOfTask; i++)
			task[i] = Math.floor(-5.0 + 10.0 * randomForGenTask.nextDouble());//TODO Should we set a bound : -5.0 <= coeff <= 5.0
		return task;
	}

	@Override
	public CallableImpl<double[], Double> getCallable(double[] input) {
		return new CallableImpl<double[], Double>(input) {
			@Override
			public Double call() throws Exception {
				PolynomialFunction f = new PolynomialFunction(input);
				LaguerreSolver solver = new LaguerreSolver();
				return solver.solve(MaxEval, f, -bound, bound);
			}
		};
	}

	@Override
	public Oracle<double[], Double> getOracle() {
		return (input, output) -> {
			double assertion = 0.0;
			for (int i = input.length - 1; i >= 0; i--)
				assertion += input[i] * Math.pow(output, i);
			return Math.floor(assertion) <= this.EPSILON;
		};
	}

	@Override
	public String getName() {
		return "laguerre";
	}

	@Override
	public String getHeader() {
		return super.indexTasks.size() + " polynomial of " + super.sizeOfTask + " degrees\ngenerated with " + seedForGenTask + " as seed\n" +
				super.locations.size() + " perturbation points\n";
	}

	@Override
	public double[] getTask(int indexOfTask) {
		if (indexOfTask >= super.tasks.size())
			return super.getTask(indexOfTask);
		double[] clone = new double[super.sizeOfTask];
		System.arraycopy(super.tasks.get(indexOfTask), 0, clone, 0, super.sizeOfTask);
		return clone;
	}

	public static void main(String[] args) throws Exception {
		int nbTask = 25;
		PerturbationLocation location = LaguerreSolver.__L73;
		location = LaguerreSolver.__L0;
		location.setPerturbator(new AddNPerturbatorImpl(1));
		location.setPerturbator(new InvPerturbatorImpl());
		LaguerreManager manager = new LaguerreManager(nbTask, 50);
		PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
		for (int t = 0; t < nbTask; t++) {
			List<Integer> failures = new ArrayList<>();
			List<Integer> errors = new ArrayList<>();
			PerturbationEngine.loggers.get("ExploreExample").logOn(location);
			manager.getCallable(manager.getTask(t)).call();
			int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(location);
			PerturbationEngine.loggers.get("ExploreExample").remove(location);
			for (int i = 0; i < nbCall; i++) {
				location.setEnactor(new NCallEnactorImpl(i));
				try {
					Double output = (Double)manager.getCallable(manager.getTask(t)).call();
					boolean assertion = manager.getOracle().assertPerturbation(manager.getTask(t), output);
					if (!assertion)
						failures.add(i);
				} catch (Exception e) {
					errors.add(i);
				}
			}
			if (!failures.isEmpty() || !errors.isEmpty()) {
				System.out.println("task " + t);
//				System.out.println(failures.size());
				System.out.println(failures + " / " + nbCall);
//				System.out.println(errors.size());
//				System.out.println(errors);
			}
		}
	}


}
