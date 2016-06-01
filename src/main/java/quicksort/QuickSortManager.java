package quicksort;

import experiment.CallableImpl;
import experiment.Manager;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 21/05/16.
 */
public class QuickSortManager extends ManagerImpl<int[], int[]> {

    public QuickSortManager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public QuickSortManager(int nbTask, int sizeTask, int seed){
        super(seed);
        super.CUP = QuickSortInstr.class;
        super.initialize(nbTask, sizeTask);
    }

    @Override
    protected int[] generateOneTask() {
        int [] task = new int[super.sizeOfTask];
        for (int i = 0; i < super.sizeOfTask ; i++)
            task[i] = randomForGenTask.nextInt();
        return task;
    }

    @Override
    public int[] getTask(int indexTask) {
        int [] clone = new int[super.sizeOfTask];
        System.arraycopy(super.tasks.get(indexTask), 0, clone, 0, super.sizeOfTask);
        return clone;
    }

    @Override
    public CallableImpl<int[], int[]> getCallable(int[] input) {
        return new CallableImpl<int[], int[]>(input) {
            @Override
            public int[] call() throws Exception {
                QuickSortInstr.sort(this.input, 0, this.input.length-1);
                return this.input;
            }
        };
    }

    @Override
    public Oracle<int[], int[]> getOracle() {
        return new QuickSortOracle();
    }

    @Override
    public String getName() {
        return "quicksort";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " arrays of " + super.sizeOfTask + " integers\n" +
                "Random integer generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
    }

}