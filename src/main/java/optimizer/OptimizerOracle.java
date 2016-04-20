package optimizer;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.util.Precision;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 15/04/16.
 */
public class OptimizerOracle implements Oracle<OptimizationData[], PointValuePair> {

    private static List<String> pathToFileOfLinearProgram = new ArrayList<>();

    private static String PATH_DIRECTORY_DATASET = "resources/optimizer/";

    static {
        File directory = new File(PATH_DIRECTORY_DATASET);
        for (File data : directory.listFiles()) {
            pathToFileOfLinearProgram.add(data.getName());
        }
    }

    public static int numberOfFile = pathToFileOfLinearProgram.size();

    private List<List<LinearConstraint>> staticListOfListOfConstraints = new ArrayList<>();

    private List<Double> valuesOfOptimization;

    private static final double EPSILON = 1e-6;

    public OptimizerOracle() {
        super();
        super.path = "optimizer";
        this.valuesOfOptimization = new ArrayList<>();
        for (OptimizationData[] datas : super.scenario) {
            SimplexSolver solver = new SimplexSolver();
            this.valuesOfOptimization.add(solver.optimize(datas).getValue());
        }
        staticListOfListOfConstraints = MPSParser.listOfListOfConstraints;
    }

    @Override
    protected OptimizationData[] generateOneTask() {
        return MPSParser.run(PATH_DIRECTORY_DATASET+pathToFileOfLinearProgram.get(scenario.size()));
    }

    @Override
    public String header() {
        String header = Runner.numberOfTask + " linear problems to be resolved\n";
        header += "linear problem come from test of commons.math lib\n";
        return header;
    }

    @Override
    public boolean check(PointValuePair output, int index) {
        return Math.abs(output.getValue() - this.valuesOfOptimization.get(index)) < EPSILON &&
                validSolution(output, staticListOfListOfConstraints.get(index));
    }

    @Override
    public OptimizationData[] get(int index) {

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
