package quicksort_visualization;// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)


import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class QuickSort2 {
    static {
        QuickSort2.initPerturbationLocation0();
    }

    public static PerturbationLocation __L303;

    public static PerturbationLocation __L304;

    public static PerturbationLocation __L305;

    public static PerturbationLocation __L306;

    public static PerturbationLocation __L307;

    public static PerturbationLocation __L308;

    public static PerturbationLocation __L309;

    public static PerturbationLocation __L310;

    public static PerturbationLocation __L311;

    public static PerturbationLocation __L312;

    public static PerturbationLocation __L313;

    public static PerturbationLocation __L314;

    public static PerturbationLocation __L315;

    public static PerturbationLocation __L316;

    public static PerturbationLocation __L317;

    public static PerturbationLocation __L318;

    public static PerturbationLocation __L319;

    public static PerturbationLocation __L320;

    public static PerturbationLocation __L321;

    public static PerturbationLocation __L322;

    public static PerturbationLocation __L323;

    public static PerturbationLocation __L324;

    public static PerturbationLocation __L325;

    public static PerturbationLocation __L326;

    public static PerturbationLocation __L327;

    public static PerturbationLocation __L328;

    public static PerturbationLocation __L329;

    public static PerturbationLocation __L330;

    public static PerturbationLocation __L331;

    public static PerturbationLocation __L332;

    public static PerturbationLocation __L333;

    public static PerturbationLocation __L334;

    public static PerturbationLocation __L335;

    public static PerturbationLocation __L336;

    public static PerturbationLocation __L337;

    public static PerturbationLocation __L338;

    public static PerturbationLocation __L339;

    public static PerturbationLocation __L340;

    public static PerturbationLocation __L341;

    public static PerturbationLocation __L342;

    public static PerturbationLocation __L343;

    public static PerturbationLocation __L344;

    public static PerturbationLocation __L345;

    public int[] values;

    public QuickSort2(int[] values) {
        QuickSort2.this.values = values;
    }

    public void sort(int l, int h) {
        LoggerExecPath.log();
        int[] array = QuickSort2.this.values;
        int i = PerturbationEngine.pint(__L303, l);
        int j = PerturbationEngine.pint(__L304, h);
        int pivot = PerturbationEngine.pint(__L312, array[PerturbationEngine.pint(__L311, ((PerturbationEngine.pint(__L305, l)) + (PerturbationEngine.pint(__L310, ((PerturbationEngine.pint(__L308, ((PerturbationEngine.pint(__L306, h)) - (PerturbationEngine.pint(__L307, l))))) / (PerturbationEngine.pint(__L309, 2)))))))]);
        while (PerturbationEngine.pboolean(__L315, ((PerturbationEngine.pint(__L313, i)) <= (PerturbationEngine.pint(__L314, j))))) {
            while (PerturbationEngine.pboolean(__L319, ((PerturbationEngine.pint(__L317, array[PerturbationEngine.pint(__L316, i)])) < (PerturbationEngine.pint(__L318, pivot)))))
                i++;
            while (PerturbationEngine.pboolean(__L323, ((PerturbationEngine.pint(__L321, array[PerturbationEngine.pint(__L320, j)])) > (PerturbationEngine.pint(__L322, pivot)))))
                j--;
            if (PerturbationEngine.pboolean(__L326, ((PerturbationEngine.pint(__L324, i)) <= (PerturbationEngine.pint(__L325, j))))) {
                swap(array, PerturbationEngine.pint(__L327, i), PerturbationEngine.pint(__L328, j));
                i++;
                j--;
            } 
        }
        if (PerturbationEngine.pboolean(__L331, ((PerturbationEngine.pint(__L329, l)) < (PerturbationEngine.pint(__L330, j))))) {
            sort(PerturbationEngine.pint(__L332, l), PerturbationEngine.pint(__L333, j));
        }
        
        if (PerturbationEngine.pboolean(__L336, ((PerturbationEngine.pint(__L334, i)) < (PerturbationEngine.pint(__L335, h))))) {
            sort(PerturbationEngine.pint(__L337, i), PerturbationEngine.pint(__L338, h));
        }
        
    }

    private void swap(int[] anArrayOfInt, int i, int j) {
        int x = PerturbationEngine.pint(__L340, anArrayOfInt[PerturbationEngine.pint(__L339, i)]);
        anArrayOfInt[PerturbationEngine.pint(__L341, i)] = PerturbationEngine.pint(__L343, anArrayOfInt[PerturbationEngine.pint(__L342, j)]);
        anArrayOfInt[PerturbationEngine.pint(__L344, j)] = PerturbationEngine.pint(__L345, x);
    }

    static private void initPerturbationLocation0() {
        __L303 = new PerturbationLocationImpl("QuickSort2.java:11" , 303 , "Numerical");
        __L304 = new PerturbationLocationImpl("QuickSort2.java:12" , 304 , "Numerical");
        __L305 = new PerturbationLocationImpl("QuickSort2.java:13" , 305 , "Numerical");
        __L306 = new PerturbationLocationImpl("QuickSort2.java:13" , 306 , "Numerical");
        __L307 = new PerturbationLocationImpl("QuickSort2.java:13" , 307 , "Numerical");
        __L308 = new PerturbationLocationImpl("QuickSort2.java:13" , 308 , "Numerical");
        __L309 = new PerturbationLocationImpl("QuickSort2.java:13" , 309 , "Numerical");
        __L310 = new PerturbationLocationImpl("QuickSort2.java:13" , 310 , "Numerical");
        __L311 = new PerturbationLocationImpl("QuickSort2.java:13" , 311 , "Numerical");
        __L312 = new PerturbationLocationImpl("QuickSort2.java:13" , 312 , "Numerical");
        __L313 = new PerturbationLocationImpl("QuickSort2.java:14" , 313 , "Numerical");
        __L314 = new PerturbationLocationImpl("QuickSort2.java:14" , 314 , "Numerical");
        __L315 = new PerturbationLocationImpl("QuickSort2.java:14" , 315 , "Boolean");
        __L316 = new PerturbationLocationImpl("QuickSort2.java:15" , 316 , "Numerical");
        __L317 = new PerturbationLocationImpl("QuickSort2.java:15" , 317 , "Numerical");
        __L318 = new PerturbationLocationImpl("QuickSort2.java:15" , 318 , "Numerical");
        __L319 = new PerturbationLocationImpl("QuickSort2.java:15" , 319 , "Boolean");
        __L320 = new PerturbationLocationImpl("QuickSort2.java:17" , 320 , "Numerical");
        __L321 = new PerturbationLocationImpl("QuickSort2.java:17" , 321 , "Numerical");
        __L322 = new PerturbationLocationImpl("QuickSort2.java:17" , 322 , "Numerical");
        __L323 = new PerturbationLocationImpl("QuickSort2.java:17" , 323 , "Boolean");
        __L324 = new PerturbationLocationImpl("QuickSort2.java:19" , 324 , "Numerical");
        __L325 = new PerturbationLocationImpl("QuickSort2.java:19" , 325 , "Numerical");
        __L326 = new PerturbationLocationImpl("QuickSort2.java:19" , 326 , "Boolean");
        __L327 = new PerturbationLocationImpl("QuickSort2.java:20" , 327 , "Numerical");
        __L328 = new PerturbationLocationImpl("QuickSort2.java:20" , 328 , "Numerical");
        __L329 = new PerturbationLocationImpl("QuickSort2.java:25" , 329 , "Numerical");
        __L330 = new PerturbationLocationImpl("QuickSort2.java:25" , 330 , "Numerical");
        __L331 = new PerturbationLocationImpl("QuickSort2.java:25" , 331 , "Boolean");
        __L332 = new PerturbationLocationImpl("QuickSort2.java:26" , 332 , "Numerical");
        __L333 = new PerturbationLocationImpl("QuickSort2.java:26" , 333 , "Numerical");
        __L334 = new PerturbationLocationImpl("QuickSort2.java:27" , 334 , "Numerical");
        __L335 = new PerturbationLocationImpl("QuickSort2.java:27" , 335 , "Numerical");
        __L336 = new PerturbationLocationImpl("QuickSort2.java:27" , 336 , "Boolean");
        __L337 = new PerturbationLocationImpl("QuickSort2.java:28" , 337 , "Numerical");
        __L338 = new PerturbationLocationImpl("QuickSort2.java:28" , 338 , "Numerical");
        __L339 = new PerturbationLocationImpl("QuickSort2.java:32" , 339 , "Numerical");
        __L340 = new PerturbationLocationImpl("QuickSort2.java:32" , 340 , "Numerical");
        __L341 = new PerturbationLocationImpl("QuickSort2.java:33" , 341 , "Numerical");
        __L342 = new PerturbationLocationImpl("QuickSort2.java:33" , 342 , "Numerical");
        __L343 = new PerturbationLocationImpl("QuickSort2.java:33" , 343 , "Numerical");
        __L344 = new PerturbationLocationImpl("QuickSort2.java:34" , 344 , "Numerical");
        __L345 = new PerturbationLocationImpl("QuickSort2.java:34" , 345 , "Numerical");
    }
}

