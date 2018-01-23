package cn.edu.nju.sklnst.whilex;

public class LiteralBoolean extends BoolExpression {

    public final boolean value;

    public LiteralBoolean(final boolean value) {
        super();
        this.value = value;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return Boolean.toString(value);
    }

    public void unparse(IndentPrinter ip, Precedence prec) {
        ip.print(toString());
    }
}
