package cn.edu.nju.sklnst.whilex;

public class SeqStatement extends Statement {

    public final Statement s1;
    public final Statement s2;

    public SeqStatement(final Statement s1, final Statement s2) {
        super(s1, s2);
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "seq(" + s1 + "," + s2 + ")";
    }

    public void unparse(IndentPrinter p, Precedence unused) {
        s1.unparse(p);
        p.println(";");
        s2.unparse(p);
    }
}
