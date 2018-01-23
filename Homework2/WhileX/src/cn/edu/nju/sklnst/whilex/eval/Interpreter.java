package cn.edu.nju.sklnst.whilex.eval;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Iterator;

import cn.edu.nju.sklnst.whilex.*;

public class Interpreter {

    /**
     * Interpret a file that cntains either a Program or a Statement
     * (or sequence of statements).  In the first case, the arguments are
     * integers to be assigned to the program inputs.  In the second case,
     * they are var=value pairs.
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java Interpreter file.whl arguments");
            System.exit(1);
        }
        String filename = args[0];
        Node node = Node.readNode(filename);
        if (node == null) return;
        if (node instanceof Program) {
            Program program = (Program) node;
            // arguments are values for program inputs
            if (program.inputs.size() != args.length - 1) {
                System.err.println("Need exactly one argument per input to program");
                System.exit(1);
            }
            State st = new State(program);
            Iterator<Variable> vars = program.inputs.iterator();
            for (int i = 1; i < args.length; ++i) {
                Variable var = vars.next();
                try {
                    st.set(var, Integer.parseInt(args[i]));
                } catch (NumberFormatException e) {
                    System.err.println("Expected integer for " + var + ": " + args[i]);
                    System.exit(1);
                }
            }
            BigStepEvaluation.evaluate(program.body, st);
            for (Variable var : program.outputs) {
                System.out.print(st.get(var));
                System.out.print(' ');
            }
            System.out.println();
        } else if (node instanceof Statement) {
            Statement stmt = (Statement) node;
            State s = new State();
            for (int i = 1; i < args.length; ++i) {
                // we delegate the job of parsing var=value pairs to the WHILE parser
                // and the evaluator.
                StreamTokenizer st = Node.createStreamTokenizer(new StringReader(args[i]));
                try {
                    BigStepEvaluation.evaluate(Statement.parse(st), s);
                } catch (IOException e) {
                    System.err.println(e);
                    System.exit(1);
                }
            }
            BigStepEvaluation.evaluate(stmt, s);
            System.out.println(s);
        } else {
            System.err.println("Cannot interpret contents of " + filename + ": " + node);
        }
    }
}
