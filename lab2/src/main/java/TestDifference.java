import java.util.List;

public class TestDifference {
    private static final AbstractFunction f1 = new CustomFunction(
            new Matrix(List.of(List.of(10., 0.),
                    List.of(0., 1.))),
            new Matrix(List.of(0., 0.), true),
            0,
            new Matrix(List.of(-10., -10.), true),
            new Matrix(List.of(30., 30.), true)
    );

    private static final AbstractFunction f2 = new CustomFunction(
            new Matrix(List.of(
                    List.of(1., 0., 0.),
                    List.of(0., 1000., 0.),
                    List.of(0., 0., 1.))),
            new Matrix(List.of(0., 0., 0.), true),
            0,
            new Matrix(List.of(-10., -10., -10.), true),
            new Matrix(List.of(30., 30., 30.), true));

    private static void addEntry(StringBuilder sb, Trace trace, AbstractFunction f) {
        int steps = trace.getSteps().size();
        Matrix x = trace.getSteps().get(steps - 1);
        sb.append(String.format("%.4f & %d", f.eval(x), steps));
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        final double eps = 1e-5;
        List<AbstractFunction> functions = List.of(f1, f2);
        for (AbstractFunction f : functions) {
            sb.append("$").append(f).append("$ & ");
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
}
