package experiment;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by spirals on 13/04/16.
 */
public class Logger {

    /**
     * Tuple results with 4 dimensions Location Task Perturbator Enactor
     */
    private static Tuple[][][][] results;

    private static int sizeOfTuple;

    /**
     * Init logger with Tuple 6 with the given numbers and 1 enactor.
     * @param numberOfLocations
     * @param numberOfTask
     * @param numberOfPerturbator
     */
    public static void init(int numberOfLocations, int numberOfTask, int numberOfPerturbator) {
        init(numberOfLocations, numberOfTask, numberOfPerturbator, 1, 6);
    }

    /**
     * Init logger with Tuple 6 with the given numbers.
     * @param numberOfLocations
     * @param numberOfTask
     * @param numberOfPerturbator
     * @param numberOfEnactor
     */
    public static void init(int numberOfLocations, int numberOfTask, int numberOfPerturbator, int numberOfEnactor) {
        init(numberOfLocations, numberOfTask, numberOfPerturbator, numberOfEnactor, 6);
    }

    /**
     * Init logger with Tuple with the given numbers.
     * @param numberOfLocations
     * @param numberOfTask
     * @param numberOfPerturbator
     * @param numberOfEnactor
     * @param sizeOfEachTuple
     */
    public static void init(int numberOfLocations, int numberOfTask, int numberOfPerturbator, int numberOfEnactor, int sizeOfEachTuple) {
        sizeOfTuple = sizeOfEachTuple;
        results = new Tuple[numberOfLocations][numberOfTask][numberOfPerturbator][numberOfEnactor];
        for (int indexLocation = 0 ; indexLocation < numberOfLocations ; indexLocation ++) {
            for (int indexTask = 0 ; indexTask < numberOfTask ; indexTask++) {
                for (int indexPerturbator = 0 ; indexPerturbator < numberOfPerturbator ; indexPerturbator++) {
                    for (int indexEnactor = 0 ; indexEnactor < numberOfEnactor ; indexEnactor++)
                    results[indexLocation][indexTask][indexPerturbator][indexEnactor] = new Tuple(sizeOfTuple);
                }
            }
        }
    }

    public static Tuple[][][][] getResults() {
        return results;
    }

    @Deprecated
    public static void add(int indexLocation, int indexTask, int indexPerturbator, int indexEnactor, Tuple result) {
        results[indexLocation][indexTask][indexPerturbator][indexEnactor] = results[indexLocation][indexTask][indexPerturbator][indexEnactor].add(result);
    }

    /**
     * Method log for explorer : it has side effect ie it will add calls and enactions of the given location and
     * increment by one the 6th integer to count the number of time it has been called.
     * This Method assume Tuple of 6 integers.
     * @param indexLocation
     * @param indexTask
     * @param indexParameters
     * @param indexEnactor
     * @param result
     * @param name
     */
    public static void log(int indexLocation, int indexTask, int indexParameters, int indexEnactor, Tuple result, String name) {
        Tuple tuple = (new Tuple(sizeOfTuple)).add(result);
        tuple.set(3, PerturbationEngine.loggers.get(name).getCalls(Runner.locations.get(indexLocation)));
        tuple.set(4, PerturbationEngine.loggers.get(name).getEnactions(Runner.locations.get(indexLocation)));
        tuple.set(5, 1);
        results[indexLocation][indexTask][indexParameters][indexEnactor] = results[indexLocation][indexTask][indexParameters][indexEnactor].add(tuple);
    }

    @Deprecated
    public static double TOLERANCE = 70.0f;

    @Deprecated
    public static void addToFragilityList(Tuple result, int total, PerturbationLocation location,
                                   List<PerturbationLocation> locationExceptionFragile, List<PerturbationLocation> locationSuperAntiFragile,
                                   List<PerturbationLocation> locationAntiFragile , List<PerturbationLocation> locationOracleFragile) {
        if (result.get(0) == total && result.get(0) != 0)//Super - Antifragile
            locationSuperAntiFragile.add(location);
        else if (Util.perc(result.get(0), total) >= TOLERANCE)//AntiFragile
            locationAntiFragile.add(location);
        else if (Util.perc(result.get(1), total) >= TOLERANCE)//OracleFragile
            locationOracleFragile.add(location);
        else if (Util.perc(result.get(2), total) >= TOLERANCE)//ExceptionFragile
            locationExceptionFragile.add(location);
    }

    @Deprecated
    public static void writeListOnGivenFile(String pathToFile, String header, List<PerturbationLocation> locations) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToFile , false);
            writer.write(header + "\n");
            for (PerturbationLocation location : locations)
                writer.write(location.getLocationIndex() + " ");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
