import java.util.Locale;

public abstract class AbstractStep {
    protected String doubleToString(double num) {
        return String.format(Locale.ROOT, "%.7f", num);
    }
    abstract String toLatex();
}
