
import java.util.ArrayList;

/** multi-thread binary search to search for a specific string (let us call this the"needle"). 
 *  The multi-thread search is to be implemented as the following.
 *  	1. Split the sorted list into n parts
 *  	2. Have n threads, each search on one of the n parts for the needle.
 *  	3. Report the index if you find the needle. Here the index should be with respect to
 *  	the full list,not a single part.
 *  
 *  Comparison multi-thread search with a single thread one (500 simulations):
 *  	(2 cores, 2 threads, needle = Zumwalt): 825 ns
 *  	(2 cores, 1 thread, needle = Zumwalt): 481 ns
 *  
 * 
 * @author Nicky
 *
 */
public class Question2 {
	
	public static void main(String[] args) throws Exception {
		
		String fileLocation = args[0];
		ArrayList<String> words = Question1.readFile(fileLocation);
		// Search string
		String needle = args[1];
		// Amount of threads
		int threads = Integer.valueOf(args[2]);
		// Start searching
		Long startTime = System.currentTimeMillis();
		// Sort ArrayList
		ArrayList<String> sortedList = mergeSort(words);
		System.out.println(multiThreadSearch(sortedList, needle, threads));
		Long endTime = System.currentTimeMillis();
		// Print time
		System.out.println((endTime-startTime));
		
		// Comparison multiThread with single thread (nanoseconds) over 500 simulations
		//comparisonSimulation(words,needle,threads,500); // uncomment if needed

	}
	/** Method to do a multiThreaded binary search
	 * 
	 * @param sortedList the arrayList where a multiThreaded binary search is going to take place
	 * @param needle the string that is to be found in the sortedList
	 * @param n the amount of threads to be initialized in the multiThreaded binary search
	 * @return the index of the needle in sortedList
	 * @throws InterruptedException when threads that are initialized are interrupted
	 */
	public static int multiThreadSearch(ArrayList<String> sortedList,String needle, int n) throws InterruptedException {
		
		// Create an array to save the threads
		Thread[] t = new Thread[n];
		Runnable[] r = new Runnable[n];
		// Create int to save result
		int result = -1;
		
		for(int i =0; i < n; i ++) {
			// Divide sortedList in n parts using low and high
			int low = i*(sortedList.size()/n);
			int high = Math.max((i+1)*(sortedList.size()/n),sortedList.size());
			// Create new Runnable
			r[i] = new binarySearchThread(sortedList,low,high,needle);
			// Create new Thread
			t[i] = new Thread(r[i]);
			// Start the Thread
			t[i].start();
		}
		
		// Join all threads
		for(int i = 0; i < n; i++) {
			t[i].join();
			// Return value
			int index = ((binarySearchThread) r[i]).getValue();
			// Check if index exceeds -1 (the value when no result is found)
			if(index > -1) {
				result = index;
			}
		}
		return result;
	}
	
	/** Method that will use the mergeSort algorithm on strArr, to sort it alphabetically
	 * 
	 * @param strArr the array to be sorted
	 * @return the sorted strArr array
	 */
	public static ArrayList<String> mergeSort(ArrayList<String> strArr) {
        ArrayList<String> leftArr = new ArrayList<String>();
        ArrayList<String> rightArr = new ArrayList<String>();
        int mid; 
        if (strArr.size() == 1) {    
            return strArr; // strArr sorted
        } else {
            mid = strArr.size()/2; // Middle strArr
            // copy the left half of strArr into the left.
            for (int i=0; i<mid; i++) {
                    leftArr.add(strArr.get(i));
            }
 
            //copy the right half of whole into the new arraylist.
            for (int i=mid; i<strArr.size(); i++) {
                    rightArr.add(strArr.get(i));
            }
 
            // Sort the left and right halves of the arraylist using mergesort.
            leftArr  = mergeSort(leftArr);
            rightArr = mergeSort(rightArr);
 
            // Merge the results back together.
            merge(leftArr, rightArr, strArr);
        }
        return strArr;
    }
 
	/** Method from the mergeSort array that will merge multiple arrays into one, while sorting
	 * 
	 * @param leftArr array on the left
	 * @param rightArr array on the right
	 * @param strArr the full aray
	 */
    public static void merge(ArrayList<String> leftArr, ArrayList<String> rightArr, ArrayList<String> strArr) {
        int indexLeft = 0, indexRight = 0, index = 0;
        // If left or right arrayList still have elements, 
        // take the smaller of (leftArr.get(indexLeft),rightArr.get(indexRight)) 
        // add this to strArr.get(index) .
        while (indexLeft < leftArr.size() && indexRight < rightArr.size()) {
        	// Initialize left and right
        	String left = leftArr.get(indexLeft);
        	String right = rightArr.get(indexRight);
            if ( left.compareTo(right) < 0) {
                strArr.set(index, left);
                indexLeft++;
            } else {
                strArr.set(index, right);
                indexRight++;
            }
            index++;
        }
        // Create a remainder arrayList
        ArrayList<String> remainder;
        int indexRemainder;
        if (indexLeft >= leftArr.size()) {
            // The left ArrayList has no more elements
            remainder = rightArr;
            indexRemainder = indexRight;
        } else {
            // The right ArrayList has no more elements
            remainder = leftArr;
            indexRemainder = indexLeft;
        }
 
        // Copy the rest of whichever ArrayList that still has elements (left or right)
        for (int i=indexRemainder; i<remainder.size(); i++) {
            strArr.set(index, remainder.get(i));
            index++;
        }
    }
    /** Method to compare results between single-threaded and multi-threaded binary search
     * 
     * @param haystack the (unsorted) list
     * @param needle the string to be found in the sorted list
     * @param n the amount of threads to be used
     * @param simulations the amount of simulations to be done
     * @throws InterruptedException when a thread that is initialized is interrupted
     */
    public static void comparisonSimulation(ArrayList<String> haystack, String needle, int n, int simulations) throws InterruptedException {
    	// Compare times from using multiple threads(n) with single-thread, this time does not include sorting
    	// Sort ArrayList
    	ArrayList<String> sortedList = mergeSort(haystack);
    	Long timeMultiThread = null;
    	Long timeSingleThread = null;
    	// First loop (multiple threads)
    	for(int i=0;i < simulations;i++) {
    		Long startTime = System.nanoTime();
    		multiThreadSearch(sortedList,needle,n);
    		Long endTime = System.nanoTime();
    		timeMultiThread=+ (endTime-startTime);
    	}
    	// Second loop (single thread)
    	for(int i=0;i < simulations;i++) {
    		Long startTime = System.nanoTime();
    		multiThreadSearch(sortedList,needle,1);
    		Long endTime = System.nanoTime();
    		timeSingleThread=+ (endTime-startTime);
    	}
    	// Compare values
    	System.out.println("Single thread mean computation time: " + timeSingleThread/simulations + " ns");
    	System.out.println("Multiple (" + n +") thread mean computation time: " + timeMultiThread/simulations+ " ns");
    	System.out.println("Computed over 500 simulations.");
    	
    }
	
}
