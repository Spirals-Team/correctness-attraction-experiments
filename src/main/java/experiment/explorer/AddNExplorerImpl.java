package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.Util;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
@Deprecated
public class AddNExplorerImpl extends AddOneExplorerImpl {

    public AddNExplorerImpl(int... ArrayOfMagnitudes) {
        super();
        super.header = "Systematical Exploration Plus Magnitudes\n";
        super.header += "magnitude value : ";
        if (ArrayOfMagnitudes.length > 0)
            super.magnitudes = ArrayOfMagnitudes;
        else
            super.magnitudes = new int[]{2,5,10,20,50};

        Logger.init(Runner.locations.size(),Runner.numberOfTask, super.magnitudes.length, 5);

        for (int magnitude : super.magnitudes)
            super.header += magnitude + " ";

        super.nbExecsPerLocationPerTaskPerMagnitude = new int[Runner.locations.size()][Runner.numberOfTask][super.magnitudes.length];

        super.header += "\n" + Runner.locations.size() + " perturbation point\n";
        super.header += "N Execution Enactor\n";
        super.header += "PMAG : Numerical Perturbator\n";
        super.name = "AddNExplorer";
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        super.run(indexOfTask, location);
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        int []searchSpaceSizePerMagnitude = new int[super.magnitudes.length];
        int []numberOfSuccessPerMagnitude = new int[super.magnitudes.length];

        Tuple[][][][] results = Logger.getResults();

        try {
            FileWriter writer = new FileWriter("results/"+ Runner.manager.getPath()+"/"+super.name+"_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write("detail per task and per magnitudes.\n" + super.header + Runner.manager.getHeader());
            writer.write(String.format(format,"Task", "Magnitude", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",
                    "#Perturbations", "%Success") + "\n");
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexMagnitude = 0 ; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexMagnitude][0];
                        searchSpaceSizePerMagnitude[indexMagnitude] += nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                        numberOfSuccessPerMagnitude[indexMagnitude] += result.get(0);
                        writer.write(String.format(format,indexTask, magnitudes[indexMagnitude], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                                result.get(4)==0?"NaN": Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                    }

                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter("results/"+ Runner.manager.getPath()+"/"+super.name+"_magnitude_analysis_graph_data.txt", false);
            writer.write("contains the data for the magnitude analysis graph.\n" + super.header + Runner.manager.getHeader());
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format, "Magnitude", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",
                    "#Perturtions", "%Success") + "\n");
            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexMagnitude = 0; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0 ; indexTask < Runner.numberOfTask ; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexMagnitude][0]);
                    }
                    writer.write(String.format(format, magnitudes[indexMagnitude], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            result.get(4)==0?"NaN":Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                    resultForLocation = resultForLocation.add(result);
                }
                Explorer.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();

            format = "%-30s %-30s";
            for (int indexMagnitude = 0 ; indexMagnitude < this.magnitudes.length ; indexMagnitude++) {
                writer = new FileWriter("results/" + Runner.manager.getPath() + "/search_space_size_AddNExplorer_"+this.magnitudes[indexMagnitude]+".txt", false);
                writer.write("detail of space for AddNExplore with magnitude = " + this.magnitudes[indexMagnitude]+"\n");
                writer.write(String.format(format, "number of Task : ", Runner.numberOfTask) + "\n");
                writer.write(String.format(format, "number of Locations : ", Runner.locations.size()) + "\n");
                writer.write(String.format(format, "number of Task done : ", searchSpaceSizePerMagnitude[indexMagnitude]) + "\n");
                writer.write(String.format(format, "number of successful task : ", numberOfSuccessPerMagnitude[indexMagnitude]) + "\n");
                writer.write(String.format(format, "% Success : ", Util.getStringPerc(numberOfSuccessPerMagnitude[indexMagnitude], searchSpaceSizePerMagnitude[indexMagnitude])) + "\n");
                writer.close();
            }

            String mag_header = "SEPM\n";
            mag_header += Runner.locations.size() + " perturbation point\n";
            mag_header += "N Execution Enactor\n";
            mag_header += "PMAG : Numerical Perturbator\n";

            /* Sum PerturbationPoint */
            format = "%-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-10s %-27s";
            for (int indexMagnitude = 0; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                writer = new FileWriter("results/"+ Runner.manager.getPath()+"/"+super.name+"_per_location_"+magnitudes[indexMagnitude]+ ".txt", false);
                writer.write("aggregate data per location for magnitude = " + magnitudes[indexMagnitude] + "\n" + mag_header + Runner.manager.getHeader());

                writer.write(String.format(format, "IndexLoc", "#Success", "#Failure", "#Exception",
                        "#CallAllExecs", "AvgCallPerExec",
                        "#Perturbations", "AvgPerturbationPerExec",
                        "#Execs", "#CallRef", "#Tasks", "%Success") + "\n");

                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = new Tuple(5);
                    int accNbOfTasks = 0;
                    int accNbExecAllTask = 0;
                    for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexMagnitude][0]);
                        accNbOfTasks += nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                        accNbExecAllTask += nbExecsPerLocationPerTaskPerMagnitude[Runner.locations.indexOf(location)][indexTask][indexMagnitude];
                    }
                    double avgCall = (double)result.get(3) / (double)accNbOfTasks;
                    double avgPerturbation = (double)result.get(4) / (double)accNbOfTasks;

                    writer.write(String.format(format, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            result.get(3), String.format("%.2f", avgCall),
                            result.get(4), String.format("%.2f", avgPerturbation),
                            accNbExecAllTask, accNbOfTasks, Runner.numberOfTask,
                            result.get(4)==0?"NaN":Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                }
                writer.close();
            }

            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + super.name + "_anti_fragile.txt",
                    "List of ids antifragile points.", locationAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + super.name + "_super_anti_fragile.txt",
                    "List of ids antifragile points.", locationSuperAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + super.name + "_oracle_fragile.txt",
                    "list ids of oracle fragile code : >" + TOLERANCE +"% of oracle failures", locationOracleFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + super.name + "_exception_fragile.txt",
                    "list ids of exception fragile code : >" + TOLERANCE +"% of exceptions.", locationExceptionFragile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}