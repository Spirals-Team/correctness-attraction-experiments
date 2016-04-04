package sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by spirals on 23/03/16.
 */
public class Oracle {

    public final static int seedOfTheRandomUsed = 23;

    public static int numberOfArrayToBeSorted = 10;

    public final static int sizeOfEachArrayToBeSorted = 100;

    public static int boudOfValue = 100;

    private static List<List<Integer>> listOfArraysToBeSorted = new ArrayList<List<Integer>>();

    static {
        initList();
    }

    public static void initList() {
        listOfArraysToBeSorted.clear();
        for (int i = 0; i < numberOfArrayToBeSorted; i++) {
            listOfArraysToBeSorted.add(generateOneListToBeSorted());
        }
    }

    public static List<Integer> generateOneListToBeSorted() {
        java.util.Random rnd = new java.util.Random(seedOfTheRandomUsed);
        List<Integer> newListToBeSorted = new ArrayList<Integer>();
        for (int i = 0; i < sizeOfEachArrayToBeSorted; i++) {
            if (boudOfValue == -1)
                newListToBeSorted.add(rnd.nextInt());
            else
                newListToBeSorted.add(rnd.nextInt(boudOfValue));
        }
        return newListToBeSorted;
    }

    public static List<Integer> getCopyOfListToBeSorted(int i) {
        return new ArrayList<Integer>(listOfArraysToBeSorted.get(i));
    }

    public static boolean check(List<Integer> lst, int i) {

        List<Integer> clone = new ArrayList<Integer>(listOfArraysToBeSorted.get(i));

        Iterator<Integer> it = lst.iterator();

        int previousValue = it.next();

        if (!clone.contains(previousValue))
            return false;
        else
            clone.remove(clone.indexOf(previousValue));

        while (it.hasNext()) {
            int current = it.next();
            if (current < previousValue)
                return false;
            else if (!clone.contains(current))
                return false;
            else
                clone.remove(clone.indexOf(current));

            previousValue = current;
        }
        return clone.isEmpty();
    }


}
