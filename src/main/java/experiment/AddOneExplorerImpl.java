package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
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
public class AddOneExplorerImpl implements Explorer {

    protected int[][] nbOfCallsPerLocationPerTask;

    protected Tuple[][][] results;

    protected String header;

    protected String path;

    public AddOneExplorerImpl() {

        results = new Tuple[Runner.locations.size()][Runner.oracle.getNumberOfTask()][1];

        nbOfCallsPerLocationPerTask = new int[Runner.locations.size()][Runner.oracle.getNumberOfTask()];

        for (PerturbationLocation location : Runner.locations) {
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                results[Runner.locations.indexOf(location)][indexTask][0] = new Tuple(5);
        }

        header = "SEP1\n";
        header += Runner.locations.size() + " perturbation point\n";
        header += "N Excecution Enactor\n";
        header += "PONE : Numerical Perturbator\n";

        path = "AddOneExplorer";
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        //run w/o perturbation
        PerturbationEngine.logger.logOn(location);
        Runner.runPerturbation(indexOfTask);
        int currentNbCall = PerturbationEngine.logger.getCalls(location);
        nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.logger.reset();
        //perturbation
        location.setPerturbator(new AddNPerturbatorImpl(1));
        for (int indexOfCall = 1; indexOfCall < currentNbCall + 1; indexOfCall++) {
            Tuple result = new Tuple(5);
            PerturbationEngine.logger.logOn(location);
            result = result.add(runAtTheIndexOfCall(indexOfCall, indexOfTask, location));
            result.set(3, PerturbationEngine.logger.getCalls(location));
            result.set(4, PerturbationEngine.logger.getEnactions(location));
            PerturbationEngine.logger.reset();
            results[Runner.locations.indexOf(location)][indexOfTask][0] = results[Runner.locations.indexOf(location)][indexOfTask][0].add(result);
        }

        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    protected Tuple runAtTheIndexOfCall(int indexOfCall, int indexOfTask, PerturbationLocation location) {
        location.setEnactor(new NCallEnactorImpl(indexOfCall, location));
        return Runner.runPerturbation(indexOfTask);
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
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-27s%n";
            writer.write(header + Runner.oracle.header() + "All Result : detail per task\n");
            writer.write(String.format(format, "Task", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",  "#Enaction", "#ExeWOPer", "%Success"));
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    searchSpaceSize += nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                    Tuple result = results[Runner.locations.indexOf(location)][indexTask][0];
                    numberOfSuccess += result.get(0);
                    writer.write(String.format(format, indexTask, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexTask] ,Runner.getStringPerc(result.get(0), result.total(3))));
                }
            }
            writer.close();

            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/search_space_size_AddOneExplorer.txt", false);
            writer.write("for " + Runner.oracle.getNumberOfTask() + " tasks with " + Runner.locations.size() +
                    " perturbations points = " + searchSpaceSize + " tasks done.\n");
            writer.write("% Success : " + Runner.getStringPerc(numberOfSuccess, searchSpaceSize) + " \n");
            writer.close();

            /* Sum PerturbationPoint */
            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_per_location_1.txt", false);
            format = "%-10s %-10s %-10s %-10s %-10s %-10s %-27s%n";
            writer.write(header + Runner.oracle.header() + "Aggregate data for all tasks per location for magnitude = 1\n");
            writer.write(String.format(format, "IndexLoc", "#Success", "#Failure", "#Exception",  "#Call",  "#Enaction", "%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = new Tuple(5);
                for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                    result = result.add(results[Runner.locations.indexOf(location)][indexTask][0]);

                Explorer.addToFragilityList(result, result.total(3), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);

                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                        Runner.getStringPerc(result.get(0), result.total(3))));

            }
            writer.close();

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
