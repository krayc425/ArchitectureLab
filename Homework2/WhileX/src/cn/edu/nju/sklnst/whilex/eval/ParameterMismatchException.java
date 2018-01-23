package cn.edu.nju.sklnst.whilex.eval;


public class ParameterMismatchException extends EvaluationException {

    public ParameterMismatchException(String type, int expected, int actual) {
        super(type + " parameter mismatch: expected " + expected + ", got " + actual);
    }

}
