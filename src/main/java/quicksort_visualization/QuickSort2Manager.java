package quicksort_visualization;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.OracleManagerImpl;
import quicksort.QuickSortInstr;

/**
 * Created by spirals on 19/04/16.
 */
public class QuickSort2Manager extends ManagerImpl<int[],int[]> {

    public QuickSort2Manager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public QuickSort2Manager(int nbTask, int sizeTask, int seed){
        super(seed);
        super.CUP = QuickSort2.class;
        super.initialize(nbTask, sizeTask);
    }
    @Override
    protected int[] generateOneTask() {
        int[] task = new int[super.sizeOfTask];
        for (int i = 0; i < super.sizeOfTask; i++)
            task[i] = (randomForGenTask.nextInt());
        return task;
    }

    @Override
    public int[] getTask(int index) {
        int[] clone = new int[super.sizeOfTask];
        for (int i = 0; i < super.sizeOfTask; i++)
            clone[i] = super.tasks.get(index)[i];
        return clone;
    }

    @Override
    public Oracle<int[], int[]> getOracle() {
        return new QuickSort2Oracle();
    }

    @Override
    public CallableImpl<int[], int[]> getCallable(int[] input) {
        return new QuickSort2CallableImpl(input);
    }

    @Override
    public String getName() {
        return "quicksort-visualization";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " arrays of " + super.sizeOfTask + " integers\n" +
                "Random integer generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
    }
}
