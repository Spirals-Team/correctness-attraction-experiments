package quicksort;

import experiment.Runner;
import experiment.Util;
import experiment.exploration.IntegerExplorationPlusMagnitude;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import experiment.explorer.RandomExplorer;

import java.util.List;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run QuickSort...");
        Runner.setup(QuickSortInstr.class, QuickSortCallableImpl.class, new QuickSortManager(),"Numerical", List.class);
        Runner.run(new CallExplorer(new IntegerExplorationPlusOne()));
        Runner.run(new CallExplorer(new IntegerExplorationPlusMagnitude()));
        Runner.run(new RandomExplorer(new IntegerExplorationPlusOne()));
        Runner.run(new RandomExplorer(new IntegerExplorationPlusMagnitude()));
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        Runner.verbose = true;
        run();
    }
}
