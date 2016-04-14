package quicksort_visualization;

import experiment.AddNExplorerImpl;
import experiment.CallableImpl;
import experiment.Explorer;
import experiment.IntegerAdd1RndEnactorExplorerImpl;
import experiment.Logger;
import experiment.OracleImpl;
import experiment.Runner;
import experiment.Tuple;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2CallableImpl extends CallableImpl<int[]> {

    static List<List<Integer>> lst = new ArrayList<>();

    static Map<String, Map<PerturbationLocation, List<Integer>>> map = new HashMap<>();

    static List<Integer> unperturbed = new ArrayList<>();

    static String[] exp;

    static Tuple[][][][] resultsPerExp;

    static int [] magnitudes;
    static float [] randomRates;

    static int currentExp = 0;

    public QuickSort2CallableImpl(int[] originalValue) {
        super(originalValue);
    }

    @Override
    public int[] call() throws Exception {
        QuickSort2 quicksort = new QuickSort2(this.originalValue);

        LoggerExecPath.init(quicksort);

        quicksort.sort(0, this.originalValue.length - 1);
        PerturbationLocation currentLocation = Runner.locations.stream()
                .filter(location ->
                        !(location.getEnactor() instanceof NeverEnactorImpl))
                .findFirst().get();

        map.get(exp[currentExp]).put(currentLocation, LoggerExecPath.execPath);

        lst.add(LoggerExecPath.execPath);
        return quicksort.values;
    }

    public static void runExp(Explorer explorer) {
        map.put(exp[currentExp], new HashMap<>());
        Runner.run(explorer);
        resultsPerExp[currentExp] = Logger.getResults();
        currentExp++;
    }

    public static void main(String[] args) {
        /* only one array */
        OracleImpl<int[]> oracle = new QuickSort2OracleImpl(1);
        Runner.setup(QuickSort2.class, QuickSort2CallableImpl.class, oracle, int[].class);
        Runner.numberOfSecondsToWait = 30;

        /* no Perturbation */
        QuickSort2 quicksort = new QuickSort2(oracle.get(0));
        LoggerExecPath.init(quicksort);
        quicksort.sort(0, oracle.get(0).length - 1);
        unperturbed = LoggerExecPath.execPath;

        magnitudes = new int[]{1, 2, 5, 10, 20, 50};
        randomRates = new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.5f, 0.9f};
        exp = new String[magnitudes.length + randomRates.length];
        resultsPerExp = new Tuple[magnitudes.length + randomRates.length][][][];

        Explorer[] addNExplorers = new Explorer[magnitudes.length];
        for (int indexMagnitude = 0; indexMagnitude < magnitudes.length; indexMagnitude++) {
            addNExplorers[indexMagnitude] = new AddNExplorerImpl(magnitudes[indexMagnitude]);
            exp[indexMagnitude] = "ADD" + magnitudes[indexMagnitude];
        }

        Explorer[] IntegerAdd1RndEnactorExplorers = new Explorer[randomRates.length];
        for (int indexRandomRate = 0; indexRandomRate < randomRates.length; indexRandomRate++) {
            IntegerAdd1RndEnactorExplorers[indexRandomRate] = new IntegerAdd1RndEnactorExplorerImpl(randomRates[indexRandomRate]);
            exp[magnitudes.length + indexRandomRate] = "RND" + randomRates[indexRandomRate];
        }


        /* Run AddNExplorer */
        for (Explorer explorer : addNExplorers) {
            runExp(explorer);
        }

        /* Run RndExplorer */
        for (Explorer explorer : IntegerAdd1RndEnactorExplorers) {
            runExp(explorer);
        }

        logAll();
        logExp();
    }

    public static void logAll() {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/exec_path_all.txt", false);
            final Random rnd = new Random(68);

            lst = lst.stream().distinct().collect(Collectors.toList());
            final double percentageToTake = (double) 1500 / (double) lst.size();
            lst = lst.stream().filter(list -> rnd.nextFloat() < percentageToTake).collect(Collectors.toList());

            String out = "";

            for (Integer value : unperturbed)
                out += value + " ";
            writer.write(out + "\n");

            for (List<Integer> list : lst) {
                out = "";
                for (Integer value : list)
                    out += value + " ";
                writer.write(out + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logExp() {
        List<List<PerturbationLocation>> listOfLocation = new ArrayList<>();
        for (int i = 0 ; i < magnitudes.length ; i++) {
            listOfLocation.add(log(i));
        }
        logCampaign(listOfLocation, "ADD", 0);
        listOfLocation.clear();
        for (int i = 0 ; i < randomRates.length ; i++) {
            listOfLocation.add(log(magnitudes.length+i));
        }
        logCampaign(listOfLocation, "RND", magnitudes.length);
    }

    public static void logCampaign(List<List<PerturbationLocation>> listOfListofLocations, String cmp, int offset) {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/exec_path_"+cmp+".txt", false);
            String out = "Unperturbed ";
            for (Integer value : unperturbed)
                out += value + " ";
            writer.write(out + "\n");
//            Collections.shuffle(listOfListofLocations);
            for (List<PerturbationLocation> locations : listOfListofLocations) {
                int indexParameters = listOfListofLocations.indexOf(locations) + offset;
                Map<PerturbationLocation, List<Integer>> mapCallsPerLocation = map.get(exp[indexParameters]);
                for (PerturbationLocation location : locations) {
                    int indexLoc = Runner.locations.indexOf(location);
                    Tuple result = resultsPerExp[indexParameters][indexLoc][0][0];
                    writer.write(exp[indexParameters]+"@"+location.getLocationIndex());
                    writer.write("#" + result.get(4));
                    for (Integer value : mapCallsPerLocation.get(location)) {
                        writer.write(value + " ");
                    }
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PerturbationLocation> log(int i) {
        List<PerturbationLocation> locSorted = null;
        try {
            FileWriter writer = new FileWriter("results/" + Runner.oracle.getPath() + "/exec_path_" + exp[i] + ".txt", false);
            Map<PerturbationLocation, List<Integer>> mapCallsPerLocation = map.get(exp[i]);
            List<Integer> distances = new ArrayList<>();
            for (List<Integer> list : mapCallsPerLocation.values()) {
                int distance = 0;
                for (int index = 0; index < Math.min(unperturbed.size(), list.size()); index++) {
                    distance += Math.abs(unperturbed.get(index) - list.get(index));
                }
                distances.add(distance);
            }
            final List<PerturbationLocation> locToBeSorted = new ArrayList<>(mapCallsPerLocation.keySet());
            locSorted = locToBeSorted.stream()
                    .distinct()
                    .sorted((l1, l2) -> -distances.get(locToBeSorted.indexOf(l1)).compareTo(distances.get(locToBeSorted.indexOf(l2))))
                    .collect(Collectors.toList());
            String out = "Unperturbed ";
            for (Integer value : unperturbed)
                out += value + " ";
            writer.write(out + "\n");
            for (PerturbationLocation key : locSorted) {
                out = key.getLocationIndex()  + " ";
                for (Integer value : mapCallsPerLocation.get(key)) {
                    out += value + " ";
                }
                writer.write(out + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locSorted;
    }


}
