

package quicksort;

import java.util.LinkedList;
import java.util.List;

public class QuickSort {
    public static List<Integer> sort(List<Integer> arr) {
        if (!(arr.isEmpty())) {
            Integer pivot = arr.get(0);
            List<Integer> less = new LinkedList<Integer>();
            List<Integer> pivotList = new LinkedList<Integer>();
            List<Integer> more = new LinkedList<Integer>();
            for (Integer i : arr) {
                if ((i.compareTo(pivot)) < 0)
                    less.add(i);
                else if ((i.compareTo(pivot)) > 0)
                    more.add(i);
                else
                    pivotList.add(i);
                
            }
            less = QuickSort.sort(less);
            more = QuickSort.sort(more);
            less.addAll(pivotList);
            less.addAll(more);
            return less;
        } 
        return arr;
    }
}

