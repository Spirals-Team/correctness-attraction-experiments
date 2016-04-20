package quicksort_visualization;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class QuickSort2Manager extends OracleManager<int[]> {

    public QuickSort2Manager() {
        super();
        super.header = Runner.numberOfTask + " arrays of " + Runner.sizeOfEachTask + " integers\n";
        super.header += "Random integer generated with " + seedForGenTask + " as seed\n";
        super.path = "quicksort-visualization";
    }

    @Override
    protected int[] generateOneTask() {
        int [] task = new int[Runner.sizeOfEachTask];
        for (int i = 0 ; i < Runner.sizeOfEachTask ; i++)
            task[i] = (randomForGenTask.nextInt());
        return task;
    }

    @Override
    public int[] get(int index) {
        int[] clone = new int[Runner.sizeOfEachTask];
        for (int i = 0 ; i< Runner.sizeOfEachTask ; i ++)
            clone[i] = this.scenario.get(index)[i];
        return clone;
    }

    @Override
    public Oracle<int[], ?> getOracle() {
        return new QuickSort2Oracle();
    }
}
