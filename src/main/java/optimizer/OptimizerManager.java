package optimizer;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class OptimizerManager extends OracleManagerImpl<OptimizationData[]> {

    private static List<String> pathToFileOfLinearProgram = new ArrayList<>();

    private static String PATH_DIRECTORY_DATASET = "resources/optimizer/";

    static {
        File directory = new File(PATH_DIRECTORY_DATASET);
        for (File data : directory.listFiles()) {
            pathToFileOfLinearProgram.add(data.getName());
        }
    }

    public static int numberOfFile = pathToFileOfLinearProgram.size();

    public OptimizerManager(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " linear problems to be resolved\n";
        super.header += "linear problem come from test of commons.math lib\n";
        super.path = "optimizer";
    }

    public OptimizerManager() {
        super();
        super.header = Runner.numberOfTask + " linear problems to be resolved\n";
        super.header += "linear problem come from test of commons.math lib\n";
        super.path = "optimizer";
    }

    protected OptimizationData[] generateOneTaskOLD() {
        return MPSParser.run(PATH_DIRECTORY_DATASET + pathToFileOfLinearProgram.get(scenario.size()));
    }

    @Override
    protected OptimizationData[] generateOneTask() {
        int nbVariable = (int)(2+Runner.sizeOfEachTask*0.1f) + randomForGenTask.nextInt((int)(5+Runner.sizeOfEachTask*0.05f));

        int upperBound = 5+(int)(Runner.sizeOfEachTask*0.2f);
        int downBound = -2+(int)(Runner.sizeOfEachTask*0.1f);

        double[] rateObjectiveFunction = new double[nbVariable];
        for (int i = 0; i < nbVariable; i++)
            rateObjectiveFunction[i] = downBound + randomForGenTask.nextInt(upperBound);
        LinearObjectiveFunction f = new LinearObjectiveFunction(rateObjectiveFunction, 0);

        List<LinearConstraint> constraints = new ArrayList<>();

        for (int i = 0; i < nbVariable; i++) {
            double[] rate = new double[nbVariable];
            for (int j = 0; j < nbVariable; j++) {
                if (i != j)
                    rate[j] = ( downBound / 2 ) + randomForGenTask.nextInt(upperBound);
            }
            int relation = -1 + randomForGenTask.nextInt(3);
            Relationship relationship = relation == 0 ? Relationship.EQ : relation == 1 ? Relationship.GEQ : Relationship.LEQ;
            constraints.add(new LinearConstraint(rate, relationship, ( downBound / 2 ) + randomForGenTask.nextInt(upperBound)));
        }

        return new OptimizationData[]{f, new LinearConstraintSet(constraints),
                randomForGenTask.nextBoolean() ? GoalType.MAXIMIZE : GoalType.MINIMIZE,
                new NonNegativeConstraint(true),
                PivotSelectionRule.BLAND};
    }


    @Override
    public OptimizationData[] get(int index) {
        return super.scenario.get(index);
    }

    @Override
    public Oracle<OptimizationData[], ?> getOracle() {
        return new OptimizerOracle();
    }
}
