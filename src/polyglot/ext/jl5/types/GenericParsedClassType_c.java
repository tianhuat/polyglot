package polyglot.ext.jl5.types;

import java.util.*;
import polyglot.types.*;
import polyglot.frontend.*;
import polyglot.util.*;

public class GenericParsedClassType_c extends JL5ParsedClassType_c implements GenericParsedClassType {

    protected List typeVariables;
   
    public GenericParsedClassType_c(TypeSystem ts, LazyClassInitializer init, Source fromSource){
        super(ts, init, fromSource);
    }
    
    public List typeVariables(){
        return typeVariables;
    }
    
    public void addTypeVariable(IntersectionType type){
        if (typeVariables == null){
            typeVariables = new TypedList(new LinkedList(), IntersectionType.class, false);
        }
        typeVariables.add(type);
    }
    
    public boolean hasTypeVariable(String name){
        for (Iterator it = typeVariables.iterator(); it.hasNext(); ){
            IntersectionType iType = (IntersectionType)it.next();
            if (iType.name().equals(name)) return true;
        }
        return false;
    }
    
    public IntersectionType getTypeVariable(String name){
        for (Iterator it = typeVariables.iterator(); it.hasNext(); ){
            IntersectionType iType = (IntersectionType)it.next();
            if (iType.name().equals(name)) return iType;
        }
        return null;
    }
}