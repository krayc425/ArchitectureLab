package cn.edu.nju.sklnst.whilex;

public abstract class Expression extends Node {

    public Expression() {
        super();
    }

    public Expression(Node c1, Node c2) {
        super(c1, c2);
    }

    public Expression(Node ch) {
        super(ch);
    }

}
