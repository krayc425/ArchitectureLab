package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

public class WhileStatement extends Statement {

    public final BoolExpression cond;
    public final Statement body;

    public WhileStatement(final BoolExpression cond, final Statement body) {
        super(cond, body);
        this.cond = cond;
        this.body = body;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "while(" + cond + "," + "...)"; // body + ")";
    }

    public static WhileStatement parse(StreamTokenizer st) throws IOException {
        requireKeyword(st, "while");
        BoolExpression cond = BoolExpression.parse(st);
        requireKeyword(st, "do");
        Statement s1 = Statement.parse(st);
        requireKeyword(st, "end");
        return new WhileStatement(cond, s1);
    }

    public void unparse(IndentPrinter ip, Precedence prec) {
        ip.print("while ");
        cond.unparse(ip);
        ip.println(" do");
        ip.indent(1);
        body.unparse(ip);
        ip.println();
        ip.indent(-1);
        ip.print("end");
    }
}
