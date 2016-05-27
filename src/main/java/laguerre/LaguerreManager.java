package laguerre;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;

/**
 * Created by bdanglot on 26/05/16.
 */
public class LaguerreManager extends ManagerImpl<double[], Double> {

    private final double EPSILON = 10e-6;

    private final int MaxEval = 1000;

    private final double bound = 10.0;

    public LaguerreManager(int nbtask, int size) {
        this(nbtask,size,23);
    }

    public LaguerreManager(int nbtask, int size, int seed) {
        super(seed);
        super.CUP = LaguerreSolver.class;
        super.initialize(nbtask,size);
    }

    @Override
    protected double[] generateOneTask() {
        double [] coef = new double[super.sizeOfTask];
        for (int i = 0 ; i < super.sizeOfTask ; i++)
            coef[i] = -5.0 + 10.0* randomForGenTask.nextDouble();//TODO Should we set a bound?
        return coef;
    }

    @Override
    public CallableImpl<double[], Double> getCallable(double[] input) {
        return new CallableImpl<double[], Double>(input) {
            @Override
            public Double call() throws Exception {
                PolynomialFunction f = new PolynomialFunction(input);
                LaguerreSolver solver = new LaguerreSolver();
                return solver.solve(MaxEval, f, -bound , bound);
            }
        };
    }

    @Override
    public Oracle<double[], Double> getOracle() {
        return (input, output) -> {
            double assertion =  0.0;
            for (int i = input.length -1 ; i >= 0 ; i--) {
                assertion += input[i] * Math.pow(output, i);
            }
            return Math.abs(assertion) < EPSILON;
        };
    }

    @Override
    public String getName() {
        return "laguerre";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " polynomial of " + super.sizeOfTask + " degrees\ngenerated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public double[] getTask(int indexOfTask) {
        double [] clone = new double[super.sizeOfTask];
        System.arraycopy(super.tasks.get(indexOfTask), 0, clone, 0, super.sizeOfTask);
        return clone;
    }
}
