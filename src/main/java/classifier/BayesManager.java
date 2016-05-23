package classifier;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.OracleManagerImpl;
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
public class BayesManager extends ManagerImpl<Experiment, InstancesResultListener> {

    private String[] path;

    private BayesOracle oracle;

    private static final String PATH_DIR = "resources/classifier/";

    public BayesManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public BayesManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = CrossValidationResultProducer.class;
        super.initialize(numberOfTask, size);
    }


    private void initPath() {
        File directory = new File(PATH_DIR);
        File [] files = directory.listFiles();
        path = new String[files.length];
        for (int i = 0 ; i < files.length ; i++)
            path[i] = files[i].getName();
        oracle = new BayesOracle();
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
        model.addElement(new File(PATH_DIR+path[super.tasks.size()]));
        input.setDatasets(model);
        InstancesResultListener irl = new InstancesResultListener();
        irl.setOutputFile(new File("output"));
        input.setResultListener(irl);

        //Compute reference run for oracle
        oracle.runReference(input);
        return input;
    }

    @Override
    public Experiment getTask(int index) {
        return super.tasks.get(index);
    }

    @Override
    public Oracle<Experiment, InstancesResultListener> getOracle() {
        return oracle;
    }

    @Override
    public CallableImpl<Experiment, InstancesResultListener> getCallable(Experiment input) {
        return new BayesCallable(input);
    }

    @Override
    public String getName() {
        return "classifier";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " datasets\nPicked up in the directory resources/classifier\n" +
                super.locations.size() + " perturbations points\n";
    }
}
