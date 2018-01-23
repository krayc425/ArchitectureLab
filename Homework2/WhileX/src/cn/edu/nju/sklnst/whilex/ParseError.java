/**
 *
 */
package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * Excetion thrown when a node class is unable to accet the next token from
 * the tokenizer.
 *
 * @author boyland
 */
public class ParseError extends IOException {
    /**
     * Eclipse wants this
     */
    private static final long serialVersionUID = -3237605109508091821L;

    public ParseError() {
        this("Parse Error");
    }

    public ParseError(String message) {
        super(message);
    }

    public ParseError(StreamTokenizer st) {
        this("Parse Error", st);
    }

    public ParseError(String message, StreamTokenizer st) {
        super(message + " at " + st.toString());
    }
}