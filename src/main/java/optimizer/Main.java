package optimizer;

import experiment.Runner;
import experiment.Util;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.SimplexSolverInstr;

/**
 * Created by spirals on 15/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run optimizer...");
        Runner.setup(SimplexSolverInstr.class, OptimizerCallableImpl.class, new OptimizerManager(), OptimizationData[].class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        if (Runner.numberOfTask  > OptimizerManager.numberOfFile)
            Runner.numberOfTask = OptimizerManager.numberOfFile;
        run();
    }

}
