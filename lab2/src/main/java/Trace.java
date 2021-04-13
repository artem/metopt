import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Trace {
    private final AbstractFunction function;
    private final ArrayList<Matrix> steps = new ArrayList<>();

    public Trace(AbstractFunction fun) {
        this.function = fun;
    }

    public void add(Matrix x) {
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
}
