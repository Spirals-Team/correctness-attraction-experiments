

package lcs;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class LCSInstr {
    static {
        LCSInstr.initPerturbationLocation0();
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

    public static PerturbationLocation __L18;

    public static PerturbationLocation __L19;

    public static PerturbationLocation __L20;

    public static PerturbationLocation __L21;

    public static PerturbationLocation __L22;

    public static PerturbationLocation __L23;

    public static PerturbationLocation __L24;

    public static PerturbationLocation __L25;

    public static PerturbationLocation __L26;

    public static PerturbationLocation __L27;

    public static PerturbationLocation __L28;

    public static PerturbationLocation __L29;

    public static PerturbationLocation __L30;

    public static PerturbationLocation __L31;

    public static PerturbationLocation __L32;

    public static PerturbationLocation __L33;

    public static PerturbationLocation __L34;

    public static PerturbationLocation __L35;

    public static PerturbationLocation __L36;

    public static PerturbationLocation __L37;

    public static PerturbationLocation __L38;

    public static PerturbationLocation __L39;

    public static PerturbationLocation __L40;

    public static PerturbationLocation __L41;

    public static PerturbationLocation __L42;

    public static PerturbationLocation __L43;

    public static PerturbationLocation __L44;

    public static PerturbationLocation __L45;

    public static PerturbationLocation __L46;

    public static PerturbationLocation __L47;

    public static PerturbationLocation __L48;

    public static PerturbationLocation __L49;

    public static PerturbationLocation __L50;

    public static PerturbationLocation __L51;

    public static PerturbationLocation __L52;

    public static PerturbationLocation __L53;

    public static PerturbationLocation __L54;

    public static PerturbationLocation __L55;

    public static PerturbationLocation __L56;

    public static PerturbationLocation __L57;

    public static PerturbationLocation __L58;

    public static PerturbationLocation __L59;

    public static PerturbationLocation __L60;

    public static PerturbationLocation __L61;

    public static PerturbationLocation __L62;

    public static PerturbationLocation __L63;

    public static PerturbationLocation __L64;

    public static PerturbationLocation __L65;

    public static PerturbationLocation __L66;

    public static PerturbationLocation __L67;

    public static PerturbationLocation __L68;

    public static PerturbationLocation __L69;

    public static PerturbationLocation __L70;

    public static PerturbationLocation __L71;

    public static PerturbationLocation __L72;

    public static PerturbationLocation __L73;

    public static PerturbationLocation __L74;

    public static PerturbationLocation __L75;

    public static PerturbationLocation __L76;

    public static PerturbationLocation __L77;

    public static PerturbationLocation __L78;

    public static PerturbationLocation __L79;

    public static PerturbationLocation __L80;

    public static PerturbationLocation __L81;

    public static PerturbationLocation __L82;

    public static PerturbationLocation __L83;

    public static PerturbationLocation __L84;

    public static PerturbationLocation __L85;

    public static PerturbationLocation __L86;

    public static PerturbationLocation __L87;

    public static String lcs(String a, String b) {
        int[][] lengths = new int[PerturbationEngine.pint(__L2, ((PerturbationEngine.pint(__L0, a.length())) + (PerturbationEngine.pint(__L1, 1))))][PerturbationEngine.pint(__L5, ((PerturbationEngine.pint(__L3, b.length())) + (PerturbationEngine.pint(__L4, 1))))];
        for (int i = PerturbationEngine.pint(__L6, 0); PerturbationEngine.pboolean(__L9, ((PerturbationEngine.pint(__L7, i)) < (PerturbationEngine.pint(__L8, a.length())))); PerturbationEngine.pint(__L10, (i++)))
            for (int j = PerturbationEngine.pint(__L11, 0); PerturbationEngine.pboolean(__L14, ((PerturbationEngine.pint(__L12, j)) < (PerturbationEngine.pint(__L13, b.length())))); PerturbationEngine.pint(__L15, (j++)))
                if (PerturbationEngine.pboolean(__L18, ((a.charAt(PerturbationEngine.pint(__L16, i))) == (b.charAt(PerturbationEngine.pint(__L17, j))))))
                    lengths[PerturbationEngine.pint(__L21, ((PerturbationEngine.pint(__L19, i)) + (PerturbationEngine.pint(__L20, 1))))][PerturbationEngine.pint(__L24, ((PerturbationEngine.pint(__L22, j)) + (PerturbationEngine.pint(__L23, 1))))] = PerturbationEngine.pint(__L29, ((PerturbationEngine.pint(__L27, lengths[PerturbationEngine.pint(__L25, i)][PerturbationEngine.pint(__L26, j)])) + (PerturbationEngine.pint(__L28, 1))));
                else
                    lengths[PerturbationEngine.pint(__L32, ((PerturbationEngine.pint(__L30, i)) + (PerturbationEngine.pint(__L31, 1))))][PerturbationEngine.pint(__L35, ((PerturbationEngine.pint(__L33, j)) + (PerturbationEngine.pint(__L34, 1))))] = PerturbationEngine.pint(__L46, Math.max(PerturbationEngine.pint(__L40, lengths[PerturbationEngine.pint(__L38, ((PerturbationEngine.pint(__L36, i)) + (PerturbationEngine.pint(__L37, 1))))][PerturbationEngine.pint(__L39, j)]), PerturbationEngine.pint(__L45, lengths[PerturbationEngine.pint(__L41, i)][PerturbationEngine.pint(__L44, ((PerturbationEngine.pint(__L42, j)) + (PerturbationEngine.pint(__L43, 1))))])));
                
        StringBuffer sb = new StringBuffer();
        for (int x = PerturbationEngine.pint(__L47, a.length()), y = PerturbationEngine.pint(__L48, b.length()); PerturbationEngine.pboolean(__L55, ((PerturbationEngine.pboolean(__L51, ((PerturbationEngine.pint(__L49, x)) != (PerturbationEngine.pint(__L50, 0))))) && (PerturbationEngine.pboolean(__L54, ((PerturbationEngine.pint(__L52, y)) != (PerturbationEngine.pint(__L53, 0)))))));) {
            if (PerturbationEngine.pboolean(__L64, ((PerturbationEngine.pint(__L58, lengths[PerturbationEngine.pint(__L56, x)][PerturbationEngine.pint(__L57, y)])) == (PerturbationEngine.pint(__L63, lengths[PerturbationEngine.pint(__L61, ((PerturbationEngine.pint(__L59, x)) - (PerturbationEngine.pint(__L60, 1))))][PerturbationEngine.pint(__L62, y)])))))
                PerturbationEngine.pint(__L65, (x--));
            else if (PerturbationEngine.pboolean(__L74, ((PerturbationEngine.pint(__L68, lengths[PerturbationEngine.pint(__L66, x)][PerturbationEngine.pint(__L67, y)])) == (PerturbationEngine.pint(__L73, lengths[PerturbationEngine.pint(__L69, x)][PerturbationEngine.pint(__L72, ((PerturbationEngine.pint(__L70, y)) - (PerturbationEngine.pint(__L71, 1))))])))))
                PerturbationEngine.pint(__L75, (y--));
            else {
                assert PerturbationEngine.pboolean(__L82, ((a.charAt(PerturbationEngine.pint(__L78, ((PerturbationEngine.pint(__L76, x)) - (PerturbationEngine.pint(__L77, 1)))))) == (b.charAt(PerturbationEngine.pint(__L81, ((PerturbationEngine.pint(__L79, y)) - (PerturbationEngine.pint(__L80, 1))))))));
                sb.append(a.charAt(PerturbationEngine.pint(__L85, ((PerturbationEngine.pint(__L83, x)) - (PerturbationEngine.pint(__L84, 1))))));
                PerturbationEngine.pint(__L86, (x--));
                PerturbationEngine.pint(__L87, (y--));
            }
        }
        return sb.reverse().toString();
    }

    private static void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("LCS.java:12", 0, "Numerical");
        __L1 = new PerturbationLocationImpl("LCS.java:12", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("LCS.java:12", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("LCS.java:12", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("LCS.java:12", 4, "Numerical");
        __L5 = new PerturbationLocationImpl("LCS.java:12", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("LCS.java:16", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("LCS.java:16", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("LCS.java:16", 8, "Numerical");
        __L9 = new PerturbationLocationImpl("LCS.java:16", 9, "Boolean");
        __L10 = new PerturbationLocationImpl("LCS.java:16", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("LCS.java:17", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("LCS.java:17", 12, "Numerical");
        __L13 = new PerturbationLocationImpl("LCS.java:17", 13, "Numerical");
        __L14 = new PerturbationLocationImpl("LCS.java:17", 14, "Boolean");
        __L15 = new PerturbationLocationImpl("LCS.java:17", 15, "Numerical");
        __L16 = new PerturbationLocationImpl("LCS.java:18", 16, "Numerical");
        __L17 = new PerturbationLocationImpl("LCS.java:18", 17, "Numerical");
        __L18 = new PerturbationLocationImpl("LCS.java:18", 18, "Boolean");
        __L19 = new PerturbationLocationImpl("LCS.java:19", 19, "Numerical");
        __L20 = new PerturbationLocationImpl("LCS.java:19", 20, "Numerical");
        __L21 = new PerturbationLocationImpl("LCS.java:19", 21, "Numerical");
        __L22 = new PerturbationLocationImpl("LCS.java:19", 22, "Numerical");
        __L23 = new PerturbationLocationImpl("LCS.java:19", 23, "Numerical");
        __L24 = new PerturbationLocationImpl("LCS.java:19", 24, "Numerical");
        __L25 = new PerturbationLocationImpl("LCS.java:19", 25, "Numerical");
        __L26 = new PerturbationLocationImpl("LCS.java:19", 26, "Numerical");
        __L27 = new PerturbationLocationImpl("LCS.java:19", 27, "Numerical");
        __L28 = new PerturbationLocationImpl("LCS.java:19", 28, "Numerical");
        __L29 = new PerturbationLocationImpl("LCS.java:19", 29, "Numerical");
        __L30 = new PerturbationLocationImpl("LCS.java:21", 30, "Numerical");
        __L31 = new PerturbationLocationImpl("LCS.java:21", 31, "Numerical");
        __L32 = new PerturbationLocationImpl("LCS.java:21", 32, "Numerical");
        __L33 = new PerturbationLocationImpl("LCS.java:21", 33, "Numerical");
        __L34 = new PerturbationLocationImpl("LCS.java:21", 34, "Numerical");
        __L35 = new PerturbationLocationImpl("LCS.java:21", 35, "Numerical");
        __L36 = new PerturbationLocationImpl("LCS.java:22", 36, "Numerical");
        __L37 = new PerturbationLocationImpl("LCS.java:22", 37, "Numerical");
        __L38 = new PerturbationLocationImpl("LCS.java:22", 38, "Numerical");
        __L39 = new PerturbationLocationImpl("LCS.java:22", 39, "Numerical");
        __L40 = new PerturbationLocationImpl("LCS.java:22", 40, "Numerical");
        __L41 = new PerturbationLocationImpl("LCS.java:22", 41, "Numerical");
        __L42 = new PerturbationLocationImpl("LCS.java:22", 42, "Numerical");
        __L43 = new PerturbationLocationImpl("LCS.java:22", 43, "Numerical");
        __L44 = new PerturbationLocationImpl("LCS.java:22", 44, "Numerical");
        __L45 = new PerturbationLocationImpl("LCS.java:22", 45, "Numerical");
        __L46 = new PerturbationLocationImpl("LCS.java:22", 46, "Numerical");
        __L47 = new PerturbationLocationImpl("LCS.java:26", 47, "Numerical");
        __L48 = new PerturbationLocationImpl("LCS.java:26", 48, "Numerical");
        __L49 = new PerturbationLocationImpl("LCS.java:27", 49, "Numerical");
        __L50 = new PerturbationLocationImpl("LCS.java:27", 50, "Numerical");
        __L51 = new PerturbationLocationImpl("LCS.java:27", 51, "Boolean");
        __L52 = new PerturbationLocationImpl("LCS.java:27", 52, "Numerical");
        __L53 = new PerturbationLocationImpl("LCS.java:27", 53, "Numerical");
        __L54 = new PerturbationLocationImpl("LCS.java:27", 54, "Boolean");
        __L55 = new PerturbationLocationImpl("LCS.java:27", 55, "Boolean");
        __L56 = new PerturbationLocationImpl("LCS.java:28", 56, "Numerical");
        __L57 = new PerturbationLocationImpl("LCS.java:28", 57, "Numerical");
        __L58 = new PerturbationLocationImpl("LCS.java:28", 58, "Numerical");
        __L59 = new PerturbationLocationImpl("LCS.java:28", 59, "Numerical");
        __L60 = new PerturbationLocationImpl("LCS.java:28", 60, "Numerical");
        __L61 = new PerturbationLocationImpl("LCS.java:28", 61, "Numerical");
        __L62 = new PerturbationLocationImpl("LCS.java:28", 62, "Numerical");
        __L63 = new PerturbationLocationImpl("LCS.java:28", 63, "Numerical");
        __L64 = new PerturbationLocationImpl("LCS.java:28", 64, "Boolean");
        __L65 = new PerturbationLocationImpl("LCS.java:29", 65, "Numerical");
        __L66 = new PerturbationLocationImpl("LCS.java:30", 66, "Numerical");
        __L67 = new PerturbationLocationImpl("LCS.java:30", 67, "Numerical");
        __L68 = new PerturbationLocationImpl("LCS.java:30", 68, "Numerical");
        __L69 = new PerturbationLocationImpl("LCS.java:30", 69, "Numerical");
        __L70 = new PerturbationLocationImpl("LCS.java:30", 70, "Numerical");
        __L71 = new PerturbationLocationImpl("LCS.java:30", 71, "Numerical");
        __L72 = new PerturbationLocationImpl("LCS.java:30", 72, "Numerical");
        __L73 = new PerturbationLocationImpl("LCS.java:30", 73, "Numerical");
        __L74 = new PerturbationLocationImpl("LCS.java:30", 74, "Boolean");
        __L75 = new PerturbationLocationImpl("LCS.java:31", 75, "Numerical");
        __L76 = new PerturbationLocationImpl("LCS.java:33", 76, "Numerical");
        __L77 = new PerturbationLocationImpl("LCS.java:33", 77, "Numerical");
        __L78 = new PerturbationLocationImpl("LCS.java:33", 78, "Numerical");
        __L79 = new PerturbationLocationImpl("LCS.java:33", 79, "Numerical");
        __L80 = new PerturbationLocationImpl("LCS.java:33", 80, "Numerical");
        __L81 = new PerturbationLocationImpl("LCS.java:33", 81, "Numerical");
        __L82 = new PerturbationLocationImpl("LCS.java:33", 82, "Boolean");
        __L83 = new PerturbationLocationImpl("LCS.java:34", 83, "Numerical");
        __L84 = new PerturbationLocationImpl("LCS.java:34", 84, "Numerical");
        __L85 = new PerturbationLocationImpl("LCS.java:34", 85, "Numerical");
        __L86 = new PerturbationLocationImpl("LCS.java:35", 86, "Numerical");
        __L87 = new PerturbationLocationImpl("LCS.java:36", 87, "Numerical");
    }
}

