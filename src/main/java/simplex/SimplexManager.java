package simplex;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.OracleManagerImpl;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class SimplexManager extends ManagerImpl<OptimizationData[], PointValuePair> {

    private static List<String> pathToFileOfLinearProgram = new ArrayList<>();

    private static String PATH_DIRECTORY_DATASET = "resources/simplex/";

    static {
        File directory = new File(PATH_DIRECTORY_DATASET);
        for (File data : directory.listFiles()) {
            pathToFileOfLinearProgram.add(data.getName());
        }
    }

    public static int numberOfFile = pathToFileOfLinearProgram.size();

    public SimplexManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public SimplexManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = SimplexSolverInstr.class;
        super.initialize(numberOfTask, size);
    }

    protected OptimizationData[] generateOneTaskOLD() {
        return MPSParser.run(PATH_DIRECTORY_DATASET + pathToFileOfLinearProgram.get(super.tasks.size()));
    }

    @Override
    protected OptimizationData[] generateOneTask() {
        OptimizationData[] datas;

        int nbVariable = (int) (2 + super.sizeOfTask * 0.1f) + randomForGenTask.nextInt((int) (5 + super.sizeOfTask * 0.05f));

        int upperBound = 5 + (int) (super.sizeOfTask * 0.2f);
        int downBound = -2 + (int) (super.sizeOfTask * 0.1f);

        double[] rateObjectiveFunction = new double[nbVariable];

        int cpt = 0;

        long time = System.currentTimeMillis();

        do {

            for (int i = 0; i < nbVariable; i++)
                rateObjectiveFunction[i] = downBound + randomForGenTask.nextInt(upperBound);
            LinearObjectiveFunction f = new LinearObjectiveFunction(rateObjectiveFunction, 0);

            List<LinearConstraint> constraints = new ArrayList<>();

            for (int i = 0; i < nbVariable; i++) {
                double[] rate = new double[nbVariable];
                for (int j = 0; j < nbVariable; j++) {
                    if (i != j)
                        rate[j] = (downBound / 2) + randomForGenTask.nextInt(upperBound);
                }
                int relation = -1 + randomForGenTask.nextInt(3);
                Relationship relationship = relation == 0 ? Relationship.EQ : relation == 1 ? Relationship.GEQ : Relationship.LEQ;
                constraints.add(new LinearConstraint(rate, relationship, (downBound / 2) + randomForGenTask.nextInt(upperBound)));
            }


            datas = new OptimizationData[]{f, new LinearConstraintSet(constraints),
                    randomForGenTask.nextBoolean() ? GoalType.MAXIMIZE : GoalType.MINIMIZE,
                    new NonNegativeConstraint(true),
                    PivotSelectionRule.BLAND};

            cpt++;

        } while (!isOk(datas));

        System.err.println(System.currentTimeMillis() - time);

        System.err.println("number of Try " + cpt);

        return datas;
    }

    private boolean isOk(OptimizationData[] datas) {
        try {
            SimplexSolverInstr solver = new SimplexSolverInstr();
            solver.optimize(datas);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public OptimizationData[] getTask(int index) {
        return super.tasks.get(index);
    }

    @Override
    public Oracle<OptimizationData[], PointValuePair> getOracle() {
        return new SimplexOracle();
    }

    @Override
    public CallableImpl<OptimizationData[], PointValuePair> getCallable(OptimizationData[] input) {
        return new SimplexCallableImpl(input);
    }

    @Override
    public String getName() {
        return "simplex";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " linear problems to be resolved\n" +
                "linear problem are generated randomly\n" +
                super.locations.size() + " perturbations points\n";
    }
}
