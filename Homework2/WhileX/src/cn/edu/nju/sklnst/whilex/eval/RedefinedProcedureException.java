package cn.edu.nju.sklnst.whilex.eval;

public class RedefinedProcedureException extends EvaluationException {

    public RedefinedProcedureException(String s) {
        super("Redefined procedure: " + s);
    }

}
