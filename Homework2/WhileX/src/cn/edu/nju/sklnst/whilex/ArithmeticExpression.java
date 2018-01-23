package cn.edu.nju.sklnst.whilex;

import static java.io.StreamTokenizer.TT_NUMBER;
import static java.io.StreamTokenizer.TT_WORD;

import java.io.IOException;
import java.io.StreamTokenizer;

public abstract class ArithmeticExpression extends Expression {

    public ArithmeticExpression() {
        super();
    }

    public ArithmeticExpression(Node c1, Node c2) {
        super(c1, c2);
    }

    public static ArithmeticExpression parse(StreamTokenizer st) throws IOException {
        ArithmeticExpression result = parseTerm(st);
        while (st.nextToken() == '+' || st.ttype == '-') {
            st.pushBack();
            BinopExpression.Operator op = BinopExpression.parseOperator(st);
            result = new BinopExpression(op, result, parseTerm(st));
        }
        st.pushBack();
        return result;
    }

    private static ArithmeticExpression parseTerm(StreamTokenizer st) throws IOException {
        ArithmeticExpression result = parseFactor(st);
        while (st.nextToken() == '*' || st.ttype == '/') {
            st.pushBack();
            BinopExpression.Operator op = BinopExpression.parseOperator(st);
            result = new BinopExpression(op, result, parseTerm(st));
        }
        st.pushBack();
        return result;
    }

    private static ArithmeticExpression parseFactor(StreamTokenizer st) throws IOException {
        switch (st.nextToken()) {
            case '(': {
                ArithmeticExpression result = parse(st);
                requireToken(st, ')');
                return result;
            }
            case TT_NUMBER:
                return new LiteralInteger((int) st.nval);
            case TT_WORD:
                return new UseVariable(Variable.get(st.sval));
        }
        throw new ParseError("Expected arithmetic expression", st);
    }

}
