package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CallStatement extends Statement {

    public final String proc;
    public final List<ArithmeticExpression> args;
    public final List<Variable> results;

    public CallStatement(final String proc, List<ArithmeticExpression> args, List<Variable> results) {
        super(args);
        this.proc = proc;
        this.args = Collections.unmodifiableList(args);
        this.results = Collections.unmodifiableList(results);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("call(" + proc);
        for (ArithmeticExpression ae : args) {
            sb.append(',');
            sb.append(ae);
        }
        boolean atStart = true;
        for (Variable v : results) {
            if (atStart) sb.append('!');
            else sb.append(',');
            atStart = false;
            sb.append(v);
        }
        sb.append(')');
        return sb.toString();
    }

    public static CallStatement parse(StreamTokenizer st) throws IOException {
        requireKeyword(st, "call");
        requireToken(st, StreamTokenizer.TT_WORD);
        String p = st.sval;
        requireToken(st, '(');
        List<ArithmeticExpression> args = new ArrayList<ArithmeticExpression>();
        if (st.nextToken() != ')' && st.ttype != '!') {
            st.pushBack();
            do {
                args.add(ArithmeticExpression.parse(st));
            } while (st.nextToken() == ',');
        }
        List<Variable> results = new ArrayList<Variable>();
        if (st.ttype == '!') {
            do {
                requireToken(st, StreamTokenizer.TT_WORD);
                results.add(Variable.get(st.sval));
            } while (st.nextToken() == ',');
        }
        st.pushBack();
        requireToken(st, ')');
        return new CallStatement(p, args, results);
    }

    public void unparse(IndentPrinter p, Precedence prec) {
        p.print("call " + proc + "(");
        boolean atStart = true;
        for (ArithmeticExpression ae : args) {
            if (!atStart) p.print(",");
            atStart = false;
            ae.unparse(p);
        }
        atStart = true;
        for (Variable v : results) {
            if (atStart) {
                p.print("!");
                atStart = false;
            } else {
                p.print(",");
            }
            p.print(v);
        }
        p.print(")");
    }
}
