import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class Intersection {
    int [] A,
           B;
    /** Using an ArrayList here because it makes testing easier. Could have
        made this work using an array but it would have taken more time and
        added complexity.
     */
    ArrayList<Integer> C;
    int lenA,
        lenB;

    public Intersection(int[] A, int[] B) {
        this.A = A;
        this.B = B;
        lenA = A.length;
        lenB = B.length;
        C = new ArrayList<Integer>();
        this.execute();    
    }

    public void execute() {
        Arrays.sort(A);
        Arrays.sort(B);
        for(int i = 0, j = 0; i < lenA && j < lenB; ) {
            if(A[i] < B[j]) {
                ++i;
            } else if (A[i] > B[j]) {
                ++j;
            } else {
                C.add(A[i]);
                while( i < (lenA - 1) && A[i+1] == A[i] ) {
                    ++i;
                }
                 while( j < (lenB - 1) && B[j+1] == B[j] ) {
                    ++i;
                }
                ++i;
                ++j;
            }
        }
        this.display();
    }

    public void display() {
        Iterator<Integer> iter = C.iterator();
        Integer num;
        System.out.println("Intersection of A and B: ");
        while(iter.hasNext()) {
            num = iter.next();
            System.out.print(num + " ");
        }
        System.out.println();
    }

    

} 
