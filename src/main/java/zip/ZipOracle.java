package zip;

import experiment.OracleManager;
import experiment.Runner;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipOracle extends OracleManager<String,String> {

    public ZipOracle(){
       super();
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
    public String header() {
        String header = Runner.numberOfTask + " string of " + Runner.sizeOfEachTask + " char\n";
        header += "Random char generated with " + seedForGenTask + " as seed\n";
        return header;
    }

    @Override
    public boolean check(String output, int index) {
        return output.equals(scenario.get(index));
    }

    @Override
    public String get(int index) {
        return new String(scenario.get(index));
    }

}
