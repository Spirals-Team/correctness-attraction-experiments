package md5;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5Oracle implements Oracle<String,byte[]> {

    @Override
    public boolean assertPerturbation(String input, byte[] output) {
        return Arrays.equals(MD5.computeMD5(input.getBytes()), output);
    }
}
