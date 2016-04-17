package optimizer;

import experiment.CallableImpl;
import experiment.Oracle;
import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.SimplexSolver;

/**
 * Created by spirals on 15/04/16.
 */
public class OptimizerCallableImpl extends CallableImpl<OptimizationData[], PointValuePair> {

    public OptimizerCallableImpl(OptimizationData[] originalValue) {
        super(originalValue);
    }

    @Override
    public PointValuePair call() throws Exception {
        SimplexSolver solver = new SimplexSolver();
        return solver.optimize(originalValue);
    }
}
