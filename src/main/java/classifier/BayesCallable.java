package classifier;

import experiment.CallableImpl;
import weka.experiment.Experiment;
import weka.experiment.InstancesResultListener;

/**
 * Created by spirals on 27/04/16.
 */
public class BayesCallable extends CallableImpl<Experiment, InstancesResultListener> {

    public BayesCallable(Experiment input) {
        super(input);
    }

    @Override
    public InstancesResultListener call() throws Exception {
        input.initialize();
        input.runExperiment();
        input.postProcess();
        return (InstancesResultListener) input.getResultListener();
    }
}
