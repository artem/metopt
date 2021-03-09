import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    private static final ArrayList<Double> fib = new ArrayList<>(List.of(1.0, 1.0));

    public static double get(final int n) {
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
        return get(a) / get(b);
    }
}
