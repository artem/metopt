package metopt.lab4;

import metopt.lab4.functions.*;
import metopt.lab4.matrices.Vector;
import metopt.lab4.methods.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"SameParameterValue", "unused"})
public class Demo {
    private static final double EPS = 1e-5;
    private static final List<QuadraticFunction> functions = List.of(
            new f1(),
            new Sample1()
    );


    private static final List<Method> newtonMethods = List.of(
            new Newton(),
            new NewtonSingleDimensionalSearch(),
            new NewtonDirectionChoosing()
    );

    private static final List<Method> quasiNewtonMethods = List.of(
            new BFS(),
            new Powell()
    );

    private static class InputData {
        public FunI f;
        public List<Vector> points;

        public InputData(final FunI f, final Vector... points) {
            this.f = f;
            this.points = Arrays.asList(points);
        }
    }

    private static final List<String> tableHeaders = List.of(
            "        Итерация & $\\alpha$ & $x$ & $y$ & $p_x$ & $p_y$ & $f(x, y)$ \\\\\n",
            "        Итерация & $\\alpha$ & $x_1$ & $x_2$ & $p_1$ & $p_2$ & $f(x)$ \\\\\n",
            "        Итерация & $\\alpha$ & $x_1$ & $x_2$ & $p_1$ & $p_2$ & $f(x)$ \\\\\n",
            "        Итерация & $x_1$ & $x_2$ & $x_3$ & $x_4$ & $f(x)$ \\\\\n"
    );

    private static final List<String> functionTables = List.of(
            "custom_functions",
            "given_functions_newton",
            "given_functions_quasinewton"
    );

    private static final String HLINE = "        \\hline\n";

    private static String tableBeginning(final int variant) {
        return "\\begin{table}[H]\n" +
                "    \\centering\n" +
                "    \\begin{tabular}{| *{7}{c|}}\n" +
                "        \\hline\n" +
                tableHeaders.get(variant) +
                "        \\hline\n";
    }

    private static String tableEnding(final String methodFullName,
                                      final String methodShortName,
                                      final String function,
                                      final int startPointIndex,
                                      final int variant,
                                      final boolean drawable) {
        final String functionsTable = functionTables.get(variant);
        return  "    \\end{tabular}\n" +
                "    \\caption{" + methodFullName + " на функции \\hyperref[table:" + functionsTable + "]{$" + function + "$}}\n" +
                "    \\label{" + methodShortName + "_" + function + "_" + startPointIndex + "}\n" +
                "\\end{table}\n" +
                "\n" +
                (drawable
                        ?
                        "\\begin{figure}[H]\n" +
                        "    \\centering\n" +
                        "    \\includegraphics[scale=0.7]{img/" + methodShortName + "_" + function + "_" + startPointIndex + ".png}\n" +
                        "    \\caption{" + methodFullName + " на функции \\hyperref[table:" + functionsTable + "]{$" + function + "$}}\n" +
                        "\\end{figure}\n" +
                        "\n"
                        : "") +
                "\\newpage\n" +
                "\n";
    }

    private static List<Integer> compressIndices(final int n) {
        final int d = n / 20;
        return (n <= 20 ? IntStream.range(0, n) : IntStream.iterate(0, i -> i + d < n, i -> i + d))
                .boxed().collect(Collectors.toList());
    }

    private static String startPoint(final Vector startPoint, final double startValue) {
        return String.format("        0 & - & %s & %s & %.7f \\\\%n",
                startPoint.texView(),
                Stream.generate(() -> "-").limit(startPoint.size()).collect(Collectors.joining(" & ")),
                startValue)
                + HLINE;
    }

    private static String startPointShort(final Vector startPoint, final double startValue) {
        return String.format("        0 & %s & %.7f \\\\%n", startPoint.texView(), startValue)
                + HLINE;
    }

    private static List<List<Integer>> iterations = Stream.generate((Supplier<ArrayList<Integer>>) ArrayList::new)
                                                            .limit(4)
                                                            .collect(Collectors.toList());

    private static void doTest(final double eps,
                               final int variant,
                               final List<Method> methods,
                               final List<InputData> inputDataList) {
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of("/home/td2r/Desktop/tmp/input.txt"))) {
            for (final Method method : methods) {
                System.out.printf("\\subsection{%s}%n%n", method.getFullName());
                int findex = -1;
                for (final InputData inputData : inputDataList) {
                    ++findex;
                    int index = 1;
                    for (final Vector x0 : inputData.points) {
                        final Result result = method.run(inputData.f, x0, eps);
                        final List<Step> steps = result.getSteps();
                        final List<Integer> indices = compressIndices(steps.size());

                        iterations.get(findex).add(steps.size());

                        final boolean drawable = x0.size() <= 2;

                        System.out.print(tableBeginning(drawable ? variant : variant + 1));
                        System.out.print(drawable ? startPoint(x0, inputData.f.eval(x0))
                                                  : startPointShort(x0, inputData.f.eval(x0)));
                        indices.forEach(i -> {
                            final Step step = steps.get(i);
                            System.out.printf("        %s \\\\%n", step.texView(drawable));
                            System.out.print(HLINE);
                        });
                        System.out.print(tableEnding(
                                method.getFullName(),
                                method.getShortName(),
                                inputData.f.getName(),
                                index,
                                variant,
                                drawable));

                        if (drawable) {
                            writer.write("[" + x0.pythonView() + "," +
                                    indices.stream()
                                            .map(steps::get)
                                            .map(Step::getX)
                                            .map(Vector::pythonView)
                                            .collect(Collectors.joining(",")) +
                                    "]");
                            writer.newLine();
                        }

                        index++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Writing error: " + e.getMessage());
        }
    }

    private static void testNewtons(final double eps) {
        final List<InputData> inputDataList = List.of(
                new InputData(new g1(), new Vector(3.4, -17.8), new Vector(12.5, -3), new Vector(14.9, 16.3)),
                new InputData(new g2(), new Vector(-96.5, -89), new Vector(1, 94.7), new Vector(16.5, -56.4)),
                new InputData(new g3(), new Vector(2, -1.4), new Vector(1.5, 2.6), new Vector(1, 1.8))
        );
        doTest(eps, 0, newtonMethods, inputDataList);
    }

    private static void testNewtonsWithGiven(final double eps) {
        final List<InputData> inputDataList = List.of(
                new InputData(new f1(), new Vector(4, 1)),
                new InputData(new f2(), new Vector(-1.2, 1))
        );
        doTest(eps, 1, newtonMethods, inputDataList);
    }

    private static final Method bestNewtonMethod = new NewtonSingleDimensionalSearch();

    private static void testQuasiNewtonsWithGiven(final double eps) {
        final List<InputData> inputDataList = List.of(
                new InputData(new f2(),
                        new Vector(-14.6, 2.6),
                        new Vector(17.6, -19),
                        new Vector(4.9, 18.8)),
                new InputData(new f3(),
                        new Vector(9.7, 9.79),
                        new Vector(1.94, -7.09),
                        new Vector(-9.53, 2.99)),
                new InputData(new f4(),
                        new Vector(100, 0, 0, 0),
                        new Vector(0, 10, 0, 10),
                        new Vector(15, 15, 15, 15)),
                new InputData(new f5(),
                        new Vector(5.59, 5.39),
                        new Vector(-5.96, 0.33),
                        new Vector(1.28, -6.87))
        );
        final List<Method> methods = Stream.of(quasiNewtonMethods, List.of(bestNewtonMethod))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        doTest(eps, 2, methods, inputDataList);
    }

    public static void main(String[] args) {
        testQuasiNewtonsWithGiven(EPS);
        System.out.println(
                "\\begin{table}[H]\n" +
                "    \\centering\n" +
                "    \\begin{tabular}{|c|c|c|c|c|c|c|c|c|c|}\n" +
                "        \\hline\n" +
                "        Функция\n" +
                "        & \\multicolumn{3}{c}{БФШ}\n" +
                "        & \\multicolumn{3}{c}{Пауэлл}\n" +
                "        & \\multicolumn{3}{c}{Ньютон} (ОС) \\\\\n" +
                "        \\hline");
        for (int i = 0; i < iterations.size(); ++i) {
            System.out.println("        $f_" +
                    (i + 1) +
                    "$ & " +
                    iterations.get(i).stream().map(String::valueOf).collect(Collectors.joining(" & ")) +
                    " \\\\\n" +
                    "        \\hline\n");
        }
        System.out.println("    \\end{tabular}\n" +
                "\\end{table}\n");
    }
}
