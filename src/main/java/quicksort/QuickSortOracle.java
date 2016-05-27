package quicksort;

import experiment.Oracle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class QuickSortOracle implements Oracle<int[], int[]> {
//public class QuickSortOracle implements Oracle<List<Integer>, List<Integer>> {

    @Override
    public boolean assertPerturbation(int[] input, int[] output) {
        int cpt = 1;

        if (!contains(input, output[0]) || input.length != output.length)
            return false;

        for (int i = 1 ; i < output.length ; i++) {
            if (output[i-1] > output[i])
                return false;
            if (contains(input, output[i]))
                cpt++;
            else {
                return false;
            }
        }

        return input.length == cpt;
    }

    private boolean contains(int [] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value)
                return true;
        }
        return false;
    }


//    @Override
//    public boolean assertPerturbation(List<Integer> input, List<Integer> output) {
//        Iterator<Integer> it = output.iterator();
//
//        int previousValue = it.next();
//
//        if (!input.contains(previousValue))
//            return false;
//        else
//            input.remove(input.indexOf(previousValue));
//
//        while (it.hasNext()) {
//            int current = it.next();
//            if (current < previousValue)
//                return false;
//            else if (!input.contains(current))
//                    return false;
//            else
//                input.remove(input.indexOf(current));
//
//            previousValue = current;
//        }
//
//        return input.isEmpty();
//    }

}
