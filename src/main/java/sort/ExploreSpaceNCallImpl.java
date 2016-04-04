package sort;

import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spirals on 04/04/16.
 */
public class ExploreSpaceNCallImpl implements Explore{

    private static int[] totalCallsPerArrayToSort = new int[Oracle.numberOfArrayToBeSorted];

    private static Map<PerturbationLocation, Integer>[] nbCallsPerLocation = new Map[Oracle.numberOfArrayToBeSorted];

    private static Map<PerturbationLocation, Tuple> resultPerLocation = new HashMap<>();

    private static Map<Integer, Tuple> resultPerValue = new HashMap<>();

    private static final int [] numberToAdd = new int[]{1, 10, 100, 1000, 10000};

    static {
        for (int i = 0; i < Oracle.numberOfArrayToBeSorted; i++) {
            nbCallsPerLocation[i] = new HashMap<>();
        }

        Main.locations.stream().forEach(location -> resultPerLocation.put(location, new Tuple()));

        for (int j = 0 ; j < numberToAdd.length ; j++) {
            resultPerValue.put(numberToAdd[j], new Tuple());
        }
    }

    public ExploreSpaceNCallImpl() {
        for (int indexOfArray = 0; indexOfArray < Oracle.numberOfArrayToBeSorted; indexOfArray++) {
            totalCallsPerArrayToSort[indexOfArray] = runSortOnList(indexOfArray);
        }
    }

    public void run(int indexOfArray, PerturbationLocation location) {
        for (int indexOfValueToAdd = 0 ; indexOfValueToAdd < numberToAdd.length ; indexOfValueToAdd++) {
            location.setPerturbator(new AddNPerturbatorImpl(numberToAdd[indexOfValueToAdd]));
            resultPerValue.put(numberToAdd[indexOfValueToAdd],
                            resultPerValue.get(numberToAdd[indexOfValueToAdd]).add(runMagnitudes(indexOfArray, location)));
        }
    }

    private static Tuple runMagnitudes(int indexOfArray, PerturbationLocation location) {
        Tuple tuple = new Tuple();
        for (int currentMagnitude = 1 ; currentMagnitude <= nbCallsPerLocation[indexOfArray].get(location) ; currentMagnitude++) {
            location.setEnactor(new NCallEnactorImpl(currentMagnitude, location));
            if (Main.runPerturbedSort(indexOfArray)) //Success
                tuple.valueOne++;
            else //Failure
                tuple.valueTwo++;
            PerturbationEngine.logger.reset();
        }
        resultPerLocation.put(location, resultPerLocation.get(location).add(tuple));
        location.setEnactor(new NeverEnactorImpl());
        return tuple;
    }

    /* Monitor each PerturbationLocation w/o enact it to retrieve the number of call per list to be sorted */
    public static int runSortOnList(int indexOfArray) {
        int totalOfCalls = 0;
        for (PerturbationLocation location : Main.locations) {
            totalOfCalls += monitorSortOnListWithoutPerturbation(indexOfArray, location);
        }
        return totalOfCalls;
    }

    /* Monitor one Perturbation Location */
    private static int monitorSortOnListWithoutPerturbation(int indexOfArray, PerturbationLocation location) {
        PerturbationEngine.logger.logOn(location);
        QuickSort.sort(Oracle.getCopyOfListToBeSorted(indexOfArray));
        nbCallsPerLocation[indexOfArray].put(location, PerturbationEngine.logger.getCalls(location));
        PerturbationEngine.logger.reset();
        return nbCallsPerLocation[indexOfArray].get(location);
    }


    public void log() {
        try {
            FileWriter writer = new FileWriter("results/sort/resultNCall", false);
            String format = "%-10s %-8s %-8s %-27s%n";
            writer.write(String.format(format,"Value","#Success","#Failure","%Success"));
            for (int j = 0 ; j < numberToAdd.length ; j++) {
                Integer key =  numberToAdd[j];
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
            FileWriter writer = new FileWriter("results/sort/resultNCall_locations", false);
            String format = "%-10s %-20s %-8s %-8s %-27s%n";
            writer.write(String.format(format,"Index", "Location","#Success","#Failure","%Success"));
            for (PerturbationLocation key : Main.locations) {
                Tuple result = resultPerLocation.get(key);
                double perc = Main.perc(result.valueOne, result.total());
                String dash = Main.dash(perc);
                writer.write(String.format(format, key.getLocationIndex() , key.getLocationInCode(),
                        result.valueOne, result.valueTwo, dash + " " + String.format("%.2f", perc) + " %"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
