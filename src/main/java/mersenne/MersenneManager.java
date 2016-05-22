package mersenne;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class MersenneManager extends ManagerImpl<Long, List<Long>> {

    public MersenneManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public MersenneManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = MersenneTwisterInstr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected Long generateOneTask() {
        return super.randomForGenTask.nextLong();
    }

    @Override
    public Long getTask(int index) {
        return new Long(super.tasks.get(index));
    }

    @Override
    public Oracle<Long, List<Long>> getOracle() {
        return new MersenneOracle();
    }

    @Override
    public CallableImpl<Long, List<Long>> getCallable(Long input) {
        return new MersenneCallableImpl(input, super.sizeOfTask);
    }

    @Override
    public String getName() {
        return "mersenne";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " list of " + super.sizeOfTask + " number\n"
                +"Random numbers generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }
}
