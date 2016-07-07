

package weka.core.matrix;


public class LinearRegression implements weka.core.RevisionHandler {
    protected double[] m_Coefficients = null;

    public LinearRegression(weka.core.matrix.Matrix a, weka.core.matrix.Matrix y, double ridge) {
        calculate(a, y, ridge);
    }

    public LinearRegression(weka.core.matrix.Matrix a, weka.core.matrix.Matrix y, double[] w, double ridge) {
        if ((w.length) != (a.getRowDimension()))
            throw new java.lang.IllegalArgumentException("Incorrect number of weights provided");
        
        Matrix weightedThis = new Matrix(a.getRowDimension(), a.getColumnDimension());
        Matrix weightedDep = new Matrix(a.getRowDimension(), 1);
        for (int i = 0; i < (w.length); i++) {
            double sqrt_weight = java.lang.Math.sqrt(w[i]);
            for (int j = 0; j < (a.getColumnDimension()); j++)
                weightedThis.set(i, j, ((a.get(i, j)) * sqrt_weight));
            weightedDep.set(i, 0, ((y.get(i, 0)) * sqrt_weight));
        }
        calculate(weightedThis, weightedDep, ridge);
    }

    protected void calculate(weka.core.matrix.Matrix a, weka.core.matrix.Matrix y, double ridge) {
        if ((y.getColumnDimension()) > 1)
            throw new java.lang.IllegalArgumentException("Only one dependent variable allowed");
        
        int nc = a.getColumnDimension();
        m_Coefficients = new double[nc];
        Matrix solution;
        Matrix ss = weka.core.matrix.LinearRegression.aTa(a);
        Matrix bb = weka.core.matrix.LinearRegression.aTy(a, y);
        boolean success = true;
        do {
            Matrix ssWithRidge = ss.copy();
            for (int i = 0; i < nc; i++)
                ssWithRidge.set(i, i, ((ssWithRidge.get(i, i)) + ridge));
            try {
                solution = ssWithRidge.solve(bb);
                for (int i = 0; i < nc; i++)
                    m_Coefficients[i] = solution.get(i, 0);
                success = true;
            } catch (java.lang.Exception ex) {
                ridge *= 10;
                success = false;
            }
        } while (!success );
    }

    private static weka.core.matrix.Matrix aTa(weka.core.matrix.Matrix a) {
        int cols = a.getColumnDimension();
        double[][] A = a.getArray();
        Matrix x = new Matrix(cols, cols);
        double[][] X = x.getArray();
        double[] Acol = new double[a.getRowDimension()];
        for (int col1 = 0; col1 < cols; col1++) {
            for (int row = 0; row < (Acol.length); row++) {
                Acol[row] = A[row][col1];
            }
            double[] Xrow = X[col1];
            for (int row = 0; row < (Acol.length); row++) {
                double[] Arow = A[row];
                for (int col2 = col1; col2 < (Xrow.length); col2++) {
                    Xrow[col2] += (Acol[row]) * (Arow[col2]);
                }
            }
            for (int col2 = col1 + 1; col2 < (Xrow.length); col2++) {
                X[col2][col1] = Xrow[col2];
            }
        }
        return x;
    }

    private static weka.core.matrix.Matrix aTy(weka.core.matrix.Matrix a, weka.core.matrix.Matrix y) {
        double[][] A = a.getArray();
        double[][] Y = y.getArray();
        Matrix x = new Matrix(a.getColumnDimension(), 1);
        double[][] X = x.getArray();
        for (int row = 0; row < (A.length); row++) {
            double[] Arow = A[row];
            double[] Yrow = Y[row];
            for (int col = 0; col < (Arow.length); col++) {
                X[col][0] += (Arow[col]) * (Yrow[0]);
            }
        }
        return x;
    }

    public final double[] getCoefficients() {
        return m_Coefficients;
    }

    public java.lang.String toString() {
        return weka.core.Utils.arrayToString(getCoefficients());
    }

    public java.lang.String getRevision() {
        return weka.core.RevisionUtils.extract("$Revision: 9768 $");
    }
}

