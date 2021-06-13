package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.Vector;

public interface Method {
    Result run(FunI function, Vector x0, double eps);
    String getFullName();
    String getShortName();
}
