package md5;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

/**
 * Created by spirals on 19/04/16.
 */
public class MD5Manager extends OracleManager<String> {

    public MD5Manager() {
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
