package cn.edu.nju.sklnst.whilex;

public class SkipStatement extends Statement {

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "skip";
    }

    @Override
    public void unparse(IndentPrinter p, Precedence precedence) {
        p.print("skip");
    }

}
