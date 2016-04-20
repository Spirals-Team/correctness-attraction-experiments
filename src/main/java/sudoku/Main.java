package sudoku;

import experiment.Runner;
import experiment.Util;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run sudoku...");
        Runner.setup(SudokuInstr.class, SudokuCallableImpl.class, new SudokuManager() , int[][].class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        else {
            Runner.numberOfTask = 1;
            Runner.numberOfSecondsToWait = 15;
        }
        run();
    }

}
