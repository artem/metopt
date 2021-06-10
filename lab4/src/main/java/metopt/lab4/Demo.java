package metopt.lab4;

import metopt.lab4.functions.QuadraticFunction;
import metopt.lab4.functions.Function1;
import metopt.lab4.functions.Sample1;
import metopt.lab4.matrices.Vector;
import metopt.lab4.methods.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {
    private static final double EPS = 1e-6;
    private static final List<QuadraticFunction> functions = List.of(
            new Function1(),
            new Sample1()
    );

    private static final List<Method> methods = List.of(
            new Newton(),
            new NewtonSingleDimensionalSearch(),
            new NewtonDirectionChoosing(),
            new BFS(),
            new Powell()
    );

    private static String getStringVector(Vector vector) {
        return "[ " +
                IntStream.range(0, vector.size())
                        .mapToObj(i -> "[" + vector.get(i) + "]").collect(Collectors.joining(", ")) +
                " ]";
    }

    private static void testSimpleDimple(Method method, QuadraticFunction function, Vector x0) {
        Result result = method.run(function, new Vector(x0), EPS);
        System.out.println(function.getName());
        System.out.println("x0: " + result.x);
        System.out.println("iterations: " + result.iterations);
        System.out.println("value:" + function.eval(result.x));
        System.out.println("Steps:");
        System.out.println("[ " +
                result.list.stream().map(Demo::getStringVector).collect(Collectors.joining(",\n")) +
                " ]");
        if (!result.additional.isEmpty()) {
            System.out.println("Alpha: " + Arrays.toString(result.additional.toArray()));
        }
    }

    private static void testSimpleDimple(Method method, QuadraticFunction function) {
        testSimpleDimple(method, function, function.getX0());
    }

    private static void testMethod(Method method) {
        System.out.println("\n\t\t" + method.name());
        functions.forEach(f -> testSimpleDimple(method, f));
    }

    private static void testIterations() {
        System.out.println();
    }

    public static void main(String[] args) {
        testMethod(new Newton());
        testMethod(new NewtonSingleDimensionalSearch());
        testMethod(new NewtonDirectionChoosing());
        testMethod(new BFS());
        testMethod(new Powell());
        testMethod(new Powell());
    }
}
