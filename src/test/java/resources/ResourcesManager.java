package resources;

import experiment.Oracle;
import experiment.OracleManagerImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesManager extends OracleManagerImpl<Integer> {

    @Override
    public Integer get(int index) {
        return 0;
    }

    @Override
    public Oracle<Integer, ?> getOracle() {
        return new ResourcesOracle();
    }

    @Override
    protected Integer generateOneTask() {
        return null;
    }
}
