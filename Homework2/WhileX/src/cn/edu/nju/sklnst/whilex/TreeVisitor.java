package cn.edu.nju.sklnst.whilex;

/**
 * A default implementation class for visitors that wish to visit
 * all the nodes in a tree.  Such a visitor can't have a useful return
 * value because it's not clear how to combine values of children.
 *
 * @author boyland
 */
public class TreeVisitor extends Visitor<Void> {

    @Override
    public Void visit(Node n) {
        for (Node ch : n) {
            ch.accept(this);
        }
        return null;
    }

}
