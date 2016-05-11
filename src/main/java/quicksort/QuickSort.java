package quicksort;

import java.util.LinkedList;
import java.util.List;

/**
 * Download from http://rosettacode.org/wiki/Rosetta_Code
 */
public class QuickSort {

    public static List<Integer> sort(List<Integer> arr) {
        if (!arr.isEmpty()) {
            Integer pivot = arr.get(0);

            List<Integer> less = new LinkedList<Integer>();
            List<Integer> pivotList = new LinkedList<Integer>();
            List<Integer> more = new LinkedList<Integer>();

            // Partition
            for (Integer i : arr) {
                if (i.compareTo(pivot) < 0)
                    less.add(i);
                else if (i.compareTo(pivot) > 0)
                    more.add(i);
                else
                    pivotList.add(i);
            }

            // Recursively sort sublists
            less = sort(less);
            more = sort(more);

            // Concatenate results
            less.addAll(pivotList);
            less.addAll(more);
            return less;
        }
        return arr;

    }


}