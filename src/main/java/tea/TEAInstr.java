

package tea;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class TEAInstr {
    static {
        TEAInstr.initPerturbationLocation0();
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

    public static PerturbationLocation __L88;

    public static PerturbationLocation __L89;

    public static PerturbationLocation __L90;

    public static PerturbationLocation __L91;

    public static PerturbationLocation __L92;

    public static PerturbationLocation __L93;

    public static PerturbationLocation __L94;

    public static PerturbationLocation __L95;

    public static PerturbationLocation __L96;

    public static PerturbationLocation __L97;

    public static PerturbationLocation __L98;

    public static PerturbationLocation __L99;

    public static PerturbationLocation __L100;

    public static PerturbationLocation __L101;

    public static PerturbationLocation __L102;

    public static PerturbationLocation __L103;

    public static PerturbationLocation __L104;

    public static PerturbationLocation __L105;

    public static int[] encrypt(int[] v, int[] k) {
        int v0 = PerturbationEngine.pint(__L54, v[PerturbationEngine.pint(__L53, 0)]);
        int v1 = PerturbationEngine.pint(__L56, v[PerturbationEngine.pint(__L55, 1)]);
        int acc = PerturbationEngine.pint(__L57, 0);
        int delta = PerturbationEngine.pint(__L58, -1640531527);
        int k0 = PerturbationEngine.pint(__L60, k[PerturbationEngine.pint(__L59, 0)]);
        int k1 = PerturbationEngine.pint(__L62, k[PerturbationEngine.pint(__L61, 1)]);
        int k2 = PerturbationEngine.pint(__L64, k[PerturbationEngine.pint(__L63, 2)]);
        int k3 = PerturbationEngine.pint(__L66, k[PerturbationEngine.pint(__L65, 3)]);
        for (int i = PerturbationEngine.pint(__L67, 0); PerturbationEngine.pboolean(__L70, ((PerturbationEngine.pint(__L68, i)) < (PerturbationEngine.pint(__L69, 32)))); i++) {
            acc += PerturbationEngine.pint(__L71, delta);
            v0 += PerturbationEngine.pint(__L86, ((PerturbationEngine.pint(__L80, ((PerturbationEngine.pint(__L76, ((PerturbationEngine.pint(__L74, ((PerturbationEngine.pint(__L72, v1)) << (PerturbationEngine.pint(__L73, 4))))) + (PerturbationEngine.pint(__L75, k0))))) ^ (PerturbationEngine.pint(__L79, ((PerturbationEngine.pint(__L77, v1)) + (PerturbationEngine.pint(__L78, acc)))))))) ^ (PerturbationEngine.pint(__L85, ((PerturbationEngine.pint(__L83, ((PerturbationEngine.pint(__L81, v1)) >> (PerturbationEngine.pint(__L82, 5))))) + (PerturbationEngine.pint(__L84, k1)))))));
            v1 += PerturbationEngine.pint(__L101, ((PerturbationEngine.pint(__L95, ((PerturbationEngine.pint(__L91, ((PerturbationEngine.pint(__L89, ((PerturbationEngine.pint(__L87, v0)) << (PerturbationEngine.pint(__L88, 4))))) + (PerturbationEngine.pint(__L90, k2))))) ^ (PerturbationEngine.pint(__L94, ((PerturbationEngine.pint(__L92, v0)) + (PerturbationEngine.pint(__L93, acc)))))))) ^ (PerturbationEngine.pint(__L100, ((PerturbationEngine.pint(__L98, ((PerturbationEngine.pint(__L96, v0)) >> (PerturbationEngine.pint(__L97, 5))))) + (PerturbationEngine.pint(__L99, k3)))))));
        }
        v[PerturbationEngine.pint(__L102, 0)] = PerturbationEngine.pint(__L103, v0);
        v[PerturbationEngine.pint(__L104, 1)] = PerturbationEngine.pint(__L105, v1);
        return v;
    }

    public static int[] decrypt(int[] v, int[] k) {
        int v0 = PerturbationEngine.pint(__L1, v[PerturbationEngine.pint(__L0, 0)]);
        int v1 = PerturbationEngine.pint(__L3, v[PerturbationEngine.pint(__L2, 1)]);
        int acc = PerturbationEngine.pint(__L4, -957401312);
        int delta = PerturbationEngine.pint(__L5, -1640531527);
        int k0 = PerturbationEngine.pint(__L7, k[PerturbationEngine.pint(__L6, 0)]);
        int k1 = PerturbationEngine.pint(__L9, k[PerturbationEngine.pint(__L8, 1)]);
        int k2 = PerturbationEngine.pint(__L11, k[PerturbationEngine.pint(__L10, 2)]);
        int k3 = PerturbationEngine.pint(__L13, k[PerturbationEngine.pint(__L12, 3)]);
        for (int i = PerturbationEngine.pint(__L14, 0); PerturbationEngine.pboolean(__L17, ((PerturbationEngine.pint(__L15, i)) < (PerturbationEngine.pint(__L16, 32)))); i++) {
            v1 -= PerturbationEngine.pint(__L32, ((PerturbationEngine.pint(__L26, ((PerturbationEngine.pint(__L22, ((PerturbationEngine.pint(__L20, ((PerturbationEngine.pint(__L18, v0)) << (PerturbationEngine.pint(__L19, 4))))) + (PerturbationEngine.pint(__L21, k2))))) ^ (PerturbationEngine.pint(__L25, ((PerturbationEngine.pint(__L23, v0)) + (PerturbationEngine.pint(__L24, acc)))))))) ^ (PerturbationEngine.pint(__L31, ((PerturbationEngine.pint(__L29, ((PerturbationEngine.pint(__L27, v0)) >> (PerturbationEngine.pint(__L28, 5))))) + (PerturbationEngine.pint(__L30, k3)))))));
            v0 -= PerturbationEngine.pint(__L47, ((PerturbationEngine.pint(__L41, ((PerturbationEngine.pint(__L37, ((PerturbationEngine.pint(__L35, ((PerturbationEngine.pint(__L33, v1)) << (PerturbationEngine.pint(__L34, 4))))) + (PerturbationEngine.pint(__L36, k0))))) ^ (PerturbationEngine.pint(__L40, ((PerturbationEngine.pint(__L38, v1)) + (PerturbationEngine.pint(__L39, acc)))))))) ^ (PerturbationEngine.pint(__L46, ((PerturbationEngine.pint(__L44, ((PerturbationEngine.pint(__L42, v1)) >> (PerturbationEngine.pint(__L43, 5))))) + (PerturbationEngine.pint(__L45, k1)))))));
            acc -= PerturbationEngine.pint(__L48, delta);
        }
        v[PerturbationEngine.pint(__L49, 0)] = PerturbationEngine.pint(__L50, v0);
        v[PerturbationEngine.pint(__L51, 1)] = PerturbationEngine.pint(__L52, v1);
        return v;
    }

    static private void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("tea.java:24", 0, "Numerical");
        __L1 = new PerturbationLocationImpl("tea.java:24", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("tea.java:24", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("tea.java:24", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("tea.java:24", 4, "Numerical");
        __L5 = new PerturbationLocationImpl("tea.java:25", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("tea.java:26", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("tea.java:26", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("tea.java:26", 8, "Numerical");
        __L9 = new PerturbationLocationImpl("tea.java:26", 9, "Numerical");
        __L10 = new PerturbationLocationImpl("tea.java:26", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("tea.java:26", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("tea.java:26", 12, "Numerical");
        __L13 = new PerturbationLocationImpl("tea.java:26", 13, "Numerical");
        __L14 = new PerturbationLocationImpl("tea.java:27", 14, "Numerical");
        __L15 = new PerturbationLocationImpl("tea.java:27", 15, "Numerical");
        __L16 = new PerturbationLocationImpl("tea.java:27", 16, "Numerical");
        __L17 = new PerturbationLocationImpl("tea.java:27", 17, "Boolean");
        __L18 = new PerturbationLocationImpl("tea.java:28", 18, "Numerical");
        __L19 = new PerturbationLocationImpl("tea.java:28", 19, "Numerical");
        __L20 = new PerturbationLocationImpl("tea.java:28", 20, "Numerical");
        __L21 = new PerturbationLocationImpl("tea.java:28", 21, "Numerical");
        __L22 = new PerturbationLocationImpl("tea.java:28", 22, "Numerical");
        __L23 = new PerturbationLocationImpl("tea.java:28", 23, "Numerical");
        __L24 = new PerturbationLocationImpl("tea.java:28", 24, "Numerical");
        __L25 = new PerturbationLocationImpl("tea.java:28", 25, "Numerical");
        __L26 = new PerturbationLocationImpl("tea.java:28", 26, "Numerical");
        __L27 = new PerturbationLocationImpl("tea.java:28", 27, "Numerical");
        __L28 = new PerturbationLocationImpl("tea.java:28", 28, "Numerical");
        __L29 = new PerturbationLocationImpl("tea.java:28", 29, "Numerical");
        __L30 = new PerturbationLocationImpl("tea.java:28", 30, "Numerical");
        __L31 = new PerturbationLocationImpl("tea.java:28", 31, "Numerical");
        __L32 = new PerturbationLocationImpl("tea.java:28", 32, "Numerical");
        __L33 = new PerturbationLocationImpl("tea.java:29", 33, "Numerical");
        __L34 = new PerturbationLocationImpl("tea.java:29", 34, "Numerical");
        __L35 = new PerturbationLocationImpl("tea.java:29", 35, "Numerical");
        __L36 = new PerturbationLocationImpl("tea.java:29", 36, "Numerical");
        __L37 = new PerturbationLocationImpl("tea.java:29", 37, "Numerical");
        __L38 = new PerturbationLocationImpl("tea.java:29", 38, "Numerical");
        __L39 = new PerturbationLocationImpl("tea.java:29", 39, "Numerical");
        __L40 = new PerturbationLocationImpl("tea.java:29", 40, "Numerical");
        __L41 = new PerturbationLocationImpl("tea.java:29", 41, "Numerical");
        __L42 = new PerturbationLocationImpl("tea.java:29", 42, "Numerical");
        __L43 = new PerturbationLocationImpl("tea.java:29", 43, "Numerical");
        __L44 = new PerturbationLocationImpl("tea.java:29", 44, "Numerical");
        __L45 = new PerturbationLocationImpl("tea.java:29", 45, "Numerical");
        __L46 = new PerturbationLocationImpl("tea.java:29", 46, "Numerical");
        __L47 = new PerturbationLocationImpl("tea.java:29", 47, "Numerical");
        __L48 = new PerturbationLocationImpl("tea.java:30", 48, "Numerical");
        __L49 = new PerturbationLocationImpl("tea.java:32", 49, "Numerical");
        __L50 = new PerturbationLocationImpl("tea.java:32", 50, "Numerical");
        __L51 = new PerturbationLocationImpl("tea.java:32", 51, "Numerical");
        __L52 = new PerturbationLocationImpl("tea.java:32", 52, "Numerical");
        __L53 = new PerturbationLocationImpl("tea.java:11", 53, "Numerical");
        __L54 = new PerturbationLocationImpl("tea.java:11", 54, "Numerical");
        __L55 = new PerturbationLocationImpl("tea.java:11", 55, "Numerical");
        __L56 = new PerturbationLocationImpl("tea.java:11", 56, "Numerical");
        __L57 = new PerturbationLocationImpl("tea.java:11", 57, "Numerical");
        __L58 = new PerturbationLocationImpl("tea.java:12", 58, "Numerical");
        __L59 = new PerturbationLocationImpl("tea.java:13", 59, "Numerical");
        __L60 = new PerturbationLocationImpl("tea.java:13", 60, "Numerical");
        __L61 = new PerturbationLocationImpl("tea.java:13", 61, "Numerical");
        __L62 = new PerturbationLocationImpl("tea.java:13", 62, "Numerical");
        __L63 = new PerturbationLocationImpl("tea.java:13", 63, "Numerical");
        __L64 = new PerturbationLocationImpl("tea.java:13", 64, "Numerical");
        __L65 = new PerturbationLocationImpl("tea.java:13", 65, "Numerical");
        __L66 = new PerturbationLocationImpl("tea.java:13", 66, "Numerical");
        __L67 = new PerturbationLocationImpl("tea.java:14", 67, "Numerical");
        __L68 = new PerturbationLocationImpl("tea.java:14", 68, "Numerical");
        __L69 = new PerturbationLocationImpl("tea.java:14", 69, "Numerical");
        __L70 = new PerturbationLocationImpl("tea.java:14", 70, "Boolean");
        __L71 = new PerturbationLocationImpl("tea.java:15", 71, "Numerical");
        __L72 = new PerturbationLocationImpl("tea.java:16", 72, "Numerical");
        __L73 = new PerturbationLocationImpl("tea.java:16", 73, "Numerical");
        __L74 = new PerturbationLocationImpl("tea.java:16", 74, "Numerical");
        __L75 = new PerturbationLocationImpl("tea.java:16", 75, "Numerical");
        __L76 = new PerturbationLocationImpl("tea.java:16", 76, "Numerical");
        __L77 = new PerturbationLocationImpl("tea.java:16", 77, "Numerical");
        __L78 = new PerturbationLocationImpl("tea.java:16", 78, "Numerical");
        __L79 = new PerturbationLocationImpl("tea.java:16", 79, "Numerical");
        __L80 = new PerturbationLocationImpl("tea.java:16", 80, "Numerical");
        __L81 = new PerturbationLocationImpl("tea.java:16", 81, "Numerical");
        __L82 = new PerturbationLocationImpl("tea.java:16", 82, "Numerical");
        __L83 = new PerturbationLocationImpl("tea.java:16", 83, "Numerical");
        __L84 = new PerturbationLocationImpl("tea.java:16", 84, "Numerical");
        __L85 = new PerturbationLocationImpl("tea.java:16", 85, "Numerical");
        __L86 = new PerturbationLocationImpl("tea.java:16", 86, "Numerical");
        __L87 = new PerturbationLocationImpl("tea.java:17", 87, "Numerical");
        __L88 = new PerturbationLocationImpl("tea.java:17", 88, "Numerical");
        __L89 = new PerturbationLocationImpl("tea.java:17", 89, "Numerical");
        __L90 = new PerturbationLocationImpl("tea.java:17", 90, "Numerical");
        __L91 = new PerturbationLocationImpl("tea.java:17", 91, "Numerical");
        __L92 = new PerturbationLocationImpl("tea.java:17", 92, "Numerical");
        __L93 = new PerturbationLocationImpl("tea.java:17", 93, "Numerical");
        __L94 = new PerturbationLocationImpl("tea.java:17", 94, "Numerical");
        __L95 = new PerturbationLocationImpl("tea.java:17", 95, "Numerical");
        __L96 = new PerturbationLocationImpl("tea.java:17", 96, "Numerical");
        __L97 = new PerturbationLocationImpl("tea.java:17", 97, "Numerical");
        __L98 = new PerturbationLocationImpl("tea.java:17", 98, "Numerical");
        __L99 = new PerturbationLocationImpl("tea.java:17", 99, "Numerical");
        __L100 = new PerturbationLocationImpl("tea.java:17", 100, "Numerical");
        __L101 = new PerturbationLocationImpl("tea.java:17", 101, "Numerical");
        __L102 = new PerturbationLocationImpl("tea.java:19", 102, "Numerical");
        __L103 = new PerturbationLocationImpl("tea.java:19", 103, "Numerical");
        __L104 = new PerturbationLocationImpl("tea.java:19", 104, "Numerical");
        __L105 = new PerturbationLocationImpl("tea.java:19", 105, "Numerical");
    }
}

