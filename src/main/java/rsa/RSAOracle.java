package rsa;

import experiment.Oracle;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSAOracle implements Oracle<String,String> {

    @Override
    public boolean assertPerturbation(String input, String output) {
        return input.equals(output);
    }

}
