package classifier;

import experiment.Oracle;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.Range;
import weka.experiment.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spirals on 27/04/16.
 */
public class BayesOracle implements Oracle<Experiment, InstancesResultListener> {

    private static final double EPSILON = 0.05d;

    private final Map<String, Double[]> standardDeviations = new HashMap<>();

    private final Map<String, Double[]> means = new HashMap<>();

    public void runReference(Experiment input) {
        String name = ((File)input.getDatasets().get(0)).getName();
        try {
            input.initialize();
            input.runExperiment();
            input.postProcess();
            ResultMatrix matrix = buildResultMatrix(((InstancesResultListener)input.getResultListener()).getOutputFile());
            standardDeviations.put(name, new Double[matrix.getColCount()]);
            means.put(name, new Double[matrix.getColCount()]);
            for (int i = 0; i < matrix.getColCount(); i++) {
                standardDeviations.get(name)[i] = matrix.getStdDev(i, 0);
                means.get(name)[i] = matrix.getMean(i, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean assertPerturbation(Experiment input, InstancesResultListener output) {
        String name = ((File)input.getDatasets().get(0)).getName();
        ResultMatrix matrix = buildResultMatrix(output.getOutputFile());
        boolean assertion = true;
        for (int i = 0; i < matrix.getColCount(); i++) {
            assertion &= Math.abs(matrix.getMean(i ,0) - means.get(name)[i]) < EPSILON &&
                         Math.abs(matrix.getStdDev(i ,0) - standardDeviations.get(name)[i]) < EPSILON;
        }
        return assertion;
    }

    private ResultMatrix buildResultMatrix(File fileResult) {
        PairedTTester tester = new PairedCorrectedTTester();
        Instances result = null;
        try {
            result = new Instances(new BufferedReader(new FileReader(fileResult)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tester.setInstances(result);
        tester.setSortColumn(-1);
        tester.setRunColumn(result.attribute("Key_Run").index());
        tester.setFoldColumn(result.attribute("Key_Fold").index());
        tester.setDatasetKeyColumns(
                new Range(
                        ""
                                + (result.attribute("Key_Dataset").index() + 1)));
        tester.setResultsetKeyColumns(
                new Range(
                        ""
                                + (result.attribute("Key_Scheme").index() + 1)
                                + ","
                                + (result.attribute("Key_Scheme_options").index() + 1)
                                + ","
                                + (result.attribute("Key_Scheme_version_ID").index() + 1)));
        tester.setResultMatrix(new ResultMatrixPlainText());
        tester.setDisplayedResultsets(null);
        tester.setSignificanceLevel(0.05);
        tester.setShowStdDevs(true);

        try {
            tester.multiResultsetFull(0, result.attribute("Percent_correct").index());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tester.getResultMatrix();
    }

}
