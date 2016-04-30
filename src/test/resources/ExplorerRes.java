

// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)


import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class ExplorerRes {
    static {
        ExplorerRes.initPerturbationLocation0();
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

    public int method() {
        int x = PerturbationEngine.pint(__L0, 0);
        for (int i = PerturbationEngine.pint(__L1, 0); PerturbationEngine.pboolean(__L4, ((PerturbationEngine.pint(__L2, i)) < (PerturbationEngine.pint(__L3, 10)))); i++)
            x = PerturbationEngine.pint(__L7, ((PerturbationEngine.pint(__L5, x)) + (PerturbationEngine.pint(__L6, 1))));
        return PerturbationEngine.pint(__L8, x);
    }

    private static void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("ExplorerRes.java:4", 0, "Numerical");
        __L1 = new PerturbationLocationImpl("ExplorerRes.java:5", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("ExplorerRes.java:5", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("ExplorerRes.java:5", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("ExplorerRes.java:5", 4, "Boolean");
        __L5 = new PerturbationLocationImpl("ExplorerRes.java:6", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("ExplorerRes.java:6", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("ExplorerRes.java:6", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("ExplorerRes.java:7", 8, "Numerical");
    }
}

