package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

public class AssignStatement extends Statement {

    public final Variable v;
    public final ArithmeticExpression e;

    public AssignStatement(final Variable v, final ArithmeticExpression e) {
        super(e);
        this.v = v;
        this.e = e;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "assign(" + v + "," + e + ")";
    }

    public static AssignStatement parse(StreamTokenizer st) throws IOException {
        requireToken(st, StreamTokenizer.TT_WORD);
        Variable v = Variable.get(st.sval);
        requireToken(st, '=');
        ArithmeticExpression e = ArithmeticExpression.parse(st);
        return new AssignStatement(v, e);
    }

    public void unparse(IndentPrinter p, Precedence prec) {
        p.print(v);
        p.print("=");
        e.unparse(p);
    }
}
