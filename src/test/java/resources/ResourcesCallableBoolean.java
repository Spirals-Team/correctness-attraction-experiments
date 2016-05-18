package resources;

import experiment.CallableImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesCallableBoolean extends CallableImpl<Boolean, Boolean> {

    public ResourcesCallableBoolean(Boolean input) {
        super(input);
    }

    @Override
    public Boolean call() throws Exception {
        return new Resources().methodBoolean();
    }

}
