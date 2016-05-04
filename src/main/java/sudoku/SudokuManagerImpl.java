package sudoku;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class SudokuManagerImpl extends OracleManagerImpl<int[][]> {

    private List<int[][]> grids;

    private BufferedReader br;

    private static final String PATH_TO_GRID_FILE = "resources/sudoku/grid/grid.txt";

    public SudokuManagerImpl(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " sudoku grid \n";
        super.header += "Those grid are read from file in resources/sudoku/grid.txt\n";
        super.path = "sudoku";
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SudokuManagerImpl() {
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

    private void readFile() {
        grids = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(PATH_TO_GRID_FILE));
            while (br.readLine() != null) {//Trash Header
                int[][] grid = new int[9][9];
                for (int row = 0; row < 9; row++) {
                    String rowAsStr = br.readLine();
                    for (int col = 0; col < rowAsStr.length(); col++) {
                        grid[row][col] = Integer.parseInt(rowAsStr.charAt(col) + "");
                    }
                }
                grids.add(grid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int[][] generateOneTask() {
        if (br == null)
            readFile();

        int[][] grid = grids.get(super.scenario.size() % grids.size());
        int nbCellToErase = 10 + (int) (Runner.sizeOfEachTask * 0.1f);
        for (int i = 0; i < nbCellToErase; i++) {
            int indexToErase = 0;
            do {
                indexToErase = randomForGenTask.nextInt(81);
            } while (grid[indexToErase % 9][(indexToErase) / 9] == 0);
            grid[indexToErase % 9][indexToErase / 9] = 0;
        }
        return grid;
    }


    @Override
    public int[][] get(int index) {
        int[][] clone = new int[9][9];
        int[][] originalValue = scenario.get(index);
        for (int row = 0; row < originalValue.length; row++) {
            for (int col = 0; col < originalValue[row].length; col++) {
                clone[row][col] = originalValue[row][col];
            }
        }
        return clone;
    }

    @Override
    public Oracle<int[][], ?> getOracle() {
        return new SudokuOracle();
    }

}
