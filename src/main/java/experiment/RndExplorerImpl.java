package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
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
public class RndExplorerImpl implements Explorer {

    private float[] randomRates = new float[]{0.001f, 0.002f , 0.005f, 0.009f, 0.01f, 0.02f, 0.05f, 0.09f, 0.1f, 0.2f, 0.5f, 0.9f};

    private int seedOfRandomEnactor = 32;

    private String header;

    private String path;

    private Map<PerturbationLocation, RandomEnactorImpl>[] enactorsOfLocationPerRandomRates;

    private Map<PerturbationLocation, Tuple> resultPerLocation;
    private Map<PerturbationLocation, Tuple> logsPerLocation;

    private Map<PerturbationLocation, Tuple>[] resultPerLocationPerRandomRates;
    private Map<PerturbationLocation, Tuple>[] logsPerLocationPerRandomRates;

    public RndExplorerImpl() {

        enactorsOfLocationPerRandomRates = new Map[randomRates.length];

        resultPerLocationPerRandomRates = new Map[randomRates.length];
        resultPerLocation = new HashMap<>();
        logsPerLocationPerRandomRates = new Map[randomRates.length];
        logsPerLocation = new HashMap<>();

        for (PerturbationLocation location : Runner.locations) {
            resultPerLocation.put(location, new Tuple(3));
            logsPerLocation.put(location, new Tuple(2));
        }

        header = "SERN\n";
        header += "Rates : ";

        for (int indexOfRandomRate = 0 ; indexOfRandomRate < randomRates.length ; indexOfRandomRate++) {
            header += randomRates[indexOfRandomRate] + " ";
            resultPerLocationPerRandomRates[indexOfRandomRate] = new HashMap<>();
            logsPerLocationPerRandomRates[indexOfRandomRate] = new HashMap<>();
            enactorsOfLocationPerRandomRates[indexOfRandomRate] = new HashMap<>();
            for (PerturbationLocation location : Runner.locations) {
                enactorsOfLocationPerRandomRates[indexOfRandomRate].put(location, new RandomEnactorImpl(seedOfRandomEnactor, randomRates[indexOfRandomRate]));
                resultPerLocationPerRandomRates[indexOfRandomRate].put(location, new Tuple(3));
                logsPerLocationPerRandomRates[indexOfRandomRate].put(location, new Tuple(2));
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

            Tuple logs = new Tuple(2);
            logs.set(0, PerturbationEngine.logger.getCalls(location));
            logs.set(1, PerturbationEngine.logger.getEnactions(location));

            resultPerLocation.put(location, resultPerLocation.get(location).add(result));

            logsPerLocation.put(location, resultPerLocation.get(location).add(logs));

            resultPerLocationPerRandomRates[indexOfRandomRate].put(location,
                   resultPerLocationPerRandomRates[indexOfRandomRate].get(location).add(result));
            logsPerLocationPerRandomRates[indexOfRandomRate].put(location,
                    resultPerLocationPerRandomRates[indexOfRandomRate].get(location).add(logs));

            PerturbationEngine.logger.reset();
        }
        location.setPerturbator(new NothingPerturbatorImpl());
        location.setEnactor(new NeverEnactorImpl());
    }

    private Tuple runRandomRate(int indexOfTask, PerturbationLocation location, int indexOfrandomRate) {
//        location.setEnactor(new RandomEnactorImpl(seedOfRandomEnactor, randomRate));
        location.setEnactor(enactorsOfLocationPerRandomRates[indexOfrandomRate].get(location));
        return Runner.runPerturbation(indexOfTask);
    }

    @Override
    public void log() {
        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path, false);
            String format = "%-10s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header());
            writer.write(String.format(format,"IndexLoc","#Success","#Failure", "#Error", "#Call", "#Enactions" ,"%Success"));
            for (PerturbationLocation location : Runner.locations) {
                Tuple result = resultPerLocation.get(location);
                Tuple log = logsPerLocation.get(location);
                writer.write(String.format(format, location.getLocationIndex(),
                        result.get(0), result.get(1), result.get(2), log.get(0), log.get(1),
                        Runner.getStringPerc(result.get(0), result.total())));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/"+path+"_perN", false);
            String format = "%-5s %-10s %-8s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(header + Runner.oracle.header());
            writer.write(String.format(format,"N", "IndexLoc","#Success","#Failure", "#Error", "#Call", "#Enactions" ,"%Success"));
            for (PerturbationLocation location : Runner.locations) {
                for (int i = 0 ; i < randomRates.length ; i++) {
                    Tuple result = resultPerLocationPerRandomRates[i].get(location);
                    Tuple log = logsPerLocationPerRandomRates[i].get(location);
                    writer.write(String.format(format, randomRates[i], location.getLocationIndex(),
                            result.get(0), result.get(1), result.get(2), log.get(0), log.get(1),
                            Runner.getStringPerc(result.get(0), result.total())));
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
