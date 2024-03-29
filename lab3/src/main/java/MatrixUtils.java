import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

// TODO
// 1. Write different matrix generators -- DONE
// 2. Write test group generator -- DONE
// 3. Write group tester
@SuppressWarnings("unused")
public class MatrixUtils {
    private static final Path TESTSPATH =
            Path.of(getClassPath()).resolve("../../../../tests").toAbsolutePath();

    static {
        if (!Files.exists(TESTSPATH)) {
            try {
                Files.createDirectories(TESTSPATH);
            } catch (IOException e) {
                throw new RuntimeException("Unable to create tests directory", e);
            }
        }
    }

    private static String getClassPath() {
        try {
            return Path.of(MatrixUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    private static String getPathname(final int testGroup, final int dimension, final int testNumber) {
        return "group" + testGroup
                + File.separatorChar + dimension
                + File.separatorChar + String.format("%02d", testNumber);
    }

    public static String getInputPathname(final int testGroup, final int dimension, final int testNumber) {
        return getPathname(testGroup, dimension, testNumber) + ".in";
    }

    public static String getOutputPathname(final int testGroup, final int dimension, final int testNumber) {
        return getPathname(testGroup, dimension, testNumber) + ".out";
    }

    private static void writeJson(final Matrix matrix, final String relativePath) {
        try {
            final Path parent = TESTSPATH.resolve(relativePath).getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new MatrixException("Unable to create parent directories for " + relativePath, e);
        }
        try (final Writer writer = new FileWriter(TESTSPATH.resolve(relativePath).toString())) {
            new Gson().toJson(matrix, writer);
        } catch (IOException e) {
            throw new MatrixException("Unable to write matrix in file " + relativePath, e);
        }
    }

    private static <T> T readJson(final Class<T> matrix, final String relativePath) {
        try (final Reader reader = new FileReader(TESTSPATH.resolve(relativePath).toString())) {
            return new Gson().fromJson(reader, matrix);
        } catch (IOException e) {
            throw new MatrixException("Unable to read matrix from file " + relativePath, e);
        }
    }

    /**
     * Generates random profile matrix.
     *
     * @param n dimension of result matrix.
     * @param k serial number of matrix.
     * @return random profile matrix.
     */
    public static Matrix generateProfile(final int n, final int k) {
        final Random random = new Random(12);
        final Matrix matrix = new FullMatrix(n, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                matrix.set(i, j, -random.nextInt(5));
                matrix.set(j, i, -random.nextInt(5));
            }
        }
        for (int i = 0; i < n; ++i) {
            long aii = 0;
            for (int j = 0; j < n; ++j) {
                if (i == j) continue;
                aii += matrix.get(i, j);
            }
            matrix.set(i, i, -aii);
        }
        final double a00 = matrix.get(0, 0);
        matrix.set(0, 0, a00 + Math.pow(10, -k));
        return new ProfileMatrix(matrix);
    }

    /**
     * Generates random full matrix.
     *
     * @param n dimension of result matrix.
     * @return random full matrix.
     */
    public static FullMatrix generateHilbert(final int n) {
        final FullMatrix matrix = new FullMatrix(n, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix.set(i, j, 1. / (i + j + 1)); // the same as 1 / (i + j - 1), cause of 0-indexation
            }
        }
        return matrix;
    }

    /**
     * Generates group of tests.
     *
     * @param testGroup number of test's group.
     * @param amount amount of tests for each dimension.
     */
    public static void generateTestGroup(final int testGroup, final int amount) {
        switch (testGroup) {
            case 1:
                for (int dimension = 10; dimension <= 1000; dimension *= 10) {
                    for (int test = 0; test < amount; ++test) {
                        writeJson(generateProfile(dimension, test), getInputPathname(testGroup, dimension, test));
                    }
                }
                break;
            case 2:
                for (int dimension = 2; dimension <= 30; dimension += 2) {
                    for (int test = 0; test < amount; ++test) {
                        writeJson(generateHilbert(dimension), getInputPathname(testGroup, dimension, test));
                    }
                }
                break;
            case 3:
                throw new IllegalArgumentException("No generation for bonus group");
            default:
                throw new IllegalArgumentException("Unrecognized tests group");
        }
    }

    public static void testGroup(final int testGroup, final int amount) {
        switch (testGroup) {
            case 1:
                System.out.println(
                        "  \\begin{center}\n" +
                                "    \\begin{tabular}{|c|c|c|c|}\n" +
                                "        \\hline\n" +
                                "        $n$ & $k$ & $\\norm{x^*-x_k}$ & $\\norm{x^*-x_k} / \\norm{x^*}$ \\\\\n" +
                                "        \\hline");

                for (int dimension = 10; dimension <= 1000; dimension *= 10) {
                    final Vector x = new Vector(dimension); // exact answer
                    for (int i = 0; i < dimension; ++i) {
                        x.set(i, i + 1);
                    }
                    final double len = x.norm();
                    for (int test = 0; test < amount; ++test) {
                        final Matrix A = readJson(ProfileMatrix.class, getInputPathname(testGroup, dimension, test));
                        final Vector b = A.mul(x);
                        final Vector y = Utils.GaussLU(A, b); // calculated answer
                        if (y == null) {
                            System.out.printf(
                                    "        %d & %d & -- & -- \\\\\n" +
                                            "        \\hline\n",
                                    dimension,
                                    test);
                            continue;
                        }
                        final double delta = x.sub(y).norm();
                        System.out.printf(
                                "        %d & %d & %.9f & %.9f \\\\\n" +
                                        "        \\hline\n",
                                dimension,
                                test,
                                delta,
                                delta / len);
                    }
                }

                System.out.println(
                        "    \\end{tabular}\n" +
                                "  \\end{center}\n");
                break;
            case 2:
                System.out.println(
                        "  \\begin{center}\n" +
                        "    \\begin{tabular}{|c|c|c|c|}\n" +
                        "        \\hline\n" +
                        "        $n$ & $k$ & $\\norm{x^*-x_k}$ & $\\norm{x^*-x_k} / \\norm{x^*}$ \\\\\n" +
                        "        \\hline");

                for (int dimension = 2; dimension <= 30; dimension += 2) {
                    final Vector x = new Vector(dimension); // exact answer
                    for (int i = 0; i < dimension; ++i) {
                        x.set(i, i + 1);
                    }
                    final double len = x.norm();
                    final FullMatrix A = readJson(FullMatrix.class, getInputPathname(testGroup, dimension, 0));
                    final Vector b = A.mul(x);
                    final Vector y = Utils.Gauss(A, b); // calculated answer
                    if (y == null) {
                        System.out.printf(
                                "        %d & %d & -- & -- \\\\\n" +
                                        "        \\hline\n",
                                dimension,
                                0);
                        continue;
                    }
                    final double delta = x.sub(y).norm();
                    System.out.printf(
                            "        %d & %d & %.9f & %.9f \\\\\n" +
                            "        \\hline\n",
                            dimension,
                            0,
                            delta,
                            delta / len);
                }

                System.out.println(
                        "    \\end{tabular}\n" +
                        "  \\end{center}\n");
                break;
            case 3:
                throw new IllegalArgumentException("No test for bonus group");
            default:
                throw new IllegalArgumentException("Unrecognized tests group");
        }
    }

    public static void main(String[] args) {
        final int testGroup = 1;
        final int amount = 5;
        generateTestGroup(testGroup, amount);
        System.out.println("Tests generated");
        testGroup(testGroup, amount);
//        final Matrix A = new FullMatrix(
//                List.of(
//                        new Vector(List.of(2.0, 3.0)),
//                        new Vector(List.of(5.0, 3.0))
//                ));
//        final Vector B = new Vector(List.of(1.0, 7.0));
//        System.out.println(Utils.Gauss(A, B));
    }
}
