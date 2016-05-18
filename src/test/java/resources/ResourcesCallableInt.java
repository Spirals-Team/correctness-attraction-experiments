package resources;

import experiment.CallableImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesCallableInt extends CallableImpl<Integer, Integer> {

    public ResourcesCallableInt(Integer input) {
        super(input);
    }

    @Override
    public Integer call() throws Exception {
        return new Resources().methodInt();
    }

}
