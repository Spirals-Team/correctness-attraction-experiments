package quicksort;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

/**
 * Created by bdanglot on 07/07/16.
 */
public class QuickSortForWCET {

	static {
		QuickSortForWCET.initPerturbationLocation0();
	}

	public static PerturbationLocation __L0;

	public static PerturbationLocation __L1;

	static long count = 0;

	public static void sort(int[] array, int beg, int end) {

		int left = beg, right = end;
		int pivot = PerturbationEngine.pint(__L1, array[PerturbationEngine.pint(__L0, beg)]);

		while (left <= right) {

			while (array[left] < pivot) {
				left++;
				count++;
			}

			while (array[right] > pivot) {
				right--;
				count++;
			}

			if (left <= right) {
				swap(array, left, right);
				left++;
				right--;
			}

		}

		if (beg < right)
			sort(array, beg, right);
		if (end > left)
			sort(array, left, end);
	}

	private static void swap(int[] array, int i, int j) {
		int x = array[i];
		array[i] = array[j];
		array[j] = x;
	}

	static private void initPerturbationLocation0() {
		__L0 = new PerturbationLocationImpl("QuickSort.java:9", 0, "Numerical");
		__L1 = new PerturbationLocationImpl("QuickSort.java:9", 0, "Numerical");
	}
}
