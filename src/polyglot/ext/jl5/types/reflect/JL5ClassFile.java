package polyglot.ext.jl5.types.reflect;

import polyglot.types.reflect.*;
import polyglot.types.*;
import polyglot.frontend.*;
import polyglot.ext.jl5.types.*;
import java.io.*;

public class JL5ClassFile extends ClassFile implements JL5LazyClassInitializer {

    public JL5ClassFile(File classFileSource, byte[] code, ExtensionInfo ext){
        super(classFileSource, code, ext);
    }

    public void initEnumConstants(JL5ParsedClassType ct){
        JL5TypeSystem ts = (JL5TypeSystem)ct.typeSystem();
        
        for (int i = 0; i < fields.length; i++){
            if ((fields[i].modifiers() & JL5Flags.ENUM_MOD) != 0) {
                FieldInstance fi = fields[i].fieldInstance(ts, ct);
                ct.addEnumConstant(ts.enumInstance(ct.position(), ct, fi.flags(), fi.name()));
            }
        }
    }

    public void initAnnotations(JL5ParsedClassType ct){
        JL5TypeSystem ts = (JL5TypeSystem)ct.typeSystem();

        for (int i = 0; i < methods.length; i++){
            MethodInstance mi = methods[i].methodInstance(ts, ct);
            AnnotationElemInstance ai = ts.annotationElemInstance(ct.position(), ct, mi.flags(), mi.returnType(), mi.name(), ((JL5Method)methods[i]).hasDefaultVal());
            ct.addAnnotationElem(ai);
        }
    }
    
    
}