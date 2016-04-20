package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */
public abstract class OracleManager<T,P> implements Oracle<T,P> {

    protected List<T> scenario;

    protected int seedForGenTask = 23;

    protected Random randomForGenTask = new Random(seedForGenTask);

    protected String path;

    public OracleManager() {
        scenario = new ArrayList<>();
        while (scenario.size() < Runner.numberOfTask)
            scenario.add(generateOneTask());
    }

    protected abstract T generateOneTask();

    @Override
    public String getPath() {
        return path;
    }





}
