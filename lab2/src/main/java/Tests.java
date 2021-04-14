import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tests {
    public static void testSubmethods(double eps, List<AbstractFunction> functions, List<Double> Ls) {
        System.out.println("Test single dimension methods");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < functions.size(); ++i) {
            sb.append("        $F_").append(i + 1).append(" = $ & $").append(functions.get(i)).append("$ \\\\ \n");
        }
        System.out.println(sb.toString());
        sb = new StringBuilder();
        for (int i = 0; i < functions.size(); ++i) {
            sb.append(" & ").append(new SteepestDescent(functions.get(i), eps, Ls.get(i)).process().getSteps().size());
        }
        System.out.println(sb.toString());
        System.out.println();
    }

    private static void addEntry(StringBuilder sb, Trace trace, AbstractFunction f) {
        int steps = trace.getSteps().size();
        Matrix x = trace.getSteps().get(steps - 1);
        sb.append(String.format("%.5f & %d", f.eval(x), steps));
    }

    public static void testDifference(double eps, List<AbstractFunction> functions, List<Double> Ls) {
        System.out.println("Test different methods and functions");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < functions.size(); ++i) {
            AbstractFunction f = functions.get(i);
            sb.append(String.format("         $F_%d$ & ", i + 1));
            addEntry(sb, new GradientDescent(f, eps).process(1, false), f);
            sb.append(" & ");
            addEntry(sb, new GradientDescent(f, eps).process(1, true), f);
            sb.append(" & ");
            addEntry(sb, new SteepestDescent(f, eps).process(), f);
            sb.append(" & ");
            addEntry(sb, new ConjugateGradient(f, eps).process(), f);
            sb.append(" \\\\ \\hline\n");
        }
        System.out.println(sb.toString());
    }

    private static Random rand;

    private static int random(int l, int r) {
        r++;
        return l + rand.nextInt(r - l);
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
        System.out.println("\\subsection*{Размерность " + dimension + "}");
        System.out.println("\\begin{center}");
        System.out.println("    \\begin{tabular}{|c|c|}");
        System.out.println("    \\hline");
        System.out.println("        Обусловленность & Число итераций \\\\ \\hline");
        for (int c = 1; c <= 2_001; c += 200) {
            DiagonalMatrix A = randomDiag(dimension, c);
            Vector B = new Vector(randomList(dimension, -c, c));
            double C = random(-c, c);
            Vector x0 = new Vector(randomList(dimension, -c, c));
            AbstractFunction f = new CustomFunction(A, B, C, x0);
            System.out.println("        " + c + " & " + new SteepestDescent(f, 1e-5, c).process().getSteps().size() + " \\\\ \\hline");
        }
        System.out.println("    \\end{tabular}");
        System.out.println("\\end{center}");
    }

    private static void testDimensionAndConditioning() {
        for (int dimension = 10; dimension <= 10_000; dimension *= 10) {
            testConditioning(dimension);
        }
    }

    public static void main(String[] args) {
        double eps = 1e-4;
        List<AbstractFunction> functions = List.of(new Function1(), new Function2(), new Function3(), new Function4());
        List<Double> Ls = List.of(254., 1014., 40., 8000.);

        rand = new Random(System.currentTimeMillis());

        testSubmethods(eps, functions, Ls);
        testDifference(eps, functions, Ls);
        testDimensionAndConditioning();
    }
}
