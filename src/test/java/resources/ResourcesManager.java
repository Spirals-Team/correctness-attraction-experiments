package resources;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.OracleManagerImpl;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesManager extends ManagerImpl<Object, Object> {

    public ResourcesManager() {
        super(1);
        super.CUP = Resources.class;
        super.initialize(2,1);
    }

    @Override
    public Object getTask(int index) {
        return index == 0 ? new Integer(1) : new Boolean(true);
    }

    @Override
    public Oracle<Object, Object> getOracle() {
        return (input, output) -> true;
    }

    @Override
    protected Integer generateOneTask() {
        return null;
    }

    @Override
    public CallableImpl<Object, Object> getCallable(Object input) {
        if (input instanceof Integer)
            return new CallableImpl<Object, Object>(input) {
                public Integer call() throws Exception {
                    return new Resources().methodInt();
                }
            };
        else
            return new CallableImpl<Object, Object>(input) {
                public Boolean call() throws Exception {
                    return new Resources().methodBoolean();
                }
            };
    }

    @Override
    public String getName() {
        return "resources";
    }

    @Override
    public String getHeader() {
        return "";
    }
}
