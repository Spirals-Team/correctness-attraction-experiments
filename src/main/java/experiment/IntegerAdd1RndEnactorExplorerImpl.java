package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spirals on 05/04/16.
 */
public class IntegerAdd1RndEnactorExplorerImpl implements Explorer {

    private float[] randomRates = new float[]{0.001f, 0.002f, 0.005f, 0.009f, 0.01f, 0.02f, 0.05f, 0.09f, 0.1f, 0.2f, 0.5f, 0.9f};

    private int seedOfRandomEnactor = 32;

    private String header;

    private String path;

    private Map<PerturbationLocation, RandomEnactorImpl>[] enactorsOfLocationPerRandomRates;

    private int[][][] nbOfCallsPerLocationPerTaskPerRates;

    public IntegerAdd1RndEnactorExplorerImpl(float... randomRates) {

        if (randomRates.length > 0)
            this.randomRates = randomRates;

        enactorsOfLocationPerRandomRates = new Map[this.randomRates.length];

        nbOfCallsPerLocationPerTaskPerRates = new int[Runner.locations.size()][Runner.oracle.getNumberOfTask()][this.randomRates.length];

        Logger.init(Runner.locations.size(),Runner.oracle.getNumberOfTask(), this.randomRates.length, 5);

        header = "SERN\n";
        header += "random Rates : ";

        for (int indexOfRandomRate = 0; indexOfRandomRate < this.randomRates.length; indexOfRandomRate++) {
            header += this.randomRates[indexOfRandomRate] + " ";
            enactorsOfLocationPerRandomRates[indexOfRandomRate] = new HashMap<>();
            for (PerturbationLocation location : Runner.locations) {
                enactorsOfLocationPerRandomRates[indexOfRandomRate].put(location, new RandomEnactorImpl(seedOfRandomEnactor, this.randomRates[indexOfRandomRate]));
            }
        }

        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "Random Enactor, seed :" + seedOfRandomEnactor + "\n";
        header += "PONE : Numerical Perturbator\n";

        path = "IntegerAdd1RndEnactorExplorer";
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        location.setPerturbator(new AddNPerturbatorImpl(1));
        for (int indexOfRandomRate = 0; indexOfRandomRate < randomRates.length; indexOfRandomRate++) {
            PerturbationEngine.logger.logOn(location);
            Tuple result = runRandomRate(indexOfTask, location, indexOfRandomRate);
            nbOfCallsPerLocationPerTaskPerRates[Runner.locations.indexOf(location)][indexOfTask][indexOfRandomRate]++;
            Tuple resultWithLog = new Tuple(5);
            resultWithLog = resultWithLog.add(result);
            resultWithLog.set(3, PerturbationEngine.logger.getCalls(location));
            resultWithLog.set(4, PerturbationEngine.logger.getEnactions(location));
            Logger.add(Runner.locations.indexOf(location), indexOfTask, indexOfRandomRate, resultWithLog);
            PerturbationEngine.logger.reset();
        }
        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    private Tuple runRandomRate(int indexOfTask, PerturbationLocation location, int indexOfrandomRate) {
        location.setEnactor(enactorsOfLocationPerRandomRates[indexOfrandomRate].get(location));
        return Runner.runPerturbation(indexOfTask);
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        Tuple [][][] results = Logger.getResults();

        try {
            /* All Log */
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write( "detail per task and per random rate.\n" + header + Runner.oracle.header());
            writer.write(String.format(format, "Task", "RandomRate", "IndexLoc", "#Success", "#Failure",
                    "#Exception", "#Call", "#Perturbation","%Success") + "\n");
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexRandomRates];
                        double avg = (double) result.get(4) / (double)nbOfCallsPerLocationPerTaskPerRates[Runner.locations.indexOf(location)][indexTask][indexRandomRates];
                        writer.write(String.format(format, indexTask, randomRates[indexRandomRates], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                                Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                    }
                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_random_rates_analysis_graph_data.txt", false);
            writer.write("contains the data for the random rates analysis graph.\n"+ header + Runner.oracle.header());
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format, "RandomRate", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",
                    "#Perturbation","%Success") + "\n");
            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexRandomRates]);

                    writer.write(String.format(format, randomRates[indexRandomRates], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                    resultForLocation = resultForLocation.add(result);
                }
                Explorer.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();

            String title = "SERN\n";
            title += Runner.locations.size() + " perturbation point\n";
            title += "Random Enactor, seed :" + seedOfRandomEnactor + "\n";
            title += "PONE : Numerical Perturbator\n";

            format = "%-10s %-10s %-10s %-10s %-10s %-14s %-10s %-22s %-27s";
            for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                /* Sum PerturbationPoint */
                writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_per_location_" + randomRates[indexRandomRates] + ".txt", false);
                writer.write("aggregate data per location for magnitude = " + randomRates[indexRandomRates] + "\n" + title + Runner.oracle.header());
                writer.write(String.format(format, "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",
                        "#Perturbation", "#Task","AvgPerturbationPerTask","%Success") + "\n");
                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = new Tuple(5);
                    int accNbOfTasks = 0;
                    for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexRandomRates]);
                        accNbOfTasks += nbOfCallsPerLocationPerTaskPerRates[Runner.locations.indexOf(location)][indexTask][indexRandomRates];
                    }
                    double avg = (double) result.get(4) / (double) accNbOfTasks;
                    writer.write(String.format(format, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            accNbOfTasks, String.format("%.2f", avg),
                            Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                }
                writer.close();
            }

            Explorer.writeListOnGivenFile("results/" + Runner.oracle.getPath() + "/" + path + "_anti_fragile.txt",
                    "List of ids antifragile points.", locationAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.oracle.getPath() + "/" + path + "_super_anti_fragile.txt",
                    "List of ids antifragile points.", locationSuperAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.oracle.getPath() + "/" + path + "_oracle_fragile.txt",
                    "list ids of oracle fragile code : >" + Explorer.TOLERANCE +"% of oracle failures", locationOracleFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.oracle.getPath() + "/" + path + "_exception_fragile.txt",
                    "list ids of exception fragile code : >" + Explorer.TOLERANCE +"% of exceptions.", locationExceptionFragile);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
