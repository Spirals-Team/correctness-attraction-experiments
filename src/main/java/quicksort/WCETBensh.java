package quicksort;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

/**
 * Created by bdanglot on 23/06/16.
 */
public class WCETBensh {

	private static final int NB_ARRAY = 100;
	private static final int SIZE_ARRAY = 4000;

	public static void main(String[] args) {
		QuickSortManager manager = new QuickSortManager(NB_ARRAY, SIZE_ARRAY);
		runWCET(manager);
		System.out.println("###############################################");
		runAlea(manager);
		System.out.println("###############################################");
		runOptim(manager);
	}

	private static int[] buildOptimalCaseFromTask(int[] task) {
		return task;
	}

	private static void runOptim(QuickSortManager manager) {

	}

	private static void runWCET(QuickSortManager manager) {
		long accCountLoopP = 0L;
		long accCountLoopRef = 0L;
		long accPerturbations = 0L;
		long accCalls = 0L;

		PerturbationEngine.loggers.put("WCET", new LoggerImpl());

		for (int i = 0; i < NB_ARRAY; i++) {
			int[] task = manager.getTask(i);

			QuickSortForWCET.sort(task, 0, task.length - 1);
			QuickSortForWCET.sort(task, 0, task.length - 1);
			if (!manager.getOracle().assertPerturbation(manager.getTask(i), task))
				System.exit(-1);
			accCountLoopRef += QuickSortForWCET.countLoop;
			QuickSortForWCET.countLoop = 0;

			QuickSortForWCET.__L0.setEnactor(new RandomEnactorImpl(23, 0.05F));
			QuickSortForWCET.__L0.setPerturbator(new AddNPerturbatorImpl(1));

			PerturbationEngine.loggers.get("WCET").logOn(QuickSortForWCET.__L0);

			QuickSortForWCET.sort(task, 0, task.length - 1);
			if (!manager.getOracle().assertPerturbation(manager.getTask(i), task))
				System.exit(-1);
			accCountLoopP += QuickSortForWCET.countLoop;
			QuickSortForWCET.countLoop = 0;


			accCalls += PerturbationEngine.loggers.get("WCET").getCalls(QuickSortForWCET.__L0);
			accPerturbations += PerturbationEngine.loggers.get("WCET").getEnactions(QuickSortForWCET.__L0);
			PerturbationEngine.loggers.get("WCET").remove(QuickSortForWCET.__L0);

			QuickSortForWCET.__L0.setEnactor(new NeverEnactorImpl());
			QuickSortForWCET.__L0.setPerturbator(new NothingPerturbatorImpl());
		}

		System.out.println(Math.round((double) accCountLoopRef / (double) NB_ARRAY) + "&"
				+ Math.round((double) accCountLoopP / (double) NB_ARRAY)+ "&"
				+ Math.round((double) accCalls / (double) NB_ARRAY) + "&"
				+ Math.round((double) accPerturbations / (double) NB_ARRAY) + "&"
				+ String.format("%.2f", 100d - ((double) accCountLoopP / (double) accCountLoopRef) * 100d));

		System.out.println("Nb loop reference : " + (double) accCountLoopRef / (double) NB_ARRAY);
		System.out.println("Nb loop perturbed : " + (double) accCountLoopP / (double) NB_ARRAY);
		System.out.println("\"Speedup Loop: \"" + String.format("%.2f", 100d - ((double) accCountLoopP / (double) accCountLoopRef) * 100d));
	}

	private static void runAlea(QuickSortManager manager) {
		long accCountLoopP = 0L;
		long accCountLoopRef = 0L;
		long accPerturbations = 0L;
		long accCalls = 0L;

		PerturbationEngine.loggers.put("WCET", new LoggerImpl());

		for (int i = 0; i < NB_ARRAY; i++) {

			int[] task = manager.getTask(i);
			QuickSortForWCET.sort(task, 0, SIZE_ARRAY - 1);
			if (!manager.getOracle().assertPerturbation(manager.getTask(i), task))
				System.exit(-1);
			accCountLoopRef += QuickSortForWCET.countLoop;
			QuickSortForWCET.countLoop = 0;

			QuickSortForWCET.__L0.setEnactor(new RandomEnactorImpl(23, 0.05F));
			QuickSortForWCET.__L0.setPerturbator(new AddNPerturbatorImpl(1));

			PerturbationEngine.loggers.get("WCET").logOn(QuickSortForWCET.__L0);

			task = manager.getTask(i);
			QuickSortForWCET.sort(task, 0, SIZE_ARRAY - 1);
			if (!manager.getOracle().assertPerturbation(manager.getTask(i), task))
				System.exit(-1);
			accCountLoopP += QuickSortForWCET.countLoop;
			QuickSortForWCET.countLoop = 0;


			accCalls += PerturbationEngine.loggers.get("WCET").getCalls(QuickSortForWCET.__L0);
			accPerturbations += PerturbationEngine.loggers.get("WCET").getEnactions(QuickSortForWCET.__L0);
			PerturbationEngine.loggers.get("WCET").remove(QuickSortForWCET.__L0);

			QuickSortForWCET.__L0.setEnactor(new NeverEnactorImpl());
			QuickSortForWCET.__L0.setPerturbator(new NothingPerturbatorImpl());
		}

		System.out.println(Math.round((double) accCountLoopRef / (double) NB_ARRAY) + "&"
				+ Math.round((double) accCountLoopP / (double) NB_ARRAY)+ "&"
				+ Math.round((double) accCalls / (double) NB_ARRAY) + "&"
				+ Math.round((double) accPerturbations / (double) NB_ARRAY) + "&"
				+ String.format("%.2f", 100d - ((double) accCountLoopP / (double) accCountLoopRef) * 100d));

		System.out.println("Nb loop reference : " + (double) accCountLoopRef / (double) NB_ARRAY);
		System.out.println("Nb loop perturbed : " + (double) accCountLoopP / (double) NB_ARRAY);
		System.out.println("\"Speedup Loop: \"" + String.format("%.2f", 100d - ((double) accCountLoopP / (double) accCountLoopRef) * 100d));
	}


}
