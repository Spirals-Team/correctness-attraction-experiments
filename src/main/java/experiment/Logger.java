package experiment;

import experiment.campaign.Campaign;
import experiment.explorer.Explorer;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 13/04/16.
 */
public class Logger {

    private static Tuple[][][] results;

    private static final int sizeOfTuple = 6;

    public static void init(int numberOfLocations, int numberOfTask, int numberOfPerturbator, @Deprecated int sizeOfEachTuple) {
        results = new Tuple[numberOfLocations][numberOfTask][numberOfPerturbator];
        for (int indexLocation = 0 ; indexLocation < numberOfLocations ; indexLocation ++) {
            for (int indexTask = 0 ; indexTask < numberOfTask ; indexTask++) {
                for (int indexPerturbator = 0 ; indexPerturbator < numberOfPerturbator ; indexPerturbator++) {
                    results[indexLocation][indexTask][indexPerturbator] = new Tuple(sizeOfTuple);
                }
            }
        }
    }

    @Deprecated
    public static Tuple[][][] getResults() {
        return results;
    }

    @Deprecated
    public static void add(int indexLocation, int indexTask, int indexParameters, Tuple result) {
        results[indexLocation][indexTask][indexParameters] = results[indexLocation][indexTask][indexParameters].add(result);
    }

    public static void log(int indexLocation, int indexTask, int indexParameters, Tuple result, String name) {
        Tuple tuple = (new Tuple(sizeOfTuple)).add(result);
        tuple.set(3, PerturbationEngine.loggers.get(name).getCalls(Runner.locations.get(indexLocation)));
        tuple.set(4, PerturbationEngine.loggers.get(name).getEnactions(Runner.locations.get(indexLocation)));
        tuple.set(5, 1);
        results[indexLocation][indexTask][indexParameters] = results[indexLocation][indexTask][indexParameters].add(tuple);
    }

    public static void writeOutput(Explorer explorer, Campaign campaign) {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        //@TODO
        String explorerName = "";
        int numberOfPerturbor = campaign.getPerturbators().size();
        String [] perturbatorsName = campaign.getPerturbatorsName();
        String campaignName = campaign.getName();

        String pathToOutPutFile = "results/"+ Runner.manager.getPath()+"/"+
                explorerName+"_"+campaignName;

        int []searchSpaceSizePerMagnitude = new int[numberOfPerturbor];
        int []numberOfSuccessPerMagnitude = new int[numberOfPerturbor];

        try {
            FileWriter writer = new FileWriter(pathToOutPutFile+"_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write("detail per task and per "+campaign.getColumnName()+".\n" + campaign.getHeader() + Runner.manager.getHeader());
            writer.write(String.format(format,
                    "Task", campaign.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturbations",
                    "%Success") + "\n");
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexPerturbator = 0 ; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexPerturbator];
                        searchSpaceSizePerMagnitude[indexPerturbator] += result.get(5);
                        numberOfSuccessPerMagnitude[indexPerturbator] += result.get(0);
                        writer.write(String.format(format,
                                indexTask, perturbatorsName[indexPerturbator], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2),
                                result.get(3), result.get(4),
                                result.get(4)==0?"NaN":Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                    }

                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter(pathToOutPutFile+"_analysis_graph_data.txt", false);
            writer.write("contains the data for build a graph for analysis.\n" +
                    campaign.getHeader() + Runner.manager.getHeader());
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format, campaign.getColumnName(),"IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturtions",
                    "%Success") + "\n");
            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor ; indexPerturbator++) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0 ; indexTask < Runner.numberOfTask ; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator]);
                    }
                    writer.write(String.format(format, perturbatorsName[indexPerturbator], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            result.get(3), result.get(4),
                            result.get(4)==0?"NaN":Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                    resultForLocation = resultForLocation.add(result);
                }
                Explorer.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();

            format = "%-30s %-30s";
            for (int indexPerturbator = 0 ; indexPerturbator < numberOfPerturbor ; indexPerturbator++) {
                writer = new FileWriter(pathToOutPutFile+"search_space_size_"+perturbatorsName[indexPerturbator]+".txt", false);
                writer.write("detail of space for "+explorerName+" with " + campaign.getColumnName() + " = " + perturbatorsName[indexPerturbator]+"\n");
                writer.write(String.format(format, "number of Task : ", Runner.numberOfTask) + "\n");
                writer.write(String.format(format, "number of Locations : ", Runner.locations.size()) + "\n");
                writer.write(String.format(format, "number of Task done : ", searchSpaceSizePerMagnitude[indexPerturbator]) + "\n");
                writer.write(String.format(format, "number of successful task : ", numberOfSuccessPerMagnitude[indexPerturbator]) + "\n");
                writer.write(String.format(format, "% Success : ", Util.getStringPerc(numberOfSuccessPerMagnitude[indexPerturbator], searchSpaceSizePerMagnitude[indexPerturbator])) + "\n");
                writer.close();
            }

            //@TODO
            String mag_header = "SEPM\n";
            mag_header += Runner.locations.size() + " perturbation point\n";
            mag_header += "N Execution Enactor\n";
            mag_header += "PMAG : Numerical Perturbator\n";

            /* Sum PerturbationPoint */
            format = "%-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
//            format = "%-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-10s %-27s";
            for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor ; indexPerturbator++) {
                writer = new FileWriter(pathToOutPutFile+"_per_location_"+perturbatorsName[indexPerturbator]+ ".txt", false);
                writer.write("aggregate data per location for "+campaign.getColumnName()+" = " + perturbatorsName[indexPerturbator] + "\n");
                writer.write(campaign.getHeader() + Runner.manager.getHeader());

                writer.write(String.format(format, "IndexLoc",
                        "#Success", "#Failure", "#Exception",
                        "#CallAllExecs", "AvgCallPerExec",
                        "#Perturbations", "AvgPerturbationPerExec",
                        "#Execs", "#Tasks", "%Success") + "\n");

//                writer.write(String.format(format, "IndexLoc",
//                        "#Success", "#Failure", "#Exception",
//                        "#CallAllExecs", "AvgCallPerExec",
//                        "#Perturbations", "AvgPerturbationPerExec",
//                        "#Execs", "#ExecsRef", #Tasks", "%Success") + "\n");

                //@TODO Missing a column : execsRef

                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = new Tuple(6);
//                    int accNbOfTasks = 0;
                    int nbExecution = 0;
                    for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator]);
//                        accNbOfTasks += nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
//                        accNbExecAllTask += nbExecsPerLocationPerTaskPerMagnitude[Runner.locations.indexOf(location)][indexTask][indexPerturbator];
                        nbExecution += result.get(5);
                    }
                    double avgCall = (double)result.get(3) / (double)nbExecution;
                    double avgPerturbation = (double)result.get(4) / (double)nbExecution;

                    writer.write(String.format(format, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            result.get(3), String.format("%.2f", avgCall),
                            result.get(4), String.format("%.2f", avgPerturbation),
                            nbExecution, Runner.numberOfTask,
                            result.get(4)==0?"NaN":Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                }
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
