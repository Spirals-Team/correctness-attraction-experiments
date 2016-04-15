package quicksort;

import experiment.Runner;
import experiment.Util;

import java.util.List;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run QuickSort...");
        Runner.setup(QuickSort.class, QuickSortCallableImpl.class, new QuickSortOracleImpl(), List.class);
        Runner.runAllCampaign();
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        run();
    }
}
