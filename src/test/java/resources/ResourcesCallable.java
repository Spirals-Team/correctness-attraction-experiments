package resources;

import experiment.CallableImpl;

/**
 * Created by bdanglot on 09/05/16.
 */
public class ResourcesCallable extends CallableImpl<Integer, Integer> {

    public ResourcesCallable(Integer input) {
        super(input);
    }

    @Override
    public Integer call() throws Exception {
        return new Resources().method();
    }

}
