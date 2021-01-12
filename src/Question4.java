
import java.util.ArrayList;
import java.util.Arrays;

/** Given an array A of n integers and an integer k < n,
 *  select exactly k elements from array A such that the difference
 *  between the value of the maximum element selected and
 *  the value of the minimum element selected is minimum among all possible selections of k
 *  elements. The time complexity is O(n * log n). 
 * 
 * @author Nicky
 *
 */
public class Question4 {

	public static void main(String[] args) throws Exception {
		String fileLocation =args[0];
		// Read file
		ArrayList<String> digits = Question1.readFile(fileLocation);
		int k = Integer.valueOf(digits.get(1));
		// Elegant lambda expression to obtain integers in third row
		int[] arr = Arrays.stream(digits.get(2).split(" "))
				.mapToInt(i -> Integer.valueOf(i))
				.toArray();
		System.out.println(minimumDifference(arr,k));
		
	}
	/** Method to choose k elements in an array s.t. difference of maximum and minimum is minimized
	 * 
	 * @param arr an array containing the integers to choose from
	 * @param k the maximum number of elements to be chosen from the array. k>0
	 * @return an integer that represents the minimum difference between the maximum and minimum of the k elements chosen
	 */
	public static int minimumDifference(int arr[],int k) {
		// Save size n of array
		int n = arr.length;
		// Initialize difference as the maximum Integer possible (as it can only get lower)
		int diff = Integer.MAX_VALUE;
		// Sort the array with O(n log(n) time complexity
		Arrays.sort(arr);
		// Find the minimum difference with O(n) time complexity
		for(int i = 0; i <= n-k; i ++) {
			// Since the array is sorted, traverse the array to find minimum difference
			diff = Math.min(diff, arr[i+k-1] - arr[i]);
		}
		// Time complexity is O( n log(n) + n) = O(n log(n)) 
		return diff;
		
	}
	
}
