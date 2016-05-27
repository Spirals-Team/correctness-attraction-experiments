package classifier;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.experiment.*;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        initPath();
        super.initialize(numberOfTask > this.path.length ? this.path.length : numberOfTask, size);
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
//        model.addElement(new File(PATH_DIR+path[super.tasks.size()]));
        model.addElement(new File(build(super.tasks.size())));
        input.setDatasets(model);
        InstancesResultListener irl = new InstancesResultListener();
        irl.setOutputFile(new File("output"));
        input.setResultListener(irl);

        //Compute reference run for oracle
        oracle.runReference(input);
        return input;
    }

    private String build(int index) {
        String file = "@relation dataset_" + index + " \n\n";
        file += "@attribute attr1 numeric\n";
        file += "@attribute attr2 numeric\n";
        file += "@attribute attr3 numeric\n";
        file += "@attribute class {class1,class2}\n\n";
        file += "@data\n";

        int cpt = 0;

        for (int i = 0 ; i < super.sizeOfTask ; i++) {
            file += String.format("%.2f", 10.0*randomForGenTask.nextDouble()).replaceAll(",", ".") +
                    "," + String.format("%.2f", 10.0*randomForGenTask.nextDouble()).replaceAll(",", ".")  +
                    "," + String.format("%.2f", 10.0*randomForGenTask.nextDouble()).replaceAll(",", ".") +
                    "," + (randomForGenTask.nextBoolean()?"class1":"class2") + "\n";
        }

        System.out.println("resources/classifier/dataset"+index+".arff");
        System.out.println(file);

        try {
            FileWriter writer = new FileWriter("resources/classifier/dataset"+index+".arff", false);
            writer.write(file);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "resources/classifier/dataset"+index+".arff";

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

    public static void main(String[] args) {
        BayesManager manager = new BayesManager(1, 10);
        try {
            for (int i = 0 ; i < 1 ; i++) {
            InstancesResultListener output = manager.getCallable(manager.getTask(i)).call();
            System.out.println(manager.getOracle().assertPerturbation(manager.getTask(i), output));}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
