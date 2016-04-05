package sort;

import experiment.Oracle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */
public class SortOracleImpl implements Oracle<List<Integer>> {

    private String PATH = "sort";

    private List<List<Integer>> scenario = new ArrayList<>();

    private int numberOfTask = 20;

    private int seedForGenTask = 23;

    private Random randomForGenTask = new Random(seedForGenTask);

    private int sizeOfEachTask = 100;

    private List<Integer> generateOneTask() {
        List<Integer> newTask = new ArrayList<>();
        for (int i = 0 ; i < sizeOfEachTask ; i++)
            newTask.add(randomForGenTask.nextInt());
        return newTask;
    }

    public SortOracleImpl() {
        while (scenario.size() < numberOfTask)
            scenario.add(generateOneTask());
    }

    @Override
    public String header() {
        String header = numberOfTask + " arrays of " + sizeOfEachTask + " integers\n";
        header += "Random integer generated with " + seedForGenTask + " as seed\n";
        return header;
    }

    @Override
    public boolean check(List<Integer> perturbedValue, int index) {
        List<Integer> clone = new ArrayList<Integer>(scenario.get(index));

        Iterator<Integer> it = perturbedValue.iterator();

        int previousValue = it.next();

        if (!clone.contains(previousValue))
            return false;
        else
            clone.remove(clone.indexOf(previousValue));

        while (it.hasNext()) {
            int current = it.next();
            if (current < previousValue)
                return false;
            else if (!clone.contains(current))
                return false;
            else
                clone.remove(clone.indexOf(current));

            previousValue = current;
        }
        return clone.isEmpty();
    }

    @Override
    public List<Integer> get(int index) {
        return new ArrayList(scenario.get(index));
    }

    @Override
    public int getNumberOfTask() {
        return numberOfTask;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
