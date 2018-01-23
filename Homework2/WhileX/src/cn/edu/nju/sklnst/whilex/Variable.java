package cn.edu.nju.sklnst.whilex;

import java.util.HashMap;
import java.util.Map;

public class Variable {
    private static final Map<String, Variable> variables = new HashMap<String, Variable>();

    public final String name;

    private Variable(String n) {
        name = n;
        variables.put(name, this);
    }

    public static synchronized Variable get(String name) {
        Variable var = variables.get(name);
        if (var == null) {
            var = new Variable(name);
            variables.put(name, var);
        }
        return var;
    }

    public String toString() {
        return name;
    }
}
