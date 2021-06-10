package metopt.lab4;

import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.functions.Function1;
import metopt.lab4.functions.Function2;
import metopt.lab4.matrices.Vector;
import metopt.lab4.methods.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {

    private static String getStringVector(Vector vector) {
        return "[ " +
                IntStream.range(0, vector.size())
                        .mapToObj(i -> "[" + vector.get(i) + "]").collect(Collectors.joining(", ")) +
                " ]";
    }

    private static void testSimpleDimple(Method method, AbstractFunction function, Vector x0) {
        Result result = method.run(function, x0, 1e-7);
        System.out.println("x0: " + result.x);
        System.out.println("iterations: " + result.iterations);
        System.out.println("value:" + function.eval(result.x));
        System.out.println(result.iterations);
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

    public static void main(String[] args) {
        System.out.println("\t\tNewton classic");

        System.out.println("Function1");
        testSimpleDimple(new Newton(), new Function1());
        System.out.println("Function2");
        testSimpleDimple(new Newton(), new Function2());

        System.out.println("\t\tNewton одномерная оптимизация");
        System.out.println("Function1");
        testSimpleDimple(new NewtonSingleDimensionalSearch(), new Function1());
        System.out.println("Function2");
        testSimpleDimple(new NewtonSingleDimensionalSearch(), new Function2());

        System.out.println("\t\tNewton поиск направления");
        System.out.println("Function1");
        testSimpleDimple(new NewtonDirectionChoosing(), new Function1());
        System.out.println("Function2");
        testSimpleDimple(new NewtonDirectionChoosing(), new Function2());

        System.out.println("\t\tBFS");
        System.out.println("Function1");
        testSimpleDimple(new BFS(), new Function1());
        System.out.println("Function2");
        testSimpleDimple(new BFS(), new Function2());

        System.out.println("\t\tPowell");
        System.out.println("Function1");
        testSimpleDimple(new Powell(), new Function1());
        System.out.println("Function2");
        testSimpleDimple(new Powell(), new Function2());
    }
}
