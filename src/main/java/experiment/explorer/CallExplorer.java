package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.Util;
import experiment.exploration.Exploration;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.Perturbator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 30/04/16.
 */
public class CallExplorer extends ExplorerImpl {

    protected int[][] nbCallReferencePerLocationPerTask;

    public CallExplorer(Exploration exploration) {
        super(exploration, "CallExplorer");
        nbCallReferencePerLocationPerTask = new int[Runner.locations.size()][Runner.numberOfTask];
    }

    @Override
    public void initLogger() {
        //Logger contains : Success Failure Exception Call Perturbation NumberOfExecution
        Logger.init(Runner.locations.size(), Runner.numberOfTask, perturbators.size());
        PerturbationEngine.loggers.put(name, new LoggerImpl());
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        //reference run : no perturbation
        PerturbationEngine.loggers.get(super.name).logOn(location);
        Runner.runPerturbation(indexOfTask);
        int currentNbCall = PerturbationEngine.loggers.get(super.name).getCalls(location);
        nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.loggers.get(super.name).reset();

        super.run(indexOfTask, location);
    }

    @Override
    public void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator) {
        int currentNbCall = nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask];
        location.setPerturbator(perturbator);
        for (int indexOfCall = 0 ; indexOfCall < currentNbCall ; indexOfCall++) {
            PerturbationEngine.loggers.get(super.name).logOn(location);
            Tuple result = runAtTheIndexOfCall(indexOfCall, indexOfTask, location);
            Logger.log(Runner.locations.indexOf(location), indexOfTask, super.perturbators.indexOf(perturbator), 0, result, super.name);
            PerturbationEngine.loggers.get(super.name).reset();
        }
    }

    private Tuple runAtTheIndexOfCall(int indexOfCall, int indexOfTask, PerturbationLocation location) {
        location.setEnactor(new NCallEnactorImpl(indexOfCall, location));
        return Runner.runPerturbation(indexOfTask);
    }

    public int[][] getNbCallReferencePerLocationPerTask() {
        return this.nbCallReferencePerLocationPerTask;
    }

    @Override
    public void log() {
        Tuple[][][][] results = Logger.getResults();

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        int numberOfPerturbor = exploration.getPerturbators().size();
        String[] perturbatorsName = exploration.getPerturbatorsName();
        String campaignName = exploration.getName();

        String pathToOutPutFile = "results/" + Runner.manager.getPath() + "/" +
                campaignName + "_" + this.name;

        int[] searchSpaceSizePerMagnitude = new int[numberOfPerturbor];
        int[] numberOfSuccessPerMagnitude = new int[numberOfPerturbor];

        try {
            FileWriter writer = new FileWriter(pathToOutPutFile + "_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write("detail per task and per " + exploration.getColumnName() + ".\n" + exploration.getHeader() + Runner.manager.getHeader());
            writer.write(String.format(format,
                    "Task", exploration.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturbations",
                    "%Success") + "\n");

            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][0];
                        searchSpaceSizePerMagnitude[indexPerturbator] += result.get(5);
                        numberOfSuccessPerMagnitude[indexPerturbator] += result.get(0);
                        writer.write(String.format(format,
                                indexTask, perturbatorsName[indexPerturbator], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2),
                                result.get(3), result.get(4),
                                result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                    }

                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter(pathToOutPutFile + "_analysis_graph_data.txt", false);
            writer.write("contains the data for build a graph for analysis.\n" +
                    exploration.getHeader() + Runner.manager.getHeader());
            format = "%-11s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format, exploration.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturtions",
                    "%Success") + "\n");
            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][0]);
                    }
                    writer.write(String.format(format, perturbatorsName[indexPerturbator], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            result.get(3), result.get(4),
                            result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                    resultForLocation = resultForLocation.add(result);
                }
                Logger.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile, locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();

            format = "%-30s %-30s";
            for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                writer = new FileWriter(pathToOutPutFile + "_search_space_size_" + perturbatorsName[indexPerturbator] + ".txt", false);
                writer.write("detail of space for " + this.name + " with " + exploration.getColumnName() + " = " + perturbatorsName[indexPerturbator] + "\n");
                writer.write(String.format(format, "number of Task : ", Runner.numberOfTask) + "\n");
                writer.write(String.format(format, "number of Locations : ", Runner.locations.size()) + "\n");
                writer.write(String.format(format, "number of executions done : ", searchSpaceSizePerMagnitude[indexPerturbator]) + "\n");
                writer.write(String.format(format, "number of successful executions done : ", numberOfSuccessPerMagnitude[indexPerturbator]) + "\n");
                writer.write(String.format(format, "% Success : ", Util.getStringPerc(numberOfSuccessPerMagnitude[indexPerturbator], searchSpaceSizePerMagnitude[indexPerturbator])) + "\n");
                writer.close();
            }

            /* Sum PerturbationPoint */
            format = "%-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-17s %-10s %-27s";
            for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                writer = new FileWriter(pathToOutPutFile + "_per_location_" + perturbatorsName[indexPerturbator] + ".txt", false);
                writer.write("aggregate data per location for " + exploration.getColumnName() + " = " + perturbatorsName[indexPerturbator] + "\n");
                writer.write(exploration.getHeader() + Runner.manager.getHeader());

                writer.write(String.format(format, "IndexLoc",
                        "#Success", "#Failure", "#Exception",
                        "#CallAllExecs", "AvgCallPerExec",
                        "#Perturbations", "AvgPerturbationPerExec",
                        "#Execs", "#ExecsRef", "AvgCallRefPerTask",
                        "#Tasks", "%Success") + "\n");

                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = new Tuple(6);
                    int accExecsRef = 0;
                    for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][0]);
                        accExecsRef += nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                    }
                    double avgCall = (double) result.get(3) / (double) result.get(5);
                    double avgPerturbation = (double) result.get(4) / (double) result.get(5);
                    double avgCallRefPerTask = (double) accExecsRef / (Runner.numberOfTask);

                    writer.write(String.format(format, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            result.get(3), String.format("%.2f", avgCall),
                            result.get(4), String.format("%.2f", avgPerturbation),
                            result.get(5), accExecsRef, avgCallRefPerTask,
                            Runner.numberOfTask,
                            result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                }
                writer.close();
            }

            Logger.writeListOnGivenFile(pathToOutPutFile + "_anti_fragile.txt",
                    "List of ids antifragile points.", locationAntiFragile);
            Logger.writeListOnGivenFile(pathToOutPutFile + "_super_anti_fragile.txt",
                    "List of ids antifragile points.", locationSuperAntiFragile);
            Logger.writeListOnGivenFile(pathToOutPutFile + "_oracle_fragile.txt",
                    "list ids of oracle fragile code : >" + Logger.TOLERANCE +"% of oracle failures", locationOracleFragile);
            Logger.writeListOnGivenFile(pathToOutPutFile + "_exception_fragile.txt",
                    "list ids of exception fragile code : >" + Logger.TOLERANCE +"% of exceptions.", locationExceptionFragile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
