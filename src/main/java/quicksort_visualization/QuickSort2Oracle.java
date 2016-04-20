package quicksort_visualization;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2Oracle implements Oracle<int[],int[]> {

    @Override
    public boolean assertPerturbation(int[] input, int[] output) {
        List<Integer> task = new ArrayList<>();

        for (int value : input)
            task.add(value);

        for (int i = 0; i < output.length - 1 ; i++) {
            if (output[i] > output[i+1])
                return false;
            if(!task.remove(new Integer(output[i])))
                return false;
        }
        return true;
    }
}
