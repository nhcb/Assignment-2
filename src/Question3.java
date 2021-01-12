
import java.util.ArrayList;
import java.util.Arrays;

/** Given an array A of n distinct integers, rearrange the elements of array A
 *  such that to obtain a new array, namely B. This new array B has exactly the
 *  same elements from that of A and represents a permutation of the elements of
 *  A. Array B is a special permutation of the elements of A once it is a rearrangement
 *  of A that minimizes the sum of the absolute values of differences between its adjacent
 *  elements among all permutations of elements of A. In other words, array B is such that
 *  the sum of |B[i] - B[i-1]| for all 0 < i < n is minimal. Of course, array B can be obtained
 *  from array A by a sequence of swaps. This program returns the minimum number of swaps that 
 *  should be performed in order to rearrange the elements of array A such that to obtain array B.
 * 
 * @author Nicky
 *
 */
public class Question3 {
	

	public static void main(String[] args) throws Exception {
		String fileLocation = args[0];
		ArrayList<String> digits = Question1.readFile(fileLocation);
		// Elegant lambda expression to obtain integers in second row
		int[] arr = Arrays.stream(digits.get(1).split(" "))
				.mapToInt(i -> Integer.valueOf(i))
				.toArray();
		int[] copy = Arrays.copyOf(arr, arr.length);
		// As answers to this questions can be either so that the array is in
		// ascending or descending order, calculate both.
		
		// Array in ascending order
		int result1 = minimumSwaps(arr,true);
		// Array in descending order
		int result2 = minimumSwaps(copy,false);
		// Print minimum of both 
		System.out.println(Math.min(result1, result2));
		
	}
	
	/** Method to print out minimum amount of swaps needed to put input array in order
	 * 
	 * @param arr input array that contains digits that are to be swapped
	 * @return the minimum amount of swaps needed to put input array in order from small to big.
	 */
	public static int minimumSwaps(int[] arr, boolean asc) {
        int n = arr.length;
        int countSwaps=0;
        // If asc is true, we wish to sort the arr in ascending order and if false descending order.
        // Traverse the array to find elements that are smaller/bigger than all other elements (minimum/maximum)
        // Put these elements at the front, now we know that the element at the front is the minimum
        // hence we can repeat the process on a smaller list as we don't have to traverse the first element
		for(int i = 0; i < n; i ++) {
			// Assume minimum value is arr[i]
	        int index = i;
	        // Traverse the array to find next minimum/maximum element
	        for(int j = i+1; j < n; j++) {
	        	if(compare(arr[j],arr[index],asc)) {
	        		index = j; // Found a smaller/bigger element, hence update index
	        	}
	        	
	        }
	        // Only if current iteration is smaller than index will we swap numbers
	        if(i < index) {
		        // Swap elements
	        	//System.out.println("Swapped " + arr[i] + " to " + arr[index]);
		        int temp = arr[i];
		        arr[i] = arr[index];
		        arr[index] = temp;
		        countSwaps++;
	        }
		}
		return countSwaps;
	}
	
	public static boolean compare(int a, int b, boolean asc) {
		if(asc) {
			return a < b;
		}
		else {
			return a > b;
		}
	}
	
}
