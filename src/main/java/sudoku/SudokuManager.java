package sudoku;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by spirals on 19/04/16.
 */
public class SudokuManager extends OracleManager<int[][]> {

    private BufferedReader br;

    private static final String PATH_TO_GRID_FILE = "resources/sudoku/grid/grid.txt";

    public SudokuManager() {
        super();
        super.header = Runner.numberOfTask + " sudoku grid \n";
        super.header += "Those grid are read from file in resources/sudoku/grid.txt\n";
        super.path = "sudoku";
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int[][] generateOneTask() {
        int[][] grid = new int[9][9];
        try {
            if (br == null)
                br = new BufferedReader(new FileReader(PATH_TO_GRID_FILE));
            //Trash Header
            br.readLine();
            for (int row = 0; row < 9; row++) {
                String rowAsStr = br.readLine();
                for (int col = 0; col < rowAsStr.length(); col++) {
                    grid[row][col] = Integer.parseInt(rowAsStr.charAt(col) + "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }


    @Override
    public int[][] get(int index) {
        int [][] clone = new int[9][9];
        int [][] originalValue = scenario.get(index);
        for (int row = 0 ; row <originalValue.length ; row++) {
            for (int col = 0 ; col < originalValue[row].length ; col++) {
                clone[row][col] =originalValue[row][col];
            }
        }
        return clone;
    }

    @Override
    public Oracle<int[][], ?> getOracle() {
        return new SudokuOracle();
    }
}
