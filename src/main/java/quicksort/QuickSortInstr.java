package quicksort;

import java.util.LinkedList;
import java.util.List;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class QuickSortInstr {
    static {
        QuickSortInstr.initPerturbationLocation0();
    }

    public static PerturbationLocation __L0;

    public static PerturbationLocation __L1;

    public static PerturbationLocation __L2;

    public static PerturbationLocation __L3;

    public static PerturbationLocation __L4;

    public static PerturbationLocation __L5;

    public static PerturbationLocation __L6;

    public static PerturbationLocation __L7;

    public static PerturbationLocation __L8;

    public static PerturbationLocation __L9;

    public static PerturbationLocation __L10;

    public static PerturbationLocation __L11;

    public static PerturbationLocation __L12;

    public static PerturbationLocation __L13;

    public static List<Integer> sort(List<Integer> arr) {
        if (!(PerturbationEngine.pboolean(__L0, arr.isEmpty()))) {
            Integer pivot = PerturbationEngine.pint(__L2, arr.get(PerturbationEngine.pint(__L1, 0)));
            List<Integer> less = new LinkedList<Integer>();
            List<Integer> pivotList = new LinkedList<Integer>();
            List<Integer> more = new LinkedList<Integer>();
            for (Integer i : arr) {
                if (PerturbationEngine.pboolean(__L6, ((PerturbationEngine.pint(__L4, i.compareTo(PerturbationEngine.pint(__L3, pivot)))) < (PerturbationEngine.pint(__L5, 0)))))
                    less.add(PerturbationEngine.pint(__L7, i));
                else if (PerturbationEngine.pboolean(__L11, ((PerturbationEngine.pint(__L9, i.compareTo(PerturbationEngine.pint(__L8, pivot)))) > (PerturbationEngine.pint(__L10, 0)))))
                    more.add(PerturbationEngine.pint(__L12, i));
                else
                    pivotList.add(PerturbationEngine.pint(__L13, i));
                
            }
            less = QuickSortInstr.sort(less);
            more = QuickSortInstr.sort(more);
            less.addAll(pivotList);
            less.addAll(more);
            return less;
        } 
        return arr;
    }

    private static void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("QuickSort.java:12" , 0 , "Boolean");
        __L1 = new PerturbationLocationImpl("QuickSort.java:13" , 1 , "Numerical");
        __L2 = new PerturbationLocationImpl("QuickSort.java:13" , 2 , "Numerical");
        __L3 = new PerturbationLocationImpl("QuickSort.java:21" , 3 , "Numerical");
        __L4 = new PerturbationLocationImpl("QuickSort.java:21" , 4 , "Numerical");
        __L5 = new PerturbationLocationImpl("QuickSort.java:21" , 5 , "Numerical");
        __L6 = new PerturbationLocationImpl("QuickSort.java:21" , 6 , "Boolean");
        __L7 = new PerturbationLocationImpl("QuickSort.java:22" , 7 , "Numerical");
        __L8 = new PerturbationLocationImpl("QuickSort.java:23" , 8 , "Numerical");
        __L9 = new PerturbationLocationImpl("QuickSort.java:23" , 9 , "Numerical");
        __L10 = new PerturbationLocationImpl("QuickSort.java:23" , 10 , "Numerical");
        __L11 = new PerturbationLocationImpl("QuickSort.java:23" , 11 , "Boolean");
        __L12 = new PerturbationLocationImpl("QuickSort.java:24" , 12 , "Numerical");
        __L13 = new PerturbationLocationImpl("QuickSort.java:26" , 13 , "Numerical");
    }
}

