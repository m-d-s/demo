//____________________________________________________________________________
// IExpr ::= Var
//        |  Int
//        |  IExpr + IExpr
//        |  IExpr - IExpr

abstract class IExpr {
  abstract int    eval(Memory mem);
  abstract String show();

  abstract Code compileTo(Reg reg, Code next);
}

class Var extends IExpr {
  private String name;
  Var(String name) { this.name = name; }

  int    eval(Memory mem) { return mem.load(name); }
  String show() { return name; }

  Code compileTo(Reg reg, Code next) {
    return new Load(reg, name, next);
  }
}

class Int extends IExpr {
  private int num;
  Int(int num) { this.num = num; }

  int   eval(Memory mem) { return num; }
  String show() { return Integer.toString(num); }

  Code compileTo(Reg reg, Code next) {
    return new Immed(reg, num, next);
  }
}

class Plus extends IExpr {
  private IExpr l, r;
  Plus(IExpr l, IExpr r) { this.l = l; this.r = r; }

  int    eval(Memory mem) { return l.eval(mem) + r.eval(mem); }
  String show() { return "(" + l.show() + " + " + r.show() + ")"; }

  Code compileTo(Reg reg, Code next) {
    Reg tmp = new Reg();
    return l.compileTo(tmp,
           r.compileTo(reg,
           new Op(reg, tmp, '+', reg, next)));
  }
}

class Minus extends IExpr {
  private IExpr l, r;
  Minus(IExpr l, IExpr r) { this.l = l; this.r = r; }

  int    eval(Memory mem) { return l.eval(mem) - r.eval(mem); }
  String show() { return "(" + l.show() + " - " + r.show() + ")"; }

  Code compileTo(Reg reg, Code next) {
    Reg tmp = new Reg();
    return l.compileTo(tmp,
           r.compileTo(reg,
           new Op(reg, tmp, '-', reg, next)));
  }
}

class Mult extends IExpr {
  private IExpr l, r;
  Mult(IExpr l, IExpr r) { this.l = l; this.r = r; }

  int    eval(Memory mem) { return l.eval(mem) * r.eval(mem); }
  String show() { return "(" + l.show() + " * " + r.show() + ")"; }

  Code compileTo(Reg reg, Code next) {
    Reg tmp = new Reg();
    return l.compileTo(tmp,
           r.compileTo(reg,
           new Op(reg, tmp, '*', reg, next)));
  }
}

class Mod extends IExpr {
  private IExpr l, r;
  Mod(IExpr l, IExpr r) { this.l = l; this.r = r; }

  int    eval(Memory mem) { return l.eval(mem) % r.eval(mem); }
  String show() { return "(" + l.show() + " % " + r.show() + ")"; }

  Code compileTo(Reg reg, Code next) {
    Reg tmp = new Reg();
    return l.compileTo(tmp,
           r.compileTo(reg,
           new Op(reg, tmp, '%', reg, next)));
  }
  
}

class Half extends IExpr {
    private IExpr p;
    Half(IExpr p) { this.p = p; }
    
    /**
     * halving the return value of p.eval
     */
    int eval(Memory mem) { 
       return p.eval(mem) / 2;
    }
    /**
     * display the half construct as a function call
     */
    String show() { return "half (" + p.show() + ")"; }
    /**
     * call compileTo method passing in abstract syntax
     * structure of target language
     */
    Code compileTo(Reg reg, Code next) {
        Reg lit = new Reg();

        return p.compileTo(reg,
                           new Immed(lit, 2, 
                           new Op(reg, reg, '/', lit, next)));
    }
}

//____________________________________________________________________________
// BExpr ::= IExpr < IExpr
//        |  IExpr == IExpr

abstract class BExpr {
  abstract boolean eval(Memory mem);
  abstract String  show();
  abstract Code compileTo(Reg reg, Code next);
}

class Bool extends BExpr {
  private int flag;
  Bool(int flag) { this.flag = flag; }

  boolean eval(Memory mem) { 
     if( 0 == flag) return false;
    return true;
  }

  String show() { 
    if( 0 == flag) {
        return "false";
    }
    return "true";
  }

  Code compileTo(Reg reg, Code next) {
    return new Immed(reg, flag, next);
  }
}

class LTE extends BExpr {
  private IExpr l, r;
  LTE(IExpr l, IExpr r) { this.l = l; this.r = r; }

  /**
   * evaluating the expression in the source language
   */
  boolean eval(Memory mem) { return l.eval(mem) <= r.eval(mem); }
  /**
   * printing the expression as a binary operator
   */
  String show()  { return "(" + l.show() + " <= " + r.show() + ")"; }

  /**
   * Recycling existing target instructions to emulate the behavior
   * of a less than or equal operator
   */
  Code compileTo(Reg reg, Code next) {
    Reg temp = new Reg();
    Reg mid = new Reg();
    return l.compileTo(reg,
           r.compileTo(temp,
               new Op(mid, reg, '-', temp, 
               new Immed(temp, 1,
                   new Op(reg, mid, '<', temp, next)))));
 }
}

class LT extends BExpr {
    private IExpr l, r;
    LT(IExpr l, IExpr r) { this.l = l; this.r = r; }

    boolean eval(Memory mem) { return l.eval(mem) < r.eval(mem); }
    String show()  { return "(" + l.show() + " < " + r.show() + ")"; }

    Code compileTo(Reg reg, Code next) {
      Reg tmp = new Reg();
      return l.compileTo(tmp,
             r.compileTo(reg,
             new Op(reg, tmp, '<', reg, next)));
    }

}

class EqEq extends BExpr {
  private IExpr l, r;
  EqEq(IExpr l, IExpr r) { this.l = l; this.r = r; }

  boolean eval(Memory mem) { return l.eval(mem) == r.eval(mem); }
  String show()  { return "(" + l.show() + " == " + r.show() + ")"; }

  Code compileTo(Reg reg, Code next) {
    Reg tmp = new Reg();
    return l.compileTo(tmp,
           r.compileTo(reg,
           new Op(reg, tmp, '=', reg, next)));
  }
}

class Not extends BExpr {
    private BExpr e;
    Not(BExpr e) { this.e = e; }
    
    /**
     * negate the return value of eval method
     */
    boolean eval(Memory mem) { return !e.eval(mem); }
    /**
     * display the not construct as a function call
     */
    String show() { return "not (" + e.show() + ")"; }
    /**
     * call compileTo method passing in null for second arg since
     * not is a unary operator
     */
    Code compileTo(Reg reg, Code next) {
        return e.compileTo(reg, new Neg(reg, next));
    }
}

class Even extends BExpr {
    private IExpr v;
    Even(IExpr v) { this.v = v; }
    
    /**
     * checking if the value of v is even with mod operator
     */
    boolean eval(Memory mem) { 
        if( 0 == v.eval(mem) % 2 ) {
            return true;
        }
        return false;
    }
    /**
     * display the not construct as a function call
     */
    String show() { return "even (" + v.show() + ")"; }
    /**
     * call compileTo method passing in abstract syntax
     * structure of target language
     */
    Code compileTo(Reg reg, Code next) {
        Reg lit = new Reg();
        Reg temp = new Reg();

        return v.compileTo(reg,
                           new Immed(lit, 2, 
                           new Op(reg, reg, '%', lit, 
                           new Immed(lit, 0,
                           new Op(reg, reg, '=', lit, next))))); 
    }
}

//____________________________________________________________________________
// Stmt  ::= Seq Stmt Stmt
//        |  Var := IExpr
//        |  While BExpr Stmt
//        |  If BExpr Stmt Stmt
//        |  Print IExpr

abstract class Stmt {
  abstract void exec(Memory mem);
  abstract Code compile(Program prog, Code next);
  abstract void print(int ind);

  static void indent(int ind) {
    for (int i=0; i<ind; i++) {
      System.out.print(" ");
    }
  }
}

class Seq extends Stmt {
  private Stmt l, r;
  Seq(Stmt l, Stmt r) { this.l = l; this.r = r; }

  void exec(Memory mem) {
    l.exec(mem);
    r.exec(mem);
  }

  Code compile(Program prog, Code next) {
    return l.compile(prog, r.compile(prog, next));
  }

  void print(int ind) {
    l.print(ind);
    r.print(ind);
  }
}

class Assign extends Stmt {
  private String lhs;
  private IExpr  rhs;
  Assign(String lhs, IExpr rhs) {
    this.lhs = lhs; this.rhs = rhs;
  }

  void exec(Memory mem) {
    mem.store(lhs, rhs.eval(mem));
  }

  Code compile(Program prog, Code next) {
    Reg tmp = new Reg();
    return rhs.compileTo(tmp, new Store(lhs, tmp, next));
  }

  void print(int ind) {
    indent(ind);
    System.out.println(lhs + " = " + rhs.show() + ";");
  }
}

class While extends Stmt {
  private BExpr test;
  private Stmt  body;
  While(BExpr test, Stmt body) {
    this.test = test; this.body = body;
  }

  void exec(Memory mem) {
    while (test.eval(mem)) {
      body.exec(mem);
    }
  }

  Code compile(Program prog, Code next) {
    Block head = prog.block();
    Code  loop = new Goto(head);
    Reg   tmp  = new Reg();
    head.set(test.compileTo(tmp,
             new Cond(tmp,
                      prog.block(body.compile(prog, loop)),
                      prog.block(next))));
    return loop;
  }

  void print(int ind) {
    indent(ind);
    System.out.println("while (" + test.show() + ") {");
    body.print(ind+2);
    indent(ind);
    System.out.println("}");
  }
}

class If extends Stmt {
  private BExpr test;
  private Stmt  t, f;
  If(BExpr test, Stmt t, Stmt f) {
    this.test = test; this.t = t; this.f = f;
  }

  void exec(Memory mem) {
    if (test.eval(mem)) {
      t.exec(mem);
    } else {
      f.exec(mem);
    }
  }

  Code compile(Program prog, Code next) {
    Reg  tmp = new Reg();
    Goto got = new Goto(prog.block(next));
    return test.compileTo(tmp,
           new Cond(tmp,
                    prog.block(t.compile(prog, got)),
                    prog.block(f.compile(prog, got))));
  }

  void print(int ind) {
    indent(ind);
    System.out.println("if (" + test.show() + ") {");
    t.print(ind+2);
    indent(ind);
    System.out.println("} else {");
    f.print(ind+2);
    indent(ind);
    System.out.println("}");
  }
}

class Print extends Stmt {
  private IExpr exp;
  Print(IExpr exp) { this.exp = exp; }

  void exec(Memory mem) {
    System.out.println("Output: " + exp.eval(mem));
  }
  
  Code compile(Program prog, Code next) {
    Reg tmp = new Reg();
    return exp.compileTo(tmp, new PCode(tmp, next));
  }

  void print(int ind) {
    indent(ind);
    System.out.println("print " + exp.show() + ";");
  }
}
