import java.util.ArrayList;
import java.util.List;

public class Result {
    List<Step> steps = new ArrayList<>();
    double x;
    double f;

    public void addStep(double x1, double x2, double f1, double f2) {
        steps.add(new Step(x1, x2, f1, f2));
    }

    public void setResult(double x, double f) {
        this.x = x;
        this.f = f;
    }

    @Override
    public String toString() {
        return String.format("Result {x=%s, f=%s}", x, f);
    }

    public static class Step {
        final double x1, x2, f1, f2;

        public Step(double x1, double x2, double f1, double f2) {
            this.x1 = x1;
            this.x2 = x2;
            this.f1 = f1;
            this.f2 = f2;
        }
    }
}
