package simplex;

import experiment.Oracle;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 15/04/16.
 */
public class SimplexOracle implements Oracle<OptimizationData[], PointValuePair> {

    private static final double EPSILON = 1e-6;

    private static boolean validSolution(PointValuePair solution, List<LinearConstraint> constraints) {
        double[] vals = solution.getPoint();
        for (LinearConstraint c : constraints) {
            double[] coeffs = c.getCoefficients().toArray();
            double result = 0.0d;
            for (int i = 0; i < vals.length; i++)
                result += vals[i] * coeffs[i];
            switch (c.getRelationship()) {
                case EQ:
                    if (!Precision.equals(result, c.getValue(), EPSILON)) {
                        return false;
                    }
                    break;
                case GEQ:
                    if (Precision.compareTo(result, c.getValue(), EPSILON) < 0) {
                        return false;
                    }
                    break;
                case LEQ:
                    if (Precision.compareTo(result, c.getValue(), EPSILON) > 0) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }


    @Override
    public boolean assertPerturbation(OptimizationData[] input, PointValuePair output) {
        SimplexSolver solver = new SimplexSolver();
        double inputValue = solver.optimize(input).getValue();

        List<LinearConstraint> constraints = new ArrayList<>();
        for (OptimizationData data : input)
            if(data instanceof LinearConstraint)
                constraints.add((LinearConstraint)data);

        return Math.abs(output.getValue() - inputValue) < EPSILON &&
                validSolution(output, constraints);
    }
}
