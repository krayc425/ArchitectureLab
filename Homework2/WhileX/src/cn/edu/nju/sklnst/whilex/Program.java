package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

public class Program extends Node {

    public final String name;
    public final List<Variable> inputs;
    public final List<Variable> outputs;
    public final List<Variable> locals;
    public final List<Procedure> procedures;
    public final Statement body;

    public Program(String name, List<Variable> inputs, List<Variable> outputs, List<Variable> locals,
                   List<Procedure> procedures, Statement body) {
        super(procedures, body);
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
        this.locals = locals;
        this.procedures = procedures;
        this.body = body;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Program parse(StreamTokenizer st) throws IOException {
        requireKeyword(st, "program");
        requireToken(st, StreamTokenizer.TT_WORD);
        String name = st.sval;
        List<Variable> inputs = new ArrayList<Variable>();
        List<Variable> outputs = new ArrayList<Variable>();
        List<Variable> locals = new ArrayList<Variable>();
        Procedure.parseFormals(st, inputs, outputs, locals);
        List<Procedure> procs = new ArrayList<Procedure>();
        while (st.nextToken() == StreamTokenizer.TT_WORD && st.sval.equals("proc")) {
            st.pushBack();
            procs.add(Procedure.parse(st));
        }
        st.pushBack();
        requireKeyword(st, "begin");
        Statement body = Statement.parse(st);
        requireKeyword(st, "end");
        return new Program(name, inputs, outputs, locals, procs, body);
    }

    public void unparse(IndentPrinter p, Precedence unused) {
        p.print("program ");
        p.print(name);
        p.print(" ");
        p.indent(1);
        Procedure.unparseFormals(p, this.inputs, this.outputs, this.locals);
        p.println();
        for (Procedure proc : procedures) {
            proc.unparse(p);
        }
        p.indent(-1);
        p.println("begin");
        p.indent(1);
        body.unparse(p);
        p.println();
        p.indent(-1);
        p.print("end");
    }
}
