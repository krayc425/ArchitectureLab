package cn.edu.nju.sklnst.whilex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Node implements Iterable<Node> {
    private final Collection<Node> children;

    Node() {
        children = Collections.emptyList();
    }

    Node(Node ch) {
        children = Collections.singletonList(ch);
    }

    Node(Node c1, Node c2) {
        children = Collections.unmodifiableList(Arrays.asList(c1, c2));
    }

    Node(Node c1, Node c2, Node c3) {
        children = Collections.unmodifiableList(Arrays.asList(c1, c2, c3));
    }

    Node(List<? extends Node> ch) {
        children = Collections.unmodifiableList(ch);
    }

    Node(List<? extends Node> most, Node last) {
        int m = most.size();
        Node[] ch = new Node[m + 1];
        most.toArray(ch);
        ch[m] = last;
        children = Collections.unmodifiableList(Arrays.asList(ch));
    }

    public Iterator<Node> iterator() {
        return children.iterator();
    }

    /**
     * Call the visior back with this node.
     * Access for the VISITOR design pattern.
     *
     * @param <T>     return type of visitor
     * @param visitor visitor to do call back on.
     *                (Must not be null).
     * @return what the visitor returned.
     */
    public abstract <T> T accept(IVisitor<T> visitor);


    /**
     * Create a {@link StreamTokenizer} for the given reader that sets
     * up the syntax tables so that nodes can be parsed.
     *
     * @param r source of characters to parse
     * @return stream tokenizer to be used in parsing WHILEX programs.
     * @see Statement#parse(StreamTokenizer)
     * @see Program#parse(StreamTokenizer)
     */
    public static StreamTokenizer createStreamTokenizer(Reader r) {
        StreamTokenizer st = new StreamTokenizer(r);
        st.eolIsSignificant(false);
        st.lowerCaseMode(true);
        st.ordinaryChars(0x21, 0x7f);
        st.parseNumbers();
        st.ordinaryChar('.'); // we don't want decimals
        st.ordinaryChar('+');
        st.ordinaryChar('-'); // we don't want + and - to be considered numbers
        st.slashSlashComments(true);
        st.slashStarComments(true);
        st.commentChar('#');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.wordChars('_', '_');
        return st;
    }

    /**
     * Convenence method for Node subclasses: make sure the following token is
     * the one expected.
     *
     * @param st source of tokens
     * @param ch token (result of {@link StreamTokenizer#ttype})
     * @throws ParseError  if token does not match
     * @throws IOException if problems with the underlying stream/reader.
     */
    protected static void requireToken(StreamTokenizer st, int ch) throws ParseError, IOException {
        if (st.nextToken() != ch) {
            final String example;
            switch (ch) {
                case StreamTokenizer.TT_EOF:
                    example = "<End of File>";
                    break;
                case StreamTokenizer.TT_WORD:
                    example = "<Identifier>";
                    break;
                case StreamTokenizer.TT_NUMBER:
                    example = "<Number>";
                    break;
                default:
                    example = "'" + (char) ch + "'";
            }
            throw new ParseError("Expected: " + example, st);
        }
    }

    /**
     * Convenence method for Node subclasses: make sure the following token is
     * the given keyword expected.
     *
     * @param st      source of tokens
     * @param keyword string to expect
     * @throws ParseError  if token does not match
     * @throws IOException if problems with the underlying stream/reader.
     */
    protected static void requireKeyword(StreamTokenizer st, String keyword) throws IOException {
        if (st.nextToken() != StreamTokenizer.TT_WORD || !st.sval.equals(keyword)) {
            throw new ParseError("Expected: '" + keyword + "'", st);
        }
    }

    static enum Precedence {
        WHOLE, TERM, FACTOR;

        public boolean atMost(Precedence p) {
            return this.ordinal() <= p.ordinal();
        }
    }

    ;

    protected abstract void unparse(IndentPrinter p, Precedence precedence);

    /**
     * Unparse a node into the given pretty printer
     *
     * @param p pretty printer to use
     */
    public final void unparse(IndentPrinter p) {
        unparse(p, Precedence.WHOLE);
    }

    /**
     * Unparse a node into a string.  This differs from {@link #toString()}
     * because it generates the source langauge syntax rather than a view
     * of the internal representation.
     *
     * @return
     */
    public final String unparse() {
        StringWriter sw = new StringWriter();
        IndentPrinter ip = new IndentPrinter(sw);
        unparse(ip);
        if (ip.checkError()) return "[Error]";
        return sw.toString();
    }

    protected final void unparseParenthesized(IndentPrinter p) {
        p.print('(');
        unparse(p);
        p.print(')');
    }

    /**
     * Test program: parse the given arguments as WHILE programs and
     * echo back the result unparsed.
     *
     * @param args array of filenames.  A filename of "-" refers to {@link System#in}.
     */
    public static void main(String[] args) {
        for (String name : args) {
            Node result;
            result = readNode(name);
            if (result != null) {
                System.out.println("Recognized " + result);
                System.out.print(result.unparse());
            }
        }
    }

    /**
     * Reader a file into a Program or a Statement.
     * Convenience method for other main programs.
     *
     * @param filename file to open, or (if "-") use {@link System#in}.
     * @return node or null if there was a problem (in which case some
     * message will be printed to {@link System#err}.
     */
    public static Node readNode(String filename) {
        try {
            Reader r;
            if (filename.equals("-")) {
                r = new InputStreamReader(System.in);
            } else {
                r = new BufferedReader(new FileReader(filename));
            }
            StreamTokenizer st = createStreamTokenizer(r);
            if (st.nextToken() == StreamTokenizer.TT_WORD && st.sval.equals("program")) {
                st.pushBack();
                return Program.parse(st);
            } else {
                st.pushBack();
                return Statement.parse(st);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not open '" + filename + "' for reading.");
            return null;
        } catch (ParseError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
