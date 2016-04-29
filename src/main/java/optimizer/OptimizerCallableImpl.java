package optimizer;

import experiment.CallableImpl;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.SimplexSolverInstr;

/**
 * Created by spirals on 15/04/16.
 */
public class OptimizerCallableImpl extends CallableImpl<OptimizationData[], PointValuePair> {

    public OptimizerCallableImpl(OptimizationData[] input) {
        super(input);
    }

    @Override
    public PointValuePair call() throws Exception {
        SimplexSolverInstr solver = new SimplexSolverInstr();
        return solver.optimize(input);
    }
}
