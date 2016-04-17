package optimizer;

import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.PivotSelectionRule;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by spirals on 15/04/16.
 */
public class MPSParser {

    public static List<List<LinearConstraint>> listOfListOfConstraints = new ArrayList<>();

    private static class Tuple {
        String row;
        double rate;
        public Tuple(String nrow, double nrate) {
            row = nrow;
            rate = nrate;
        }
        public String toString() {
            return row + "\t" + rate;
        }
    }


    public static OptimizationData[] run(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String name = br.readLine().trim().replaceAll(" +", " ").split(" ")[1];
            Map<String, Character> rows = readRows(br);
            Map<String, List<Tuple>> col = readColumns(br);
            Map<String, Double> RHS = readRHS(br);

            String obj = nameOfObjectiv(rows);
            rows.remove(obj);

            LinearObjectiveFunction f = new LinearObjectiveFunction(buildLinearObjectiveFunction(obj, col), 0);
            List<LinearConstraint> constraints = buildLinearConstraints(rows, col, RHS, obj);

            listOfListOfConstraints.add(constraints);

            //we will select lp to not have any ranges clause
            String bounds = br.readLine();
            if (bounds == null)
                return new OptimizationData[] {f, new LinearConstraintSet(constraints),
                            GoalType.MAXIMIZE,
                            new NonNegativeConstraint(true),
                            PivotSelectionRule.BLAND};
            else {
                //@TODO
                return new OptimizationData[] {f, new LinearConstraintSet(constraints),
                        GoalType.MAXIMIZE,
                        new NonNegativeConstraint(true),
                        PivotSelectionRule.BLAND};
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<LinearConstraint> buildLinearConstraints(Map<String, Character> rows, Map<String, List<Tuple>> col,
                                              Map<String, Double> RHS, String obj) {
        Map<String, double[]> linearConstraints = new HashMap<>();
        List<String> keys = new ArrayList<>(col.keySet());
        ArrayList<LinearConstraint> constraints = new ArrayList<>();
        for (String key : keys) {
            for (Tuple t : col.get(key)) {
                if (!t.row.equals(obj)) {
                    if (!linearConstraints.containsKey(t.row))
                        linearConstraints.put(t.row, new double[keys.size()]);
                    linearConstraints.get(t.row)[keys.indexOf(key)] = t.rate;
                }
            }
        }
        for (String row : rows.keySet())
            constraints.add(new LinearConstraint(linearConstraints.get(row), getRelation(rows.get(row)), RHS.getOrDefault(row, 0.0d)));
        return constraints;
    }

    private static Relationship getRelation(Character relation) {
        switch (relation) {
            case 'E' :
                return Relationship.EQ;
            case 'L':
                return Relationship.LEQ;
            case 'G':
                return Relationship.GEQ;
            default:
                return null;
        }
    }

    private static double[] buildLinearObjectiveFunction(String obj, Map<String, List<Tuple>> col) {
        double [] function = new double[col.keySet().size()];
        List<String> keys = new ArrayList<>(col.keySet());
        for (String key : keys) {
            for (Tuple t : col.get(key)) {
                if (t.row.equals(obj))
                    function[keys.indexOf(key)] = t.rate;
            }
        }
        return function;
    }

    private static String nameOfObjectiv(Map<String, Character> rows) {
        for (String key : rows.keySet()) {
            if (rows.get(key) == 'N')
                return key;
        }
        return null;
    }

    private static Map<String, Double> readRHS(BufferedReader br) throws IOException {
        Map<String, Double> rhs = new LinkedHashMap<>();
        String s;
        while (!isHeader(s = br.readLine())) {
            String[] sAsArray = s.trim().replaceAll(" +", " ").split(" ");
            for (int i = 1 ; i < sAsArray.length ; i = i + 2)
                rhs.put(sAsArray[i], parseDouble(sAsArray[i+1]));
        }
        return rhs;
    }

    private static Map<String, List<Tuple>> readColumns(BufferedReader br) throws IOException {
        Map<String, List<Tuple>> columns = new LinkedHashMap<>();
        String s;
        while (!isHeader(s = br.readLine())) {
            String[] sAsArray = s.trim().replaceAll(" +", " ").split(" ");
            if (!columns.containsKey(sAsArray[0]))
                columns.put(sAsArray[0], new ArrayList<>());
            for (int i = 1 ; i < sAsArray.length ; i = i + 2)
                columns.get(sAsArray[0]).add(new Tuple(sAsArray[i], parseDouble(sAsArray[i + 1])));
        }
        return columns;
    }

    private static double parseDouble(String str) {
        if (str.startsWith("."))
            return Double.parseDouble("0"+str);
        else if (str.startsWith("-") && str.charAt(1) == '.')
            return Double.parseDouble("-0"+str.substring(1));
        else if (str.endsWith("."))
            return Double.parseDouble(str+"0");
        else
            return Double.parseDouble(str);
    }

    private static Map<String, Character> readRows(BufferedReader br) throws IOException {
        Map<String, Character> rows = new LinkedHashMap<>();
        br.readLine();//trash ROWS
        String s;
        while ( ! isHeader(s = br.readLine())) {
            String[] sAsArray = s.trim().replaceAll(" +", " ").split(" ");
            rows.put(sAsArray[1], sAsArray[0].charAt(0));
        }
        return rows;
    }

    private static boolean isHeader(String line) {
        return ! (line.charAt(0) == ' ');
    }

    public static void main(String[] args) {
        run("resources/optimizer/lp");
    }

}
