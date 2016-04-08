package md5;

import experiment.OracleImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 07/04/16.
 */
public class Md5OracleImpl extends OracleImpl<String> {

    private List<String> hashOfTask;

    public Md5OracleImpl() {
        super();
        super.path = "md5";
        hashOfTask = new ArrayList<String>();
        for (String stringToBeHash : scenario)
            hashOfTask.add("0x" + MD5.toHexString(MD5.computeMD5(stringToBeHash.getBytes())));
    }

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
        return perturbedValue.equals(hashOfTask.get(index));
    }

    @Override
    public String get(int index) {
        return new String(super.scenario.get(index));
    }
}
