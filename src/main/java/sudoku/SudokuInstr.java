package sudoku;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class SudokuInstr {
    static {
        SudokuInstr.initPerturbationLocation0();
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

    private int[][] mBoard;

    private int mBoardSize;

    private int mBoxSize;

    private boolean[][] mRowSubset;

    private boolean[][] mColSubset;

    private boolean[][] mBoxSubset;

    public SudokuInstr(int[][] board) {
        mBoard = board;
        mBoardSize = mBoard.length;
        mBoxSize = ((int)(Math.sqrt(mBoardSize)));
    }

    public int[][] getGrid() {
        return sudoku.SudokuInstr.this.mBoard;
    }

    public void initSubsets() {
        mRowSubset = new boolean[PerturbationEngine.pint(__L74, mBoardSize)][PerturbationEngine.pint(__L75, mBoardSize)];
        mColSubset = new boolean[PerturbationEngine.pint(__L76, mBoardSize)][PerturbationEngine.pint(__L77, mBoardSize)];
        mBoxSubset = new boolean[PerturbationEngine.pint(__L78, mBoardSize)][PerturbationEngine.pint(__L79, mBoardSize)];
        for (int i = PerturbationEngine.pint(__L80, 0) ; PerturbationEngine.pboolean(__L83, ((PerturbationEngine.pint(__L81, i)) < (PerturbationEngine.pint(__L82, mBoard.length)))) ; i++) {
            for (int j = PerturbationEngine.pint(__L84, 0) ; PerturbationEngine.pboolean(__L87, ((PerturbationEngine.pint(__L85, j)) < (PerturbationEngine.pint(__L86, mBoard.length)))) ; j++) {
                int value = PerturbationEngine.pint(__L90, mBoard[PerturbationEngine.pint(__L88, i)][PerturbationEngine.pint(__L89, j)]);
                if (PerturbationEngine.pboolean(__L93, ((PerturbationEngine.pint(__L91, value)) != (PerturbationEngine.pint(__L92, 0))))) {
                    setSubsetValue(PerturbationEngine.pint(__L94, i), PerturbationEngine.pint(__L95, j), PerturbationEngine.pint(__L96, value), PerturbationEngine.pboolean(__L97, true));
                } 
            }
        }
    }

    private void setSubsetValue(int i, int j, int value, boolean present) {
        mRowSubset[PerturbationEngine.pint(__L98, i)][PerturbationEngine.pint(__L101, ((PerturbationEngine.pint(__L99, value)) - (PerturbationEngine.pint(__L100, 1))))] = PerturbationEngine.pboolean(__L102, present);
        mColSubset[PerturbationEngine.pint(__L103, j)][PerturbationEngine.pint(__L106, ((PerturbationEngine.pint(__L104, value)) - (PerturbationEngine.pint(__L105, 1))))] = PerturbationEngine.pboolean(__L107, present);
        mBoxSubset[PerturbationEngine.pint(__L110, computeBoxNo(PerturbationEngine.pint(__L108, i), PerturbationEngine.pint(__L109, j)))][PerturbationEngine.pint(__L113, ((PerturbationEngine.pint(__L111, value)) - (PerturbationEngine.pint(__L112, 1))))] = PerturbationEngine.pboolean(__L114, present);
    }

    public boolean solve() {
        return PerturbationEngine.pboolean(__L16, solve(PerturbationEngine.pint(__L14, 0), PerturbationEngine.pint(__L15, 0)));
    }

    public boolean solve(int i, int j) {
        if (PerturbationEngine.pboolean(__L19, ((PerturbationEngine.pint(__L17, i)) == (PerturbationEngine.pint(__L18, mBoardSize))))) {
            i = PerturbationEngine.pint(__L20, 0);
            if (PerturbationEngine.pboolean(__L22, ((++j) == (PerturbationEngine.pint(__L21, mBoardSize))))) {
                return PerturbationEngine.pboolean(__L23, true);
            } 
        } 
        if (PerturbationEngine.pboolean(__L28, ((PerturbationEngine.pint(__L26, mBoard[PerturbationEngine.pint(__L24, i)][PerturbationEngine.pint(__L25, j)])) != (PerturbationEngine.pint(__L27, 0))))) {
            return PerturbationEngine.pboolean(__L33, solve(PerturbationEngine.pint(__L31, ((PerturbationEngine.pint(__L29, i)) + (PerturbationEngine.pint(__L30, 1)))), PerturbationEngine.pint(__L32, j)));
        } 
        for (int value = PerturbationEngine.pint(__L34, 1) ; PerturbationEngine.pboolean(__L37, ((PerturbationEngine.pint(__L35, value)) <= (PerturbationEngine.pint(__L36, mBoardSize)))) ; value++) {
            if (PerturbationEngine.pboolean(__L41, isValid(PerturbationEngine.pint(__L38, i), PerturbationEngine.pint(__L39, j), PerturbationEngine.pint(__L40, value)))) {
                mBoard[PerturbationEngine.pint(__L42, i)][PerturbationEngine.pint(__L43, j)] = PerturbationEngine.pint(__L44, value);
                setSubsetValue(PerturbationEngine.pint(__L45, i), PerturbationEngine.pint(__L46, j), PerturbationEngine.pint(__L47, value), PerturbationEngine.pboolean(__L48, true));
                if (PerturbationEngine.pboolean(__L53, solve(PerturbationEngine.pint(__L51, ((PerturbationEngine.pint(__L49, i)) + (PerturbationEngine.pint(__L50, 1)))), PerturbationEngine.pint(__L52, j)))) {
                    return PerturbationEngine.pboolean(__L54, true);
                } 
                setSubsetValue(PerturbationEngine.pint(__L55, i), PerturbationEngine.pint(__L56, j), PerturbationEngine.pint(__L57, value), PerturbationEngine.pboolean(__L58, false));
            } 
        }
        mBoard[PerturbationEngine.pint(__L59, i)][PerturbationEngine.pint(__L60, j)] = PerturbationEngine.pint(__L61, 0);
        return PerturbationEngine.pboolean(__L62, false);
    }

    private boolean isValid(int i, int j, int val) {
        val--;
        boolean isPresent = PerturbationEngine.pboolean(__L12, ((PerturbationEngine.pboolean(__L6, ((PerturbationEngine.pboolean(__L2, mRowSubset[PerturbationEngine.pint(__L0, i)][PerturbationEngine.pint(__L1, val)])) || (PerturbationEngine.pboolean(__L5, mColSubset[PerturbationEngine.pint(__L3, j)][PerturbationEngine.pint(__L4, val)]))))) || (PerturbationEngine.pboolean(__L11, mBoxSubset[PerturbationEngine.pint(__L9, computeBoxNo(PerturbationEngine.pint(__L7, i), PerturbationEngine.pint(__L8, j)))][PerturbationEngine.pint(__L10, val)]))));
        return !(PerturbationEngine.pboolean(__L13, isPresent));
    }

    private int computeBoxNo(int i, int j) {
        int boxRow = PerturbationEngine.pint(__L65, ((PerturbationEngine.pint(__L63, i)) / (PerturbationEngine.pint(__L64, mBoxSize))));
        int boxCol = PerturbationEngine.pint(__L68, ((PerturbationEngine.pint(__L66, j)) / (PerturbationEngine.pint(__L67, mBoxSize))));
        return PerturbationEngine.pint(__L73, ((PerturbationEngine.pint(__L71, ((PerturbationEngine.pint(__L69, boxRow)) * (PerturbationEngine.pint(__L70, mBoxSize))))) + (PerturbationEngine.pint(__L72, boxCol))));
    }

    static private void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("Sudoku.java:76" , 0 , "Numerical");
        __L1 = new PerturbationLocationImpl("Sudoku.java:76" , 1 , "Numerical");
        __L2 = new PerturbationLocationImpl("Sudoku.java:76" , 2 , "Boolean");
        __L3 = new PerturbationLocationImpl("Sudoku.java:76" , 3 , "Numerical");
        __L4 = new PerturbationLocationImpl("Sudoku.java:76" , 4 , "Numerical");
        __L5 = new PerturbationLocationImpl("Sudoku.java:76" , 5 , "Boolean");
        __L6 = new PerturbationLocationImpl("Sudoku.java:76" , 6 , "Boolean");
        __L7 = new PerturbationLocationImpl("Sudoku.java:76" , 7 , "Numerical");
        __L8 = new PerturbationLocationImpl("Sudoku.java:76" , 8 , "Numerical");
        __L9 = new PerturbationLocationImpl("Sudoku.java:76" , 9 , "Numerical");
        __L10 = new PerturbationLocationImpl("Sudoku.java:76" , 10 , "Numerical");
        __L11 = new PerturbationLocationImpl("Sudoku.java:76" , 11 , "Boolean");
        __L12 = new PerturbationLocationImpl("Sudoku.java:76" , 12 , "Boolean");
        __L13 = new PerturbationLocationImpl("Sudoku.java:77" , 13 , "Boolean");
        __L14 = new PerturbationLocationImpl("Sudoku.java:46" , 14 , "Numerical");
        __L15 = new PerturbationLocationImpl("Sudoku.java:46" , 15 , "Numerical");
        __L16 = new PerturbationLocationImpl("Sudoku.java:46" , 16 , "Boolean");
        __L17 = new PerturbationLocationImpl("Sudoku.java:50" , 17 , "Numerical");
        __L18 = new PerturbationLocationImpl("Sudoku.java:50" , 18 , "Numerical");
        __L19 = new PerturbationLocationImpl("Sudoku.java:50" , 19 , "Boolean");
        __L20 = new PerturbationLocationImpl("Sudoku.java:51" , 20 , "Numerical");
        __L21 = new PerturbationLocationImpl("Sudoku.java:52" , 21 , "Numerical");
        __L22 = new PerturbationLocationImpl("Sudoku.java:52" , 22 , "Boolean");
        __L23 = new PerturbationLocationImpl("Sudoku.java:53" , 23 , "Boolean");
        __L24 = new PerturbationLocationImpl("Sudoku.java:56" , 24 , "Numerical");
        __L25 = new PerturbationLocationImpl("Sudoku.java:56" , 25 , "Numerical");
        __L26 = new PerturbationLocationImpl("Sudoku.java:56" , 26 , "Numerical");
        __L27 = new PerturbationLocationImpl("Sudoku.java:56" , 27 , "Numerical");
        __L28 = new PerturbationLocationImpl("Sudoku.java:56" , 28 , "Boolean");
        __L29 = new PerturbationLocationImpl("Sudoku.java:57" , 29 , "Numerical");
        __L30 = new PerturbationLocationImpl("Sudoku.java:57" , 30 , "Numerical");
        __L31 = new PerturbationLocationImpl("Sudoku.java:57" , 31 , "Numerical");
        __L32 = new PerturbationLocationImpl("Sudoku.java:57" , 32 , "Numerical");
        __L33 = new PerturbationLocationImpl("Sudoku.java:57" , 33 , "Boolean");
        __L34 = new PerturbationLocationImpl("Sudoku.java:59" , 34 , "Numerical");
        __L35 = new PerturbationLocationImpl("Sudoku.java:59" , 35 , "Numerical");
        __L36 = new PerturbationLocationImpl("Sudoku.java:59" , 36 , "Numerical");
        __L37 = new PerturbationLocationImpl("Sudoku.java:59" , 37 , "Boolean");
        __L38 = new PerturbationLocationImpl("Sudoku.java:60" , 38 , "Numerical");
        __L39 = new PerturbationLocationImpl("Sudoku.java:60" , 39 , "Numerical");
        __L40 = new PerturbationLocationImpl("Sudoku.java:60" , 40 , "Numerical");
        __L41 = new PerturbationLocationImpl("Sudoku.java:60" , 41 , "Boolean");
        __L42 = new PerturbationLocationImpl("Sudoku.java:61" , 42 , "Numerical");
        __L43 = new PerturbationLocationImpl("Sudoku.java:61" , 43 , "Numerical");
        __L44 = new PerturbationLocationImpl("Sudoku.java:61" , 44 , "Numerical");
        __L45 = new PerturbationLocationImpl("Sudoku.java:62" , 45 , "Numerical");
        __L46 = new PerturbationLocationImpl("Sudoku.java:62" , 46 , "Numerical");
        __L47 = new PerturbationLocationImpl("Sudoku.java:62" , 47 , "Numerical");
        __L48 = new PerturbationLocationImpl("Sudoku.java:62" , 48 , "Boolean");
        __L49 = new PerturbationLocationImpl("Sudoku.java:63" , 49 , "Numerical");
        __L50 = new PerturbationLocationImpl("Sudoku.java:63" , 50 , "Numerical");
        __L51 = new PerturbationLocationImpl("Sudoku.java:63" , 51 , "Numerical");
        __L52 = new PerturbationLocationImpl("Sudoku.java:63" , 52 , "Numerical");
        __L53 = new PerturbationLocationImpl("Sudoku.java:63" , 53 , "Boolean");
        __L54 = new PerturbationLocationImpl("Sudoku.java:64" , 54 , "Boolean");
        __L55 = new PerturbationLocationImpl("Sudoku.java:66" , 55 , "Numerical");
        __L56 = new PerturbationLocationImpl("Sudoku.java:66" , 56 , "Numerical");
        __L57 = new PerturbationLocationImpl("Sudoku.java:66" , 57 , "Numerical");
        __L58 = new PerturbationLocationImpl("Sudoku.java:66" , 58 , "Boolean");
        __L59 = new PerturbationLocationImpl("Sudoku.java:70" , 59 , "Numerical");
        __L60 = new PerturbationLocationImpl("Sudoku.java:70" , 60 , "Numerical");
        __L61 = new PerturbationLocationImpl("Sudoku.java:70" , 61 , "Numerical");
        __L62 = new PerturbationLocationImpl("Sudoku.java:71" , 62 , "Boolean");
        __L63 = new PerturbationLocationImpl("Sudoku.java:81" , 63 , "Numerical");
        __L64 = new PerturbationLocationImpl("Sudoku.java:81" , 64 , "Numerical");
        __L65 = new PerturbationLocationImpl("Sudoku.java:81" , 65 , "Numerical");
        __L66 = new PerturbationLocationImpl("Sudoku.java:82" , 66 , "Numerical");
        __L67 = new PerturbationLocationImpl("Sudoku.java:82" , 67 , "Numerical");
        __L68 = new PerturbationLocationImpl("Sudoku.java:82" , 68 , "Numerical");
        __L69 = new PerturbationLocationImpl("Sudoku.java:83" , 69 , "Numerical");
        __L70 = new PerturbationLocationImpl("Sudoku.java:83" , 70 , "Numerical");
        __L71 = new PerturbationLocationImpl("Sudoku.java:83" , 71 , "Numerical");
        __L72 = new PerturbationLocationImpl("Sudoku.java:83" , 72 , "Numerical");
        __L73 = new PerturbationLocationImpl("Sudoku.java:83" , 73 , "Numerical");
        __L74 = new PerturbationLocationImpl("Sudoku.java:26" , 74 , "Numerical");
        __L75 = new PerturbationLocationImpl("Sudoku.java:26" , 75 , "Numerical");
        __L76 = new PerturbationLocationImpl("Sudoku.java:27" , 76 , "Numerical");
        __L77 = new PerturbationLocationImpl("Sudoku.java:27" , 77 , "Numerical");
        __L78 = new PerturbationLocationImpl("Sudoku.java:28" , 78 , "Numerical");
        __L79 = new PerturbationLocationImpl("Sudoku.java:28" , 79 , "Numerical");
        __L80 = new PerturbationLocationImpl("Sudoku.java:29" , 80 , "Numerical");
        __L81 = new PerturbationLocationImpl("Sudoku.java:29" , 81 , "Numerical");
        __L82 = new PerturbationLocationImpl("Sudoku.java:29" , 82 , "Numerical");
        __L83 = new PerturbationLocationImpl("Sudoku.java:29" , 83 , "Boolean");
        __L84 = new PerturbationLocationImpl("Sudoku.java:30" , 84 , "Numerical");
        __L85 = new PerturbationLocationImpl("Sudoku.java:30" , 85 , "Numerical");
        __L86 = new PerturbationLocationImpl("Sudoku.java:30" , 86 , "Numerical");
        __L87 = new PerturbationLocationImpl("Sudoku.java:30" , 87 , "Boolean");
        __L88 = new PerturbationLocationImpl("Sudoku.java:31" , 88 , "Numerical");
        __L89 = new PerturbationLocationImpl("Sudoku.java:31" , 89 , "Numerical");
        __L90 = new PerturbationLocationImpl("Sudoku.java:31" , 90 , "Numerical");
        __L91 = new PerturbationLocationImpl("Sudoku.java:32" , 91 , "Numerical");
        __L92 = new PerturbationLocationImpl("Sudoku.java:32" , 92 , "Numerical");
        __L93 = new PerturbationLocationImpl("Sudoku.java:32" , 93 , "Boolean");
        __L94 = new PerturbationLocationImpl("Sudoku.java:33" , 94 , "Numerical");
        __L95 = new PerturbationLocationImpl("Sudoku.java:33" , 95 , "Numerical");
        __L96 = new PerturbationLocationImpl("Sudoku.java:33" , 96 , "Numerical");
        __L97 = new PerturbationLocationImpl("Sudoku.java:33" , 97 , "Boolean");
        __L98 = new PerturbationLocationImpl("Sudoku.java:40" , 98 , "Numerical");
        __L99 = new PerturbationLocationImpl("Sudoku.java:40" , 99 , "Numerical");
        __L100 = new PerturbationLocationImpl("Sudoku.java:40" , 100 , "Numerical");
        __L101 = new PerturbationLocationImpl("Sudoku.java:40" , 101 , "Numerical");
        __L102 = new PerturbationLocationImpl("Sudoku.java:40" , 102 , "Boolean");
        __L103 = new PerturbationLocationImpl("Sudoku.java:41" , 103 , "Numerical");
        __L104 = new PerturbationLocationImpl("Sudoku.java:41" , 104 , "Numerical");
        __L105 = new PerturbationLocationImpl("Sudoku.java:41" , 105 , "Numerical");
        __L106 = new PerturbationLocationImpl("Sudoku.java:41" , 106 , "Numerical");
        __L107 = new PerturbationLocationImpl("Sudoku.java:41" , 107 , "Boolean");
        __L108 = new PerturbationLocationImpl("Sudoku.java:42" , 108 , "Numerical");
        __L109 = new PerturbationLocationImpl("Sudoku.java:42" , 109 , "Numerical");
        __L110 = new PerturbationLocationImpl("Sudoku.java:42" , 110 , "Numerical");
        __L111 = new PerturbationLocationImpl("Sudoku.java:42" , 111 , "Numerical");
        __L112 = new PerturbationLocationImpl("Sudoku.java:42" , 112 , "Numerical");
        __L113 = new PerturbationLocationImpl("Sudoku.java:42" , 113 , "Numerical");
        __L114 = new PerturbationLocationImpl("Sudoku.java:42" , 114 , "Boolean");
    }
}

