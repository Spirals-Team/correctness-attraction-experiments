package sudoku;

import experiment.*;

/**
 * Created by spirals on 08/04/16.
 */
public class SudokuCallableImpl extends CallableImpl<int[][],int[][]> {

    public SudokuCallableImpl(int[][] input) {
        super(input);
    }

    @Override
    public int[][] call() throws Exception {
        SudokuInstr sudoku = new SudokuInstr(super.input);
        sudoku.initSubsets();
        sudoku.solve();
        return sudoku.getGrid();
    }
}
