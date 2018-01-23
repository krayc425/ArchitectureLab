package cn.edu.nju.sklnst.whilex;

public class LiteralInteger extends ArithmeticExpression {

    public final int value;

    public LiteralInteger(final int value) {
        super();
        this.value = value;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return Integer.toString(value);
    }

    public void unparse(IndentPrinter ip, Precedence prec) {
        ip.print(toString());
    }
}
