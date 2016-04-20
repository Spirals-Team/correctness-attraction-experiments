package optimizer;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;
import org.apache.commons.math3.optim.OptimizationData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class OptimizerManager extends OracleManager<OptimizationData[]> {

    private static List<String> pathToFileOfLinearProgram = new ArrayList<>();

    private static String PATH_DIRECTORY_DATASET = "resources/optimizer/";

    static {
        File directory = new File(PATH_DIRECTORY_DATASET);
        for (File data : directory.listFiles()) {
            pathToFileOfLinearProgram.add(data.getName());
        }
    }

    public static int numberOfFile = pathToFileOfLinearProgram.size();

    public OptimizerManager() {
        super();
        super.header = Runner.numberOfTask + " linear problems to be resolved\n";
        super.header += "linear problem come from test of commons.math lib\n";
        super.path = "optimizer";
    }

    @Override
    protected OptimizationData[] generateOneTask() {
        return MPSParser.run(PATH_DIRECTORY_DATASET+pathToFileOfLinearProgram.get(scenario.size()));
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
