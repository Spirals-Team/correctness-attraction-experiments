package quicksort;

import experiment.CallableImpl;

import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class QuickSortCallableImpl extends CallableImpl<List<Integer>,List<Integer>> {

    public QuickSortCallableImpl(List<Integer> input) {
        super(input);
    }

    @Override
    public List<Integer> call() throws Exception {
        return QuickSortInstr.sort(super.input);
    }
}
