package zip;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class ZipManager extends OracleManagerImpl<String> {

    public ZipManager(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " string of " + Runner.sizeOfEachTask + " char\n";
        super.header += "Random char generated with " + seedForGenTask + " as seed\n";
        super.path = "zip";
    }

    public ZipManager() {
        super();
        super.header = Runner.numberOfTask + " string of " + Runner.sizeOfEachTask + " char\n";
        super.header += "Random char generated with " + seedForGenTask + " as seed\n";
        super.path = "zip";
    }

    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0; i < Runner.sizeOfEachTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }

    @Override
    public String get(int index) {
        return new String(scenario.get(index));
    }

    @Override
    public Oracle<String, ?> getOracle() {
        return new ZipOracle();
    }
}
