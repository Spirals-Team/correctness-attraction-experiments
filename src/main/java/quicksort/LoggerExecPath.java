package quicksort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 14/04/16.
 */
public class LoggerExecPath {

    static final List<Integer> execPath = new ArrayList<>();

    static void reset() {
        execPath.clear();
    }

    static void log(int [] array) {
        int nbPairUnsorted = 0;
        for (int i = 0; i < array.length - 1; i++)
            for (int j = i + 1; j < array.length; j++)
                if (array[i] > array[j])
                    nbPairUnsorted++;
        execPath.add(nbPairUnsorted);
    }

}
