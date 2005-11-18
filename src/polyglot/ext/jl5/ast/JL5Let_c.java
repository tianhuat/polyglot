package polyglot.ext.jl5.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.util.*;
import polyglot.visit.*;
import java.util.*;

public class JL5Let_c extends Expr_c implements JL5Let {

    protected LocalDecl localDecl;
    //protected Expr alpha;
    protected Expr beta;
    
    public JL5Let_c(Position pos, LocalDecl localDecl, Expr beta){
        super(pos);
        this.localDecl = localDecl;
        //this.alpha = alpha;
        this.beta = beta;
    }
    
    public LocalDecl localDecl(){
        return this.localDecl;
    }
 
    /*public Expr alpha(){
        return this.alpha;
    }
    
    public JL5Let alpha(Expr e){
        JL5Let_c n = (JL5Let_c)copy();
        n.alpha = e;
        return n;
    }*/
    
    
    public Expr beta(){
        return this.beta;
    }

    public JL5Let beta(Expr e){
        JL5Let_c n = (JL5Let_c)copy();
        n.beta = e;
        return n;
    }
    
    public Node visitChildren(NodeVisitor v){
        LocalDecl localDecl = (LocalDecl)visitChild(this.localDecl, v);
        //Expr alpha = (Expr)visitChild(this.alpha, v);
        Expr beta = (Expr)visitChild(this.beta, v);
        return reconstruct(localDecl, beta);
    }

    protected JL5Let reconstruct(LocalDecl localDecl, Expr beta){
        if (localDecl != this.localDecl || beta != this.beta){
            JL5Let_c n = (JL5Let_c)copy();
            n.localDecl = localDecl;
            //n.alpha = alpha;
            n.beta = beta;    
            return n;
        }
        return this;
    }

    public Term entry(){
        return this;
    }

    public List acceptCFG(CFGBuilder v, List succs){
        //v.visitCFG(alpha, beta.entry());
        v.visitCFG(beta, this);
        return succs;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr){
        w.write("(");
        print(localDecl, w, tr);
        w.write(" ");
        //print(alpha, w, tr);
        //w.write(", ");
        print(beta, w, tr);
        w.write(")");
    }
}