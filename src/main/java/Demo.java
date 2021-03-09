import java.text.MessageFormat;

public class Demo {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Should call with number of method: " +
                            "\n    0 - Dichotomy method" +
                            "\n    1 - Golden ratio method" +
                            "\n    2 - Fibonacci method" +
                            "\n    3 - Parabola method" +
                            "\n    4 - Brent's method" +
                    "\nAnd eps" +
                    "\n -t return time");
            System.out.println("Got:");
            for (String s : args) {
                System.out.println(s);
            }
            return;
        }
        int num = Integer.parseInt(args[0]);
        double eps = Double.parseDouble(args[1]);
        Result res = null;
        long startTime = System.nanoTime();
        boolean isTime = args.length == 3 && args[2].equals("-t");
        for (int i = 0; i < (isTime ? 500 : 1); i++) {
            switch (num) {
                case 0:
                    res = Methods.dichotomy(new FunVar2(), eps);
                    break;
                case 1:
                    res = Methods.goldenRatio(new FunVar2(), eps);
                    break;
                case 2:
                    res = Methods.parabola(new FunVar2(), eps);
                    break;
                case 3:
                    res = Methods.fib(new FunVar2(), eps);
                    break;
                case 4:
                    res = Methods.brent(new FunVar2(), eps);
                    break;
                default:
                    System.out.println("Wrong arguments");
                    return;
            }
        }
        if (isTime) {
            System.out.println(System.nanoTime() - startTime);
        } else {
            System.out.println(res.x);
            System.out.println(res.f);
            for (ResultPart i : res.steps) {
                System.out.println(i);
            }
        }
    }
}
