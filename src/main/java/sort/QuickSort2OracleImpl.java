package sort;

import experiment.OracleImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2OracleImpl extends OracleImpl<int[]> {

    public QuickSort2OracleImpl(int nb) {
        super(nb);
        super.path = "sort2";
    }

    public QuickSort2OracleImpl() {
        super();
        super.path = "sort2";
    }

    @Override
    public String header() {
        String header = numberOfTask + " arrays of " + sizeOfEachTask + " integers\n";
        header += "Random integer generated with " + seedForGenTask + " as seed\n";
        return header;
    }


    @Override
    protected int[] generateOneTask() {
        int [] task = new int[super.sizeOfEachTask];
        for (int i = 0 ; i < sizeOfEachTask ; i++)
            task[i] = (randomForGenTask.nextInt());
        return task;
    }

    @Override
    public boolean check(int[] perturbedValue, int index) {

        List<Integer> task = new ArrayList<>();

        for (int value : get(index))
            task.add(value);

        for (int i = 0 ; i < perturbedValue.length - 1 ; i++) {
            if (perturbedValue[i] > perturbedValue[i+1])
                return false;
            if(!task.remove(new Integer(perturbedValue[i])))
                return false;
        }
        return true;
    }

    @Override
    public int[] get(int index) {
        int[] clone = new int[sizeOfEachTask];
        for (int i = 0 ; i< sizeOfEachTask ; i ++)
            clone[i] = this.scenario.get(index)[i];
        return clone;
    }
}
