package quicksort;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class QuickSortManagerImpl extends OracleManagerImpl<List<Integer>> {

    public QuickSortManagerImpl(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " arrays of " + Runner.sizeOfEachTask + " integers\nRandom integer generated with " + super.seedForGenTask + " as seed\n";
        super.path = "quicksort";
    }

    public QuickSortManagerImpl() {
        super();
        super.header = Runner.numberOfTask + " arrays of " + Runner.sizeOfEachTask + " integers\nRandom integer generated with " + super.seedForGenTask + " as seed\n";
        super.path = "quicksort";
    }

    @Override
    protected List<Integer> generateOneTask() {
        List<Integer> newTask = new ArrayList<>();
        for (int i = 0; i < Runner.sizeOfEachTask ; i++)
            newTask.add(randomForGenTask.nextInt());
        return newTask;
    }

    @Override
    public List<Integer> get(int index) {
        return new ArrayList(scenario.get(index));
    }

    @Override
    public Oracle<List<Integer>, ?> getOracle() {
        return new QuickSortOracle();
    }
}
