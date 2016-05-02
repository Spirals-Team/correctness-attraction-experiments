

package mersenne;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import java.util.Random;

public class MersenneTwisterInstr extends Random {
    static {
        MersenneTwisterInstr.initPerturbationLocation0();
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

    public static PerturbationLocation __L106;

    public static PerturbationLocation __L107;

    public static PerturbationLocation __L108;

    public static PerturbationLocation __L109;

    public static PerturbationLocation __L110;

    public static PerturbationLocation __L111;

    public static PerturbationLocation __L112;

    public static PerturbationLocation __L113;

    public static PerturbationLocation __L114;

    public static PerturbationLocation __L115;

    public static PerturbationLocation __L116;

    public static PerturbationLocation __L117;

    public static PerturbationLocation __L118;

    public static PerturbationLocation __L119;

    public static PerturbationLocation __L120;

    public static PerturbationLocation __L121;

    public static PerturbationLocation __L122;

    public static PerturbationLocation __L123;

    public static PerturbationLocation __L124;

    public static PerturbationLocation __L125;

    public static PerturbationLocation __L126;

    public static PerturbationLocation __L127;

    public static PerturbationLocation __L128;

    public static PerturbationLocation __L129;

    public static PerturbationLocation __L130;

    public static PerturbationLocation __L131;

    public static PerturbationLocation __L132;

    public static PerturbationLocation __L133;

    public static PerturbationLocation __L134;

    public static PerturbationLocation __L135;

    public static PerturbationLocation __L136;

    public static PerturbationLocation __L137;

    public static PerturbationLocation __L138;

    public static PerturbationLocation __L139;

    public static PerturbationLocation __L140;

    public static PerturbationLocation __L141;

    public static PerturbationLocation __L142;

    public static PerturbationLocation __L143;

    public static PerturbationLocation __L144;

    public static PerturbationLocation __L145;

    public static PerturbationLocation __L146;

    public static PerturbationLocation __L147;

    public static final int N = 624;

    public static final int M = 397;

    public static final int MATRIX_A = -1727483681;

    public static final int UPPER_MASK = -2147483648;

    public static final int LOWER_MASK = 2147483647;

    private long[] mt;

    private int mti = PerturbationEngine.pint(__L2, ((PerturbationEngine.pint(__L0, MersenneTwisterInstr.N)) + (PerturbationEngine.pint(__L1, 1))));

    public MersenneTwisterInstr() {
        mt = new long[MersenneTwisterInstr.N];
    }

    public MersenneTwisterInstr(long s) {
        this();
        mt[0] = s;
        for (mti = 1; (mti) < (MersenneTwisterInstr.N); (mti)++) {
            mt[mti] = (1812433253 * ((mt[((mti) - 1)]) ^ ((mt[((mti) - 1)]) >> 30))) + (mti);
            mt[mti] &= ((long) (-1));
            
        }
    }

    public void initGenRand(long s) {
        mt[PerturbationEngine.pint(__L124, 0)] = PerturbationEngine.plong(__L125, s);
        for (mti = PerturbationEngine.pint(__L126, 1); PerturbationEngine.pboolean(__L129, ((PerturbationEngine.pint(__L127, mti)) < (PerturbationEngine.pint(__L128, MersenneTwisterInstr.N)))); (mti)++) {
            mt[PerturbationEngine.pint(__L130, mti)] = PerturbationEngine.plong(__L145, ((PerturbationEngine.plong(__L143, ((PerturbationEngine.pint(__L131, 1812433253)) * (PerturbationEngine.plong(__L142, ((PerturbationEngine.plong(__L135, mt[PerturbationEngine.pint(__L134, ((PerturbationEngine.pint(__L132, mti)) - (PerturbationEngine.pint(__L133, 1))))])) ^ (PerturbationEngine.plong(__L141, ((PerturbationEngine.plong(__L139, mt[PerturbationEngine.pint(__L138, ((PerturbationEngine.pint(__L136, mti)) - (PerturbationEngine.pint(__L137, 1))))])) >> (PerturbationEngine.pint(__L140, 30))))))))))) + (PerturbationEngine.pint(__L144, mti))));
            mt[PerturbationEngine.pint(__L146, mti)] &= PerturbationEngine.plong(__L147, ((long) (-1)));
            
        }
    }

    public long genrand() {
        int y;
        long[] mag01 = new long[]{ PerturbationEngine.plong(__L4, ((long) (0))) , PerturbationEngine.plong(__L5, ((long) (MersenneTwisterInstr.MATRIX_A))) };
        if (PerturbationEngine.pboolean(__L8, ((PerturbationEngine.pint(__L6, mti)) >= (PerturbationEngine.pint(__L7, MersenneTwisterInstr.N))))) {
            int kk;
            if (PerturbationEngine.pboolean(__L13, ((PerturbationEngine.pint(__L9, mti)) == (PerturbationEngine.pint(__L12, ((PerturbationEngine.pint(__L10, MersenneTwisterInstr.N)) + (PerturbationEngine.pint(__L11, 1))))))))
                initGenRand(PerturbationEngine.pint(__L14, 5489));
            
            for (kk = PerturbationEngine.pint(__L15, 0); PerturbationEngine.pboolean(__L20, ((PerturbationEngine.pint(__L16, kk)) < (PerturbationEngine.pint(__L19, ((PerturbationEngine.pint(__L17, MersenneTwisterInstr.N)) - (PerturbationEngine.pint(__L18, MersenneTwisterInstr.M))))))); kk++) {
                y = PerturbationEngine.pint(__L31, ((int) ((PerturbationEngine.plong(__L24, ((PerturbationEngine.plong(__L22, mt[PerturbationEngine.pint(__L21, kk)])) & (PerturbationEngine.pint(__L23, MersenneTwisterInstr.UPPER_MASK))))) | (PerturbationEngine.plong(__L30, ((PerturbationEngine.plong(__L28, mt[PerturbationEngine.pint(__L27, ((PerturbationEngine.pint(__L25, kk)) + (PerturbationEngine.pint(__L26, 1))))])) & (PerturbationEngine.pint(__L29, MersenneTwisterInstr.LOWER_MASK))))))));
                mt[PerturbationEngine.pint(__L32, kk)] = PerturbationEngine.plong(__L45, ((PerturbationEngine.plong(__L40, ((PerturbationEngine.plong(__L36, mt[PerturbationEngine.pint(__L35, ((PerturbationEngine.pint(__L33, kk)) + (PerturbationEngine.pint(__L34, MersenneTwisterInstr.M))))])) ^ (PerturbationEngine.pint(__L39, ((PerturbationEngine.pint(__L37, y)) >> (PerturbationEngine.pint(__L38, 1)))))))) ^ (PerturbationEngine.plong(__L44, mag01[PerturbationEngine.pint(__L43, ((PerturbationEngine.pint(__L41, y)) & (PerturbationEngine.pint(__L42, 1))))]))));
            }
            for (; PerturbationEngine.pboolean(__L50, ((PerturbationEngine.pint(__L46, kk)) < (PerturbationEngine.pint(__L49, ((PerturbationEngine.pint(__L47, MersenneTwisterInstr.N)) - (PerturbationEngine.pint(__L48, 1))))))); kk++) {
                y = PerturbationEngine.pint(__L61, ((int) ((PerturbationEngine.plong(__L54, ((PerturbationEngine.plong(__L52, mt[PerturbationEngine.pint(__L51, kk)])) & (PerturbationEngine.pint(__L53, MersenneTwisterInstr.UPPER_MASK))))) | (PerturbationEngine.plong(__L60, ((PerturbationEngine.plong(__L58, mt[PerturbationEngine.pint(__L57, ((PerturbationEngine.pint(__L55, kk)) + (PerturbationEngine.pint(__L56, 1))))])) & (PerturbationEngine.pint(__L59, MersenneTwisterInstr.LOWER_MASK))))))));
                mt[PerturbationEngine.pint(__L62, kk)] = PerturbationEngine.plong(__L77, ((PerturbationEngine.plong(__L72, ((PerturbationEngine.plong(__L68, mt[PerturbationEngine.pint(__L67, ((PerturbationEngine.pint(__L63, kk)) + (PerturbationEngine.pint(__L66, ((PerturbationEngine.pint(__L64, MersenneTwisterInstr.M)) - (PerturbationEngine.pint(__L65, MersenneTwisterInstr.N)))))))])) ^ (PerturbationEngine.pint(__L71, ((PerturbationEngine.pint(__L69, y)) >> (PerturbationEngine.pint(__L70, 1)))))))) ^ (PerturbationEngine.plong(__L76, mag01[PerturbationEngine.pint(__L75, ((PerturbationEngine.pint(__L73, y)) & (PerturbationEngine.pint(__L74, 1))))]))));
            }
            y = PerturbationEngine.pint(__L88, ((int) ((PerturbationEngine.plong(__L83, ((PerturbationEngine.plong(__L81, mt[PerturbationEngine.pint(__L80, ((PerturbationEngine.pint(__L78, MersenneTwisterInstr.N)) - (PerturbationEngine.pint(__L79, 1))))])) & (PerturbationEngine.pint(__L82, MersenneTwisterInstr.UPPER_MASK))))) | (PerturbationEngine.plong(__L87, ((PerturbationEngine.plong(__L85, mt[PerturbationEngine.pint(__L84, 0)])) & (PerturbationEngine.pint(__L86, MersenneTwisterInstr.LOWER_MASK))))))));
            mt[PerturbationEngine.pint(__L91, ((PerturbationEngine.pint(__L89, MersenneTwisterInstr.N)) - (PerturbationEngine.pint(__L90, 1))))] = PerturbationEngine.plong(__L104, ((PerturbationEngine.plong(__L99, ((PerturbationEngine.plong(__L95, mt[PerturbationEngine.pint(__L94, ((PerturbationEngine.pint(__L92, MersenneTwisterInstr.M)) - (PerturbationEngine.pint(__L93, 1))))])) ^ (PerturbationEngine.pint(__L98, ((PerturbationEngine.pint(__L96, y)) >> (PerturbationEngine.pint(__L97, 1)))))))) ^ (PerturbationEngine.plong(__L103, mag01[PerturbationEngine.pint(__L102, ((PerturbationEngine.pint(__L100, y)) & (PerturbationEngine.pint(__L101, 1))))]))));
            mti = PerturbationEngine.pint(__L105, 0);
        } 
        y = PerturbationEngine.pint(__L106, ((int) (mt[((mti)++)])));
        y ^= PerturbationEngine.pint(__L109, ((PerturbationEngine.pint(__L107, y)) >> (PerturbationEngine.pint(__L108, 11))));
        y ^= PerturbationEngine.pint(__L114, ((PerturbationEngine.pint(__L112, ((PerturbationEngine.pint(__L110, y)) << (PerturbationEngine.pint(__L111, 7))))) & (PerturbationEngine.pint(__L113, -1658038656))));
        y ^= PerturbationEngine.pint(__L119, ((PerturbationEngine.pint(__L117, ((PerturbationEngine.pint(__L115, y)) << (PerturbationEngine.pint(__L116, 15))))) & (PerturbationEngine.pint(__L118, -272236544))));
        y ^= PerturbationEngine.pint(__L122, ((PerturbationEngine.pint(__L120, y)) >> (PerturbationEngine.pint(__L121, 18))));
        return PerturbationEngine.pint(__L123, y);
    }

    protected synchronized int next(int bits) {
        return PerturbationEngine.pint(__L3, ((int) (genrand())));
    }

    static private void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("MersenneTwister.java:49", 0, "Numerical");
        __L1 = new PerturbationLocationImpl("MersenneTwister.java:49", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("MersenneTwister.java:49", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("MersenneTwister.java:154", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("MersenneTwister.java:119", 4, "Numerical");
        __L5 = new PerturbationLocationImpl("MersenneTwister.java:119", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("MersenneTwister.java:122", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("MersenneTwister.java:122", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("MersenneTwister.java:122", 8, "Boolean");
        __L9 = new PerturbationLocationImpl("MersenneTwister.java:125", 9, "Numerical");
        __L10 = new PerturbationLocationImpl("MersenneTwister.java:125", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("MersenneTwister.java:125", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("MersenneTwister.java:125", 12, "Numerical");
        __L13 = new PerturbationLocationImpl("MersenneTwister.java:125", 13, "Boolean");
        __L14 = new PerturbationLocationImpl("MersenneTwister.java:126", 14, "Numerical");
        __L15 = new PerturbationLocationImpl("MersenneTwister.java:128", 15, "Numerical");
        __L16 = new PerturbationLocationImpl("MersenneTwister.java:128", 16, "Numerical");
        __L17 = new PerturbationLocationImpl("MersenneTwister.java:128", 17, "Numerical");
        __L18 = new PerturbationLocationImpl("MersenneTwister.java:128", 18, "Numerical");
        __L19 = new PerturbationLocationImpl("MersenneTwister.java:128", 19, "Numerical");
        __L20 = new PerturbationLocationImpl("MersenneTwister.java:128", 20, "Boolean");
        __L21 = new PerturbationLocationImpl("MersenneTwister.java:129", 21, "Numerical");
        __L22 = new PerturbationLocationImpl("MersenneTwister.java:129", 22, "Numerical");
        __L23 = new PerturbationLocationImpl("MersenneTwister.java:129", 23, "Numerical");
        __L24 = new PerturbationLocationImpl("MersenneTwister.java:129", 24, "Numerical");
        __L25 = new PerturbationLocationImpl("MersenneTwister.java:129", 25, "Numerical");
        __L26 = new PerturbationLocationImpl("MersenneTwister.java:129", 26, "Numerical");
        __L27 = new PerturbationLocationImpl("MersenneTwister.java:129", 27, "Numerical");
        __L28 = new PerturbationLocationImpl("MersenneTwister.java:129", 28, "Numerical");
        __L29 = new PerturbationLocationImpl("MersenneTwister.java:129", 29, "Numerical");
        __L30 = new PerturbationLocationImpl("MersenneTwister.java:129", 30, "Numerical");
        __L31 = new PerturbationLocationImpl("MersenneTwister.java:129", 31, "Numerical");
        __L32 = new PerturbationLocationImpl("MersenneTwister.java:130", 32, "Numerical");
        __L33 = new PerturbationLocationImpl("MersenneTwister.java:130", 33, "Numerical");
        __L34 = new PerturbationLocationImpl("MersenneTwister.java:130", 34, "Numerical");
        __L35 = new PerturbationLocationImpl("MersenneTwister.java:130", 35, "Numerical");
        __L36 = new PerturbationLocationImpl("MersenneTwister.java:130", 36, "Numerical");
        __L37 = new PerturbationLocationImpl("MersenneTwister.java:130", 37, "Numerical");
        __L38 = new PerturbationLocationImpl("MersenneTwister.java:130", 38, "Numerical");
        __L39 = new PerturbationLocationImpl("MersenneTwister.java:130", 39, "Numerical");
        __L40 = new PerturbationLocationImpl("MersenneTwister.java:130", 40, "Numerical");
        __L41 = new PerturbationLocationImpl("MersenneTwister.java:130", 41, "Numerical");
        __L42 = new PerturbationLocationImpl("MersenneTwister.java:130", 42, "Numerical");
        __L43 = new PerturbationLocationImpl("MersenneTwister.java:130", 43, "Numerical");
        __L44 = new PerturbationLocationImpl("MersenneTwister.java:130", 44, "Numerical");
        __L45 = new PerturbationLocationImpl("MersenneTwister.java:130", 45, "Numerical");
        __L46 = new PerturbationLocationImpl("MersenneTwister.java:132", 46, "Numerical");
        __L47 = new PerturbationLocationImpl("MersenneTwister.java:132", 47, "Numerical");
        __L48 = new PerturbationLocationImpl("MersenneTwister.java:132", 48, "Numerical");
        __L49 = new PerturbationLocationImpl("MersenneTwister.java:132", 49, "Numerical");
        __L50 = new PerturbationLocationImpl("MersenneTwister.java:132", 50, "Boolean");
        __L51 = new PerturbationLocationImpl("MersenneTwister.java:133", 51, "Numerical");
        __L52 = new PerturbationLocationImpl("MersenneTwister.java:133", 52, "Numerical");
        __L53 = new PerturbationLocationImpl("MersenneTwister.java:133", 53, "Numerical");
        __L54 = new PerturbationLocationImpl("MersenneTwister.java:133", 54, "Numerical");
        __L55 = new PerturbationLocationImpl("MersenneTwister.java:133", 55, "Numerical");
        __L56 = new PerturbationLocationImpl("MersenneTwister.java:133", 56, "Numerical");
        __L57 = new PerturbationLocationImpl("MersenneTwister.java:133", 57, "Numerical");
        __L58 = new PerturbationLocationImpl("MersenneTwister.java:133", 58, "Numerical");
        __L59 = new PerturbationLocationImpl("MersenneTwister.java:133", 59, "Numerical");
        __L60 = new PerturbationLocationImpl("MersenneTwister.java:133", 60, "Numerical");
        __L61 = new PerturbationLocationImpl("MersenneTwister.java:133", 61, "Numerical");
        __L62 = new PerturbationLocationImpl("MersenneTwister.java:134", 62, "Numerical");
        __L63 = new PerturbationLocationImpl("MersenneTwister.java:134", 63, "Numerical");
        __L64 = new PerturbationLocationImpl("MersenneTwister.java:134", 64, "Numerical");
        __L65 = new PerturbationLocationImpl("MersenneTwister.java:134", 65, "Numerical");
        __L66 = new PerturbationLocationImpl("MersenneTwister.java:134", 66, "Numerical");
        __L67 = new PerturbationLocationImpl("MersenneTwister.java:134", 67, "Numerical");
        __L68 = new PerturbationLocationImpl("MersenneTwister.java:134", 68, "Numerical");
        __L69 = new PerturbationLocationImpl("MersenneTwister.java:134", 69, "Numerical");
        __L70 = new PerturbationLocationImpl("MersenneTwister.java:134", 70, "Numerical");
        __L71 = new PerturbationLocationImpl("MersenneTwister.java:134", 71, "Numerical");
        __L72 = new PerturbationLocationImpl("MersenneTwister.java:134", 72, "Numerical");
        __L73 = new PerturbationLocationImpl("MersenneTwister.java:134", 73, "Numerical");
        __L74 = new PerturbationLocationImpl("MersenneTwister.java:134", 74, "Numerical");
        __L75 = new PerturbationLocationImpl("MersenneTwister.java:134", 75, "Numerical");
        __L76 = new PerturbationLocationImpl("MersenneTwister.java:134", 76, "Numerical");
        __L77 = new PerturbationLocationImpl("MersenneTwister.java:134", 77, "Numerical");
        __L78 = new PerturbationLocationImpl("MersenneTwister.java:136", 78, "Numerical");
        __L79 = new PerturbationLocationImpl("MersenneTwister.java:136", 79, "Numerical");
        __L80 = new PerturbationLocationImpl("MersenneTwister.java:136", 80, "Numerical");
        __L81 = new PerturbationLocationImpl("MersenneTwister.java:136", 81, "Numerical");
        __L82 = new PerturbationLocationImpl("MersenneTwister.java:136", 82, "Numerical");
        __L83 = new PerturbationLocationImpl("MersenneTwister.java:136", 83, "Numerical");
        __L84 = new PerturbationLocationImpl("MersenneTwister.java:136", 84, "Numerical");
        __L85 = new PerturbationLocationImpl("MersenneTwister.java:136", 85, "Numerical");
        __L86 = new PerturbationLocationImpl("MersenneTwister.java:136", 86, "Numerical");
        __L87 = new PerturbationLocationImpl("MersenneTwister.java:136", 87, "Numerical");
        __L88 = new PerturbationLocationImpl("MersenneTwister.java:136", 88, "Numerical");
        __L89 = new PerturbationLocationImpl("MersenneTwister.java:137", 89, "Numerical");
        __L90 = new PerturbationLocationImpl("MersenneTwister.java:137", 90, "Numerical");
        __L91 = new PerturbationLocationImpl("MersenneTwister.java:137", 91, "Numerical");
        __L92 = new PerturbationLocationImpl("MersenneTwister.java:137", 92, "Numerical");
        __L93 = new PerturbationLocationImpl("MersenneTwister.java:137", 93, "Numerical");
        __L94 = new PerturbationLocationImpl("MersenneTwister.java:137", 94, "Numerical");
        __L95 = new PerturbationLocationImpl("MersenneTwister.java:137", 95, "Numerical");
        __L96 = new PerturbationLocationImpl("MersenneTwister.java:137", 96, "Numerical");
        __L97 = new PerturbationLocationImpl("MersenneTwister.java:137", 97, "Numerical");
        __L98 = new PerturbationLocationImpl("MersenneTwister.java:137", 98, "Numerical");
        __L99 = new PerturbationLocationImpl("MersenneTwister.java:137", 99, "Numerical");
        __L100 = new PerturbationLocationImpl("MersenneTwister.java:137", 100, "Numerical");
        __L101 = new PerturbationLocationImpl("MersenneTwister.java:137", 101, "Numerical");
        __L102 = new PerturbationLocationImpl("MersenneTwister.java:137", 102, "Numerical");
        __L103 = new PerturbationLocationImpl("MersenneTwister.java:137", 103, "Numerical");
        __L104 = new PerturbationLocationImpl("MersenneTwister.java:137", 104, "Numerical");
        __L105 = new PerturbationLocationImpl("MersenneTwister.java:139", 105, "Numerical");
        __L106 = new PerturbationLocationImpl("MersenneTwister.java:142", 106, "Numerical");
        __L107 = new PerturbationLocationImpl("MersenneTwister.java:145", 107, "Numerical");
        __L108 = new PerturbationLocationImpl("MersenneTwister.java:145", 108, "Numerical");
        __L109 = new PerturbationLocationImpl("MersenneTwister.java:145", 109, "Numerical");
        __L110 = new PerturbationLocationImpl("MersenneTwister.java:146", 110, "Numerical");
        __L111 = new PerturbationLocationImpl("MersenneTwister.java:146", 111, "Numerical");
        __L112 = new PerturbationLocationImpl("MersenneTwister.java:146", 112, "Numerical");
        __L113 = new PerturbationLocationImpl("MersenneTwister.java:146", 113, "Numerical");
        __L114 = new PerturbationLocationImpl("MersenneTwister.java:146", 114, "Numerical");
        __L115 = new PerturbationLocationImpl("MersenneTwister.java:147", 115, "Numerical");
        __L116 = new PerturbationLocationImpl("MersenneTwister.java:147", 116, "Numerical");
        __L117 = new PerturbationLocationImpl("MersenneTwister.java:147", 117, "Numerical");
        __L118 = new PerturbationLocationImpl("MersenneTwister.java:147", 118, "Numerical");
        __L119 = new PerturbationLocationImpl("MersenneTwister.java:147", 119, "Numerical");
        __L120 = new PerturbationLocationImpl("MersenneTwister.java:148", 120, "Numerical");
        __L121 = new PerturbationLocationImpl("MersenneTwister.java:148", 121, "Numerical");
        __L122 = new PerturbationLocationImpl("MersenneTwister.java:148", 122, "Numerical");
        __L123 = new PerturbationLocationImpl("MersenneTwister.java:150", 123, "Numerical");
        __L124 = new PerturbationLocationImpl("MersenneTwister.java:73", 124, "Numerical");
        __L125 = new PerturbationLocationImpl("MersenneTwister.java:73", 125, "Numerical");
        __L126 = new PerturbationLocationImpl("MersenneTwister.java:74", 126, "Numerical");
        __L127 = new PerturbationLocationImpl("MersenneTwister.java:74", 127, "Numerical");
        __L128 = new PerturbationLocationImpl("MersenneTwister.java:74", 128, "Numerical");
        __L129 = new PerturbationLocationImpl("MersenneTwister.java:74", 129, "Boolean");
        __L130 = new PerturbationLocationImpl("MersenneTwister.java:75", 130, "Numerical");
        __L131 = new PerturbationLocationImpl("MersenneTwister.java:76", 131, "Numerical");
        __L132 = new PerturbationLocationImpl("MersenneTwister.java:76", 132, "Numerical");
        __L133 = new PerturbationLocationImpl("MersenneTwister.java:76", 133, "Numerical");
        __L134 = new PerturbationLocationImpl("MersenneTwister.java:76", 134, "Numerical");
        __L135 = new PerturbationLocationImpl("MersenneTwister.java:76", 135, "Numerical");
        __L136 = new PerturbationLocationImpl("MersenneTwister.java:76", 136, "Numerical");
        __L137 = new PerturbationLocationImpl("MersenneTwister.java:76", 137, "Numerical");
        __L138 = new PerturbationLocationImpl("MersenneTwister.java:76", 138, "Numerical");
        __L139 = new PerturbationLocationImpl("MersenneTwister.java:76", 139, "Numerical");
        __L140 = new PerturbationLocationImpl("MersenneTwister.java:76", 140, "Numerical");
        __L141 = new PerturbationLocationImpl("MersenneTwister.java:76", 141, "Numerical");
        __L142 = new PerturbationLocationImpl("MersenneTwister.java:76", 142, "Numerical");
        __L143 = new PerturbationLocationImpl("MersenneTwister.java:76", 143, "Numerical");
        __L144 = new PerturbationLocationImpl("MersenneTwister.java:76", 144, "Numerical");
        __L145 = new PerturbationLocationImpl("MersenneTwister.java:76", 145, "Numerical");
        __L146 = new PerturbationLocationImpl("MersenneTwister.java:81", 146, "Numerical");
        __L147 = new PerturbationLocationImpl("MersenneTwister.java:81", 147, "Numerical");
    }
}

