package polyglot.ext.jl5.ast;

import java.util.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.ext.jl.ast.*;
import polyglot.visit.*;
import polyglot.util.*;
import polyglot.ext.jl5.types.*;
/**
 * An immutable representation of a Java language extended <code>for</code>
 * statement.  Contains a statement to be executed and an expression
 * to be tested indicating whether to reexecute the statement.
 */
public class EnumConstantDecl_c extends Term_c implements EnumConstantDecl
{   
    protected List args;
    protected String name;
    protected ClassBody body;
    protected EnumInstance ei;
    
    public EnumConstantDecl_c(Position pos, String name, List args, ClassBody body){
        super(pos);
        this.name = name;
        this.args = args;
        this.body = body;
    }
        
    
    /** get args */
    public List args(){
        return args;
    }

    /** set args */
    public EnumConstantDecl args(List args){
        EnumConstantDecl_c n = (EnumConstantDecl_c)copy();
        n.args = args;
        return n;
    }

    /** set name */
    public EnumConstantDecl name(String name){
        EnumConstantDecl_c n = (EnumConstantDecl_c)copy();
        n.name = name;
        return n;
    }

    /** get name */
    public String name(){
        return name;
    }
    
    /** set body */
    public EnumConstantDecl body(ClassBody body){
        EnumConstantDecl_c n = (EnumConstantDecl_c)copy();
        n.body = body;
        return n;
    }

    /** get body */
    public ClassBody body(){
        return body;
    }

    public EnumConstantDecl enumInstance(EnumInstance ei){
        EnumConstantDecl_c n = (EnumConstantDecl_c) copy();
        n.ei = ei;
        return n;
    }

    public EnumInstance enumInstance(){
        return ei;
    }
    
    protected EnumConstantDecl_c reconstruct(List args, ClassBody body){
        if (!CollectionUtil.equals(args, this.args) || body != this.body) {
            EnumConstantDecl_c n = (EnumConstantDecl_c) copy();
            n.args = TypedList.copyAndCheck(args, Expr.class, true);
            n.body = body;
            return n;
        }
        return this;
    }

    public Node visitChildren(NodeVisitor v){
        List args = visitList(this.args, v);
        ClassBody body = (ClassBody) visitChild(this.body, v);
        return reconstruct(args, body);
    }

    public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
        return tb.pushCode();
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        JL5TypeSystem ts = (JL5TypeSystem)tb.typeSystem();

        EnumConstantDecl n = this;

        EnumInstance ei = ts.enumInstance(n.position(), ts.Enum(), JL5Flags.NONE, n.name());

        return n.enumInstance(ei);
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.SUPER) {
            return ar.bypassChildren(this);
        }

        return ar;
    }


    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {

        if (ar.kind() == AmbiguityRemover.SIGNATURES){
            Context c = ar.context();
            JL5TypeSystem ts = (JL5TypeSystem)ar.typeSystem();

            ParsedClassType ct = c.currentClassScope();

            //Flags f = flags;
            
            EnumInstance ei = ts.enumInstance(position(), ct, JL5Flags.NONE, name);
            return enumInstance(ei);
        }

        return this;
    }
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();
        Context c = tc.context();
        ClassType ct = c.currentClass();

        List argTypes = new LinkedList();
        for (Iterator it = this.args.iterator(); it.hasNext();){
            Expr e = (Expr) it.next();
            argTypes.add(e.type());
        }

        ConstructorInstance ci = ts.findConstructor(ct, argTypes, c.currentClass());
        return this;
    }

    public String toString(){
        return name + "(" + args + ")" + body != null ? "..." : "";
    }
    
    public void prettyPrint(CodeWriter w, PrettyPrinter tr){
        w.write(name);
        if (args != null){
            w.write(" ( ");
            Iterator it = args.iterator();
            while (it.hasNext()){
                Expr e = (Expr) it.next();
                print(e, w, tr);
                if (it.hasNext()){
                    w.write(", ");
                    w.allowBreak(0);
                }
            }
            w.write(" )");
        }
        if (body != null){
            w.write(" {");
            print(body, w, tr);
            w.write("}");
        }
    }

    public List acceptCFG(CFGBuilder v, List succs){
        return succs;
    }

    public Term entry(){
        return this;
    }

    public NodeVisitor addMembersEnter(AddMemberVisitor am){
        JL5ParsedClassType ct = (JL5ParsedClassType_c)am.context().currentClassScope();

        ct.addEnumConstant(ei);
        return am.bypassChildren(this);
    }
}
