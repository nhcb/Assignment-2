


import java.util.ArrayList;
/** BinarySearchThread class that is a runnable which acts as an independent searcher
 * 
 * @author Nicky
 *
 */
public class binarySearchThread implements Runnable{

	// Create private volatile int to save binarySearch value
	private volatile int value;
	private ArrayList<String> haystack;
	private String needle;
	private int low;
	private int high;
	
	/** Constructor for binarySearchThread
	 * 
	 * @param haystack the arrayList where the needle is to be found
	 * @param low the index on the left side from the recursive call on binarySearch
	 * @param high the index on the right side from the recursive call on binarySearch
	 * @param needle the string to be found in haystack
	 */
	public binarySearchThread(ArrayList<String> haystack, int low, int high,String needle) {
		this.haystack = haystack;
		this.needle = needle;
		this.low = low;
		this.high = high;
	}
	
	
	/** Run the binarySearch in this specific haystack
	 * 
	 */
     @Override
     public void run() {
          value = Question1.binarySearch(haystack,low,high,needle);
     }
     /** Returns value from binarySearch algorithm in this specific thread
      * 
      * @return value from binarySearch method in this thread
      */
     public int getValue() {
    	 if(value>-1) {
    		 return value;
    	 }
    	 else {
    		 return -1;
    	 }
     }

}
