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

    public int seedForGenTask;

    protected String path;

    protected String header;

    protected List<T> scenario;

    protected Random randomForGenTask;

    public OracleManager(int seed) {
        this.seedForGenTask = seed;
        this.randomForGenTask = new Random(seedForGenTask);
        scenario = new ArrayList<>();
        while (scenario.size() < Runner.numberOfTask)
            scenario.add(generateOneTask());
    }

    public OracleManager() {
        this(23);
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
