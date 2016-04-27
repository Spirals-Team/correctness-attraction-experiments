package classifier;

import experiment.CallableImpl;
import weka.experiment.Experiment;
import weka.experiment.InstancesResultListener;

/**
 * Created by spirals on 27/04/16.
 */
public class BayesCallable extends CallableImpl<Experiment, InstancesResultListener> {

    public BayesCallable(Experiment originalValue) {
        super(originalValue);
    }

    @Override
    public InstancesResultListener call() throws Exception {
        originalValue.initialize();
        originalValue.runExperiment();
        originalValue.postProcess();
        return (InstancesResultListener)originalValue.getResultListener();
    }
}
