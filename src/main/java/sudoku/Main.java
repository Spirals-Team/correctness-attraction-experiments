package sudoku;

import experiment.Runner;

/**
 * Created by spirals on 13/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run sudoku...");
        Runner.setup(Sudoku.class, SudokuCallableImpl.class, new SudokuOracleImpl() , int[][].class);
        Runner.runAllCampaign();
    }

    public static void main(String[] args) {
        run();
    }

}
