package polyglot.ext.jl5.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.frontend.Job;
import polyglot.types.Package;
import polyglot.visit.*;

/** Visitor which performs type checking on the AST. */
public class ApplicationChecker extends ContextVisitor
{
    public ApplicationChecker(Job job, TypeSystem ts, NodeFactory nf) {
	    super(job, ts, nf);
    }

    protected Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
        if (n instanceof ApplicationCheck){
	        return ((ApplicationCheck)n).applicationCheck((ApplicationChecker) v);
        }
	    return n;
    }
}
