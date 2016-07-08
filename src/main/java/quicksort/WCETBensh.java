package quicksort;

import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 23/06/16.
 */
public class WCETBensh {

	private static final int NB_ARRAY = 100;
	private static final int SIZE_ARRAY = 4000;

	public static void main(String[] args) {
		QuickSortManager manager = new QuickSortManager(NB_ARRAY, SIZE_ARRAY);
		run(manager);
	}

	private static List<PerturbationLocation> getLocationPivot() {
		List<PerturbationLocation> locations = new ArrayList<>();
		locations.add(QuickSortInstr.__L8);
		return locations;
	}

	private static void run(QuickSortManager manager) {
		long accCount = 0L;

		for (int i = 0; i < NB_ARRAY; i++) {
			int[] task = manager.getTask(i);
			QuickSortForWCET.sort(task, 0, task.length - 1);
			QuickSortForWCET.count = 0;
			QuickSortForWCET.sort(task, 0, task.length - 1);
			accCount += QuickSortForWCET.count;
			QuickSortForWCET.count = 0;
		}
		System.out.println((double) accCount / (double) NB_ARRAY);

		final long accRef = accCount;
		accCount = 0L;

		for (int i = 0; i < NB_ARRAY; i++) {
			int[] task = manager.getTask(i);

			QuickSortForWCET.sort(task, 0, task.length - 1);
			QuickSortForWCET.count = 0;

			QuickSortForWCET.__L0.setEnactor(new RandomEnactorImpl());
			QuickSortForWCET.__L0.setPerturbator(new AddNPerturbatorImpl(1));

			QuickSortForWCET.sort(task, 0, task.length - 1);
			accCount += QuickSortForWCET.count;
			QuickSortForWCET.count = 0;

			QuickSortForWCET.__L0.setEnactor(new NeverEnactorImpl());
			QuickSortForWCET.__L0.setPerturbator(new NothingPerturbatorImpl());
		}
		System.out.println((double) accCount / (double) NB_ARRAY);
		System.out.println(String.format("%.2f", ((double) accCount / (double) accRef) * 100d));
	}


}
