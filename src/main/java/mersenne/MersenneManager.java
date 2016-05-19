package mersenne;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class MersenneManager extends OracleManagerImpl<Long> {

    public MersenneManager(int numberOfTask, int seed) {
        super(numberOfTask, seed);
        super.header = super.numberOfTask + " list of " + Runner.sizeOfEachTask + " number\n";
        super.header += "Random numbers generated with " + seedForGenTask + " as seed\n";
        super.path = "mersenne";
    }

    public MersenneManager(int seed) {
        this(Runner.numberOfTask, seed);

    }

    public MersenneManager() {
        this(Runner.numberOfTask, 23);
    }

    @Override
    protected Long generateOneTask() {
        return super.randomForGenTask.nextLong();
    }

    @Override
    public Long get(int index) {
        return new Long(super.scenario.get(index));
    }

    @Override
    public Oracle<Long, ?> getOracle() {
        return new MersenneOracle();
    }
}
