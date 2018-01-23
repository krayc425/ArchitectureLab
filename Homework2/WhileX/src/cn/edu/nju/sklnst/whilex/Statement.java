package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.List;

public abstract class Statement extends Node {

    public Statement() {
        super();
    }

    public Statement(List<? extends Node> ch) {
        super(ch);
    }

    public Statement(Node c1, Node c2, Node c3) {
        super(c1, c2, c3);
    }

    public Statement(Node c1, Node c2) {
        super(c1, c2);
    }

    public Statement(Node ch) {
        super(ch);
    }

    public static Statement parse(StreamTokenizer st) throws IOException {
        Statement result = parseStatement(st);
        while (st.nextToken() == ';') {
            if (st.nextToken() == StreamTokenizer.TT_WORD && st.sval.equals("end")) {
                break;
            } else {
                st.pushBack();
            }
            result = new SeqStatement(result, parseStatement(st));
        }
        st.pushBack();
        return result;
    }

    private static Statement parseStatement(StreamTokenizer st) throws IOException {
        switch (st.nextToken()) {
            default:
                throw new ParseError("Expected statement", st);
            case StreamTokenizer.TT_WORD:
                if (st.sval.equals("skip")) {
                    return new SkipStatement();
                } else if (st.sval.equals("if")) {
                    st.pushBack();
                    return IfStatement.parse(st);
                } else if (st.sval.equals("while")) {
                    st.pushBack();
                    return WhileStatement.parse(st);
                } else if (st.sval.equals("call")) {
                    st.pushBack();
                    return CallStatement.parse(st);
                } else {
                    st.pushBack();
                    return AssignStatement.parse(st);
                }
        }
    }
}
