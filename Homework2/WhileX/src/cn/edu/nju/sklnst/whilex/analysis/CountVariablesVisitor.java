package cn.edu.nju.sklnst.whilex.analysis;

import cn.edu.nju.sklnst.whilex.*;

import java.util.HashSet;
import java.util.Set;

public class CountVariablesVisitor extends Visitor<Integer> {

    private Set<Variable> resultSet = new HashSet<>();

    @Override
    public Integer visit(UseVariable n) {
        resultSet.add(n.var);
        return null;
    }

    @Override
    public Integer visit(AssignStatement s) {
        resultSet.add(s.v);
        return null;
    }

    @Override
    public Integer visit(Node n) {
        for (Node next : n) {
            next.accept(this);
        }
        return null;
    }

    public Set<Variable> getResultSet() {
        return resultSet;
    }

}
