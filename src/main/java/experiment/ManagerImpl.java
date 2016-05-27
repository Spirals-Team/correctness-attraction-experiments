package experiment;

import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * T : input type
 * P : output type
 */
public abstract class ManagerImpl<T, P> implements Manager<T, P> {

    public int seedForGenTask;

    protected Random randomForGenTask;

    /**
     * Number of seconds to wait until the callable time out
     */
    protected int numberOfSecondsToWait;

    /**
     * Class Under Perturbations
     */
    protected Class<?> CUP;

    /**
     * List of perturbations points.
     */
    protected List<PerturbationLocation> locations;

    /**
     * List of the tasks used for the experiments.
     */
    protected List<T> tasks;

    protected int sizeOfTask;

    /**
     * List of index of task to used for perturbed execution.
     * This list allow to filter the tasks, without erase them.
     */
    protected List<Integer> indexTasks;

    /**
     * List of outputs produced at each perturbed execution
     */
    protected List<P> outputs;

    /**
     * Each subclass must provide a way to generate a task for his own subject
     * @return a new Task
     */
    protected abstract T generateOneTask();

    public ManagerImpl(int seed) {
        this.seedForGenTask = seed;
        this.randomForGenTask = new Random(seed);
    }

    @Override
    public Class<?> getCUP() {
        return this.CUP;
    }

    @Override
    public void initialize(int numberOfTask, int sizeOfTask) {
        this.locations = PerturbationLocationImpl.getLocationFromClass(CUP);
        this.outputs = new ArrayList<>();
        this.indexTasks = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.sizeOfTask = sizeOfTask;
        IntStream.range(0, numberOfTask).forEach(index -> {
            this.indexTasks.add(index);
            this.tasks.add(this.generateOneTask());
        });
    }

    @Override
    public int getSizeOfTask() {
        return this.sizeOfTask;
    }

    @Override
    public List<Integer> getIndexTask() {
        return this.indexTasks;
    }

    @Override
    public void setIndexTask(List<Integer> tasks) {
        this.indexTasks = tasks;
    }

    @Override
    public List<PerturbationLocation> getLocations() {
        return this.locations;
    }

    @Override
    public void setLocations(List<PerturbationLocation> locations) {
        this.locations = locations;
    }


    @Override
    public List<PerturbationLocation> getLocations(String filter) {
        this.locations = this.locations.stream()
                .filter(location -> location.getType().equals(filter))
                .collect(Collectors.toList());
        return this.locations;
    }
}
