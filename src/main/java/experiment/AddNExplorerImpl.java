package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public class AddNExplorerImpl extends AddOneExplorerImpl {

    private int [] magnitudes = new int[]{2,5,10,20,50};

    public AddNExplorerImpl(int... magnitudes) {
        super();
        super.header = "SEPM\n";
        super.header += "magnitudes value : ";
        if (magnitudes.length > 0)
            this.magnitudes = magnitudes;
        super.results = new Tuple[Runner.locations.size()][Runner.oracle.getNumberOfTask()][this.magnitudes.length];
        for (int i = 0 ; i < this.magnitudes.length ; i++) {
            super.header += this.magnitudes[i] + " ";
            for (PerturbationLocation location : Runner.locations) {
                for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++)
                    super.results[Runner.locations.indexOf(location)][indexTask][i] = new Tuple(5);
            }
        }
        super.header += "\n" + Runner.locations.size() + " perturbation point\n";
        super.header += "N Excecution Enactor\n";
        super.header += "PMAG : Numerical Perturbator\n";
        super.path = "AddNExplorer";
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {

        PerturbationEngine.logger.logOn(location);
        Runner.runPerturbation(indexOfTask);
        int currentNbCall = PerturbationEngine.logger.getCalls(location);
        nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.logger.reset();

        for (int indexMagnitude = 0 ; indexMagnitude < magnitudes.length ; indexMagnitude++) {
            runOneMagnitude(indexOfTask, location, indexMagnitude);
        }

        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    private void runOneMagnitude(int indexOfTask, PerturbationLocation location, int indexMagnitude) {
        int currentNbCall = nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask];
        location.setPerturbator(new AddNPerturbatorImpl(magnitudes[indexMagnitude]));
        for (int indexOfCall = 1 ; indexOfCall < currentNbCall + 1; indexOfCall++ ){
            Tuple result = new Tuple(5);
            PerturbationEngine.logger.logOn(location);
            result = result.add(super.runAtTheIndexOfCall(indexOfCall, indexOfTask, location));
            result.set(3, PerturbationEngine.logger.getCalls(location));
            result.set(4, PerturbationEngine.logger.getEnactions(location));
            PerturbationEngine.logger.reset();
            results[Runner.locations.indexOf(location)][indexOfTask][indexMagnitude] =
                    results[Runner.locations.indexOf(location)][indexOfTask][indexMagnitude].add(result);
        }
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<>();
        List<PerturbationLocation> locationSuperAntiFragile = new ArrayList<>();

        int searchSpaceSize = 0;
        int numberOfSuccess = 0;

        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+super.path+"_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-27s%n";
            writer.write(header + Runner.oracle.header() + "detail per task and per magnitudes.\n");
            writer.write(String.format(format,"Task", "Magnitude", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call", "#Enaction", "%Success"));
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexMagnitude = 0 ; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexMagnitude];
                        numberOfSuccess += result.get(0);
                        searchSpaceSize += nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                        writer.write(String.format(format,indexTask, magnitudes[indexMagnitude], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), Runner.getStringPerc(result.get(0), result.total(3))));
                    }
                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_magnitude_analysis_graph_data.txt", false);
            writer.write(header + Runner.oracle.header() + "contains the data for the magnitude analysis graph.\n");
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-27s%n";
            writer.write(String.format(format, "Magnitude", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call", "#Enaction", "%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexMagnitude = 0; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexMagnitude]);

                    writer.write(String.format(format, magnitudes[indexMagnitude], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            Runner.getStringPerc(result.get(0), result.total(3))));

                    resultForLocation = resultForLocation.add(result);
                }
                Explorer.addToFragilityList(resultForLocation, resultForLocation.total(), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);
            }
            writer.close();


            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/search_space_size_AddNExplorer.txt", false);
            writer.write("for " + Runner.oracle.getNumberOfTask() + " tasks with " + Runner.locations.size() +
                    " perturbations points = " + searchSpaceSize + " tasks done.\n");
            writer.write("% Success : " + Runner.getStringPerc(numberOfSuccess, searchSpaceSize) + " \n");
            writer.close();


            /* Sum PerturbationPoint */
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-27s%n";
            for (int indexMagnitude = 0; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_per_location_"+magnitudes[indexMagnitude]+ ".txt", false);
                writer.write(header + Runner.oracle.header() + "aggregate data per location for magnitude = " + magnitudes[indexMagnitude] + "\n");
                writer.write(String.format(format, "IndexLoc", "#Success","#Failure", "#Exception", "#Call", "#Enaction", "%Success"));
                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = new Tuple(5);
                    for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexMagnitude]);

                    writer.write(String.format(format, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3),result.get(4), Runner.getStringPerc(result.get(0), result.total(3))));
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
