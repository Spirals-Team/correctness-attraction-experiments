package resources;

import experiment.Oracle;
import experiment.OracleManagerImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesManager extends OracleManagerImpl<Object> {

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Oracle<Object, ?> getOracle() {
        return new ResourcesOracle();
    }

    @Override
    protected Integer generateOneTask() {
        return null;
    }
}
