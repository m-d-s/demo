class TestHeap8 {
  static final int S = 10;

  public static void main(String[] args) {
    Heap h = Heap.make(S);
    h.a = h.alloc(3);
    h.b = h.alloc(2);
    int t = h.alloc(2);
    h.store(h.a, 1, h.b);
    h.store(h.a, 2, h.a);
    h.store(h.a, 3,   t);
    h.store(  t, 1, h.a);
    h.store(  t, 2,   t);
    h.store(h.b, 1, h.a);
    h.store(h.b, 2, h.b);

    System.out.println("Before garbage collection;");
    h.dump();
    System.out.println("Free space remaining = " + h.freeSpace());

    h.garbageCollect();

    System.out.println("\nAfter garbage collection;");
    h.dump();
    System.out.println("Free space remaining = " + h.freeSpace());
  }
}
