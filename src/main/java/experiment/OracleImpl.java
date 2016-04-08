package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */
public abstract class OracleImpl<T> implements Oracle<T> {

    protected List<T> scenario;

    protected int seedForGenTask = 23;

    protected Random randomForGenTask = new Random(seedForGenTask);

    protected int numberOfTask = 20;

    protected int sizeOfEachTask = 100;

    protected String path;

    public OracleImpl() {
        scenario = new ArrayList<T>();
        while (scenario.size() < numberOfTask)
            scenario.add(generateOneTask());
    }

    public OracleImpl(int numberOfTask) {
        this.numberOfTask = numberOfTask;
        scenario = new ArrayList<T>();
        while (scenario.size() <  this.numberOfTask)
            scenario.add(generateOneTask());
    }

    protected abstract T generateOneTask();

    @Override
    public int getNumberOfTask() {
        return numberOfTask;
    }

    @Override
    public String getPath() {
        return path;
    }





}
