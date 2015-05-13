package ast;
import compiler.Failure;

/** Abstract syntax for definitions (either global variables or functions.
 */
public abstract class Defn {

    /** Print an indented description of this definition.
     */
    public abstract void indent(IndentOutput out, int n);

    /** Extend the environments in the given context with entries from
     *  this definition.
     */
    abstract void extendGlobalEnv(Context ctxt)
      throws Failure;

    /** Generate assembly code for given set of top-level definitions.
     */
    public static void compile(String name, Defn[] defns) {
        LocEnv   globals = null;
        Assembly a       = Assembly.assembleToFile(name);
        Formal [] forms = new Formal[0];
        a.emit();
        a.emit(".data");
        for (int i=0; i<defns.length; i++) {
            globals = defns[i].declareGlobals(a, globals);
        }

        a.emit();
        a.emit(".text");

        a.emit();
        a.emit(".globl", a.name("initGlobals"));
        a.emitLabel(a.name("initGlobals"));
        a.emitPrologue(); 
       
        Frame f = new FunctionFrame(forms, globals);

        for ( int i = 0; i < defns.length; ++i) {
            defns[i].compileGlobals(a,globals,f);
        }
        
        a.emitEpilogue();

        for (int i=0; i<defns.length; i++) {
            defns[i].compileFunction(a, globals);
        }

        a.close();
    }

    /** Declare storage for global variables.
     */
    abstract LocEnv declareGlobals(Assembly a, LocEnv env);

    /** Generate compiled code for a function.
     */
    abstract void compileFunction(Assembly a, LocEnv globals);

    abstract void compileGlobals(Assembly a, LocEnv globals, Frame f);
}
