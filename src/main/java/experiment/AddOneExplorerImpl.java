package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spirals on 05/04/16.
 */
public class AddOneExplorerImpl implements Explorer {

    protected int[][] nbOfCallsPerLocationPerTask;

    protected Map<PerturbationLocation, Tuple> resultPerLocation;

    protected String header;

    protected String path;

    public AddOneExplorerImpl() {
        nbOfCallsPerLocationPerTask = new int[Runner.locations.size()][Runner.oracle.getNumberOfTask()];
        resultPerLocation = new HashMap<>();
        for (PerturbationLocation location : Runner.locations) {
            resultPerLocation.put(location, new Tuple(3));
        }
        header = "SEP1\n";
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
        for (int indexOfCall = 1 ; indexOfCall < currentNbCall + 1; indexOfCall++) {
            resultPerLocation.put(location, resultPerLocation.get(location).add(runAtTheIndexOfCall(indexOfCall, indexOfTask, location)));
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
        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path, false);
            String format = "%-10s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header());
            writer.write(String.format(format,"IndexLoc","#Success","#Failure", "#Error","%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = resultPerLocation.get(location);
                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), Runner.getStringPerc(result.get(0), result.total())));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
