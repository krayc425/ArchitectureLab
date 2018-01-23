package cn.edu.nju.sklnst.whilex;

public class UseVariable extends ArithmeticExpression {

    public final Variable var;

    public UseVariable(final Variable var) {
        super();
        this.var = var;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return var.toString();
    }

    public void unparse(IndentPrinter p, Precedence unused) {
        p.print(var);
    }
}
