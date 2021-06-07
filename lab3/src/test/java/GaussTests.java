import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testE() {
        FullMatrix matrix = LUTests.genMatrix(1, 0, 0, 0, 1, 0, 0, 0, 1);
        Vector xb = new Vector(List.of(1., 2., 3.));
        assertVectorsEquals(xb, Utils.gauss(matrix, xb));
    }

    @Test
    public void test1() {
        FullMatrix matrix = LUTests.genMatrix(3, 7, 8, 1, -5, 5, 4, 2, 3);
        Vector b = new Vector(List.of(1., 2., 3.));
        Vector x = new Vector(List.of(19./22., -5./22, 0.));
        assertVectorsEquals(x, Utils.gauss(matrix, b));
    }

    @Test
    public void test2() {
        FullMatrix matrix = LUTests.genMatrix(-1, 0.3, 15, -1.5, -10, 3,13.7, 150, -0.2);
        Vector b = new Vector(List.of(45.5, 13.7, -33.2));
        Vector x = new Vector(List.of(-4261001./527380., -51411./105476, 2621023./1054760));
        assertVectorsEquals(x, Utils.gauss(matrix, b));
    }

    @Test
    public void testBig() {
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
        Vector result = Utils.gauss(matrix, b);
        for (int i = 0; i < x.size(); i++) {
            double y = x.get(i) / result.get(i);
            y = y < 1 ? 1/y : y;
            assertTrue(y < 1.000001, "expected " + x.get(i) + " got " + result.get(i));
        }
    }
}
