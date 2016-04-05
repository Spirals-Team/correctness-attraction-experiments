package sort;

import experiment.Runner;

import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class Main {

    public static void main(String[] args) {
        Runner.setup(QuickSort.class, "sort", new SortOracleImpl(), List.class);
        Runner.runAllCampaign();
    }

}
