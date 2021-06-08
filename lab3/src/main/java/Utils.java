import java.util.*;

import static java.lang.Math.abs;

public class Utils {
    private static final double EPS = 1e-20;

    private static int n;
    private static double[][] a;
    private static double[] b;
    private static int[] columnByIndex;
    private static int[] indexByColumn;
    private final static Stack<Integer> swap1 = new Stack<>();
    private final static Stack<Integer> swap2 = new Stack<>();

    private static void swapRows(final int i1, final int i2) {
        if (i1 == i2) {
            return;
        }
        final double[] tmp1 = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp1;
        final double tmp2 = b[i1];
        b[i1] = b[i2];
        b[i2] = tmp2;
        swap1.push(i1);
        swap2.push(i2);
    }

    private static void swapColumns(final int i1, final int i2) {
        final int c1 = columnByIndex[i1];
        final int c2 = columnByIndex[i2];
        columnByIndex[i1] = c2;
        columnByIndex[i2] = c1;
        indexByColumn[c1] = i2;
        indexByColumn[c2] = i1;
    }

    private static int rowMaxUnder(final int r, final int c) {
        int x = r;
        for (int i = r + 1; i < n; ++i) {
            if (abs(a[i][c]) > abs(a[x][c])) {
                x = i;
            }
        }
        return x;
    }

    private static int colMaxRighter(final int r, final int c) {
        final double[] row = a[r];
        int x = indexByColumn[c];
        for (int j = c + 1; j < n; ++j) {
            if (abs(row[indexByColumn[j]]) > abs(row[x])) {
                x = j;
            }
        }
        return x;
    }

    private static void combineRows(final int i, final int t, final double k, int c) {
        if (abs(k) < EPS) {
            return;
        }
        while (c < n) {
            a[t][indexByColumn[c]] += k * a[i][indexByColumn[c]];
            ++c;
        }
        b[t] += k * b[i];
    }

    private static int[] forwardGauss() {
        columnByIndex = new int[n];
        indexByColumn = new int[n];
        for (int i = 0; i < n; ++i) {
            columnByIndex[i] = indexByColumn[i] = i;
        }
        for (int i = 0; i < n; ++i) {
            int j = indexByColumn[i];
            swapRows(i, rowMaxUnder(i, j));
            if (abs(a[i][j]) < EPS) {
                swapColumns(j, colMaxRighter(i, i));
                j = indexByColumn[i];
            }
            if (abs(a[i][j]) < EPS) {
                return null; // no solution
            }
            for (int t = i + 1; t < n; ++t) {
                combineRows(i, t, -a[t][j] / a[i][j], i);
            }
        }
        return indexByColumn;
    }

    private static int[] backwardGauss(final int[] indexByColumn) {
        if (indexByColumn == null) {
            return null;
        }
        for (int i = n - 1; i >= 0; --i) {
            final int j = indexByColumn[i];
            for (int t = i - 1; t >= 0; --t) {
                combineRows(i, t, -a[t][j] / a[i][j], i);
            }
        }
        return indexByColumn;
    }

    private static void normalizeAnswer(final int[] indexByColumn) {
        final double[] x = Arrays.copyOf(b, n);
        for (int i = 0; i < n; ++i) {
            x[i] = b[indexByColumn[i]] / a[i][indexByColumn[i]];
        }
        b = x;
        while (!swap1.isEmpty()) {
            final int i = swap1.peek();
            final int j = swap2.peek();
            final double tmp = b[i];
            b[i] = b[j];
            b[j] = tmp;
            swap1.pop();
            swap2.pop();
        }
    }

    /**
     * Return solution for liner equations system (null if no solution).
     * @param A matrix
     * @param B vector
     * @return solution for linear equations system or {@code null} if no solution.
     */
    public static Vector Gauss(final Matrix A, final Vector B) {
        n = A.size();
        a = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = A.get(i, j);
            }
        }
        b = new double[n];
        for (int i = 0; i < n; ++i) {
            b[i] = B.get(i);
        }
        final int[] indexByColumn = backwardGauss(forwardGauss());
        if (indexByColumn == null) {
            return null;
        }
        normalizeAnswer(indexByColumn);
        return new Vector(b);
    }

    public static Vector GaussLU(final Matrix A, final Vector B) {
        final LU lu = new LU(A);
        n = A.size();
        a = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                a[i][j] = lu.getL(i, j);
            }
            for (int j = i + 1; j < n; ++j) {
                a[i][j] = 0.0;
            }
        }
        b = new double[n];
        for (int i = 0; i < n; ++i) {
            b[i] = B.get(i);
        }
        final int[] indexByColumn = forwardGauss();
        if (indexByColumn == null) {
            return null;
        }
        normalizeAnswer(indexByColumn);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                a[i][j] = 0.0;
            }
            for (int j = i; j < n; ++j) {
                a[i][j] = lu.getU(i, j);
            }
            indexByColumn[i] = i;
        }
        backwardGauss(indexByColumn);
        return new Vector(b);
    }

    public static Vector gauss(final FullMatrix aold, final Vector bold) {
        final FullMatrix a = new FullMatrix(aold);
        final Vector b = new Vector(bold);
        final int n = a.size();
        final Vector ans = new Vector(n);

        final List<Integer> columnSelect = new ArrayList<>(Collections.nCopies(n, -1));
        int recentRow = 0;
        for (int i = 0; i < n && recentRow < n; i++) {
            // опорный элемент
            int m = recentRow;
            for (int k = recentRow; k < n; k++) {
                if (a.get(k, i) != 0 && Math.abs(a.get(k, i)) < Math.abs(a.get(m, i))) {
                    m = k;
                }
            }

            // прямой ход
            if (Math.abs(a.get(m, i)) > EPS) {
                if (m != recentRow) {
                    a.swapRows(m, recentRow);
                    b.swap(m, recentRow);
                }
                columnSelect.set(i, recentRow);
                for (int t = 0; t < n; t++) {
                    if (t != recentRow) {
                        final double c = a.get(t, i) / a.get(recentRow, i);

                        double newVal;
                        for (int k = i; k < n; ++k) {
                            newVal = a.get(t, k) - c * a.get(recentRow, k);
                            a.set(t, k, newVal);
                        }
                        newVal = b.get(t) - c * b.get(recentRow);
                        b.set(t, newVal);
                    }
                }
                recentRow++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (columnSelect.get(i) != -1) {
                ans.set(i, b.get(columnSelect.get(i)) / a.get(columnSelect.get(i), i));
            }
        }
        return ans;
    }
}
