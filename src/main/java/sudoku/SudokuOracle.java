package sudoku;

import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 08/04/16.
 */
public class SudokuOracle implements Oracle<int[][],int[][]> {

    private List<Integer> buildListOf9Integer() {
        List<Integer> lst = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++)
            lst.add(i);
        return lst;
    }

    @Override
    public boolean assertPerturbation(int[][] input, int[][] output) {
        for (int row = 0; row < output.length; row++) {
            List<Integer> listOfInteger = buildListOf9Integer();
            for (int col = 0; col < output[row].length; col++) {
                if (!listOfInteger.remove(new Integer(output[row][col])))
                    return false;
                if (input[row][col] != 0 && input[row][col] != output[row][col])
                    return false;
            }
        }
//        for (int col = 0; col < output.length; col++) {
//            List<Integer> listOfInteger = buildListOf9Integer();
//            for (int row = 0; row < output[row].length; row++) {
//                if (!listOfInteger.remove(new Integer(output[row][col])))
//                    return false;
//                if (input[row][col] != 0 && input[row][col] != output[row][col])
//                    return false;
//            }
//        }
        return true;
    }
}
