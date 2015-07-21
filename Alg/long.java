class Main {
    public static void main(String[] args) {
    int[] A = {0,1,1,1,0,0,0,1,1,1,1,0,0,1,1,1,1,1};
    System.out.println(containsMConseqOnes(A,6));
    } 

    /** Returns the starting index of (m > 0) consecutive ones
        if such a string exists. If one does not, -1 is returned.
     */
    public static int containsMConseqOnes(int []A, int m) {
        boolean hitOne = false;
        int  oneIdx = 0, counter = 0, length = A.length;

        for(int i = 0; i < length; ++i) {
            if(A[i] == 1) {
                if(hitOne == false) {
                    hitOne = true;
                    oneIdx = i;
                }
                ++counter;
            } else {
                hitOne = false;
                counter = 0;
                oneIdx = 0;
            }   
            if(m <= counter) {
                return oneIdx;
            }
        }
        return -1;
    }
}


