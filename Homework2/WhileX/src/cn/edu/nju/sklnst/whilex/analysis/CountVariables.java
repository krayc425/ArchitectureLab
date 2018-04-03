package cn.edu.nju.sklnst.whilex.analysis;

import cn.edu.nju.sklnst.whilex.Node;
import cn.edu.nju.sklnst.whilex.Program;
import cn.edu.nju.sklnst.whilex.Statement;

import static cn.edu.nju.sklnst.whilex.Node.readNode;

public class CountVariables {

    public static void main(String[] args) {
        String filename = args[0];

        CountVariablesVisitor countVariablesVisitor = new CountVariablesVisitor();
        Node node = readNode(filename);

        if (node == null) {
        	return;
        }
        
        System.out.print("java cn.edu.nju.sklnst.whilex.analysis.CountVariables ");
        System.out.println(args[0]);
        if (node instanceof Program) {
            Program program = (Program) node;
            if (program.inputs.size() != args.length - 1) {
                System.err.println("Need exactly one argument per input to program");
                System.exit(1);
            }

            node.accept(countVariablesVisitor);
            System.out.println(args[0] + " has " + countVariablesVisitor.getResultSet().size() + " variables:");
            System.out.println(countVariablesVisitor.getResultSet().toString());
        } else if (node instanceof Statement) {

            node.accept(countVariablesVisitor);
            System.out.println(args[0] + " has " + countVariablesVisitor.getResultSet().size() + " variables:");
            System.out.println(countVariablesVisitor.getResultSet().toString());
        } else {
            System.err.println("Cannot interpret contents of " + filename + ": " + node);
        }

    }
}
