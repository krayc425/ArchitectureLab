package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

public class IfStatement extends Statement {

    public final BoolExpression cond;
    public final Statement ifTrue;
    public final Statement ifFalse;

    public IfStatement(final BoolExpression cond, final Statement ifTrue, final Statement ifFalse) {
        super(cond, ifTrue, ifFalse);
        this.cond = cond;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "if(" + cond + "," + "...)"; // ifTrue + "," + ifFalse + ")";
    }

    public static IfStatement parse(StreamTokenizer st) throws IOException {
        requireKeyword(st, "if");
        BoolExpression cond = BoolExpression.parse(st);
        requireKeyword(st, "then");
        Statement s1 = Statement.parse(st);
        requireKeyword(st, "else");
        Statement s2 = Statement.parse(st);
        requireKeyword(st, "end");
        return new IfStatement(cond, s1, s2);
    }

    public void unparse(IndentPrinter ip, Precedence prec) {
        ip.print("if ");
        cond.unparse(ip);
        ip.println(" then");
        ip.indent(1);
        ifTrue.unparse(ip);
        ip.println();
        ip.indent(-1);
        ip.println("else");
        ip.indent(1);
        ifFalse.unparse(ip);
        ip.println();
        ip.indent(-1);
        ip.print("end");
    }
}
