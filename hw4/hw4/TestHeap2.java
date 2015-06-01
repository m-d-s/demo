class TestHeap2 {
  static final int S = 100;
  static final int N = 8;

  public static void main(String[] args) {
    for(int i = 0; i < 13; ++i) {
         System.out.print("i = " + i);
         test(i);
         System.out.println();
    }  

  }

  public static void test(int N) {
    Heap h = Heap.make(S);
    for (int i=0; i<N; i++) {
      System.out.println("Allocating object " + i
                       + " at address " + h.alloc(8));
    }
    h.dump();
    System.out.println("Free space remaining = " + h.freeSpace());

  }
}
