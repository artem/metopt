import com.google.gson.Gson;

public class Trace {
    private final AbstractFunction function;

    public Trace(AbstractFunction fun) {
        this.function = fun;
    }

    @Override
    public String toString() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }
}
