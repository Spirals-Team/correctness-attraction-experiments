package resources;

// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)


import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class Resources {
    static {
        Resources.initPerturbationLocation0();
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

    public static PerturbationLocation __L14;

    public static PerturbationLocation __L15;

    public static PerturbationLocation __L16;

    public static PerturbationLocation __L17;

    public int methodInt() {
        int x = PerturbationEngine.pint(__L9, 0);
        for (int i = PerturbationEngine.pint(__L10, 0); PerturbationEngine.pboolean(__L13, ((PerturbationEngine.pint(__L11, i)) < (PerturbationEngine.pint(__L12, 10)))); i++)
            x = PerturbationEngine.pint(__L16, ((PerturbationEngine.pint(__L14, x)) + (PerturbationEngine.pint(__L15, 1))));
        return PerturbationEngine.pint(__L17, x);
    }

    public boolean methodBoolean() {
        boolean x = PerturbationEngine.pboolean(__L0, true);
        for (int i = PerturbationEngine.pint(__L1, 0); PerturbationEngine.pboolean(__L4, ((PerturbationEngine.pint(__L2, i)) < (PerturbationEngine.pint(__L3, 10)))); i++)
            x = PerturbationEngine.pboolean(__L7, ((PerturbationEngine.pboolean(__L5, x)) && (PerturbationEngine.pboolean(__L6, true))));
        return PerturbationEngine.pboolean(__L8, x);
    }

    private static void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("Resources.java:11", 0, "Boolean");
        __L1 = new PerturbationLocationImpl("Resources.java:12", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("Resources.java:12", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("Resources.java:12", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("Resources.java:12", 4, "Boolean");
        __L5 = new PerturbationLocationImpl("Resources.java:13", 5, "Boolean");
        __L6 = new PerturbationLocationImpl("Resources.java:13", 6, "Boolean");
        __L7 = new PerturbationLocationImpl("Resources.java:13", 7, "Boolean");
        __L8 = new PerturbationLocationImpl("Resources.java:14", 8, "Boolean");
        __L9 = new PerturbationLocationImpl("Resources.java:4", 9, "Numerical");
        __L10 = new PerturbationLocationImpl("Resources.java:5", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("Resources.java:5", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("Resources.java:5", 12, "Numerical");
        __L13 = new PerturbationLocationImpl("Resources.java:5", 13, "Boolean");
        __L14 = new PerturbationLocationImpl("Resources.java:6", 14, "Numerical");
        __L15 = new PerturbationLocationImpl("Resources.java:6", 15, "Numerical");
        __L16 = new PerturbationLocationImpl("Resources.java:6", 16, "Numerical");
        __L17 = new PerturbationLocationImpl("Resources.java:7", 17, "Numerical");
    }
}

