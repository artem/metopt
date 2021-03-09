import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    private static final ArrayList<Long> fib = new ArrayList<>(List.of(1L, 1L));

    public static long get(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        if (n > fib.size()) {
            fib.ensureCapacity(n);
            for (int i = fib.size() + 1; i <= n; i++) {
                fib.add(fib.get(i - 3) + fib.get(i - 2));
            }
        }

        return fib.get(n - 1);
    }

    public static double relation(int a, int b) {
        int max = a - ((a - b) & (a - b) >> 31);
        if (max > 91) {
            final double funnyConst = (Math.sqrt(5.0) + 1.0) / 2.0;
            return Math.pow(funnyConst, a - b);
        } else {
            return (double) get(a) / (double) get(b);
        }
    }
}