package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.Util;
import experiment.exploration.Exploration;
import perturbation.PerturbationEngine;
import perturbation.enactor.Enactor;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.Perturbator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beyni on 30/04/16.
 */
public class RandomExplorer extends ExplorerImpl {

    private float[] randomRates;

    private int seedOfRandomEnactor = 32;

    private Enactor[][] enactorsOfLocationPerRandomRates;

    private int numberOfRepeat;

    public RandomExplorer(Exploration exploration) {
        this(exploration, 5, 0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.5f, 0.9f);
    }

    public RandomExplorer(Exploration exploration, float... randomRates) {
        this(exploration, 5, randomRates);
    }

    public RandomExplorer(Exploration exploration, int numberOfRepeat) {
        this(exploration, numberOfRepeat, 0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.5f, 0.9f);
    }

    public RandomExplorer(Exploration exploration, int repeat, float... randomRates) {
        super(exploration, "RandomExplorer", randomRates.length >= 1 ? randomRates.length : 7);
        if (randomRates.length >= 1)
            this.randomRates = randomRates;
        else
            this.randomRates = new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.5f, 0.9f};

        this.numberOfRepeat = repeat;

        this.enactorsOfLocationPerRandomRates = new Enactor[Runner.locations.size()][this.randomRates.length];

        for (int indexLocation = 0; indexLocation < Runner.locations.size(); indexLocation++) {
            for (int indexRandom = 0; indexRandom < this.randomRates.length; indexRandom++) {
                this.enactorsOfLocationPerRandomRates[indexLocation][indexRandom] =
                        new RandomEnactorImpl(seedOfRandomEnactor, this.randomRates[indexRandom]);
            }
        }

        PerturbationEngine.loggers.put(super.name, new LoggerImpl());
    }

    @Override
    public void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator) {
        location.setPerturbator(perturbator);
        for (int indexRandomRate = 0; indexRandomRate < this.randomRates.length; indexRandomRate++)
            runOneRandomRate(indexOfTask, location, indexRandomRate, super.perturbators.indexOf(perturbator));
    }

    private void runOneRandomRate(int indexOfTask, PerturbationLocation location, int indexRandomRate, int perturbator) {
        int indexLocation = Runner.locations.indexOf(location);
        location.setEnactor(enactorsOfLocationPerRandomRates[indexLocation][indexRandomRate]);
        for (int i = 0; i < numberOfRepeat; i++) {
            PerturbationEngine.loggers.get(super.name).logOn(location);
            Tuple result = Runner.runPerturbation(indexOfTask);
            Logger.log(Runner.locations.indexOf(location), indexOfTask, perturbator, indexRandomRate, result, super.name);
            PerturbationEngine.loggers.get(super.name).reset();
        }
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        Tuple[][][][] results = Logger.getResults();

        int numberOfPerturbor = exploration.getPerturbators().size();
        String[] perturbatorsName = exploration.getPerturbatorsName();
        String campaignName = exploration.getName();

        String pathToOutPutFile = "results/" + Runner.manager.getPath() + "/" +
                campaignName + "_" + this.name;

        try {
            /* All Log */
            FileWriter writer = new FileWriter(pathToOutPutFile + "_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-14s %-22s %-27s";
            writer.write("detail per task and per random rate and per " + exploration.getColumnName() + ".\n" + exploration.getHeader() + Runner.manager.getHeader());
            writer.write(String.format(format,
                    "Task", "RandomRate",
                    exploration.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturbation", "AvgPerturbationPerCall",
                    "%Success") + "\n");
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                        for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                            Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][indexRandomRates];
                            double avg = (double) result.get(4) / (double) result.get(5);
                            writer.write(String.format(format,
                                    indexTask, randomRates[indexRandomRates],
                                    perturbatorsName[indexPerturbator], location.getLocationIndex(),
                                    result.get(0), result.get(1), result.get(2),
                                    result.get(3), result.get(4), avg,
                                    result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                        }
                    }
                }
            }
            writer.close();

             /* Sum Arrays */
            writer = new FileWriter(pathToOutPutFile+"_analysis_graph_data.txt", false);
            writer.write("contains the data for the random rates analysis graph with " + exploration.getColumnName() + " as perturbator.\n");
                    writer.write(exploration.getHeader() + Runner.manager.getHeader());
            format = "%-10s %-"+exploration.getColumnName().length()+"s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format,
                    "RandomRate", exploration.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturbation",
                    "%Success") + "\n");

            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                    Tuple result = new Tuple(5);
                    for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                        for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++)
                            result = result.add(results[Runner.locations.indexOf(location)][indexTask][0][indexRandomRates]);

                        writer.write(String.format(format,
                                randomRates[indexRandomRates], perturbatorsName[indexPerturbator], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2),
                                result.get(3), result.get(4),
                                result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                        resultForLocation = resultForLocation.add(result);
                    }
                }
                Explorer.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();

            format = "%-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            for (int indexPerturbator = 0 ; indexPerturbator < numberOfPerturbor ; indexPerturbator++) {
                for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                /* Sum PerturbationPoint */
                    writer = new FileWriter(pathToOutPutFile + "_per_location_" +  randomRates[indexRandomRates] + "_" + perturbatorsName[indexPerturbator] + ".txt", false);
                    writer.write("aggregate data per location for random rate = " + randomRates[indexRandomRates] + " and with " + exploration.getColumnName()+" = " + perturbatorsName[indexPerturbator] + "\n");
                    writer.write(exploration.getHeader() + Runner.manager.getHeader());
                    writer.write(String.format(format,
                            "IndexLoc", "#Success", "#Failure", "#Exception",
                            "#CallAllExecs", "AvgCallPerExec",
                            "#Perturbations", "AvgPerturbationPerExec",
                            "#Execs", "#Tasks", "%Success") + "\n");

                    for (PerturbationLocation location : Runner.locations) {
                        Tuple result = new Tuple(5);
                        int accNbOfTasks = 0;
                        for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                            result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][indexRandomRates]);
                            accNbOfTasks += results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][indexRandomRates].get(5);
                        }

                        double avgCalls = ((double) result.get(3) / (double) accNbOfTasks);
                        double avgPerturbation = (double) result.get(4) / (double) accNbOfTasks;

                        writer.write(String.format(format,
                                location.getLocationIndex(), result.get(0), result.get(1), result.get(2), //results
                                result.get(3), String.format("%.2f", avgCalls), // Calls
                                result.get(4), String.format("%.2f", avgPerturbation), //Perturbations
                                accNbOfTasks, Runner.numberOfTask,//Execs Task
                                result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");//Percentage success
                    }
                    writer.close();
                }
            }

            Explorer.writeListOnGivenFile(pathToOutPutFile + "_anti_fragile.txt",
                    "List of ids antifragile points.", locationAntiFragile);
            Explorer.writeListOnGivenFile(pathToOutPutFile + "_super_anti_fragile.txt",
                    "List of ids antifragile points.", locationSuperAntiFragile);
            Explorer.writeListOnGivenFile(pathToOutPutFile + "_oracle_fragile.txt",
                    "list ids of oracle fragile code : >" + Explorer.TOLERANCE +"% of oracle failures", locationOracleFragile);
            Explorer.writeListOnGivenFile(pathToOutPutFile + "_exception_fragile.txt",
                    "list ids of exception fragile code : >" + Explorer.TOLERANCE +"% of exceptions.", locationExceptionFragile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
