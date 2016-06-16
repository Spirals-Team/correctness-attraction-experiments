package quicksort;

import lcs.LCS;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomUniqueEpsilonEnactor;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 13/06/16.
 */
public class DistanceToSuccess {

    private static List<Integer> readFragile(String exploration) {
        String path = "results/quicksort/" + exploration + "_CallExplorer";
        try {
            List<Integer> indexFragilePoints = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path + "_oracle_fragile" + ".txt"));
            br.readLine();//trash
            Arrays.stream(br.readLine().split(" ")).forEach(index -> indexFragilePoints.add(Integer.parseInt(index)));
            br.close();
            return indexFragilePoints;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void computeDistance(PerturbationLocation location) {
        System.out.println(location.getLocationIndex() + "&" + (double) IntStream.range(0, manager.getIndexTask().size())
                .reduce(0, (acc, indexTask) ->
                        acc + runTask(indexTask, location)
                )
                / (double) 20 + "\\\\");
    }

    private static int runTask(int indexTask, PerturbationLocation location) {
        try {
            location.setEnactor(new RandomUniqueEpsilonEnactor(0.3f));
            location.setPerturbator(new AddNPerturbatorImpl(1));
            int[] output = manager.getTask(indexTask);
            try {
                output = manager.getCallable(manager.getTask(indexTask)).call();
            } catch (Exception e) {
            }
            int distance = computeDistance(indexTask, output);
            location.setEnactor(new NeverEnactorImpl());
            location.setPerturbator(new NothingPerturbatorImpl());
            int distanceRef = LCS.lcs(arrayToSTring(manager.getTask(indexTask)), arrayToSTring(manager.getTask(indexTask))).length();
            return 100 - (int) (( (double) distance / (double) distanceRef) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int computeDistance(int indexTask, int[] output) {
            int[] input = manager.getTask(indexTask);
            QuickSort.sort(input, 0, input.length - 1);
            return LCS.lcs(arrayToSTring(input), arrayToSTring(output)).length();
//            return IntStream.range(0, input.length).reduce(0, (acc, index) ->
//                    acc + (input[index] != output[index] ? 1 : 0)
//            );
    }

    private static String arrayToSTring(int[] array) {
        String str = "";
        for (int anArray : array) {
            str += anArray;
        }
        return str;
    }

    private static QuickSortManager manager = new QuickSortManager(20, 100);

    public static void main(String[] args) {
//        String exploration = "BooleanNegation";
        String exploration = "IntegerAddOne";
        List<Integer> fragilesPoints = readFragile(exploration);
        System.out.println(fragilesPoints);
        manager.getLocations().stream().filter(location
                -> fragilesPoints.contains(location.getLocationIndex())
        ).forEach(DistanceToSuccess::computeDistance);
    }


}
