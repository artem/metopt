import java.util.ArrayList;
import java.util.List;

public class Result {
    private final String name;
    final List<ResultPart> steps = new ArrayList<>();
    double x;
    double f;

    public Result(String name) {
        this.name = name;
    }

    public void addStep(double a, double b, double x1, double x2, double f1, double f2) {
        Step step = new Step(a, b, x1, x2, f1, f2);
        steps.add(step);
        System.out.println(step.toLatex());
    }

    public void addStep(ResultPart step) {
        steps.add(step);
    }

    public void setResult(double x, double f) {
        this.x = x;
        this.f = f;
    }

    @Override
    public String toString() {
        return String.format("%s {x=%s, f=%s}", name, x, f);
    }
}
