import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tests {
    private static double eps;
    private static List<AbstractFunction> functions;
    private static List<Double> l;
    private static List<Double> L;

    @SuppressWarnings("unused")
    public static void testSubmethods() {
        System.out.println("Test single dimension methods");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < functions.size(); ++i) {
            sb.append("        $F_").append(i + 1).append(" = $ & $").append(functions.get(i)).append("$ \\\\ \n");
        }
        System.out.println(sb);
        sb = new StringBuilder();
        for (AbstractFunction function : functions) {
            sb.append(" & ").append(new SteepestDescent(function, eps).process().getSteps().size());
        }
        System.out.println(sb);
        System.out.println();
    }

    private static void addEntry(StringBuilder sb, Trace trace, AbstractFunction f) {
        int steps = trace.getSteps().size();
        Matrix x = trace.getSteps().get(steps - 1);
        sb.append(String.format("%.5f & %d", f.eval(x), steps));
    }

    public static void testDifference() {
        System.out.println("Test different methods and functions");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < functions.size(); ++i) {
            AbstractFunction f = functions.get(i);
            sb.append(String.format("         $F_%d$ & ", i + 1));
            addEntry(sb, new GradientDescent(f, eps, l.get(i), L.get(i)).process(), f);
            sb.append(" & ");
            addEntry(sb, new SteepestDescent(f, eps).process(), f);
            sb.append(" & ");
            addEntry(sb, new ConjugateGradient(f, eps).process(), f);
            sb.append(" \\\\ \\hline\n");
        }
        System.out.println(sb);
    }

    private static Random rand;

    private static int random(int l, int r) {
        return l + rand.nextInt(r + 1 - l);
    }

    private static List<Double> randomList(int dim, int l, int r) {
        List<Double> result = new ArrayList<>(Collections.nCopies(dim, 0.));
        for (int i = 0; i < dim; ++i) {
            result.set(i, (double) random(l, r));
        }
        return result;
    }

    private static DiagonalMatrix randomDiag(int dim, double k) {
        List<Double> data = randomList(dim, 1, (int) k);
        data.set(0, 1.);
        data.set(dim - 1, k);
        return new DiagonalMatrix(data);
    }

    private static void testConditioning(int dimension) {
        System.out.println("Размерность " + dimension);
        for (int c = 1; c <= 2_001; c += 200) {
            DiagonalMatrix A = randomDiag(dimension, c);
            Vector B = new Vector(randomList(dimension, -c, c));
            double C = random(-c, c);
            Vector x0 = new Vector(randomList(dimension, -c, c));
            AbstractFunction f = new CustomFunction(A, B, C, x0);
//            System.out.println(c + " " + new GradientDescent(f, eps, A.minElement(), c).process().getSteps().size());
            System.out.println(c + " " + new SteepestDescent(f, eps).process().getSteps().size());
//            System.out.println(c + " " + new ConjugateGradient(f, eps).process().getSteps().size());
        }
    }

    @SuppressWarnings("unused")
    private static void testDimensionAndConditioning() {
        for (int dimension = 10; dimension <= 10_000; dimension *= 10) {
            testConditioning(dimension);
        }
    }

    private static void init() {
        rand = new Random(System.currentTimeMillis());
        eps = 1e-5;
        functions = List.of(new Function1(), new Function2(), new Function3(), new Function4());
        l = List.of(1.0, 2.0, 13.4559962547, 2.0);
        L = List.of(10.0, 1014.0, 30.5440037453, 8000.0);
    }

    public static void main(String[] args) {
        init();
        testDimensionAndConditioning();
    }
}
