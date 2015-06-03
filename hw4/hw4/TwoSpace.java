class TwoSpace extends Heap {
  protected int[] toSpace;        // Pointer to toSpace

  /** Construct a new TwoSpace collected heap of the given size.
   */
  public TwoSpace(int size) {
     super(size);
     toSpace = new int[size];     // Create the toSpace heap
  }

  /** Run the garbage collector.
   */
  public void garbageCollect() {
    // Reset the heap allocation pointer to the start of toSpace:
    hp = 0;

    // Forward any objects that are pointed to by a, b, c, or d.
    // These are the "roots" for our garbage collection.
    a = forward(a);
    b = forward(b);
    c = forward(c);
    d = forward(d);

    // Now scavenge the objects that we have already copied over
    // into toSpace and look for reachable objects in the original
    // heap that should also be forwarded into toSpace:
    int toVisit = 0;
    while (toVisit < hp) {
      toVisit += scavenge(toVisit);
    }

    // Now that we have copied all the non-garbage over into toSpace,
    // swap the two heap portions so that further allocation can
    // continue in the toSpace that we've just built without any of
    // the garbage that was lying around in the original heap.  Of
    // course we'll keep the original heap structure around too so
    // that it can be used as our toSpace on the next call to the
    // garbage collector.
    int[] newheap = toSpace;
    toSpace       = heap;
    heap          = newheap;
  }

  /** Forward a reachable object from the current heap into toSpace.
   */
  private int forward(int obj) {
    // If the object is not a pointer, it does not need to be forwarded    
    if(obj >= 0 ) { return obj; } 
    // Retrieve the length of the object by indexing into the heap
    int length = heap[size + obj];    
    // If the length is negative, this object has already been forwarded
    if( length < 0 ) {
        return length;
    }
    //copy over the object from the heap to the copy space
    for(int i = 0; i <= length; ++i) {
        toSpace[hp++] = heap[size + obj + i];
    }
    // set the index in the heap where the length was retrieved to point
    // to the address of the object in the copy space
    return heap[size + obj] = -((size - hp) + length + 1);
  }

  /** Scavenge an object in toSpace for pointers to reachable objects
   *  that are still in the current heap.  Return the total number of
   *  words that are used to represent the object, which is just the
   *  total number of fields plus one (for the length field at the
   *  start of the object).
   */
  private int scavenge(int obj) {
    int len = toSpace[obj];
    // Scan the fields in this object, using forward on
    // any pointer fields that we find to make sure the
    // objects that they refer to are copied into toSpace.
    for(int i = 1; i <= len; ++i) {
        if(toSpace[obj + i] < 0) {
           toSpace[obj + i] = forward(toSpace[obj + i]);
        }
    }
    return 1+len;
  }
}
