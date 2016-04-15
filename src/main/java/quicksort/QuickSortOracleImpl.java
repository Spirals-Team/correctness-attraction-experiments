package quicksort;

import experiment.OracleImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class QuickSortOracleImpl extends OracleImpl<List<Integer>> {

    @Override
    protected List<Integer> generateOneTask() {
        List<Integer> newTask = new ArrayList<>();
        for (int i = 0 ; i < sizeOfEachTask ; i++)
            newTask.add(randomForGenTask.nextInt());
        return newTask;
    }

    public QuickSortOracleImpl() {
        super();
        super.path = "quicksort";
    }

    public QuickSortOracleImpl(int numberOfTask) {
        super(numberOfTask);
        super.path = "quicksort";
    }


    public QuickSortOracleImpl(int numberOfTask, int sizeOfEachTask) {
        super(numberOfTask, sizeOfEachTask);
        super.path = "quicksort";
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

}
