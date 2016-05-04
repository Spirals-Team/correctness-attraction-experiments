package mersenne;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class MersenneManagerImpl extends OracleManagerImpl<Long> {

    public MersenneManagerImpl(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " list of " + Runner.sizeOfEachTask + " number\n";
        super.header += "Random numbers generated with " + seedForGenTask + " as seed\n";
        super.path = "mersenne";
    }

    public MersenneManagerImpl() {
        super();
        super.header = Runner.numberOfTask + " list of " + Runner.sizeOfEachTask + " number\n";
        super.header += "Random numbers generated with " + seedForGenTask + " as seed\n";
        super.path = "mersenne";
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
