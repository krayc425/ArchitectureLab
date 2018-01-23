package cn.edu.nju.sklnst.whilex.eval;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.sklnst.whilex.*;

public class BigStepEvaluation implements IVisitor<Integer> {
    /**
     * Evaluate a statement in a given state (for side-effect).
     *
     * @param n a WHILEX language statement
     * @param s state to evaluate in (and side-effect)
     */
    public static void evaluate(Statement n, State s) {
        evaluateInternal(n, s);
    }

    /**
     * Evaluate an expression in a given state and return the result.
     *
     * @param ae a WHILEX language arihmetic expression
     * @param s  state in which to evaluate
     * @return result of evaluating the arithmetic expression
     */
    public static int evaluate(ArithmeticExpression ae, State s) {
        return evaluateInternal(ae, s);
    }

    /**
     * Evaluate a boolean expression in a given state and return the result.
     *
     * @param ae a WHILEX language boolean expression
     * @param s  state in which to evaluate
     * @return result of evaluating the boolean expression
     */
    public static boolean evaluate(BoolExpression b, State s) {
        return evaluateInternal(b, s) == 0 ? false : true;
    }

    // actually start the work:

    private static Integer evaluateInternal(Node n, State s) {
        return n.accept(new BigStepEvaluation(s));
    }

    private final State state;

    private BigStepEvaluation(State s) {
        state = s;
    }

    // remaining methods of evaluation engine are visit functions

    // expressions return integers

    public Integer visit(UseVariable n) {
        return state.get(n.var);
    }

    public Integer visit(LiteralInteger n) {
        return n.value;
    }

    public Integer visit(BinopExpression n) {
        int n1 = n.e1.accept(this);
        int n2 = n.e2.accept(this);
        // System.out.println(n1 + " " + n.op + " " + n2);
        switch (n.op) {
            default:
                return 0; // shouldn't happen
            case PLUS:
                return n1 + n2;
            case MINUS:
                return n1 - n2;
            case TIMES:
                return n1 * n2;
            case DIVIDE:
                if (n2 == 0) throw new DivideException();
                return n1 / n2;
        }
    }

    // booleans return 0 or 1

    public Integer visit(LiteralBoolean x) {
        return x.value ? 1 : 0;
    }

    public Integer visit(NotExpression x) {
        return 1 - x.e.accept(this);
    }

    public Integer visit(AndExpression x) {
        return x.e1.accept(this) & x.e2.accept(this);
    }

    public Integer visit(OrExpression x) {
        return x.e1.accept(this) | x.e2.accept(this);
    }

    public Integer visit(RelopExpression n) {
        int n1 = n.e1.accept(this);
        int n2 = n.e2.accept(this);
        // System.out.println(n1 + " " + n.op + " " + n2);
        switch (n.op) {
            default:
                return 0; // shouldn't happen
            case LESS:
                return n1 < n2 ? 1 : 0;
            case EQUAL:
                return n1 == n2 ? 1 : 0;
        }
    }

    // Statements return null

    public Integer visit(AssignStatement s) {
        state.set(s.v, s.e.accept(this));
        return null;
    }

    public Integer visit(SkipStatement s) {
        return null;
    }

    public Integer visit(SeqStatement s) {
        s.s1.accept(this);
        // System.out.println(state);
        s.s2.accept(this);
        return null;
    }

    public Integer visit(IfStatement s) {
        if (s.cond.accept(this).intValue() == 1) {
            s.ifTrue.accept(this);
        } else {
            s.ifFalse.accept(this);
        }
        return null;
    }

    public Integer visit(WhileStatement s) {
        while (s.cond.accept(this).intValue() == 1) {
            s.body.accept(this);
        }
        return null;
    }

    public Integer visit(Program p) {
        throw new EvaluationException("Can't evaluate a program directly");
    }

    public Integer visit(Procedure p) {
        throw new EvaluationException("Can't evaluate a procedure directly");
    }

    public Integer visit(CallStatement s) {
        Procedure callee = state.lookup(s.proc, s.args.size(), s.results.size());
        List<Integer> args = new ArrayList<Integer>();
        for (ArithmeticExpression ae : s.args) {
            args.add(ae.accept(this));
        }
        List<Integer> savedValues = state.getAll(callee.valueFormals);
        List<Integer> savedResults = state.getAll(callee.resultFormals);
        List<Integer> savedLocals = state.getAll(callee.locals);
        state.setAll(callee.valueFormals, args);
        state.clearAll(callee.resultFormals);
        state.clearAll(callee.locals);
        callee.body.accept(this);
        List<Integer> results = new ArrayList<Integer>();
        for (Variable v : callee.resultFormals) {
            results.add(state.get(v)); // force exception if undefined
        }
        state.setAll(callee.valueFormals, savedValues);
        state.setAll(callee.resultFormals, savedResults);
        state.setAll(callee.locals, savedLocals);
        state.setAll(s.results, results);
        return null;
    }


}
