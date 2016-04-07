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

    private int [] magnitudes = new int[]{1,2,5,10,20,50};

    public AddNExplorerImpl() {
        super();
        super.header = "SEPM\n";
        super.header += "magnitudes : ";

        super.results = new Tuple[Runner.locations.size()][Runner.oracle.getNumberOfTask()][magnitudes.length];

        for (int i = 0 ; i < magnitudes.length ; i++) {
            super.header += magnitudes[i] + " ";
            for (PerturbationLocation location : Runner.locations) {
                for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++)
                    super.results[Runner.locations.indexOf(location)][indexTask][i] = new Tuple(3);
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

            Tuple result = super.runAtTheIndexOfCall(indexOfCall, indexOfTask, location);

            results[Runner.locations.indexOf(location)][indexOfTask][indexMagnitude] =
                    results[Runner.locations.indexOf(location)][indexOfTask][indexMagnitude].add(result);
        }
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<PerturbationLocation>();

        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+super.path, false);
            String format = "%-8s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header() + "All Result\n");
            writer.write(String.format(format,"Task", "N", "IndexLoc", "#Success","#Failure", "#Error","%Success"));
            for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexMagnitude = 0 ; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexMagnitude];
                        writer.write(String.format(format,indexTask, magnitudes[indexMagnitude], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), Runner.getStringPerc(result.get(0), result.total())));
                    }
                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_perRates", false);
            writer.write(header + Runner.oracle.header() + "sum of result per magnitudes\n");
            format = "%-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(String.format(format, "N", "IndexLoc","#Success","#Failure", "#Error","%Success"));
            for (PerturbationLocation location : Runner.locations) {
                for (int indexRandomRates = 0 ; indexRandomRates < magnitudes.length ; indexRandomRates++) {
                    Tuple result = new Tuple(3);
                    for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexRandomRates]);

                    writer.write(String.format(format, magnitudes[indexRandomRates], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2),
                            Runner.getStringPerc(result.get(0), result.total())));
                }
            }
            writer.close();

            /* Sum PerturbationPoint */
            writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_perLocation", false);
            format = "%-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header() + "sum of result per Perturbation point\n");
            writer.write(String.format(format, "IndexLoc", "#Success","#Failure", "#Error","%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = new Tuple(3);
                for (int indexMagnitude = 0 ; indexMagnitude < magnitudes.length ; indexMagnitude++) {
                    for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexMagnitude]);
                }

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
