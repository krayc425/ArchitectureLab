package cn.edu.nju.sklnst.whilex;

import static java.io.StreamTokenizer.TT_WORD;

import java.io.IOException;
import java.io.StreamTokenizer;

public abstract class BoolExpression extends Expression {

    public BoolExpression() {
        super();
    }

    public BoolExpression(Node c1, Node c2) {
        super(c1, c2);
    }

    public BoolExpression(Node ch) {
        super(ch);
    }

    public static BoolExpression parse(StreamTokenizer st) throws IOException {
        BoolExpression result = parseTerm(st);
        while (st.nextToken() == TT_WORD) {
            if (st.sval.equals("or")) {
                result = new OrExpression(result, parseTerm(st));
            } else {
                break;
            }
        }
        st.pushBack();
        return result;
    }

    private static BoolExpression parseTerm(StreamTokenizer st) throws IOException {
        BoolExpression result = parseFactor(st);
        while (st.nextToken() == TT_WORD) {
            if (st.sval.equals("and")) {
                result = new AndExpression(result, parseFactor(st));
            } else {
                break;
            }
        }
        st.pushBack();
        return result;
    }

    private static BoolExpression parseFactor(StreamTokenizer st) throws IOException {
        switch (st.nextToken()) {
            case '(': {
                BoolExpression result = parse(st);
                requireToken(st, ')');
                return result;
            }
            case TT_WORD:
                if (st.sval.equals("true")) return new LiteralBoolean(true);
                if (st.sval.equals("false")) return new LiteralBoolean(false);
                if (st.sval.equals("not")) return new NotExpression(parseFactor(st));
                // fall though
            default: {
                st.pushBack();
                ArithmeticExpression e1 = ArithmeticExpression.parse(st);
                RelopExpression.Operator relop = RelopExpression.parseOperator(st);
                ArithmeticExpression e2 = ArithmeticExpression.parse(st);
                return new RelopExpression(relop, e1, e2);
            }
        }
    }
}
