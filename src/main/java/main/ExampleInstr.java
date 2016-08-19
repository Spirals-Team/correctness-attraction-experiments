

package main;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class ExampleInstr {
    static {
        ExampleInstr.initPerturbationLocation0();
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

    static String trace = "";

    static int function(int bound) {
        int acc = PerturbationEngine.pint(ExampleInstr.__L0, 0);
        int mask = PerturbationEngine.pint(ExampleInstr.__L1, 2);
        for (int i = PerturbationEngine.pint(ExampleInstr.__L2, bound); (PerturbationEngine.pint(ExampleInstr.__L3, i)) > (PerturbationEngine.pint(ExampleInstr.__L4, 0)); PerturbationEngine.pint(ExampleInstr.__L5, (i--))) {
            int valueOfI = (PerturbationEngine.pint(ExampleInstr.__L6, i));
            acc |= PerturbationEngine.pint(ExampleInstr.__L8, (valueOfI) >> (PerturbationEngine.pint(ExampleInstr.__L7, mask)));
            trace += acc + " & " + valueOfI + " \\\\\n";
        }
        return PerturbationEngine.pint(ExampleInstr.__L9, acc);
    }

    static private void initPerturbationLocation0() {
        ExampleInstr.__L0 = new PerturbationLocationImpl("Example.java:9", 0, "Numerical");
        ExampleInstr.__L1 = new PerturbationLocationImpl("Example.java:10", 1, "Numerical");
        ExampleInstr.__L2 = new PerturbationLocationImpl("Example.java:11", 2, "Numerical");
        ExampleInstr.__L3 = new PerturbationLocationImpl("Example.java:11", 3, "Numerical");
        ExampleInstr.__L4 = new PerturbationLocationImpl("Example.java:11", 4, "Numerical");
        ExampleInstr.__L5 = new PerturbationLocationImpl("Example.java:11", 5, "Numerical");
        ExampleInstr.__L6 = new PerturbationLocationImpl("Example.java:12", 6, "Numerical");
        ExampleInstr.__L7 = new PerturbationLocationImpl("Example.java:12", 7, "Numerical");
        ExampleInstr.__L8 = new PerturbationLocationImpl("Example.java:12", 8, "Numerical");
        ExampleInstr.__L9 = new PerturbationLocationImpl("Example.java:14", 9, "Numerical");
    }
}

