package md5;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class MD5ManagerImpl extends OracleManagerImpl<String> {

    public MD5ManagerImpl(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " string of " + Runner.sizeOfEachTask + " char\nRandom char generated with " + seedForGenTask + " as seed\n";
        super.path = "md5";
    }

    public MD5ManagerImpl() {
        super();
        super.header = Runner.numberOfTask + " string of " + Runner.sizeOfEachTask + " char\nRandom char generated with " + seedForGenTask + " as seed\n";
        super.path = "md5";
    }

    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0 ; i < Runner.sizeOfEachTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }

    @Override
    public String get(int index) {
        return new String(super.scenario.get(index));
    }

    @Override
    public Oracle<String, ?> getOracle() {
        return new MD5Oracle();
    }
}
