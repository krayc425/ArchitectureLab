package cn.edu.nju.sklnst.whilex.eval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.edu.nju.sklnst.whilex.*;

/**
 * The mutable state while executing a WHILEX program.
 *
 * @author boyland
 */
public class State implements Cloneable {
    private final Map<Variable, Integer> varState = new HashMap<Variable, Integer>();
    private final Map<String, Procedure> procedures = new HashMap<String, Procedure>();

    /**
     * Create an empty state with no procedures
     */
    public State() {
        // nothing
    }

    public State clone() {
        State st = new State();
        st.varState.putAll(varState);
        return st;
    }

    /**
     * Create an empty state with procedures from the given program
     *
     * @param p source of procedures
     * @throws EvaluationException if error with procedures
     */
    public State(Program p) throws EvaluationException {
        for (Procedure proc : p.procedures) {
            String name = proc.name;
            if (procedures.get(name) != null) throw new RedefinedProcedureException(name);
            procedures.put(name, proc);
        }
    }

    /**
     * Return the value of the variable
     *
     * @param v varia le to look up
     * @return current value of this variable
     * @throws VariableUnassignedException if variable has no value
     */
    public int get(Variable v) throws VariableUnassignedException {
        Integer value = varState.get(v);
        if (value == null) throw new VariableUnassignedException(v);
        return value;
    }

    /**
     * Assign a (new) value to the given variable
     *
     * @param v        variable to assign
     * @param newValue new value of variable.
     */
    public void set(Variable v, int newValue) {
        varState.put(v, newValue);
    }

    /**
     * Look up a procedure by name, number of formals and number of results.
     *
     * @param name    name of procedure
     * @param values  number of value parameters it must have
     * @param results number of result parameters it must have
     * @return procedure mathing these constraints.
     * @throws EvaluationException if no such procedure can be found.
     */
    public Procedure lookup(String name, int values, int results) throws EvaluationException {
        Procedure p = procedures.get(name);
        if (p == null) {
            throw new UnknownProcedureException(name);
        }
        if (p.valueFormals.size() != values) {
            throw new ParameterMismatchException("value", p.valueFormals.size(), values);
        }
        if (p.resultFormals.size() != results) {
            throw new ParameterMismatchException("result", p.resultFormals.size(), results);
        }
        return p;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Variable, Integer> e : varState.entrySet()) {
            if (e.getValue() != null) {
                sb.append(e.getKey());
                sb.append(" -> ");
                sb.append(e.getValue());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    // for procedures

    /**
     * Get the value of all the variables.
     * If a variable is not assigned, the corresponding value in the result is
     * null; no exception is thrown.
     *
     * @param vars (unchanged) list of variables
     * @return list in same order of variable values.
     */
    public List<Integer> getAll(List<Variable> vars) {
        List<Integer> result = new ArrayList<Integer>();
        for (Variable v : vars) {
            result.add(varState.get(v));
        }
        return result;
    }

    /**
     * Set the vale of all variables.
     * If a value is null, the corresponding variable becomes unassigned.
     * Neither parameer list is changed.
     *
     * @param vars       list of variables
     * @param savedState list of new values (must be same length)
     */
    public void setAll(List<Variable> vars, List<Integer> savedState) {
        assert vars != null && savedState != null && vars.size() == savedState.size();
        Iterator<Integer> ints = savedState.iterator();
        for (Variable v : vars) {
            varState.put(v, ints.next());
        }
    }

    /**
     * Make the given variables all unassigned.
     *
     * @param vars (unchanged) list of variables.
     */
    public void clearAll(List<Variable> vars) {
        for (Variable v : vars) {
            varState.put(v, null);
        }
    }

    public boolean equals(Object o) {
        if (o instanceof State) {
            return varState.equals(((State) o).varState);
        }
        return false;
    }

    public int hashCode() {
        return varState.hashCode();
    }
}
