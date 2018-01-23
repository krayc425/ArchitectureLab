package cn.edu.nju.sklnst.whilex;

/**
 * The interface that any visitor that wishes to be {@link Node#accept(IVisitor)}ed
 * must implement.  This is a component of an instance of the VISITOR design pattern.
 * The VISITOR design pattern uses ``double dispatch''; only one of the given methods
 * will be called for any particular call to {@link Node#accept(IVisitor)}.
 *
 * @param <T> return tye of visitor
 * @author boyland
 * @see Visitor
 * @see TreeVisitor
 */
public interface IVisitor<T> {

    public abstract T visit(UseVariable n);

    public abstract T visit(LiteralInteger n);

    public abstract T visit(BinopExpression n);

    public abstract T visit(LiteralBoolean x);

    public abstract T visit(NotExpression x);

    public abstract T visit(AndExpression x);

    public abstract T visit(OrExpression x);

    public abstract T visit(RelopExpression x);

    public abstract T visit(AssignStatement s);

    public abstract T visit(SkipStatement s);

    public abstract T visit(SeqStatement s);

    public abstract T visit(IfStatement s);

    public abstract T visit(WhileStatement s);

    // only in extended While
    public abstract T visit(Program p);

    public abstract T visit(Procedure p);

    public abstract T visit(CallStatement s);
}
