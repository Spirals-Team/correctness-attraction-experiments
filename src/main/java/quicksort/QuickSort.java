package quicksort;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

/**
 * Created by bdanglot on 26/05/16.
 */
public class QuickSort {

    static {
        QuickSort.initPerturbationLocation0();
    }

    public static PerturbationLocation __L0;

    public static PerturbationLocation __L1;

    static int rcount = 0;

    static int count = 0;

    public static void r_sort(int[] array, int beg, int end) {

        rcount++;

        int left = beg, right = end;
//        int pivot = array[beg + ((end - beg) / 2)];
        int pivot = PerturbationEngine.pint(__L1, array[PerturbationEngine.pint(__L0, beg)]);

        while (left <= right) {

            while (array[left] < pivot) {
                left++;
            }

            while (array[right] > pivot) {
                right--;
            }

            if (left <= right) {
                swap(array, left, right);
                left++;
                right--;
            }

        }
        if (beg < right)
            r_sort(array, beg, right);
        if (end > left)
            r_sort(array, left, end);
    }

    public static void sort(int[] array, int beg, int end) {

        count++;

        int left = beg, right = end;
//        int pivot = array[beg + ((end - beg) / 2)];
        int pivot = array[beg];

        while (left <= right) {

            while (array[left] < pivot) {
                left++;
            }

            while (array[right] > pivot) {
                right--;
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
