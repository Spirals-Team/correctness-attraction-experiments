package quicksort;

import experiment.Oracle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class QuickSortOracle implements Oracle<List<Integer>, List<Integer>> {

    @Override
    public boolean assertPerturbation(List<Integer> input, List<Integer> output) {
        Iterator<Integer> it = output.iterator();

        int previousValue = it.next();

        if (!input.contains(previousValue))
            return false;
        else
            input.remove(input.indexOf(previousValue));

        while (it.hasNext()) {
            int current = it.next();
            if (current < previousValue)
                return false;
            else if (!input.contains(current))
                return false;
            else
                input.remove(input.indexOf(current));

            previousValue = current;
        }
        return input.isEmpty();
    }
}
