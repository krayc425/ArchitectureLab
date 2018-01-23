package cn.edu.nju.sklnst.whilex;

/**
 * A default implementation superclass for visitors to (extended) while programs.
 * By default, it throws an exception if any node isn't handled through its case or
 * using an overarching case.
 *
 * @param <T> type of value to return.
 * @author boyland
 */
public class Visitor<T> implements IVisitor<T> {

    @SuppressWarnings("serial")
    public static class NotHandledException extends RuntimeException {
        public NotHandledException(Node n) {
            super(n.getClass().getName());
        }
    }

    public T visit(Node n) {
        throw new NotHandledException(n);
    }

    // expressions:
    public T visit(Expression n) {
        return visit((Node) n);
    }

    public T visit(ArithmeticExpression n) {
        return visit((Expression) n);
    }

    public T visit(UseVariable n) {
        return visit((ArithmeticExpression) n);
    }

    public T visit(LiteralInteger n) {
        return visit((ArithmeticExpression) n);
    }

    public T visit(BinopExpression n) {
        return visit((ArithmeticExpression) n);
    }

    public T visit(BoolExpression n) {
        return visit((Expression) n);
    }

    public T visit(LiteralBoolean x) {
        return visit((BoolExpression) x);
    }

    public T visit(NotExpression x) {
        return visit((BoolExpression) x);
    }

    public T visit(AndExpression x) {
        return visit((BoolExpression) x);
    }

    public T visit(OrExpression x) {
        return visit((BoolExpression) x);
    }

    public T visit(RelopExpression x) {
        return visit((BoolExpression) x);
    }

    public T visit(Statement s) {
        return visit((Node) s);
    }

    public T visit(AssignStatement s) {
        return visit((Statement) s);
    }

    public T visit(SkipStatement s) {
        return visit((Statement) s);
    }

    public T visit(SeqStatement s) {
        return visit((Statement) s);
    }

    public T visit(IfStatement s) {
        return visit((Statement) s);
    }

    public T visit(WhileStatement s) {
        return visit((Statement) s);
    }

    public T visit(Program p) {
        return visit((Node) p);
    }

    public T visit(Procedure p) {
        return visit((Node) p);
    }

    public T visit(CallStatement s) {
        return visit((Statement) s);
    }
}
