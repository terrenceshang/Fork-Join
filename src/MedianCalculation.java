/**
*<h1>MedianCalculation!</h1>
*A MedianCalculation.java that works out the data smoothed array using parallelism.
*@author Zenan Shang
*@version 1.1
*@since 19-08-21
*/

import java.util.Arrays;
import java.util.concurrent.*;

public class MedianCalculation extends RecursiveAction{
/**
*stores the lowest point the array we're working with
*/	
   int low; 
/**
*stores highest point the array we're working with
*/   
   int high; 
/**
*stores filterSize for data smoothing
*/
   int size; 
   
/**
*stores the array we're working with
*/   
   Float[] arr; 
   
/**
*stores the final answer of the data smoothing
*/
   Float[] ans;
   
/**
*holds Sequential cutoff for the program
*/
	final static int SEQUENTIAL_CUTOFF = 60000;

/**
*Constructor for MedianCalculation
*@param low lowest point the array we're working with
*@param high highest point the array we're working with
*@param size filterSize for data smoothing
*@param ans the final answer of the data smoothing
*@param arr the array we're working with
*/
	public MedianCalculation (Float[] a, int l, int h, int f){
      low = l; arr = a; high = h; size = f;
      ans = a;
	}
  
/**
compute method to get the result of the problem using parallelism
@param tempArray the tempoary I used to store the filter size data
*/
	protected void compute() {
		
		if ((high - low) < SEQUENTIAL_CUTOFF) {
         Float[] tempArray = new Float[size];
         for (int i = 0; i < high; i++) { //for loop for the low to high of the array
            int temp = i;
            
            if (high - size == temp-1) //if the pointer is at the border
               break;
            else {
               for (int j = 0; j < size; j++) { //creating the temporary array for the filtering
                  tempArray[j] = arr[temp];
                  temp++;
               }
               temp = i + (size-1)/2;
               Arrays.sort(tempArray);
               float midPoint = tempArray[tempArray.length/2];// finding the midpoint
               
               if (temp < high - (size-1)/2) 
                  ans[temp] = midPoint; //writing the midpoint to the output array
               else
                  break;
            }
         }
         
         
      } else {
      //Parallelism codes for fork join method
         int mid = (high + low) / 2;  
			MedianCalculation left = new MedianCalculation(arr, low, mid, size);
			MedianCalculation right = new MedianCalculation(arr, mid, high, size);
         left.fork();
         right.compute();
         left.join();			
		}
   }
}


/*    This is the version 1.0 which I decided to stop using because it is not running
      Float[] tempArray = new Float[size];
		
		   for (int i = low; i < high; i++) { // for loop for that part of the array in the parallel code
			   int count = i; int temp = i;
			   if ( ((size-1)/2 <= i) && (i < high - (size-1)/2) ){
				   for (int j = 0; j < size; j++) {
                  if (count < arr.length) {
					      tempArray[j] = arr[count]; // Creating a temporary array to store the filter size samples for time smoothing
		   		      count ++;
                  }
               }
               temp += (size - 1)/2;
			   	if (temp < high - (size-1)/2) { //finding median of the tempArray
                  Arrays.sort(tempArray);
                  ans[temp] = tempArray[(tempArray.length)/2];
               } else 
                  break;   
		   	} 
		   }
*/