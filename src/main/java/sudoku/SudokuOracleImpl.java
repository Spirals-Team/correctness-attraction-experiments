package sudoku;

import experiment.OracleImpl;
import experiment.Runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 08/04/16.
 */
public class SudokuOracleImpl extends OracleImpl<int[][],Sudoku> {

    private BufferedReader br;

    private static final String PATH_TO_GRID_FILE = "resources/sudoku/grid/grid.txt";

    public SudokuOracleImpl() {
        super();
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
    public String header() {
        String header = Runner.numberOfTask + " sudoku grid \n";
        header += "Those grid are read from file in resources/sudoku/grid.txt\n";
        return header;
    }

    @Override
    public boolean check(Sudoku perturbedValue, int index) {
        int[][] perturbedGrid = perturbedValue.getGrid();
        int[][] originalGrid = get(index);
        for (int row = 0; row < perturbedGrid.length; row++) {
            List<Integer> listOfInteger = buildListOf9Integer();
            for (int col = 0; col < perturbedGrid[row].length; col++) {
                if (!listOfInteger.remove(new Integer(perturbedGrid[row][col])))
                    return false;
                if (originalGrid[row][col] != 0 && originalGrid[row][col] != perturbedGrid[row][col])
                    return false;
            }
        }
//        for (int col = 0; col < perturbedValue.length; col++) {
//            List<Integer> listOfInteger = buildListOf9Integer();
//            for (int row = 0; row < perturbedValue[row].length; row++) {
//                if (!listOfInteger.remove(new Integer(perturbedValue[row][col])))
//                    return false;
//                if (originalGrid[row][col] != 0 && originalGrid[row][col] != perturbedValue[row][col])
//                    return false;
//            }
//        }
        return true;
    }

    private List<Integer> buildListOf9Integer() {
        List<Integer> lst = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++)
            lst.add(i);
        return lst;
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
}
