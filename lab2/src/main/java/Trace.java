import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Trace {
    private final AbstractFunction function;
    private final ArrayList<Matrix> steps = new ArrayList<>();

    public Trace(final AbstractFunction fun) {
        this.function = fun;
    }

    public void add(final Matrix x) {
        steps.add(x);
    }

    @Override
    public String toString() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }

    public AbstractFunction getFunction() {
        return function;
    }

    public List<Matrix> getSteps() {
        return steps;
    }

    public String stepsToWolfram() {
        if (steps.isEmpty() || steps.get(0).n != 2) {
            return "";
        }

        final String points = steps.stream().map(
                p -> String.format(Locale.US, "{%.2f, %.2f}", p.get(0, 0), p.get(1, 0))
        ).collect(Collectors.joining(", "));

        return "{" + points + "}";
    }
}
