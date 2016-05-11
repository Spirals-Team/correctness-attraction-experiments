package quicksort_visualization;

import experiment.*;
import experiment.exploration.IntegerExplorationPlusMagnitude;
import experiment.explorer.CallExplorer;
import experiment.explorer.Explorer;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by spirals on 12/04/16.
 */
//@TODO refactor : not working anymore
public class QuickSort2CallableImpl extends CallableImpl<int[],int[]> {

    static List<List<Integer>> paths = new ArrayList<>();

    static List<Integer> unperturbed = new ArrayList<>();

    static int [] magnitudes;

    public QuickSort2CallableImpl(int[] input) {
        super(input);
    }

    @Override
    public int[] call() throws Exception {
        QuickSort2 quicksort = new QuickSort2(this.input);
        LoggerExecPath.init(quicksort);
        quicksort.sort(0, this.input.length - 1);
        paths.add(LoggerExecPath.execPath);
        return quicksort.values;
    }

    public static void main(String[] args) {
        /* only one array */
        Runner.numberOfTask = 1;
        Runner.numberOfSecondsToWait = 30;
        OracleManager<int[]> manager = new QuickSort2Manager(192);
        Runner.setup(QuickSort2.class, QuickSort2CallableImpl.class, manager, "Numerical", int[].class);
        Runner.verbose = true;

        /* no Perturbation */
        QuickSort2 quicksort = new QuickSort2(manager.get(0));
        LoggerExecPath.init(quicksort);
        quicksort.sort(0, manager.get(0).length - 1);
        unperturbed = LoggerExecPath.execPath;

        magnitudes = new int[]{1, 2, 5, 10, 20, 50, 75, 100};

        CallExplorer explorer = new CallExplorer(new IntegerExplorationPlusMagnitude(magnitudes));

        Runner.run(explorer);

        int[][] nbCAllRef = explorer.getNbCallReferencePerLocationPerTask();

        int sumExecsRefs = 0;

        for (int i = 0 ; i < Runner.locations.size() ; i++)
            sumExecsRefs += nbCAllRef[i][0];

        System.out.println(sumExecsRefs);

        log();
    }

    public static List<List<Integer>> getFarthestPath(List<List<Integer>> lst) {

        List<Integer> distances = new ArrayList<>();

        for (List<Integer> path : lst) {
            int distance = 0;
            for (int i = 0 ; i <Math.min(path.size(), unperturbed.size()); i++)
                distance += Math.abs(unperturbed.get(i) - path.get(i));
            distances.add(distance);
        }
        List<List<Integer>> list = lst.stream()
                .distinct()
                .sorted((l1,l2) ->
                        -distances.get(lst.indexOf(l1)).compareTo(distances.get(lst.indexOf(l2)))
                ).collect(Collectors.toList());
        return list.subList(0, Math.min(300, list.size()));
    }

    private static void log() {

        Collections.shuffle(paths.stream().distinct().collect(Collectors.toList())
                , new java.util.Random(23));

        List<List<Integer>> successes = paths.stream()
                .filter(path -> path.get(path.size()-1) == 0)
                .collect(Collectors.toList());

        successes = getFarthestPath(successes);
        successes = successes.subList(0, Math.min(successes.size(),600));

        List<List<Integer>> failures = paths.stream()
                .filter(path -> path.get(path.size()-1) != 0)
                .collect(Collectors.toList());

        failures = getFarthestPath(failures).subList(0, Math.min(successes.size(),600));

        try {

            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/exec_path.txt", false);

            for (Integer pairs : unperturbed)
                writer.write(pairs + " ");
            writer.write("\n");

            failures.addAll(successes);
            Collections.shuffle(failures);

            for (List<Integer> failure : failures) {
                for (Integer pairs : failure)
                    writer.write(pairs + " ");
                writer.write("\n");
            }

            writer.close();

        } catch (Exception e) {}


    }




}
