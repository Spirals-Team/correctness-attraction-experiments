package md5;

import experiment.OracleImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5OracleImpl extends OracleImpl<String> {

    private List<String> hashOfTask;

    public MD5OracleImpl() {
        super();
        super.path = "md5";
        hashOfTask = new ArrayList<String>();
        for (String stringToBeHash : scenario)
            hashOfTask.add("0x" + toHexString(MD5.computeMD5(stringToBeHash.getBytes())));
    }

    public MD5OracleImpl(int numberOfTask) {
        super(numberOfTask);
        super.path = "md5";
        hashOfTask = new ArrayList<String>();
        for (String stringToBeHash : scenario)
            hashOfTask.add("0x" + toHexString(MD5.computeMD5(stringToBeHash.getBytes())));
    }

    public MD5OracleImpl(int numberOfTask, int sizeOfEachTask) {
        super(numberOfTask, sizeOfEachTask);
        super.path = "md5";
        hashOfTask = new ArrayList<String>();
        for (String stringToBeHash : scenario)
            hashOfTask.add("0x" + toHexString(MD5.computeMD5(stringToBeHash.getBytes())));
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

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++)
        {
            sb.append(String.format("%02X", b[i] & 0xFF));
        }
        return sb.toString();
    }

}
