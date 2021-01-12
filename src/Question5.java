
import java.util.ArrayList;
import java.util.Arrays;
/** Counting inversions
 *  Algorithm from https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/05DivideAndConquerI.pdf
 * 
 * @author Nicky
 *
 */
public class Question5 {

	public static void main(String[] args) throws Exception {
		String fileLocation = args[0];
		// Read file
		ArrayList<String> digits = Question1.readFile(fileLocation);
		// Elegant lambda expression to obtain integers in second row
		int[] arr = Arrays.stream(digits.get(1).split(" "))
				.mapToInt(i -> Integer.valueOf(i))
				.toArray();
		//int[] arr = {10,19,1,14,99,64,99,54,99,64,24,9,24,1,6,50,2,8,31,25,18,9,24,4,15,30,1,9,11,5,120,119,21,104,909,614,199,524,199,6,12,21,1000,140,992,643,39,34,49,64,475};
		System.out.println(mergeSortCount(arr,0,arr.length-1));
	}
	/** Rewritten method from Question 2, that takes in an array and can be used recursively. 
	 *  Which in contrary to question 2 is done
	 *  It merges and sorts the input array, and returns the bad pairs (swaps) counts
	 * 
	 * @param arr input integer array where bad pairs are to be found
	 * @param left integer where the left side of the array to be splitted starts
	 * @param mid integer where left side of array to be splitted ends and right side starts, after adding 1 
	 * @param right integer where right side of array to be splitted ends, after adding 1
	 * @return the amount of swaps
	 */
	public static int mergeCount(int[] arr, int left, int mid, int right) {
		
		// Get a copy of left range of input array
		int[] leftArr = Arrays.copyOfRange(arr,left,mid + 1);
		// Get a copy of right range of input array
		int[] rightArr = Arrays.copyOfRange(arr,mid+1,right + 1);
		
		int indexLeft = 0, indexRight = 0, index = left, swaps = 0;
        // If left or right arrayList still have elements, 
        // take the smaller of (leftArr[indexLeft],rightArr[indexRight]) 
        // add this to arr[index].
        while (indexLeft < leftArr.length && indexRight < rightArr.length) {
            if ( (leftArr[indexLeft] <= rightArr[indexRight])) {
            	arr[index] = leftArr[indexLeft];
            	indexLeft++;
            } else {
            	arr[index] = rightArr[indexRight];
                indexRight++;
                // Update swaps...
                swaps += (mid+1) - (left + indexLeft);
            }
            index++;
        }
        
        // Create a remainder array
        int[] remainder;
        int indexRemainder;
        if (indexLeft >= leftArr.length) {
            // The left Array has no more elements
            remainder = rightArr;
            indexRemainder = indexRight;
        } else {
            // The right Array has no more elements
            remainder = leftArr;
            indexRemainder = indexLeft;
        }

        // Copy the rest of whichever array that still has elements (left or right)
        for (int i=indexRemainder; i<remainder.length; i++) {
            arr[index] = remainder[i];
            index++;
        }
        
        return swaps;	
	}
	/** Method that counts the amount of inversions/ bad pairs by adding left,right subarray count and merge count
	 * 
	 * @param arr the array where inversions are to be counted
	 * @param left beginning index of array
	 * @param right ending index of array
	 * @return count of inversions/ bad pairs
	 */
	public static int mergeSortCount(int[] arr, int left, int right) {
		// Inversion count at this node of recursive tree
		int count = 0;
		// If there are more items in right part of array
		if(left < right) {
			
			// Calculate mid of array
			int mid = (left + right)/2;
			
			// From algorithm used from princeton
			// Total inversion count=left subarray count + right subarray count + merge count
			
			// Left side recursive call
			count += mergeSortCount(arr,left,mid);
			// Right side recursive call
			count += mergeSortCount(arr,mid+1,right);
			// Merge count
			count += mergeCount(arr, left, mid, right);
		}
		return count;
	}
}
