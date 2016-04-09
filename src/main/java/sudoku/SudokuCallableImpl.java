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

    @Deprecated
    public static void main(String[] args) {
       long time = System.currentTimeMillis();
        Runner.setup(Sudoku.class, SudokuCallableImpl.class, "runSudoku", new SudokuOracleImpl() , int[][].class);
        Runner.run(new AddOneExplorerImpl());
        Runner.run(new AddNExplorerImpl(1,2,5,10,20,50));
        Runner.run(new RndExplorerImpl(0.001f, 0.002f , 0.005f, 0.009f));
        System.out.println(System.currentTimeMillis() - time + " ms");
    }


}
