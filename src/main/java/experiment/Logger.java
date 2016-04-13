package experiment;

/**
 * Created by spirals on 13/04/16.
 */
public class Logger {

    private static Tuple[][][] results;

    public static void init(int numberOfLocations, int numberOfTask, int numberOfParameters, int sizeOfEachTuple) {
        results = new Tuple[numberOfLocations][numberOfParameters][numberOfTask];
        for (int indexLocation = 0 ; indexLocation < numberOfLocations ; indexLocation ++) {
            for (int indexTask = 0 ; indexTask < numberOfTask ; indexTask++) {
                for (int indexParameters = 0 ; indexParameters < numberOfParameters ; indexParameters++) {
                    results [indexLocation][indexTask][indexParameters] = new Tuple(sizeOfEachTuple);
                }
            }
        }
    }

    public static void add(int indexLocation, int indexTask, int indexParameters, Tuple result) {
        results[indexLocation][indexTask][indexParameters] = results[indexLocation][indexTask][indexParameters].add(result);
    }

    public static Tuple[][][] getResults() {
        return results;
    }

}
