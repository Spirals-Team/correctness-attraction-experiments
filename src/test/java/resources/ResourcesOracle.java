package resources;

import experiment.Oracle;
import experiment.OracleManagerImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesOracle implements Oracle<Object,Object> {

    @Override
    public boolean assertPerturbation(Object input, Object output) {
        return true;
    }

}
