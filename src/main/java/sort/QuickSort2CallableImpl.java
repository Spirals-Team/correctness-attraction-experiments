package sort;

import experiment.AddNExplorerImpl;
import experiment.AddOneExplorerImpl;
import experiment.CallableImpl;
import experiment.OracleImpl;
import experiment.RndExplorerImpl;
import experiment.Runner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class QuickSort2CallableImpl extends CallableImpl<int[]> {

    static List<List<Integer>> lst = new ArrayList<>();

    static boolean log = false;

    public QuickSort2CallableImpl(int[] originalValue) {
        super(originalValue);
    }

    @Override
    public int[] call() throws Exception {
        QuickSort2 quicksort = new QuickSort2(this.originalValue);
        quicksort.sort(0, this.originalValue.length-1);
        quicksort.getListOfPairUnsorted();
        if (log)
            lst.add(quicksort.getListOfPairUnsorted());
        return quicksort.values;
    }

    public static void main(String[] args) {
        OracleImpl<int[]> oracle =  new QuickSort2OracleImpl(1);
        Runner.setup(QuickSort2.class, QuickSort2CallableImpl.class, oracle, int[].class);
        Runner.numberOfSecondsToWait = 30;
        /* no Perturbation */
        QuickSort2 quicksort = new QuickSort2(oracle.get(0));
        quicksort.sort(0, oracle.get(0).length-1);
        quicksort.getListOfPairUnsorted();
        lst.add(quicksort.getListOfPairUnsorted());
        log = true;
        Runner.run(new AddOneExplorerImpl());
        Runner.run(new AddNExplorerImpl());
        Runner.run(new RndExplorerImpl());
        log();
    }

    public static List<List<Integer>> computeFarestList( List<Integer> ref) {
        List<Integer> distances = new ArrayList<>();
        for (List<Integer> list : lst) {
            int distance = 0;
            for (int index = 0 ; index < Math.min(ref.size(), list.size()) ; index++) {
                distance += Math.abs(ref.get(index) - list.get(index));
            }
            distances.add(distance);
        }
        return lst.stream().sorted((l1,l2) ->
                -distances.get(lst.indexOf(l1)).compareTo(distances.get(lst.indexOf(l2)))
        ).collect(Collectors.toList());
    }

    public static List<List<Integer>> computeShorterList(List<Integer> ref) {
        return lst.stream().filter(list ->
                list.size() < ref.size()).sorted( (l1,l2) ->
                new Integer(l1.size()).compareTo(l2.size())
        ).collect(Collectors.toList());
    }

    public static void log() {
        try {
            FileWriter writer = new FileWriter("results/sort2/exec_path.txt", false);
            lst = lst.stream().distinct().filter(list -> list.get(list.size()-1) == 0).collect(Collectors.toList());
            String out =  "";
            for (Integer value : lst.get(0))
                out+= value + " ";
            writer.write(out + "\n");
            List<Integer> ref = lst.get(0);
            lst.remove(0);
            List<List<Integer>> farest = computeFarestList(ref);
            List<List<Integer>> shorter = computeShorterList(ref);

            List<List<Integer>> list = farest.subList(0, Math.min(3, farest.size()));
            list.addAll(shorter.subList(0, Math.min(2, shorter.size())));

            for (int i = 0 ; i < list.size() ; i++) {
                out =  "";
                for (Integer value : list.get(i))
                    out+= value + " ";
                writer.write(out + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
