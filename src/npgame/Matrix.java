/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

/**
 *
 * @author Lorenzo
 */
public class Matrix {

    public static final double EPS = 10E-10;

    public static Matrix solve(Matrix A, Matrix b) {
        int N = b.rows;

        for (int p = 0; p < N; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A.get(i, p)) > Math.abs(A.get(max, p))) {
                    max = i;
                }
            }

            A = A.swap(p, max);
            b = b.swap(p, max);

            // singular or nearly singular
            if (Math.abs(A.get(p, p)) <= EPS) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                double alpha = A.get(i, p) / A.get(p, p);
                b.set(i, 0, b.get(i) - alpha * b.get(p));
                for (int j = p; j < N; j++) {
                    A.set(i, j, A.get(i, j) - alpha * A.get(p, j));
                }
            }
        }

        // back substitution
        Matrix x = new Matrix(N, 1);
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A.get(i,j) * x.get(j);
            }
            x.set(i, (b.get(i) - sum) / A.get(i,i));
        }
        return x;
    }

    int rows;
    int columns;
    double[][] values;

    public Matrix(int n) {
        this(new double[n][1]);
    }

    public Matrix(int r, int c) {
        this(new double[r][c]);
    }

    public Matrix(double[][] values) {
        this.values = values;
        this.rows = values.length;
        this.columns = values[0].length;
    }
    
    public Matrix(Matrix m) {
        this.rows = m.rows;
        this.columns = m.columns;
        this.values = new double[rows][columns];
        for (int i = 0; i < values.length; i++) {
            System.arraycopy(m.values[i], 0, values[i], 0, values[i].length);
        }
    }

    public Matrix inv() {
        double det = det();
        if (Math.abs(det) > EPS) {
            return cof().transpose().mul(1.0 / det);
        } else {
            throw new RuntimeException("Matrix is singular");
        }
    }

    private Matrix cof() {
        Matrix c = new Matrix(rows, columns);
        for (int i = 0; i < c.rows; i++) {
            for (int j = 0; j < c.columns; j++) {
                Matrix appo = sub(i, j);
                c.values[i][j] = Math.pow(-1, i + j) * appo.det();
            }
        }
        return c;
    }

    private Matrix sub(int i, int j) {
        Matrix s = new Matrix(rows - 1, columns - 1);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (r != i && c != j) {
                    int row = (r < i) ? r : r - 1;
                    int col = (c < j) ? c : c - 1;
                    s.set(row, col, values[r][c]);
                }
            }
        }
        return s;
    }

    public void set(int row, int col, double value) {
        values[row][col] = value;
    }
    
    public void set(int row, double value) {
        values[row][0] = value;
    }

    public Matrix transpose() {
        Matrix m = new Matrix(columns, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                m.set(i, j, values[j][i]);
            }
        }
        return m;
    }

    public double det() {
        if (values.length == 1) {
            return values[0][0];
        }
        double det = 0;
        for (int i = 0; i < rows; i++) {
            det += Math.pow(-1, i) * values[i][0] * sub(i, 0).det();
        }
        return det;
    }

    public Matrix mul(double d) {
        Matrix m = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                m.set(i, j, values[i][j] * d);
            }
        }
        return m;
    }

    public Matrix mul(Matrix m) {
        Matrix res = new Matrix(rows, m.columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                double s = 0;
                for (int k = 0; k < columns; k++) {
                    s += values[i][k] * m.values[k][j];
                }
                res.set(i, j, s);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] value : values) {
            for (int j = 0; j < value.length; j++) {
                sb.append(String.format("%5.2f", value[j])).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public double get(int r, int c) {
        return values[r][c];
    }
    
    public double get(int r) {
        return values[r][0];
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    private Matrix swap(int r1, int r2) {
        Matrix ret = new Matrix(this);
        double[] appo = ret.values[r1];
        ret.values[r1] = ret.values[r2];
        ret.values[r2] = appo;
        return ret;
    }

}
