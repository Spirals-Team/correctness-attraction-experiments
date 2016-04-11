package sudoku;

import experiment.*;

/**
 * Created by spirals on 08/04/16.
 */
public class SudokuCallableImpl extends CallableImpl<int[][]> {

    public SudokuCallableImpl(int[][] originalValue) {
        super(originalValue);
    }

    @Override
    public int[][] call() throws Exception {
        Sudoku sudoku = new Sudoku(super.originalValue);
        sudoku.initSubsets();
        sudoku.solve();
        return sudoku.getGrid();
    }

    public static void run() {
        System.out.println("Run sudoku...");
        Runner.setup(Sudoku.class, SudokuCallableImpl.class, "runSudoku", new SudokuOracleImpl() , int[][].class);
        Runner.runAllCampaign();
    }


}
