package optimizer;

import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.SimplexSolver;

/**
 * Created by spirals on 15/04/16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Run optimizer...");
        Runner.verbose = true;
        Runner.numberOfTask = 1;
        Runner.setup(SimplexSolver.class, OptimizerCallableImpl.class, new OptimizerOracleImpl(), OptimizationData[].class);
        Runner.runAllCampaign();
    }

}
