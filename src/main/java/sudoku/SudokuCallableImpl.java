package sudoku;

import experiment.*;

/**
 * Created by spirals on 08/04/16.
 */
public class SudokuCallableImpl extends CallableImpl<int[][],Sudoku> {

    public SudokuCallableImpl(int[][] originalValue) {
        super(originalValue);
    }

    @Override
    public Sudoku call() throws Exception {
        Sudoku sudoku = new Sudoku(super.originalValue);
        sudoku.initSubsets();
        sudoku.solve();
        return sudoku;
    }
}
