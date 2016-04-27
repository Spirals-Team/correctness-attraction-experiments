package classifier;

import experiment.Runner;
import experiment.Util;
import weka.experiment.Experiment;
import weka.experiment.CrossValidationResultProducer;


/**
 * Created by spirals on 27/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("run Naive Bayes Classsifier...");
        System.err.close();
        Runner.setup(CrossValidationResultProducer.class, BayesCallable.class, new BayesManager(), "Numerical", Experiment.class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length > 1)
            Util.parseArgs(args);
        run();
    }
}
