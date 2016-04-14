package quicksort_visualization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 14/04/16.
 */
public class LoggerExecPath {

    public static List<Integer> execPath;

    public static QuickSort2 quickSort2;

    public static String parameter;

    public static void init(QuickSort2 quickSort) {
        quickSort2 = quickSort;
        execPath = new ArrayList<>();
    }

    public static void log() {
        int nbPairUnsorted = 0;
        for (int i = 0; i < quickSort2.values.length - 1; i++)
            if (quickSort2.values[i] > quickSort2.values[i + 1])
                nbPairUnsorted++;
        execPath.add(nbPairUnsorted);
    }

}
