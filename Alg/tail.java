class Main {
  public static void main(String[] args) {
      int num = 10, fibNum;
      fibNum = recFib(num);
      System.out.print("RecFib: " + fibNum + "\n");
      fibNum = tailRecFib(0,1, num);
      System.out.print("TailRecFib: " + fibNum + "\n");
      
  } 

  public static int tailRecFib(int x, int y, int n) {
       if(n < 2) return y;
       return tailRecFib(y, x+y, n-1);
  }

  public static int recFib(int n) {
      if(n < 2) return n;
      return recFib(n-2) + recFib(n-1);
  }
}


