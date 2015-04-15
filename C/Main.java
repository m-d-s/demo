class Main {
  public static void main(String[] args) {
    /**
    Stmt s
     = new Seq(new Assign("t", new Int(0)),
       new Seq(new Assign("i", new Int(0)),
       new Seq(new While(new LT(new Var("i"), new Int(11)),
                         new Seq(new Assign("t", new Plus(new Var("t"), new Var("i"))),
                                 new Assign("i", new Plus(new Var("i"), new Int(1))))),
               new Print(new Var("t")))));
    */
    Stmt s = notTest();

    System.out.println("Complete program is:");
    s.print(4);

    System.out.println("Running on an empty memory:");
    Memory mem = new Memory();
    s.exec(mem);

    System.out.println("Compiling:");
    Program p     = new Program();
    Block   entry = p.block(s.compile(p, new Stop()));
    System.out.println("Entry point is at " + entry);
    p.show();

    System.out.println("Running on an empty memory:");
    mem      = new Memory();
    Block pc = entry;
    while (pc!=null)  {
      pc = pc.code().run(mem);
    } 

    System.out.println("Done!");
  }

  public static Stmt multTest() {
    Stmt s
       = new Seq(new Assign("t", new Int(1)),
         new Seq(new Assign("i", new Int(1)),
         new Seq(new While(new LT(new Var("i"), new Int(5)),
                           new Seq(new Assign("t", new Mult(new Var("t"), new Var("i"))),
                                   new Assign("i", new Plus(new Var("i"), new Int(1))))),
                 new Print(new Var("t")))));
    return s;
  }

  public static Stmt notTest() {
    Stmt s
       = new If(new Not( new Not(new EqEq(new Int(1), new Int(2)))),
                new Print(new Int(256)),
                new Print(new Int(1024)));
    return s;
  }

  public static Stmt lTETest() {
      Stmt s
         = new If(new Not( new LTE(new Int(1), new Int(1))),
                  new Print(new Int(0)),
                  new Print(new Int(42)));
      return s;
  }

  public static Stmt evenTest() {
    Stmt s
       = new If(new Even(new Int(2)),
                new Print(new Int(128)),
                new Print(new Int(256)));
    return s;

  }

  public static Stmt halfTest() {
    Stmt s
       = new Seq(new Assign("i", new Int(3)),
         new Seq(new Assign("k", new Int(20)),
         new While( new LT(new Int(4), new Var("k")),
                    new Seq(new Assign("k", new Half(new Var("k"))),
                    new Seq(new Assign("i", new Minus(new Var("i"), new Int(1))),
                            new Print(new Var("i")))))));
      return s;                            
  }
  
  public static Stmt q2() {
    Stmt s
       = new Seq( new Assign("t", new Int(0)),
         new Seq( new Assign("x", new Int(6)),
         new Seq( new Assign("y", new Int(7)),
         new Seq( new While( new LT( new Int(0), new Var("x")),
                  new Seq( new If( new Not( new Even( new Var("x"))),
                           new Assign( "t", new Plus( new Var("t"), new Var("y"))),
                           new Assign( "t", new Var("t"))),
                  new Seq( new Assign( "y", new Plus( new Var("y"), new Var("y"))),
                  new Assign( "x", new  Half( new Var( "x")))))),
         new Print( new Var("t"))))));
    return s;          
  }
}


