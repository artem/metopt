package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.QuadraticFunction;
import metopt.lab4.matrices.Vector;

public interface Method {
    public Result run(QuadraticFunction function, Vector x0, double eps);

    public String name();
}
