package ast;
import compiler.Failure;

/** Abstract syntax for for loops.
 */
public class For extends Stmt {

    /** The initialization expression.
     */
    private StmtExpr init;

    /** The test portion of this loop.
     */
    private Expr test;

    /** The step portion of this for loop.
     */
    private StmtExpr step;

    /** The body of this loop.
     */
    private Stmt body;

    /** Default constructor.
     */
    public For(StmtExpr init, Expr test, StmtExpr step, Stmt body) {
        this.init = init;
        this.test = test;
        this.step = step;
        this.body = body;
    }

    /** Print an indented description of this abstract syntax node,
     *  including a name for the node itself at the specified level
     *  of indentation, plus more deeply indented descriptions of
     *  any child nodes.
     */
    public void indent(IndentOutput out, int n) {
        out.indent(n, "For");
  
        if (init!=null) {
           init.indent(out, n+1);
        } else {
            out.indent(n+1, "No init");
        }
  
        if (test!=null) {
           test.indent(out, n+1);
        } else {
            out.indent(n+1, "No test");
        }
  
        if (step!=null) {
           step.indent(out, n+1);
        } else {
            out.indent(n+1, "No step");
        }
  
        body.indent(out, n+1);
    }

    /** Type check this statement, using the specified context, with
     *  the given initial typing environment, and returning the typing
     *  environment for a following statement.
     */
    public TypeEnv check(Context ctxt, TypeEnv locals)
      throws Failure {
        if (init!=null) {
            init.check(ctxt, locals);
        }
        if (test!=null) {
            try {
                if (!test.typeOf(ctxt, locals).equals(Type.BOOLEAN)) {
                    ctxt.report(new Failure("WhileBoolean"));
                }
            } catch (Failure f) {
                ctxt.report(f);
            }
        }
        if (step!=null) {
            step.check(ctxt, locals);
        }
        body.check(ctxt, locals); // discard final environment
        return locals;
    }

    /** Generate code for executing this statement.
     *  Returns true if there is a chance that execution may
     *  continue with the next statement.
     */
    public boolean compile(Assembly a, Frame f) {
       // Generate two labels for jump targets
       String lab1 = a.newLabel();
       String lab2 = a.newLabel();
       // Only compile an looping variable initialization if one exists
       if(init != null) {
           init.compileExpr(a,f);
       }
       // Emit a jump to the second label
       a.emit("jmp", lab2);
       // Emit the first label
       a.emitLabel(lab1);
       // The body must always exist, and appear between the looping target labels
       body.compile(a,f);
       // If a step expression exits, compile it
       if(step != null) {
            step.compileExpr(a, f);
       }
       // Emit the second label
       a.emitLabel(lab2);
       /** Only compile and brance on the text conditional expression if one exists
        *  Otherwise, only emit a jump instruction to the first label to ensure that
        *  looping behaviour still occurs regardless of the existance of a conditional
        *  expression
        */
       if( test != null ) {
           test.branchTrue(a,f,lab1); 
       } else {
           a.emit("jmp", lab1);
       }
       return true;
    }
}
