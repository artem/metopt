import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    private static final double EPS = 1e-9;

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
                if (Math.abs(a.get(k, i)) < Math.abs(a.get(m, i))) {
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
