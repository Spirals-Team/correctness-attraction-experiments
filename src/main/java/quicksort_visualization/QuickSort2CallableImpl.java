package quicksort_visualization;

import experiment.AddNExplorerImpl;
import experiment.AddOneExplorerImpl;
import experiment.CallableImpl;
import experiment.IntegerAdd1RndEnactorExplorerImpl;
import experiment.OracleImpl;
import experiment.Runner;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2CallableImpl extends CallableImpl<int[]> {

    static List<List<Integer>> lst = new ArrayList<>();

    static Map<String, Map<String, List<Integer>>> map = new HashMap<>();

    static List<Integer> unperturbed = new ArrayList<>();

    static String[] exp = new String[]{"ADD1", "ADDN", "RNDE"};

    static int currentExp = 0;

    static boolean log = false;

    public QuickSort2CallableImpl(int[] originalValue) {
        super(originalValue);
    }

    @Override
    public int[] call() throws Exception {
        QuickSort2 quicksort = new QuickSort2(this.originalValue);
        quicksort.sort(0, this.originalValue.length - 1);
        if (log) {

            PerturbationLocation currentLocation = Runner.locations.stream().filter(location ->
                    !(location.getEnactor() instanceof NeverEnactorImpl)
            ).findFirst().get();

            map.get(exp[currentExp]).put(currentLocation.getLocationIndex()+"", quicksort.getListOfPairUnsorted());

            lst.add(quicksort.getListOfPairUnsorted());
        }
        return quicksort.values;
    }

    public static void main(String[] args) {
        /* only one array */
        OracleImpl<int[]> oracle = new QuickSort2OracleImpl(1);
        Runner.setup(QuickSort2.class, QuickSort2CallableImpl.class, oracle, int[].class);
        Runner.numberOfSecondsToWait = 30;

        /* no Perturbation */
        QuickSort2 quicksort = new QuickSort2(oracle.get(0));
        quicksort.sort(0, oracle.get(0).length - 1);
        unperturbed = quicksort.getListOfPairUnsorted();
        //lst.add(quicksort.getListOfPairUnsorted());

        /* perturbation on */
        log = true;
        map.put(exp[currentExp], new HashMap<>());
        Runner.run(new AddOneExplorerImpl());


        currentExp++;
        map.put(exp[currentExp], new HashMap<>());
        Runner.run(new AddNExplorerImpl());

        currentExp++;
        map.put(exp[currentExp], new HashMap<>());
        Runner.run(new IntegerAdd1RndEnactorExplorerImpl());

        logAll();
        log();
    }

    public static void logAll() {
        try {
            FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/exec_path_all.txt", false);

            lst = lst.stream().distinct().collect(Collectors.toList()).subList(0, 1000);

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

    public static void log() {
        try {

            for (int i = 0; i < exp.length; i++) {
                FileWriter writer = new FileWriter("results/"+ Runner.oracle.getPath()+"/exec_path_" + exp[i] + ".txt", false);

                Map<String, List<Integer>> mapCallsPerLocation = map.get(exp[i]);

                List<Integer> distances = new ArrayList<>();
                for (List<Integer> list : mapCallsPerLocation.values()) {
                    int distance = 0;
                    for (int index = 0; index < Math.min(unperturbed.size(), list.size()); index++) {
                        distance += Math.abs(unperturbed.get(index) - list.get(index));
                    }
                    distances.add(distance);
                }

                final List<String> locToBeSorted = new ArrayList<>(mapCallsPerLocation.keySet());

                List<String> locSorted = locToBeSorted.stream().sorted((l1, l2) ->
                        -distances.get(locToBeSorted.indexOf(l1)).compareTo(distances.get(locToBeSorted.indexOf(l2)))
                ).collect(Collectors.toList()).subList(0, 5);

                String out = "Unperturbed ";
                for (Integer value : unperturbed)
                    out += value + " ";
                writer.write(out + "\n");

                for (String key : locSorted) {
                    out = key + " ";
                    for (Integer value : mapCallsPerLocation.get(key)) {
                        out += value + " ";
                    }
                    writer.write(out + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
