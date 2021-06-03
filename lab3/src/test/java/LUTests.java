import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LUTests {

    private LU genLU(double x00, double x01, double x02,
                     double x10, double x11, double x12,
                     double x20, double x21, double x22) {
        Matrix matrix = new FullMatrix(
                new ArrayList<>(
                        List.of(
                                new Vector(List.of(x00, x01, x02)),
                                new Vector(List.of(x10, x11, x12)),
                                new Vector(List.of(x20, x21, x22)))));
        return new LU(matrix);
    }

    @Test
    void test3_1() {
        LU lu = genLU(
                1, 0, 0,
                0, 2, -1,
                0, -1, 3);
        // L
        assertEquals(1, lu.getL(0, 0));
        assertEquals(0, lu.getL(1, 0));
        assertEquals(2, lu.getL(1, 1));
        assertEquals(0, lu.getL(2, 0));
        assertEquals(-1, lu.getL(2, 1));
        assertEquals(2.5, lu.getL(2, 2));
        // U
        assertEquals(1, lu.getU(0, 0));
        assertEquals(0, lu.getU(0, 1));
        assertEquals(0, lu.getU(0, 2));
        assertEquals(1, lu.getU(1, 1));
        assertEquals(-0.5, lu.getU(1, 2));
        assertEquals(1, lu.getU(2, 2));
    }

    @Test
    void test3_2() {
        LU lu = genLU(
                3, 4, 2,
                2, 5, 15,
                7, 6, 2.98);
        // L
        assertEquals(3, lu.getL(0, 0));
        assertEquals(2, lu.getL(1, 0));
        assertEquals(5 - 2 * 4. / 3, lu.getL(1, 1));
        assertEquals(7, lu.getL(2, 0));
        assertEquals(-3.333333333333332, lu.getL(2, 1));
        assertEquals(17.837142857142847, lu.getL(2, 2));
        // U
        assertEquals(1, lu.getU(0, 0));
        assertEquals(1 + 1. / 3, lu.getU(0, 1));
        assertEquals(2. / 3, lu.getU(0, 2));
        assertEquals(1, lu.getU(1, 1));
        assertEquals(5.857142857142857, lu.getU(1, 2));
        assertEquals(1, lu.getU(2, 2));
    }
}
