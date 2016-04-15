package zip;

import experiment.OracleImpl;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipOracleImpl extends OracleImpl<String> {

    public ZipOracleImpl(){
       super();
        super.path = "zip";
    }

    public ZipOracleImpl(int numberOfTask){
        super(numberOfTask);
        super.path = "zip";
    }

    public ZipOracleImpl(int numberOfTask, int sizeOfEachTask){
        super(numberOfTask, sizeOfEachTask);
        super.path = "zip";
    }


    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0 ; i < sizeOfEachTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }


    @Override
    public String header() {
        String header = numberOfTask + " string of " + sizeOfEachTask + " char\n";
        header += "Random char generated with " + seedForGenTask + " as seed\n";
        return header;
    }

    @Override
    public boolean check(String perturbedValue, int index) {
        return perturbedValue.equals(scenario.get(index));
    }

    @Override
    public String get(int index) {
        return new String(scenario.get(index));
    }

}
