import java.util.List;

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

    public static void main(String[] args) {
        final double eps = 1e-5;
        List<AbstractFunction> functions = List.of(new Function1(), new Function2(), new Function3(), new Function4());
        List<Double> Ls = List.of(254., 1014., 40., 8000.);

        testSubmethods(eps, functions, Ls);
        testDifference(eps, functions, Ls);
    }
}
