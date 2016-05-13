package resources;

import experiment.Oracle;
import experiment.OracleManagerImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesOracle implements Oracle<Integer,Integer> {

    @Override
    public boolean assertPerturbation(Integer input, Integer output) {
        return output == 10;
    }

}
