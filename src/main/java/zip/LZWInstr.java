

package zip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class LZWInstr {
    static {
        LZWInstr.initPerturbationLocation0();
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

    public static List<Integer> compress(String uncompressed) {
        int dictSize = PerturbationEngine.pint(__L15, 256);
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (int i = PerturbationEngine.pint(__L16, 0); PerturbationEngine.pboolean(__L19, ((PerturbationEngine.pint(__L17, i)) < (PerturbationEngine.pint(__L18, 256)))); i++)
            dictionary.put(("" + ((char) (i))), PerturbationEngine.pint(__L20, i));
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (PerturbationEngine.pboolean(__L21, dictionary.containsKey(wc)))
                w = wc;
            else {
                result.add(PerturbationEngine.pint(__L22, dictionary.get(w)));
                dictionary.put(wc, (dictSize++));
                w = "" + c;
            }
        }
        if (!(PerturbationEngine.pboolean(__L23, w.equals(""))))
            result.add(PerturbationEngine.pint(__L24, dictionary.get(w)));
        
        return result;
    }

    public static String decompress(List<Integer> compressed) {
        int dictSize = PerturbationEngine.pint(__L0, 256);
        Map<Integer, String> dictionary = new HashMap<Integer, String>();
        for (int i = PerturbationEngine.pint(__L1, 0); PerturbationEngine.pboolean(__L4, ((PerturbationEngine.pint(__L2, i)) < (PerturbationEngine.pint(__L3, 256)))); i++)
            dictionary.put(PerturbationEngine.pint(__L5, i), ("" + ((char) (i))));
        String w = "" + ((char) ((int) (compressed.remove(PerturbationEngine.pint(__L6, 0)))));
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (PerturbationEngine.pboolean(__L8, dictionary.containsKey(PerturbationEngine.pint(__L7, k))))
                entry = dictionary.get(PerturbationEngine.pint(__L9, k));
            else if (PerturbationEngine.pboolean(__L12, ((PerturbationEngine.pint(__L10, k)) == (PerturbationEngine.pint(__L11, dictSize)))))
                entry = w + (w.charAt(PerturbationEngine.pint(__L13, 0)));
            else
                throw new IllegalArgumentException(("Bad compressed k: " + k));
            
            result.append(entry);
            dictionary.put((dictSize++), (w + (entry.charAt(PerturbationEngine.pint(__L14, 0)))));
            w = entry;
        }
        return result.toString();
    }

    static private void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("LZW.java:37", 0, "Numerical");
        __L1 = new PerturbationLocationImpl("LZW.java:39", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("LZW.java:39", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("LZW.java:39", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("LZW.java:39", 4, "Boolean");
        __L5 = new PerturbationLocationImpl("LZW.java:40", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("LZW.java:42", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("LZW.java:46", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("LZW.java:46", 8, "Boolean");
        __L9 = new PerturbationLocationImpl("LZW.java:47", 9, "Numerical");
        __L10 = new PerturbationLocationImpl("LZW.java:48", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("LZW.java:48", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("LZW.java:48", 12, "Boolean");
        __L13 = new PerturbationLocationImpl("LZW.java:49", 13, "Numerical");
        __L14 = new PerturbationLocationImpl("LZW.java:56", 14, "Numerical");
        __L15 = new PerturbationLocationImpl("LZW.java:9", 15, "Numerical");
        __L16 = new PerturbationLocationImpl("LZW.java:11", 16, "Numerical");
        __L17 = new PerturbationLocationImpl("LZW.java:11", 17, "Numerical");
        __L18 = new PerturbationLocationImpl("LZW.java:11", 18, "Numerical");
        __L19 = new PerturbationLocationImpl("LZW.java:11", 19, "Boolean");
        __L20 = new PerturbationLocationImpl("LZW.java:12", 20, "Numerical");
        __L21 = new PerturbationLocationImpl("LZW.java:18", 21, "Boolean");
        __L22 = new PerturbationLocationImpl("LZW.java:21", 22, "Numerical");
        __L23 = new PerturbationLocationImpl("LZW.java:29", 23, "Boolean");
        __L24 = new PerturbationLocationImpl("LZW.java:30", 24, "Numerical");
    }
}

