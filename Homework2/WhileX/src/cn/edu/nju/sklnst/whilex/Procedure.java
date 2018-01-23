package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

public class Procedure extends Node {

    public final String name;
    public final List<Variable> valueFormals;
    public final List<Variable> resultFormals;
    public final List<Variable> locals;
    public final Statement body;

    public Procedure(final String name,
                     final List<Variable> valueFormals, final List<Variable> resultFormals, final List<Variable> locals,
                     final Statement body) {
        super(body);
        this.name = name;
        this.valueFormals = Collections.unmodifiableList(valueFormals);
        this.resultFormals = Collections.unmodifiableList(resultFormals);
        this.locals = Collections.unmodifiableList(locals);
        this.body = body;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "proc(" + name + ")";
    }

    public static Procedure parse(StreamTokenizer st) throws IOException {
        requireKeyword(st, "proc");
        requireToken(st, StreamTokenizer.TT_WORD);
        String name = st.sval;
        List<Variable> valueFormals = new ArrayList<Variable>();
        List<Variable> resultFormals = new ArrayList<Variable>();
        List<Variable> locals = new ArrayList<Variable>();
        parseFormals(st, valueFormals, resultFormals, locals);
        requireKeyword(st, "is");
        Statement body = Statement.parse(st);
        requireKeyword(st, "end");
        return new Procedure(name, valueFormals, resultFormals, locals, body);
    }

    static void parseFormals(StreamTokenizer st, List<Variable> valueFormals, List<Variable> resultFormals, List<Variable> locals) throws IOException {
        requireToken(st, '(');
        if (st.nextToken() != ')' && st.ttype != '!') {
            st.pushBack();
            do {
                requireToken(st, StreamTokenizer.TT_WORD);
                valueFormals.add(Variable.get(st.sval));
            } while (st.nextToken() == ',');
        }
        if (st.ttype == '!') {
            do {
                requireToken(st, StreamTokenizer.TT_WORD);
                resultFormals.add(Variable.get(st.sval));
            } while (st.nextToken() == ',');
        }
        st.pushBack();
        requireToken(st, ')');
        if (st.nextToken() == StreamTokenizer.TT_WORD && st.sval.equals("locals")) {
            do {
                requireToken(st, StreamTokenizer.TT_WORD);
                locals.add(Variable.get(st.sval));
            } while (st.nextToken() == ',');
            st.pushBack();
        } else {
            st.pushBack();
        }
    }

    public void unparse(IndentPrinter p, Precedence prec) {
        p.print("proc " + name);
        List<Variable> valueList = this.valueFormals;
        List<Variable> resultList = this.resultFormals;
        unparseFormals(p, valueList, resultList, locals);
        p.println(" is");
        p.indent(1);
        body.unparse(p);
        p.println();
        p.indent(-1);
        p.println("end");
    }

    static void unparseFormals(IndentPrinter p, List<Variable> valueList, List<Variable> resultList, List<Variable> locals) {
        p.print("(");
        boolean atStart = true;
        for (Variable v : valueList) {
            if (!atStart) p.print(",");
            atStart = false;
            p.print(v);
        }
        atStart = true;
        for (Variable v : resultList) {
            if (atStart) {
                p.print("!");
                atStart = false;
            } else {
                p.print(",");
            }
            p.print(v);
        }
        p.print(")");
        if (!locals.isEmpty()) {
            atStart = true;
            for (Variable v : locals) {
                if (atStart) {
                    p.print("locals ");
                    atStart = false;
                } else {
                    p.print(",");
                }
                p.print(v);
            }
        }
    }
}
