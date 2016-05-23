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
 * Created by beyni on 21/05/16.
 */
public class QuickSortManager extends ManagerImpl<List<Integer>, List<Integer>> {

    public QuickSortManager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public QuickSortManager(int nbTask, int sizeTask, int seed){
        super(seed);
        super.CUP = QuickSortInstr.class;
        super.initialize(nbTask, sizeTask);
    }

    @Override
    protected List<Integer> generateOneTask() {
        List<Integer> newTask = new ArrayList<>();
        for (int i = 0; i < super.sizeOfTask ; i++)
            newTask.add(randomForGenTask.nextInt());
        return newTask;
    }

    @Override
    public List<Integer> getTask(int indexTask) {
        List<Integer> clone = new ArrayList<>();
        this.tasks.get(indexTask).forEach(clone::add);
        return clone;
    }

    @Override
    public CallableImpl<List<Integer>, List<Integer>> getCallable(List<Integer> input) {
        return new QuickSortCallableImpl(input);
    }

    @Override
    public Oracle<List<Integer>, List<Integer>> getOracle() {
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

    public static void main(String[] args) {
        Manager manager = new QuickSortManager(20, 100);
        new CallExplorer(manager, new IntegerExplorationPlusOne()).run();
    }
}
