package metopt.lab4;

import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.functions.Function1;
import metopt.lab4.functions.Function2;
import metopt.lab4.matrices.Vector;
import metopt.lab4.methods.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {
    private static final double EPS = 1e-6;
    private static final List<AbstractFunction> functions = List.of(
        new Function1(),
        new Function2()
    );

    private static String getStringVector(Vector vector) {
        return "[ " +
                IntStream.range(0, vector.size())
                        .mapToObj(i -> "[" + vector.get(i) + "]").collect(Collectors.joining(", ")) +
                " ]";
    }

    private static void testSimpleDimple(Method method, AbstractFunction function, Vector x0) {
        Result result = method.run(function, new Vector(x0), EPS);
        System.out.println(function.name);
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

    private static void testSimpleDimple(Method method, AbstractFunction function) {
        testSimpleDimple(method, function, function.x0);
    }

    private static void testMethod(Method method, String methodName) {
        System.out.println("\n\t\t" + methodName);
        functions.forEach(f -> testSimpleDimple(method, f));
    }

    public static void main(String[] args) {
        testMethod(new Newton(), "Newton");
        testMethod(new NewtonSingleDimensionalSearch(), "Newton одномерная оптимизация");
        testMethod(new NewtonDirectionChoosing(), "Newton поиск направления");
        testMethod(new BFS(), "БФШ");
        testMethod(new Powell(), "Пауэлл");
    }
}
