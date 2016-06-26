package regression;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import weka.core.matrix.LinearRegression;
import weka.core.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bdanglot on 16/06/16.
 */
public class LinearRegressionManager extends ManagerImpl<Matrix[], double[]> {

    private static final double RIDGE = 0.5D;

    private static final double EPSILON = 1.0d;

    private static final int NB_POINT = 100;

    private static final int BOUND = 10;

    private List<double[]> reference = new ArrayList<>();

    public LinearRegressionManager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public LinearRegressionManager(int nbTask, int sizeTask, int seed) {
        super(seed);
        super.CUP = LinearRegression.class;
        super.initialize(nbTask, sizeTask);
    }

    @Override
    protected Matrix[] generateOneTask() {
        double[][] x = new double[NB_POINT][super.sizeOfTask];
        double[][] y = new double[NB_POINT][1];
        double[] function = new double[super.sizeOfTask];
        for (int i = 0; i < super.sizeOfTask; i++)
            function[i] = -BOUND + ((2 * BOUND) * super.randomForGenTask.nextDouble());
        for (int i = 0; i < NB_POINT; i++)
            buildTask(x[i], y[i], function);
        Matrix a = new Matrix(x);
        Matrix b = new Matrix(y);
        this.reference.add(a.regression(b, RIDGE).getCoefficients());
        return new Matrix[]{a, b};
    }

    private void buildTask(double[] x, double[] y, double[] function) {
        for (int i = 0; i < NB_POINT; i++) {
            buildVector(x);
            y[0] = applyFunction(x, function);
            noiseVector(x);
        }
    }

    /**
     * @return a double between -2.5 and 2.5
     */
    private double noise() {
        return -2.5 + (5.0 * super.randomForGenTask.nextDouble());
    }

    /**
     * Add noise to the given vector
     *
     * @param x the vector to be noised
     */
    private void noiseVector(double[] x) {
        for (int i = 0; i < x.length; i++) {
            x[i] = x[i] + noise();
        }
    }

    /**
     * @return a vector of double with each value -25 <=  x <= 25
     */
    private void buildVector(double[] x) {
        for (int i = 0; i < x.length; i++)
            x[i] = -25 + super.randomForGenTask.nextInt(50);
    }

    /**
     * Apply the given function on a value of the given vector
     *
     * @param x
     * @param function
     * @return
     */
    private double applyFunction(double[] x, double[] function) {
        double result = 0.0D;
        for (int i = 0; i < function.length; i++) {
            result += function[i] * x[i];
        }
        return result;
    }

    @Override
    public Matrix[] getTask(int indexTask) {
        if (indexTask >= super.tasks.size())
            super.getTask(indexTask);
        return super.tasks.get(indexTask);
    }

    @Override
    public CallableImpl<Matrix[], double[]> getCallable(Matrix[] input) {
        return new CallableImpl<Matrix[], double[]>(input) {
            @Override
            public double[] call() throws Exception {
                return input[0].regression(input[1], RIDGE).getCoefficients();
            }
        };
    }

    @Override
    public Oracle<Matrix[], double[]> getOracle() {
        return (input, output) -> {
            double[] realFunction = reference.get(super.tasks.indexOf(input));
            for (int i = 0; i < realFunction.length; i++) {
                if (realFunction[i] != output[i])
                    return false;
            }
            return true;
        };
    }

    @Override
    public String getName() {
        return "regression";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " dataset of " + super.sizeOfTask + " dimensions, " + NB_POINT + " points per datasets " + -BOUND + " <= x  <=" + BOUND + " with 2.5 noise\n" +
                "Random integer generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
    }
}
