import java.util.*;
public class bubbleSort{

    public static void main(String[] args) {   
    //0-5
    int[] num = {5,3,2,5,6,7};
    System.out.println(Arrays.toString(num));
    System.out.println("This is the sorted array through bubble Sort: " + Arrays.toString(sort(num)));
       
    }


    public static int[]  sort(int[] num) {
    int temp = 0;
        for(int pass = 0; pass < 5; pass++){
        //moves the biggest int to the right but one passes the array once.
            for(int i = 0; i<5; i++){
                if(num[i]>num[i+1]){
                    temp = num[i];
                    num[i] = num[i+1];
                    num[i+1] = temp;
                }
            }
        
        }
        return num;   
    }    
}