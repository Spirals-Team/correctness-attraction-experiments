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
                results[Runner.locations.indexOf(location)][indexTask][0] = new Tuple(4);
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
            Tuple result = new Tuple(4);
            PerturbationEngine.logger.logOn(location);
            result = result.add(runAtTheIndexOfCall(indexOfCall, indexOfTask, location));
            result.set(3, PerturbationEngine.logger.getCalls(location));
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

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<PerturbationLocation>();

        try {
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path, false);
            String format = "%-8s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header() + "All Result\n");
            writer.write(String.format(format, "Task", "IndexLoc", "#Success", "#Failure", "#Error", "#Calls", "%Success"));
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    Tuple result = results[Runner.locations.indexOf(location)][indexTask][0];
                    writer.write(String.format(format, indexTask, location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), result.get(3), Runner.getStringPerc(result.get(0), result.total(3))));
                }
            }
            writer.close();

            /* Sum PerturbationPoint */
            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_perLocation", false);
            format = "%-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header() + "sum of result per Perturbation point\n");
            writer.write(String.format(format, "IndexLoc", "#Success", "#Failure", "#Error", "%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = new Tuple(3);
                for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                    result = result.add(results[Runner.locations.indexOf(location)][indexTask][0]);

                Explorer.addToFragilityList(result, result.total(), location, locationExceptionFragile, locationAntiFragile, locationOracleFragile);

                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), Runner.getStringPerc(result.get(0), result.total())));
            }
            writer.close();

            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_AntiFragile", false);
            for (PerturbationLocation location : locationAntiFragile)
                writer.write(location + "\n");
            writer.close();

            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_OracleFragile", false);
            for (PerturbationLocation location : locationOracleFragile)
                writer.write(location + "\n");
            writer.close();

            writer = new FileWriter("results/" + Runner.oracle.getPath() + "/" + path + "_ExceptionFragile", false);
            for (PerturbationLocation location : locationExceptionFragile)
                writer.write(location + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
