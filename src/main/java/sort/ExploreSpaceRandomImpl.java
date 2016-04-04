package sort;

import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spirals on 04/04/16.
 */
public class ExploreSpaceRandomImpl implements Explore {

    private static float[] randomRates = new float[]{0.01f, 0.05f, 0.1f, 0.5f, 0.9f};

    private static Map<PerturbationLocation, Tuple> resultPerLocation = new HashMap<>();

    private static Map<PerturbationLocation, Tuple> callsPerLocation = new HashMap<>();

    private static Map<Float, Tuple> resultPerValue = new HashMap<>();

    public ExploreSpaceRandomImpl() {
        for (int j = 0 ; j < randomRates.length ; j++) {
            resultPerValue.put(randomRates[j], new Tuple());
        }
        Main.locations.stream().forEach(location -> resultPerLocation.put(location, new Tuple()));
        Main.locations.stream().forEach(location -> callsPerLocation.put(location, new Tuple()));
    }

    public void run(int indexOfArray, PerturbationLocation location) {

        PerturbationEngine.logger.logOn(location);
        Tuple tuple = new Tuple();

        for (int i = 0; i < randomRates.length; i++) {

            location.setEnactor(new RandomEnactorImpl(randomRates[i]));

            if (Main.runPerturbedSort(indexOfArray)) { //Success
                tuple.valueOne++;
                resultPerValue.get(randomRates[i]).valueOne += 1;
            } else {//Failure
                tuple.valueTwo++;
                resultPerValue.get(randomRates[i]).valueTwo += 1;
            }

        }

        resultPerLocation.put(location, resultPerLocation.get(location).add(tuple));

        callsPerLocation.put(location, callsPerLocation.get(location).add(new Tuple(
                PerturbationEngine.logger.getCalls(location), PerturbationEngine.logger.getCalls(location)
        )));

        PerturbationEngine.logger.reset();
        location.setEnactor(new NeverEnactorImpl());
    }

    public void log() {
        try {
            FileWriter writer = new FileWriter("results/sort/resultRandom", false);
            String format = "%-10s %-8s %-8s %-27s%n";
            writer.write(String.format(format,"Value","#Success","#Failure","%Success"));
            for (int j = 0 ; j < randomRates.length ; j++) {
                Float key =  randomRates[j];
                Tuple result = resultPerValue.get(key);
                double perc = Main.perc(result.valueOne, result.total());
                String dash = Main.dash(perc);
                writer.write(String.format(format, key, result.valueOne, result.valueTwo, dash + " " + String.format("%.2f", perc) + " %"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logLocation();
    }

    public void logLocation() {
        try {
            FileWriter writer = new FileWriter("results/sort/resultRandom_locations", false);
            String format = "%-10s %-20s %-8s %-8s %-8s %-8s %-27s%n";
            writer.write(String.format(format,"Index", "Location","#Success","#Failure","#Calls","#Enactions","%Success"));
            for (PerturbationLocation key : Main.locations) {
                Tuple result = resultPerLocation.get(key);
                double perc = Main.perc(result.valueOne, result.total());
                String dash = Main.dash(perc);
                Tuple calls = callsPerLocation.get(key);
                writer.write(String.format(format, key.getLocationIndex() , key.getLocationInCode(),
                        result.valueOne, result.valueTwo, calls.valueOne, calls.valueTwo,
                        dash + " " + String.format("%.2f", perc) + " %"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
