package cn.edu.nju.sklnst.whilex.eval;

import cn.edu.nju.sklnst.whilex.Variable;

public class VariableUnassignedException extends EvaluationException {

    public VariableUnassignedException(Variable v) {
        super(v.name);
        // TODO Auto-generated constructor stub
    }

}
