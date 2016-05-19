package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by spirals on 05/04/16.
 */

/**
 * OracleManagerImpl
 *
 * @param <T>
 */
public abstract class OracleManagerImpl<T> implements OracleManager<T> {

    public int seedForGenTask;

    protected String path;

    protected String header;

    protected List<T> scenario;

    protected Random randomForGenTask;

    protected int numberOfTask;

    public OracleManagerImpl(int numberOfTask, int seed) {
        this.seedForGenTask = seed;
        this.numberOfTask = numberOfTask;
        this.randomForGenTask = new Random(seedForGenTask);
        scenario = new ArrayList<>();
        while (scenario.size() < this.numberOfTask)
            scenario.add(generateOneTask());
    }

    public void setPath(String path) {
        this.path = path;
    }

    public abstract T get(int index);

    public abstract Oracle<T, ?> getOracle();

    public String getPath() {
        return path;
    }

    public String getHeader() {
        return this.header;
    }

    public void filterScenarios(List<Integer> indexToKeep) {
        scenario = scenario.stream()
                .filter(sc -> indexToKeep.contains(scenario.indexOf(sc))
                ).collect(Collectors.toList());
        this.numberOfTask = scenario.size();
    }

    /**
     * Every OracleManager must be able to generate task for the given subject
     *
     * @return
     */
    protected abstract T generateOneTask();


}
