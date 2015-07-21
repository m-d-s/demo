class Main {
  public static void main(String[] args) {
    int [] first = {1,2,3,4,5,6,7,8,9,10},
           second = {2,10,22,42,17,88,34},
           third = {-100,66,-4000,55555,12345,-88888},
           fourth = {1,2,3,5,7,11,13,17,19,23,29,31,37};
    elemAbsDiffDivPosAbsDiff(first);
    elemAbsDiffDivPosAbsDiff(second);
    elemAbsDiffDivPosAbsDiff(third);
    elemAbsDiffDivPosAbsDiff(fourth);
  } 

  public static void elemAbsDiffDivPosAbsDiff(int []A) {
    int iP = 0, jP = 1, m = 0, length = A.length, x, y, z;
    printArr(A, length);
    for(int i = 0; i < length; ++i) {
        for(int j = 0; j < length; ++j) {
            if(i != j) {
                x = A[i] - A[j];
                y = i - j;

                if(x < 0) {
                    x *= -1;
                }
 
               if(y < 0) {
                    y *= -1;
                }
 
                z = x/y;
                System.out.print("\ni = " + i +" j = " + j + " m = " + m + " z =" + z);
                if(z > m) {
                    m = z;
                    iP = i;
                    jP = j;
                }                 
            }
        }
    } 
    System.out.print("\n\niP = " + iP + "\njP = " + jP);
  }

  public static void printArr(int []A, int length) {
    System.out.println();
    for(int i = 0; i < length; ++i) {
        System.out.print(A[i] + " ");
    }
    System.out.println();
  }
}


