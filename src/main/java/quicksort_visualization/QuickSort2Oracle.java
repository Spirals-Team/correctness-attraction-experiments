package quicksort_visualization;

import experiment.OracleManager;
import experiment.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2Oracle extends OracleManager<int[],int[]> {

    public QuickSort2Oracle() {
        super();
        super.path = "quicksort-visualization";
    }

    @Override
    public String header() {
        String header = Runner.numberOfTask + " arrays of " + Runner.sizeOfEachTask + " integers\n";
        header += "Random integer generated with " + seedForGenTask + " as seed\n";
        return header;
    }


    @Override
    protected int[] generateOneTask() {
        int [] task = new int[Runner.sizeOfEachTask];
        for (int i = 0 ; i < Runner.sizeOfEachTask ; i++)
            task[i] = (randomForGenTask.nextInt());
        return task;
    }

    @Override
    public boolean check(int[] output, int index) {

        List<Integer> task = new ArrayList<>();

        for (int value : get(index))
            task.add(value);

        for (int i = 0; i < output.length - 1 ; i++) {
            if (output[i] > output[i+1])
                return false;
            if(!task.remove(new Integer(output[i])))
                return false;
        }
        return true;
    }

    @Override
    public int[] get(int index) {
        int[] clone = new int[Runner.sizeOfEachTask];
        for (int i = 0 ; i< Runner.sizeOfEachTask ; i ++)
            clone[i] = this.scenario.get(index)[i];
        return clone;
    }
}
