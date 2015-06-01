class TestHeap6 {
  static final int S = 100;

  public static void main(String[] args) {
    Heap h = TwoSpace.make(S);
    h.alloc(3);
    h.a = h.alloc(9);
    System.out.println("\n" + h.a + "\n");
    h.alloc(3);
    h.store(h.a, 1, h.alloc(5));
    h.alloc(5);
    h.store(h.a, 2, h.alloc(4));
    h.alloc(2);
    h.alloc(60);
    System.out.println("Free space remaining = " + h.freeSpace());
    h.dump();

    h.store(h.a, 3, h.alloc(4));

    h.dump();
    System.out.println("Free space remaining = " + h.freeSpace());

    h.garbageCollect();

    h.dump();
    System.out.println("Free space remaining = " + h.freeSpace());
  }

  
}
