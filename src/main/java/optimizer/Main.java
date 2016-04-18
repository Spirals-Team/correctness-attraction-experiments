package optimizer;

import experiment.Runner;
import experiment.Util;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.SimplexSolver;

/**
 * Created by spirals on 15/04/16.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        if (Runner.numberOfTask  > OptimizerOracleImpl.numberOfFile)
            Runner.numberOfTask = OptimizerOracleImpl.numberOfFile;
        run();
    }

    public static void run() {
        System.out.println("Run optimizer...");
        Runner.setup(SimplexSolver.class, OptimizerCallableImpl.class, new OptimizerOracleImpl(), OptimizationData[].class);
        Runner.runAllCampaign();
    }

}
