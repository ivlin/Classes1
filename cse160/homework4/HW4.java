public class HW4{

    //Array-2 post4
    /**
     *Given a non-empty array of ints, return a new array containing the elements from the original array that come after the last 4 in the original array. The original array will contain at least one 4. Note that it is valid in java to create an array of length 0.
     */
    public int[] post4(int[] nums) {
        int startInd=nums.length-1;
        for (; startInd>=0&&nums[startInd]!=4; startInd--){
        }
        int[] newNums=new int[nums.length-startInd-1];
        for (int i = 0;i<newNums.length;i++){
            newNums[i]=nums[startInd+1+i];
        }
        return newNums;
    }

    //Array-2 notAlone
    /**
     *We'll say that an element in an array is "alone" if there are values before and after it, and those values are different from it. Return a version of the given array where every instance of the given value which is alone is replaced by whichever value to its left or right is larger.
     */
    public int[] notAlone(int[] nums, int val) {
        int before, after;
        for (int i=1;i<nums.length-1;i++){
            if (nums[i]==val){
                before=nums[i-1];
                after=nums[i+1];
                if (!(before == nums[i] || after == nums[i])){
                    if (before>after){
                        nums[i]=before;
                    }
                    else{
                        nums[i]=after;
                    }
                }
            }
        }
        return nums;
    }

    //Array-2 zeroFront
    /**
     *Return an array that contains the exact same numbers as the given array, but rearranged so that all the zeros are grouped at the start of the array. The order of the non-zero numbers does not matter. So {1, 0, 0, 1} becomes {0 ,0, 1, 1}. You may modify and return the given array or make a new array.
     */
    public int[] zeroFront(int[] nums) {
        int partition=0;
        for (int cur=0;cur<nums.length;cur++){
            if (nums[cur]==0){
                nums[cur]=nums[partition];
                nums[partition]=0;
                partition++;
            }
        }
        return nums;
    }
    //Array-2 withoutTen
    /**
     *Return a version of the given array where all the 10's have been removed. The remaining elements should shift left towards the start of the array as needed, and the empty spaces a the end of the array should be 0. So {1, 10, 10, 2} yields {1, 2, 0, 0}. You may modify and return the given array or make a new array.
     */
    public int[] withoutTen(int[] nums) {
        int partition=0;
        for (int cur=0;cur<nums.length;cur++){
            if (nums[cur]!=10){
                nums[partition]=nums[cur];
                partition++;
            }
        }
        for (int cur=partition;cur<nums.length;cur++){
            nums[cur]=0;
        }
        return nums;
    }

    //Array-2 zeroMax
    /**
     *Return a version of the given array where each zero value in the array is replaced by the largest odd value to the right of the zero in the array. If there is no odd value to the right of the zero, leave the zero as a zero.
     */
    public int[] zeroMax(int[] nums) {
        int highestOdd;
        for (int i = 0; i < nums.length; i++){
            if (nums[i]==0){
                highestOdd=0;
                for (int nextOdd=i;nextOdd<nums.length;nextOdd++){
                    if (nums[nextOdd]%2==1 && nums[nextOdd]>highestOdd){
                        highestOdd=nums[nextOdd];
                    }
                }
                nums[i]=highestOdd;
            }
        } 
        return nums;
    }

    //Array-2 evenOdd
    /**
     *Return an array that contains the exact same numbers as the given array, but rearranged so that all the even numbers come before all the odd numbers. Other than that, the numbers can be in any order. You may modify and return the given array, or make a new array.
     */
    public int[] evenOdd(int[] nums) {
        int partition=0;
        int temp;
        for (int i=0;i<nums.length;i++){
            if (nums[i]%2==0){
                temp=nums[partition];
                nums[partition]=nums[i];
                nums[i]=temp;
                partition++;
            }
        }
        return nums;
    }

    //Array-2 fizzBuzz
    /**
     *This is slightly more difficult version of the famous FizzBuzz problem which is sometimes given as a first problem for job interviews. (See also: FizzBuzz Code.) Consider the series of numbers beginning at start and running up to but not including end, so for example start=1 and end=5 gives the series 1, 2, 3, 4. Return a new String[] array containing the string form of these numbers, except for multiples of 3, use "Fizz" instead of the number, for multiples of 5 use "Buzz", and for multiples of both 3 and 5 use "FizzBuzz". In Java, String.valueOf(xxx) will make the String form of an int or other type. This version is a little more complicated than the usual version since you have to allocate and index into an array instead of just printing, and we vary the start/end instead of just always doing 1..100.
     */
    public String[] fizzBuzz(int start, int end) {
        String[] nums=new String[end-start];
        String str;
        for (int i=0;i<nums.length;i++){
            str="";
            if ((start+i)%3==0){
                str+="Fizz";
            }
            if ((start+i)%5==0){
                str+="Buzz";
            }
            if ((start+i)%3!=0&&(start+i)%5!=0){
                str+=String.valueOf(start+i);
            }
            nums[i]=str;
        }
        return nums;
    }

    //Array-3 seriesUp
    /**
     *Given n>=0, create an array with the pattern {1,    1, 2,    1, 2, 3,   ... 1, 2, 3 .. n} (spaces added to show the grouping). Note that the length of the array will be 1 + 2 + 3 ... + n, which is known to sum to exactly n*(n + 1)/2.
     */
    public int[] seriesUp(int n) {
        int[] nums= new int[n*(n+1)/2];
        for (int i=1; i<=n; i+=1){
            for (int x=1; x<=i; x++){
                nums[((i-1)*i/2)+x-1]=x;
            }
        }
        return nums;
    }

    //Array-3 mirrorCounter
    /**
     *We'll say that a "mirror" section in an array is a group of contiguous elements such that somewhere in the array, the same group appears in reverse order. For example, the largest mirror section in {1, 2, 3, 8, 9, 3, 2, 1} is length 3 (the {1, 2, 3} part). Return the size of the largest mirror section found in the given array.
     */
    public int maxMirror(int[] nums) {
        int maxMirror = 0;
        int mirrorCounter;
        for (int fore=0; fore<nums.length; fore++){
            for (int back=nums.length-1; back>=0; back--){
                mirrorCounter=0;
                for (;fore+mirrorCounter < nums.length && back-mirrorCounter>=0 && 
                nums[fore+mirrorCounter]==nums[back-mirrorCounter];mirrorCounter++){
                }
                if (mirrorCounter > maxMirror){
                    maxMirror=mirrorCounter;
                }
            }
        }
        return maxMirror;
    }

    //Array-3 countClumps
    /**
     * Say that a "clump" in an array is a series of 2 or more adjacent elements of the same value. Return the number of clumps in the given array.
     */
    public int countClumps(int[] nums) {
        int clumpCount=0;
        int curClump;
        for (int clumpStart=0; clumpStart<nums.length-1; ){
            for (curClump=0; clumpStart+curClump<nums.length && nums[clumpStart]==nums[clumpStart+curClump]; curClump++){
            }
            if (curClump>1){
                clumpCount++;
            }
            clumpStart=clumpStart+curClump;
        }
        return clumpCount;
    }
}