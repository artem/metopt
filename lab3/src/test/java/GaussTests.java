import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GaussTests {

    private static void assertVectorsEquals(Vector expected, Vector actual) {
        List<Double> expectedList = IntStream.range(0, expected.size())
                .mapToObj(expected::get).collect(Collectors.toList());
        List<Double> actualList =IntStream.range(0, expected.size())
                .mapToObj(actual::get).collect(Collectors.toList());
        assertEquals(expectedList, actualList);
    }

    private static void assertVectorsSoftEquals(Vector expected, Vector actual, double diff) {
        for (int i = 0; i < expected.size(); i++) {
            if (actual.get(i) == 0.) {
                assertTrue(expected.get(i) < diff);
            }
            double y = expected.get(i) / actual.get(i);
            y = Math.abs(y < 1 ? 1/y : y);
            assertTrue(Math.abs(y) < diff, i + "; " + y + " expected " + expected.get(i) + " got " + actual.get(i));
        }
    }

    @Test
    public void testE() {
        FullMatrix matrix = LUTests.genMatrix(1, 0, 0, 0, 1, 0, 0, 0, 1);
        Vector xb = new Vector(List.of(1., 2., 3.));
        assertVectorsSoftEquals(xb, Utils.Gauss(matrix, xb), 1.0000000001);
    }

    @Test
    public void test3_1() {
        FullMatrix matrix = LUTests.genMatrix(3, 7, 8, 1, -5, 5, 4, 2, 3);
        Vector b = new Vector(List.of(1., 2., 3.));
        Vector x = new Vector(List.of(19./22., -5./22, 0.));
        assertVectorsSoftEquals(x, Utils.Gauss(matrix, b), 1.000000001);
    }

    @Test
    public void test3_2() {
        FullMatrix matrix = LUTests.genMatrix(-1, 0.3, 15, -1.5, -10, 3,13.7, 150, -0.2);
        Vector b = new Vector(List.of(45.5, 13.7, -33.2));
        Vector x = new Vector(List.of(-2715971./429880, 30881./85976, 203603./78160));
        assertVectorsSoftEquals(x, Utils.Gauss(matrix, b), 1.000000001);
    }

    @Test
    public void test6() {
        FullMatrix matrix =  new FullMatrix(
                new ArrayList<>(
                        List.of(
                                new Vector(List.of(1., 2., 3., 4., 5., 6.)),
                                new Vector(List.of(123., 32., 23., 14.7, 3., 1.)),
                                new Vector(List.of(3., 34., 54., -33.2, 6., .5)),
                                new Vector(List.of(12., 645., 64., 34., 53., 2.)),
                                new Vector(List.of(54., 45., 667., 223., 65., 4.)),
                                new Vector(List.of(11., 23., 23., 12., 99., 5.))
                                )));
        Vector b = new Vector(List.of(1., 2., 3., 4., 5., 6.));
        Vector x = new Vector(List.of(
                156496267745./9759238282031.,
                2834723389./1394176897433.,
                158717478545./9759238282031.,
                -474306261090./9759238282031.,
                519493634393./9759238282031.,
                1397776720600./9759238282031.
                ));
        Vector result = Utils.Gauss(matrix, b);
        assertVectorsSoftEquals(x, result, 1.000000000001);
    }

    private void testHilbert(int n, double diff) {
        FullMatrix A = MatrixUtils.generateHilbert(n);
        Vector v = new Vector(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            v.set(i, random.nextInt(n) + 1);
        }
        Vector b = A.mul(v);
        Vector x = Utils.Gauss(A, b);
        assertVectorsSoftEquals(v, x, diff);
    }

    @Test
    public void testHilbert3() {
        testHilbert(3, 1.0000000001);
    }

    @Test
    public void testHilbert6() {
        testHilbert(6, 1.000000001);
    }

    @Test
    public void testHilbert10() {
        testHilbert(10, 1.000000001);
    }
}
