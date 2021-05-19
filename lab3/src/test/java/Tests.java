import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import java.lang.Math;

public class Tests {
    Matrix full;
    Random generator;

    @BeforeEach
    void setUp() {
        full = new FullMatrix(3, 3);
        generator = new Random(3);
    }

    @Test
    void testZeros() {
        Matrix profile = new ProfileMatrix(full);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testDiag() {
        full.set(0, 0, 1);
        full.set(1, 1, 2);
        full.set(2, 2, 3);
        Matrix profile = new ProfileMatrix(full);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testRowsGet() {
        full.set(1, 0, 2);
        full.set(2, 1, 3);
        Matrix profile = new ProfileMatrix(full);
        assertEquals(profile.get(1, 0), full.get(1, 0));
        assertEquals(profile.get(2, 1), full.get(2, 1));
    }

    @Test
    void testRowsFull() {
        full.set(1, 0, 2);
        full.set(2, 1, 3);
        Matrix profile = new ProfileMatrix(full);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testCols() {
        full.set(0, 1, 2);
        full.set(1, 2, 3);
        Matrix profile = new ProfileMatrix(full);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testRowsAndCols() {
        full.set(0, 1, 1);
        full.set(1, 2, 2);
        full.set(1, 0, 3);
        full.set(2, 0, 4);
        Matrix profile = new ProfileMatrix(full);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testSetDiag() {
        Matrix profile = new ProfileMatrix(full);
        full.set(0, 0, 1);
        profile.set(0, 0, 1);
        full.set(2, 2, 2);
        profile.set(2, 2, 2);
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testSetRows() {
        full.set(1, 0, 1);
        full.set(2, 1, 1);
        Matrix profile = new ProfileMatrix(full);
        full.set(1, 0, 2);
        profile.set(1, 0, 2);
        assertEquals(full.get(1, 0), profile.get(1, 0));
        full.set(2, 1, 3);
        profile.set(2, 1, 3);
        assertEquals(full.get(2, 1), profile.get(2, 1));
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testSetCols() {
        full.set(0, 1, 1);
        full.set(0, 2, 1);
        full.set(1, 2, 1);
        Matrix profile = new ProfileMatrix(full);
        full.set(0, 1, 2);
        profile.set(0, 1, 2);
        assertEquals(full.get(0, 1), profile.get(0, 1));
        full.set(0, 2, 3);
        profile.set(0, 2, 3);
        assertEquals(full.get(0, 2), profile.get(0, 2));
        full.set(1, 2, 4);
        profile.set(1, 2, 4);
        assertEquals(full.get(1, 2), profile.get(1, 2));
        assertEquals(full.toString(), profile.toString());
    }

    @Test
    void testBigConstructorAndGetRows() {
        for (int size = 3; size <= 500; size++) {
            full = new FullMatrix(size, size);
            for (int i = 0; i < size * size / 2; i++) {
                int value = (int) (generator.nextDouble() * 100);
                int x = (int) (generator.nextDouble() * size);
                int y = (int) (generator.nextDouble() * size);
                if (y <= x)
                    full.set(x, y, value);
            }
            Matrix profile = new ProfileMatrix(full);
            assertEquals(full.toString(), profile.toString());
        }
    }

    @Test
    void testBigConstructorAndGetAll() {
        for (int size = 3; size <= 500; size++) {
            full = new FullMatrix(size, size);
            for (int i = 0; i < size * size / 2; i++) {
                int value = (int) (generator.nextDouble() * 100);
                int x = (int) (generator.nextDouble() * size);
                int y = (int) (generator.nextDouble() * size);
                full.set(x, y, value);
            }
            Matrix profile = new ProfileMatrix(full);
            assertEquals(full.toString(), profile.toString());
        }
    }

    @Test
    void testFull() {
        for (int size = 3; size <= 500; size++) {
            full = new FullMatrix(size, size);
            for (int i = 0; i < size * size / 5; i++) {
                int value = (int) (generator.nextDouble() * 10);
                int x = (int) (generator.nextDouble() * size);
                int y = (int) (generator.nextDouble() * size);
                full.set(x, y, value);
            }
            Matrix profile = new ProfileMatrix(full);
            for (int i = 0; i < size * size; i++) {
                int value = (int) (generator.nextDouble() * 10);
                int x = (int) (generator.nextDouble() * size);
                int y = (int) (generator.nextDouble() * size);
                if (full.get(x, y) != 0) {
                    full.set(x, y, value);
                    profile.set(x, y, value);
                }
                assertEquals(full.get(x,y), profile.get(x,y));
            }
            assertEquals(full.toString(), profile.toString());
        }
    }
}
