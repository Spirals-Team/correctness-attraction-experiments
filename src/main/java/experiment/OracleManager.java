package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */

/**
 * OracleManager
 * @param <T>
 */
public abstract class OracleManager<T> {

    protected String path;

    protected String header;

    protected int seedForGenTask = 23;

    protected List<T> scenario;

    protected Random randomForGenTask = new Random(seedForGenTask);

    public OracleManager() {
        scenario = new ArrayList<>();
        while (scenario.size() < Runner.numberOfTask)
            scenario.add(generateOneTask());
    }

    protected abstract T generateOneTask();

    public abstract T get(int index);

    public abstract Oracle<T, ?> getOracle();

    public String getPath() {
        return path;
    }

    public String getHeader() {
        return this.header;
    }





}
