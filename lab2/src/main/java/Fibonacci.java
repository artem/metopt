import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    private static final ArrayList<Double> fib = new ArrayList<>(List.of(0.0, 1.0, 1.0));

    public static double get(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        if (n > fib.size() - 1) {
            fib.ensureCapacity(n + 1);
            for (int i = fib.size(); i <= n; i++) {
                fib.add(fib.get(i - 2) + fib.get(i - 1));
            }
        }

        return fib.get(n);
    }

    public static double relation(int a, int b) {
        return get(a) / get(b);
    }
}
