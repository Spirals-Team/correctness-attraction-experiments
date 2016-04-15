package md5;

import experiment.OracleImpl;
import experiment.Runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5OracleImpl extends OracleImpl<String,byte[]> {

    private List<byte[]> hashOfTask;

    public MD5OracleImpl() {
        super();
        super.path = "md5";
        hashOfTask = new ArrayList<>();
        for (String stringToBeHash : scenario)
            hashOfTask.add(MD5.computeMD5(stringToBeHash.getBytes()));
    }

    protected String generateOneTask() {
        String string = "";
        for (int i = 0 ; i < Runner.sizeOfEachTask ; i++) {
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
    public boolean check(byte[] perturbedValue, int index) {
        return Arrays.equals(perturbedValue, hashOfTask.get(index));
    }

    @Override
    public String get(int index) {
        return new String(super.scenario.get(index));
    }

}
