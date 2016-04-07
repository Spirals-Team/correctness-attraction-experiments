package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spirals on 05/04/16.
 */
public class RndExplorerImpl implements Explorer {

    private float[] randomRates = new float[]{0.001f, 0.002f , 0.005f, 0.009f, 0.01f, 0.02f, 0.05f, 0.09f, 0.1f, 0.2f, 0.5f, 0.9f};

    private int seedOfRandomEnactor = 32;

    private String header;

    private String path;

    private Tuple[][][] results;

    private Map<PerturbationLocation, RandomEnactorImpl>[] enactorsOfLocationPerRandomRates;

    public RndExplorerImpl() {

        enactorsOfLocationPerRandomRates = new Map[randomRates.length];

        results = new Tuple[Runner.locations.size()][Runner.oracle.getNumberOfTask()][randomRates.length];

        header = "SERN\n";
        header += "Rates : ";

        for (int indexOfRandomRate = 0 ; indexOfRandomRate < randomRates.length ; indexOfRandomRate++) {
            header += randomRates[indexOfRandomRate] + " ";
            enactorsOfLocationPerRandomRates[indexOfRandomRate] = new HashMap<>();
            for (PerturbationLocation location : Runner.locations) {
                enactorsOfLocationPerRandomRates[indexOfRandomRate].put(location, new RandomEnactorImpl(seedOfRandomEnactor, randomRates[indexOfRandomRate]));
                for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask ++)
                    results[Runner.locations.indexOf(location)][indexTask][indexOfRandomRate] = new Tuple(5);
            }
        }

        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "Random Enactor, seed :" + seedOfRandomEnactor +"\n";
        header += "PONE : Numerical Perturbator\n";

        path = "RndExplorer";
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        location.setPerturbator(new AddNPerturbatorImpl(1));
        for (int indexOfRandomRate = 0 ; indexOfRandomRate < randomRates.length ; indexOfRandomRate++) {

            PerturbationEngine.logger.logOn(location);

            Tuple result = runRandomRate(indexOfTask,location,indexOfRandomRate);

            Tuple resultWithLog = new Tuple(5);
            resultWithLog = resultWithLog.add(result);
            resultWithLog.set(3, PerturbationEngine.logger.getCalls(location));
            resultWithLog.set(4, PerturbationEngine.logger.getEnactions(location));

            results[Runner.locations.indexOf(location)][indexOfTask][indexOfRandomRate] = resultWithLog;

            PerturbationEngine.logger.reset();
        }
        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    private Tuple runRandomRate(int indexOfTask, PerturbationLocation location, int indexOfrandomRate) {
        location.setEnactor(enactorsOfLocationPerRandomRates[indexOfrandomRate].get(location));
        return Runner.runPerturbation(indexOfTask);
    }

    @Override
    public void log() {

        List<PerturbationLocation> locationExceptionFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationOracleFragile = new ArrayList<PerturbationLocation>();
        List<PerturbationLocation> locationAntiFragile = new ArrayList<PerturbationLocation>();

        try {
            /* All Log */
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path, false);
            String format = "%-8s %-8s %-8s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header() + "All Result\n");
            writer.write(String.format(format, "Task", "N", "IndexLoc","#Success","#Failure", "#Error", "#Call", "#Enactions" ,"%Success"));
            for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++) {
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexRandomRates = 0 ; indexRandomRates < randomRates.length ; indexRandomRates++) {
                        Tuple result = results[Runner.locations.indexOf(location)][indexTask][indexRandomRates];
                        writer.write(String.format(format,indexTask, randomRates[indexRandomRates], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                                Runner.getStringPerc(result.get(0), result.total(3))));
                    }
                }
            }
            writer.close();

            /* Sum Arrays */
            writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_perRates", false);
            writer.write(header + Runner.oracle.header() + "sum of result per random Rates\n");
            format = "%-5s %-8s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(String.format(format, "N", "IndexLoc","#Success","#Failure", "#Error", "#Call", "#Enactions" ,"%Success"));
                for (PerturbationLocation location : Runner.locations) {
                    for (int indexRandomRates = 0 ; indexRandomRates < randomRates.length ; indexRandomRates++) {
                        Tuple result = new Tuple(5);
                        for (int indexTask = 0 ; indexTask < Runner.oracle.getNumberOfTask() ; indexTask++)
                            result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexRandomRates]);

                        writer.write(String.format(format, randomRates[indexRandomRates], location.getLocationIndex(),
                                result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                                Runner.getStringPerc(result.get(0), result.total(3))));
                }
            }
            writer.close();

            /* Sum PerturbationPoint */
            writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_perLocation", false);
            writer.write(header + Runner.oracle.header() + "sum of result per Perturbation point\n");
            format = "%-8s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(String.format(format, "IndexLoc","#Success","#Failure", "#Error", "#Call", "#Enactions" ,"%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = new Tuple(5);
                for (int indexRandomRates = 0 ; indexRandomRates < randomRates.length ; indexRandomRates++) {
                    for (int indexTask = 0; indexTask < Runner.oracle.getNumberOfTask(); indexTask++)
                        result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexRandomRates]);
                }

                Explorer.addToFragilityList(result, result.total(3), location, locationExceptionFragile, locationAntiFragile, locationOracleFragile);

                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), result.get(3), result.get(4),
                        Runner.getStringPerc(result.get(0), result.total(3))));
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
