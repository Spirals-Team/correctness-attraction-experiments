package rsa;

import experiment.Oracle;

import java.util.Arrays;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSAOracle implements Oracle<byte[],byte[]> {

    @Override
    public boolean assertPerturbation(byte[] input, byte[] output) {
        return Arrays.equals(input, output);
//        return input.equals(output);
    }

}
