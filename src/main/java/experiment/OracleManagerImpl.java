package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */

/**
 * OracleManagerImpl
 * @param <T>
 */
public abstract class OracleManagerImpl<T> implements OracleManager<T> {

    public int seedForGenTask;

    protected String path;

    protected String header;

    protected List<T> scenario;

    protected Random randomForGenTask;

    public OracleManagerImpl(int seed) {
        this.seedForGenTask = seed;
        this.randomForGenTask = new Random(seedForGenTask);
        scenario = new ArrayList<>();
        while (scenario.size() < Runner.numberOfTask)
            scenario.add(generateOneTask());
    }

    public OracleManagerImpl() {
        this(23);
    }

    public abstract T get(int index);

    public abstract Oracle<T, ?> getOracle();

    public String getPath() {
        return path;
    }

    public String getHeader() {
        return this.header;
    }

    /**
     * Every OracleManager must be able to generate task for the given subject
     * @return
     */
    protected abstract T generateOneTask();



}
