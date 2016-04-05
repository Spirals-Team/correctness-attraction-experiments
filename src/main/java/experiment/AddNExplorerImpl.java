package experiment;

import perturbation.PerturbationEngine;
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
public class AddNExplorerImpl extends AddOneExplorerImpl {

    private int [] magnitudes = new int[]{1,2,5,10,20,50};

    private Map<PerturbationLocation, Tuple>[] resultPerLocationPerMagnitude;

    public AddNExplorerImpl() {
        super();
        super.header = "SEPM\n";
        super.header += "magnitudes : ";

        resultPerLocationPerMagnitude = new Map[magnitudes.length];

        for (int i = 0 ; i < magnitudes.length ; i++) {
            super.header += magnitudes[i] + " ";
            resultPerLocationPerMagnitude[i] = new HashMap<>();
            for (PerturbationLocation location : Runner.locations)
                resultPerLocationPerMagnitude[i].put(location, new Tuple(3));
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

            Tuple result = super.runAtTheIndexOfCall(indexOfCall, indexOfTask, location);

            resultPerLocationPerMagnitude[indexMagnitude].put(location,
                    resultPerLocationPerMagnitude[indexMagnitude].get(location).add(result));
            resultPerLocation.put(location, resultPerLocation.get(location).add(result));
        }
    }

    @Override
    public void log() {
        super.log();
        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+super.path+"_perN", false);
            String format = "%-5s %-10s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header());
            writer.write(String.format(format,"N", "IndexLoc","#Success","#Failure", "#Error","%Success"));
            for (PerturbationLocation location : Runner.locations) {
                for (int i = 0 ; i < magnitudes.length ; i++) {
                    Tuple result = resultPerLocationPerMagnitude[i].get(location);
                    writer.write(String.format(format, magnitudes[i], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), Runner.getStringPerc(result.get(0), result.total())));
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
