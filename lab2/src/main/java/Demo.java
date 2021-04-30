import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Demo {
    public static List<AbstractFunction> functions = List.of(
            new Function1(),
            new Function2(),
            new Function3(),
            new Function4()
    );

    public static void printHelp(){
        System.out.println("-f - function: (0-3) ");
        System.out.println("-m - method: (0-2): ");
        System.out.println("    0 - GradientDescent");
        System.out.println("    1 - SteepestDescent");
        System.out.println("    2 - ConjugateGradient");
        System.out.println("-e - eps");
        System.out.println("-a - alpha");
        System.out.println("-ng - normalized gradient in GradientDescent");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }
        int fnInd = 0;
        int methodInd = 0;
        double eps = 0.001;
        double alpha = 1;
        boolean ng = false;
        CustomFunction customFunction = null;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-c":
                    String path = args[++i];
                    try {
                        final Gson gson = new Gson();
                        Type typeToken = new TypeToken<CustomFunction>(){}.getType();
                        JsonReader reader = new JsonReader(new FileReader(path));
                        customFunction = gson.fromJson(reader, typeToken);
                    } catch (IOException e) {
                        System.out.println("cant read from file");
                        return;
                    }
                case "-f":
                    fnInd = Integer.parseInt(args[++i]);
                    break;
                case "-m":
                    methodInd = Integer.parseInt(args[++i]);
                    break;
                case "-e":
                    eps = Double.parseDouble(args[++i]);
                    break;
                case "-a":
                    alpha = Double.parseDouble(args[++i]);
                    break;
                case "-ng":
                    ng = Boolean.parseBoolean(args[++i]);
                    break;
                default:
                    printHelp();
                    return;
            }
        }
        Method method = null;
        AbstractFunction fn;
        if (customFunction == null) {
            fn = functions.get(fnInd);
        } else {
            fn = customFunction;
        }
        if (methodInd == 0) {
            method = new GradientDescent(fn, eps, alpha);
        } else if (methodInd == 1) {
            method = new SteepestDescent(fn, eps);
        } else if (methodInd == 2) {
            method = new ConjugateGradient(fn, eps);
        }
        if (method != null) {
            System.out.println(method.process());
        }
    }
}