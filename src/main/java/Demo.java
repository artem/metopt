import java.text.MessageFormat;

public class Demo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(
                    "Should call with number of method: " +
                            "\n 0 - Dichotomy method" +
                            "\n 1 - Golden ratio method" +
                            "\n 2 - Fibonacci method" +
                            "\n 3 - Parabola method" +
                            "\n 4 - Brent's method");
            return;
        }
        int num = Integer.parseInt(args[0]);
        Result res;
        switch (num) {
            case 0:
                res = Methods.dichotomy(new FunVar2(), 1e-10);
                break;
            case 1:
                res = Methods.goldenRatio(new FunVar2(), 1e-10);
                break;
            default:
                System.out.println("Wrong argument");
                return;
        }
        System.out.println(res.x);
        System.out.println(res.f);
        for (Step i : res.steps) {
            System.out.println(i.x1 + " " + i.f1 + " " + i.x2 + " " + i.f2);
        }
    }
}
