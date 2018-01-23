package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.Writer;

/**
 * A class similar to {@link java.io.PrintWriter} that has controlled indentation.
 * At the start of every line, output spaces to the current indentation level.
 * Currently each increment of indentation adds two spaces.
 *
 * @author boyland
 */
public class IndentPrinter {
    private final Writer w;
    private final int indentAmount;
    private int indent;
    private boolean atStart;
    private final String lineSeparator = System.getProperty("line.separator");
    private boolean errorsNoticed = false;
    private String spaces = "    ";

    public IndentPrinter(Writer out) {
        this(out, 2);
    }

    public IndentPrinter(Writer out, int indentAmount) {
        w = out;
        this.indentAmount = indentAmount;
        this.indent = 0;
        this.atStart = true;
    }

    public void println() {
        try {
            w.write(lineSeparator);
            w.flush();
        } catch (IOException e) {
            errorsNoticed = true;
        }
        atStart = true;
    }

    public void print(Object o) {
        try {
            if (atStart) {
                int numSpaces = indent * indentAmount;
                while (numSpaces > spaces.length()) {
                    spaces = spaces + spaces;
                }
                w.write(spaces, 0, numSpaces);
                atStart = false;
            }
            w.write(o.toString());
        } catch (IOException e) {
            errorsNoticed = true;
        }
    }

    public void println(Object o) {
        print(o);
        println();
    }

    public boolean checkError() {
        try {
            w.flush();
        } catch (IOException e) {
            errorsNoticed = true;
        }
        return errorsNoticed;
    }

    /**
     * Change the indentattion by the given delta.
     *
     * @param delta Usually 1 to increase indentation or -1 to decrease it.
     * @return the new indentation level
     */
    public int indent(int delta) {
        indent += delta;
        return indent;
    }

    /**
     * Set the indentation absolutely.
     *
     * @param in
     */
    public void setIndent(int in) {
        indent = in;
    }
}
