import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

// TODO
// 1. Write different matrix generators -- DONE
// 2. Write test group generator -- DONE
// 3. Write group tester
@SuppressWarnings("unused")
public class MatrixUtils {
    private static final Path TESTSPATH =
            Path.of(getClassPath()).getParent().getParent().getParent().getParent().resolve("tests").toAbsolutePath();

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
    private static Matrix generateProfile(final int n, final int k) {
        final Random random = new Random(System.currentTimeMillis());
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
    private static Matrix generateHilbert(final int n) {
        final Matrix matrix = new FullMatrix(n, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix.set(i, j, 1 / ((double) i + j + 1)); // the same as 1 / (i + j - 1), cause of 0-indexation
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
                for (int dimension = 10; dimension <= 1000; dimension *= 10) {
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

    public static void main(String[] args) {
    }
}
