package metopt.lab4;

import metopt.lab4.matrices.Matrix;

public class LU {
    private final Matrix matrix;

    public double getL(int i, int j) {
        return matrix.get(i, j);
    }

    public double getU(int i, int j) {
        if (i == j)
            return 1;
        return getL(i, j);
    }

    public void set(int i, int j, double value) {
        matrix.set(i, j, value);
    }

    public LU(Matrix A) {
        this.matrix = A;
        int n = A.size();
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                double Lij = A.get(i, j);
                for (int k = 0; k < j; k++) {
                    Lij -= matrix.get(i, k) * matrix.get(k, j);
                }
                matrix.set(i, j, Lij);
            }
            for (int j = 0; j < i; j++) {
                double Uji = A.get(j, i);
                for (int k = 0; k < j; k++) {
                    Uji -= matrix.get(j, k) * matrix.get(k, i);
                }
                matrix.set(j, i, Uji / matrix.get(j, j));
            }
            double Lii = A.get(i, i);
            for (int k = 0; k < i; k++) {
                Lii -= matrix.get(i, k) * matrix.get(k, i);
            }
            matrix.set(i, i, Lii);
        }
    }
}
