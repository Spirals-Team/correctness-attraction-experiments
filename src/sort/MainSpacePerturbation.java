package sort;

import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by spirals on 01/04/16.
 */
public class MainSpacePerturbation {

    public static List<PerturbationLocation> locations= PerturbationLocation.getLocationFromClass(QuickSort.class).
            stream().filter(location -> location.getType().equals("Numerical")).collect(Collectors.toList());

    public static int[] totalCallsPerArrayToSort = new int[Oracle.numberOfArrayToBeSorted];

    public static Map<PerturbationLocation, Integer>[] nbCallsPerLocation = new Map[Oracle.numberOfArrayToBeSorted];

    public static Map<PerturbationLocation, Result> resultPerLocation = new HashMap<>();

    public static final int [] numberToAdd = new int[]{1, 10, 100, 1000, 10000};

    static {
        for (int i = 0 ; i < Oracle.numberOfArrayToBeSorted ; i++)
            nbCallsPerLocation[i] = new HashMap<>();

        locations.stream().forEach(location -> resultPerLocation.put(location, new Result()));
    }

    public static void main(String[] args) {

        /* Initialisation of number of Calls total in the loop, per Location in subroutine */
        for (int indexOfArray = 0; indexOfArray < Oracle.numberOfArrayToBeSorted; indexOfArray++) {
            totalCallsPerArrayToSort[indexOfArray] = runSortOnList(indexOfArray);
        }

        PerturbationEngine.logger.reset();

        for (int indexOfValueToAdd = 0 ; indexOfValueToAdd < numberToAdd.length ; indexOfValueToAdd++) {
            Result[] resultsPerArray = runPerturbation(numberToAdd[indexOfValueToAdd]);
            Logger.log(resultsPerArray, numberToAdd[indexOfValueToAdd]);
        }

    }

    public static Result[] runPerturbation(int valueToAdd) {
        Result[] resultsPerArray = new Result[Oracle.numberOfArrayToBeSorted];
        for (int indexOfArray = 0; indexOfArray < Oracle.numberOfArrayToBeSorted ; indexOfArray++) {
            resultsPerArray[indexOfArray] = runList(valueToAdd, indexOfArray);
        }
        return resultsPerArray;
    }

    public static Result runList(int valueToAdd, int indexOfArray) {
        Result resultForArray = new Result();
        for (PerturbationLocation location : locations) {
            location.setPerturbator(new AddNPerturbatorImpl(valueToAdd));
            Result result = runLocationOnList(indexOfArray, location);
            resultPerLocation.put(location, resultPerLocation.get(location).add(result));
            resultForArray = resultForArray.add(result);
        }
        return resultForArray;
    }

    public static Result runLocationOnList(int indexOfArray, PerturbationLocation location) {
        Result result = new Result();

        for (int currentMagnitude = 1 ; currentMagnitude <= nbCallsPerLocation[indexOfArray].get(location) ; currentMagnitude++) {

            location.setEnactor(new NCallEnactorImpl(currentMagnitude, location));

            if (runPerturbationWithMagnitude(indexOfArray)) //Success
                result.numberOfSuccess++;
             else //Failure
                result.numberOfFailure++;

            PerturbationEngine.logger.reset();
        }

        return result;
    }

    public static boolean runPerturbationWithMagnitude(int indexOfArray) {
        try {
            List<Integer> sortedLst = QuickSort.sort(Oracle.getCopyOfListToBeSorted(indexOfArray));
            return Oracle.check(sortedLst, indexOfArray);
        } catch (Exception | Error e) {
            return false;
        }
    }

    /* Monitor each PerturbationLocation w/o enact it to retrieve the number of call per list to be sorted */
    public static int runSortOnList(int indexOfArray) {
        int totalOfCalls = 0;
        for (PerturbationLocation location : locations) {
            totalOfCalls += monitorSortOnListWithoutPerturbation(indexOfArray, location);
        }
        return totalOfCalls;
    }

    /* Monitor one Perturbation Location */
    public static int monitorSortOnListWithoutPerturbation(int indexOfArray, PerturbationLocation location) {
        PerturbationEngine.logger.logOn(location);
        QuickSort.sort(Oracle.getCopyOfListToBeSorted(indexOfArray));
        nbCallsPerLocation[indexOfArray].put(location, PerturbationEngine.logger.getCalls(location));
        PerturbationEngine.logger.reset();
        return nbCallsPerLocation[indexOfArray].get(location);
    }

}
