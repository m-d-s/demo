class Main {
  public static void main(String[] args) {
    int [] first = {1,2,3,4,5,6,7,8,9,10},
           second = {2,10,22,42,17,88,34},
           third = {-100,66,-4000,55555,12345,-88888},
           fourth = {1,2,3,5,7,11,13,17,19,23,29,31,37},
           test = {3,1,4,2};
//    mergeSort(first, 0, first.length);
//    display(first, "first sorted");
    mergeSort(test, 0, test.length); 
    display(test, "second sorted");
//    mergeSort(third, 0, third.length);
//    display(third, "third sorted"); 
//    mergeSort(fourth, 0, fourth.length);
//    display(fourth, "fourth sorted");
 } 

  public static void merge(int []A, int p, int q, int r) {
      int  n1 = q - p,
           n2 = r - q,
           i,
           j,
           k;
System.out.println("p= " +p+ " r= " +r+ " q= " +q+ " n1= " +n1+ " n2= " +n2+ " A.length=" + A.length);

      int[] L =  new int[n1+1],
            R =  new int[n2+1];
      // copy the left partition into L
      for(i = 0; i < n1; ++i) {
          L[i] = A[p + i];
      }
      L[i] = Integer.MAX_VALUE;
      // copy the right partition into R
      for(j = 0; j < n2; ++j) {
          R[j] = A[q + j];
      }
      R[j] = Integer.MAX_VALUE;
      i = 0;
      j = 0;
      // Sort the subarrays back into A
      for(k = p; k < r; ++k) {
          if(L[i] <= R[j]) {
              A[k] = L[i];
              i += 1;
          }else {
              A[k] = R[j];
              j += 1;
          } 
      }

display(L, "L");
display(R, "R");
display(A, "A"); 

  }

  public static void mergeSort(int []A, int p, int r) {
      int q;
      if(p < r) {
          q = (int) Math.floor((p+r)/2);
          mergeSort(A, p, q);
          mergeSort(A, q + 1, r);
          merge(A, p, q, r);
      }
  }

  public static void display(int[] A, String msg) {
      int length = A.length;
      System.out.println(msg);
      for(int i = 0; i < length; ++i) {
          System.out.print(A[i] + " ");
      }
      System.out.println();
  }
}


