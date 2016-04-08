package sort;

import experiment.CallableImpl;
import experiment.Runner;

import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class QuickSortCallableImpl extends CallableImpl<List<Integer>> {

    public QuickSortCallableImpl(List<Integer> originalValue) {
        super(originalValue);
    }

    @Override
    public List<Integer> call() throws Exception {
        return QuickSort.sort(super.originalValue);
    }

    @Deprecated
    public static void main(String[] args) {
        Runner.setup(QuickSort.class, QuickSortCallableImpl.class ,"sort", new SortOracleImpl(), List.class);
        Runner.runAllCampaign();
    }
}
