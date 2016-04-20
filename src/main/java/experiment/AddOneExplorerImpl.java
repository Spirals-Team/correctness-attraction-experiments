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

    protected String header;

    protected String path;

    public AddOneExplorerImpl() {

        Logger.init(Runner.locations.size(),Runner.numberOfTask,1, 5);

        nbOfCallsPerLocationPerTask = new int[Runner.locations.size()][Runner.numberOfTask];

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
            Logger.add(Runner.locations.indexOf(location), indexOfTask, 0, result);
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

        Tuple [][][] results = Logger.getResults();

        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + path + "_detail.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write("All Result : detail per task\n" + header + Runner.manager.getHeader());
            writer.write(String.format(format, "Task", "IndexLoc", "#Success", "#Failure", "#Exception", "#Call",
                    "#Perturbations", "%Success") + "\n");
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    searchSpaceSize += nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                    Tuple result = results[Runner.locations.indexOf(location)][indexTask][0];
                    numberOfSuccess += result.get(0);
                    writer.write(String.format(format, indexTask, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                            Util.getStringPerc(result.get(0), result.total(3))) + "\n");
                }
            }
            writer.close();

            format = "%-30s %-30s";
            writer = new FileWriter("results/" + Runner.manager.getPath() + "/search_space_size_AddOneExplorer.txt", false);
            writer.write(String.format(format, "number of Task : ",  Runner.numberOfTask) + "\n");
            writer.write(String.format(format,"number of Locations : " , Runner.locations.size()) + "\n");
            writer.write(String.format(format,"number of Task done : " , searchSpaceSize) + "\n");
            writer.write(String.format(format,"number of successful task : " , numberOfSuccess ) + "\n");
            writer.write(String.format(format,"% Success : " , Util.getStringPerc(numberOfSuccess, searchSpaceSize)) + "\n");
            writer.close();

            /* Sum PerturbationPoint */
            writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + path + "_per_location_1.txt", false);
            format = "%-10s %-10s %-10s %-10s %-10s %-14s %-10s %-22s %-27s";
            writer.write("Aggregate data for all tasks per location for magnitude = 1\n"  + header + Runner.manager.getHeader());
            writer.write(String.format(format, "IndexLoc", "#Success", "#Failure", "#Exception",  "#Call",
                    "#Perturbations", "#Tasks",  "AvgPerturbationPerTask", "%Success") + "\n");
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = new Tuple(5);
                int accNbOfTasks = 0;
                for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                    result = result.add(results[Runner.locations.indexOf(location)][indexTask][0]);
                    accNbOfTasks += nbOfCallsPerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                }

                Explorer.addToFragilityList(result, result.total(3), location, locationExceptionFragile,locationSuperAntiFragile,
                        locationAntiFragile, locationOracleFragile);

                double avg = (double)result.get(4) / (double)accNbOfTasks;

                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                        accNbOfTasks, String.format("%.2f", avg),
                        Util.getStringPerc(result.get(0), result.total(3))) + "\n");

            }
            writer.close();

            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + path + "_anti_fragile.txt",
                    "List of ids antifragile points.", locationAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + path + "_super_anti_fragile.txt",
                    "List of ids antifragile points.", locationSuperAntiFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + path + "_oracle_fragile.txt",
                    "list ids of oracle fragile code : >" + Explorer.TOLERANCE +"% of oracle failures", locationOracleFragile);
            Explorer.writeListOnGivenFile("results/" + Runner.manager.getPath() + "/" + path + "_exception_fragile.txt",
                    "list ids of exception fragile code : >" + Explorer.TOLERANCE +"% of exceptions.", locationExceptionFragile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
