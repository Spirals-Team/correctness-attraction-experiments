package sort;

import perturbation.location.PerturbationLocation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 01/04/16.
 */
public class Main {

    public static String path = "results/sort/";

    public static double perc(int nb, int total) {
        return (double)nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0 ; d < perc / 5 ; d++) dash += "-";
        return dash;
    }

    public static List<PerturbationLocation> locations= PerturbationLocation.getLocationFromClass(QuickSort.class).
            stream().filter(location -> location.getType().equals("Numerical")).collect(Collectors.toList());

    public static Explore explorator;

    private static  void run(Explore exploratorImpl) {
        explorator = exploratorImpl;
        for (int indexOfArray = 0; indexOfArray < Oracle.numberOfArrayToBeSorted ; indexOfArray++) {
            runPerturbationLocation(indexOfArray);
        }
        explorator.log();
    }

    private static void runPerturbationLocation(int indexOfArray) {
        for (PerturbationLocation location : locations) {
            explorator.run(indexOfArray, location);
        }
    }

    public static boolean runPerturbedSort(int indexOfArray) {
        try {
            List<Integer> sortedLst = QuickSort.sort(Oracle.getCopyOfListToBeSorted(indexOfArray));
            return Oracle.check(sortedLst, indexOfArray);
        } catch (Exception | Error e) {
           return false;
        }
    }

    public static void main(String[] args) {

        if (args.length == 1)
            path += args[0];
        else if (args.length > 1) {
            path += args[0];
            Oracle.boudOfValue = Integer.parseInt(args[1]);
        }

        run(new ExploreSpaceNCallImpl());
        Oracle.numberOfArrayToBeSorted = 1000;
        Oracle.initList();
        run(new ExploreSpaceRandomImpl());
    }

}
