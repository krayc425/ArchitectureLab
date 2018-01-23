package cn.edu.nju.sklnst.whilex.eval;

public class UnknownProcedureException extends EvaluationException {

    public UnknownProcedureException(String name) {
        super("unknown procedure: " + name);
    }

}
