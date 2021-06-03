import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// TODO
// 1. Write different matrix generators
// 2. Write test group generator
// 3. ....
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
        System.out.println("Tests dir created");
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

    public static void main(String[] args) {
        final Matrix fullMatrix = new FullMatrix(List.of(
                List.of(1.0, 0.0, 0.0),
                List.of(0.0, 2.0, 3.0),
                List.of(0.0, 4.0, 5.0)
        ));
        final Matrix profileMatrix = new ProfileMatrix(fullMatrix);
        writeJson(profileMatrix, getInputPathname(1, 3, 0));
//        final Matrix matrix = readJson(ProfileMatrix.class, getInputPathname(1, 3, 0));
//        System.out.println(matrix);
    }
}
