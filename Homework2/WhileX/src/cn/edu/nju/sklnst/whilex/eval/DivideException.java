package cn.edu.nju.sklnst.whilex.eval;

public class DivideException extends EvaluationException {

    public DivideException() {
        super("divide by zero");
    }

    public DivideException(String s) {
        super(s);
    }

}
