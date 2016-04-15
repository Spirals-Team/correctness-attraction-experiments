package optimizer;

import experiment.OracleImpl;
import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.PivotSelectionRule;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 15/04/16.
 */
public class OptimizerOracleImpl extends OracleImpl<OptimizationData[], PointValuePair> {

    private static List<List<LinearConstraint>> staticListOfListOfConstraints = new ArrayList<>();

    private List<Double> valuesOfOptimization;

    private static final double EPSILON = 1e-6;

    public OptimizerOracleImpl() {
        super();
        super.path = "optimizer";
        this.valuesOfOptimization = new ArrayList<>();
        for (OptimizationData[] datas : super.scenario) {
            SimplexSolver solver = new SimplexSolver();
            this.valuesOfOptimization.add(solver.optimize(datas).getValue());
        }
    }

    @Override
    protected OptimizationData[] generateOneTask() {
        return buildTask(super.scenario.size());
    }

    @Override
    public String header() {
        String header = Runner.numberOfTask + " linear problems to be resolved\n";
        header += "linear problem come from test of commons.math lib\n";
        return header;
    }

    @Override
    public boolean check(PointValuePair perturbedValue, int index) {
        return Math.abs(perturbedValue.getValue() - this.valuesOfOptimization.get(index)) < EPSILON &&
                validSolution(perturbedValue, staticListOfListOfConstraints.get(index));
    }

    @Override
    public OptimizationData[] get(int index) {
        return super.scenario.get(index);
    }

    private OptimizationData[] buildTask(int indexTask) {
        LinearObjectiveFunction f;
        ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        // from http://www.math.toronto.edu/mpugh/Teaching/APM236_04/bland
        //      maximize 10 x1 - 57 x2 - 9 x3 - 24 x4
        //      subject to
        //          1/2 x1 - 11/2 x2 - 5/2 x3 + 9 x4  <= 0
        //          1/2 x1 -  3/2 x2 - 1/2 x3 +   x4  <= 0
        //              x1                  <= 1
        //      x1,x2,x3,x4 >= 0
        f = new LinearObjectiveFunction(new double[]{10, -57, -9, -24}, 0);
        constraints.add(new LinearConstraint(new double[]{0.5, -5.5, -2.5, 9}, Relationship.LEQ, 0));
        constraints.add(new LinearConstraint(new double[]{0.5, -1.5, -0.5, 1}, Relationship.LEQ, 0));
        constraints.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.LEQ, 1));
        staticListOfListOfConstraints.add(constraints);
        return new OptimizationData[]{f, new LinearConstraintSet(constraints),
                GoalType.MAXIMIZE,
                new NonNegativeConstraint(true),
                PivotSelectionRule.BLAND};
//        return MPSParser.run(indexTask);
    }

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


}
