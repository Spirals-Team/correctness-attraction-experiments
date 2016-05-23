package sudoku;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 19/04/16.
 */
public class SudokuManager extends ManagerImpl<int[][],int[][]> {

    private List<int[][]> grids;

    private BufferedReader br;

    private static final String PATH_TO_GRID_FILE = "resources/sudoku/grid/grid.txt";

    public SudokuManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public SudokuManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = SudokuInstr.class;
        super.initialize(numberOfTask, size);
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

        int[][] grid = grids.get(super.indexTasks.size() % grids.size());
        int nbCellToErase = 10 + (int) (super.sizeOfTask * 0.1f);
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
    public int[][] getTask(int index) {
        int[][] clone = new int[9][9];
        int[][] originalValue = super.tasks.get(index);
        for (int row = 0; row < originalValue.length; row++) {
            for (int col = 0; col < originalValue[row].length; col++) {
                clone[row][col] = originalValue[row][col];
            }
        }
        return clone;
    }

    @Override
    public CallableImpl<int[][], int[][]> getCallable(int[][] input) {
        return new SudokuCallableImpl(input);
    }

    @Override
    public String getName() {
        return "sudoku";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " sudoku grid \n"
                + "Those grid are read from file in resources/sudoku/grid.txt\n"+
                super.locations.size() + " perturbations points\n";
    }

    @Override
    public Oracle<int[][], int[][]> getOracle() {
        return new SudokuOracle();
    }

}
