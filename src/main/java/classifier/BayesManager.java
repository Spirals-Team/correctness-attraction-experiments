package classifier;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.experiment.*;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;

/**
 * Created by spirals on 27/04/16.
 */
public class BayesManager extends OracleManager<Experiment> {

    private String[] path;

    private BayesOracle oracle;

    private static final String PATH_DIR = "resources/classifier/";

    public BayesManager() {
        super();
        super.header = Runner.numberOfTask + " datasets\nPicked up in the data set provided by Weka.\n";
        super.path = "classifier";
    }

    private void initPath() {
        File directory = new File(PATH_DIR);
        File [] files = directory.listFiles();
        assert files == null;
        path = new String[files.length];
        for (int i = 0 ; i < files.length ; i++)
            path[i] = files[i].getName();
        oracle = new BayesOracle();
        if (Runner.numberOfTask > path.length)
            Runner.numberOfTask = path.length;
    }

    @Override
    protected Experiment generateOneTask() {
        if (path == null)
            initPath();
        Experiment input = new Experiment();
        input.setPropertyArray(new Classifier[0]);
        input.setUsePropertyIterator(true);
        SplitEvaluator se  = new ClassifierSplitEvaluator();
        Classifier sec = ((ClassifierSplitEvaluator) se).getClassifier();
        CrossValidationResultProducer cvrp = new CrossValidationResultProducer();
        cvrp.setNumFolds(10);
        cvrp.setSplitEvaluator(se);
        PropertyNode[] propertyPath = new PropertyNode[2];
        try {
            propertyPath[0] = new PropertyNode(
                    se,
                    new PropertyDescriptor("splitEvaluator",
                            CrossValidationResultProducer.class),
                    CrossValidationResultProducer.class);
            propertyPath[1] = new PropertyNode(
                    sec,
                    new PropertyDescriptor("classifier",
                            se.getClass()),
                    se.getClass());
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
        }

        Classifier c = new NaiveBayes();
        input.setPropertyArray(new Classifier[]{c});
        input.setResultProducer(cvrp);
        input.setPropertyPath(propertyPath);
        input.setRunLower(1);
        input.setRunUpper(10);
        DefaultListModel model = new DefaultListModel();
        model.addElement(new File(PATH_DIR+path[scenario.size()]));
        input.setDatasets(model);
        InstancesResultListener irl = new InstancesResultListener();
        irl.setOutputFile(new File("output"));
        input.setResultListener(irl);

        //Compute reference run for oracle
        oracle.runReference(input);
        return input;
    }

    @Override
    public Experiment get(int index) {
        return scenario.get(index);
    }

    @Override
    public Oracle<Experiment, ?> getOracle() {
        return oracle;
    }
}
