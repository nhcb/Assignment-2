
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**  implemented a selection sort algorithm to sort the list in
 *   A-Z order and uses a binary search algorithm to search for
 *   a specific country/region.
 * 
 * @author Nicky
 *
 */
public class Question1 {

	public static void main(String[] args) throws Exception{
		
		// CodeGrade input
		ArrayList<String> countries = readFile(args[0]);
		String location = args[1];
		System.out.println(search(countries,location));
	} 
	
	/** Search method, using selection sort and binarySearch to find needle in haystack
	 * 
	 * @param haystack the list (to be sorted) and where the needle is to be found
	 * @param needle the item to be found in hay stack
	 * @return index of needle in sorted list
	 */
	public static int search(ArrayList<String> haystack,String needle) {
		// Sort ArrayList
		ArrayList<String> sortedHaystack = selectionSort(haystack);
		// Do binarySearch
		return binarySearch(sortedHaystack,0,sortedHaystack.size()-1,needle);
	}
	
	/** Method to read a file and return output as an arrayList of String format
	 * 
	 * @param fileLocation the location where the file should be present
	 * @return an arrayList containing the information from every line in the File in a new element
	 * @throws IOException happens when there is an error during reading or writing in an array or file is not found
	 */
	public static ArrayList<String> readFile(String fileLocation) throws IOException {
		// Initialize arrayList
		ArrayList<String> result = new ArrayList<>();
		// Read file 
		File file = new File(fileLocation); 
		// Use bufferedReader for efficient reading

		// This is because BufferedReader uses a char buffer to simultaneously read multiple
		// values from a character-input stream
		// and hence reduces the number of read() calls made by the underlying FileStream.

		BufferedReader br = new BufferedReader(new FileReader(file)); 
		while (br.ready()) {
			String str = br.readLine();
			//System.out.println(str); //Uncomment for reading input
			// Save
			result.add(str);
		}
		br.close();

		return result;
	}
	
	/** Recursive method that uses the binary search algorithm to search for the needle in haystack
	 * 
	 * @param haystack the arrayList where the needle is to be found
	 * @param low the index on the left side from the recursive call on binarySearch
	 * @param high the index on the right side from the recursive call on binarySearch
	 * @param needle the string to be found in haystack
	 * @return the index of needle in the sorted list haystack or -1 if not found
	 */
	public static int binarySearch(ArrayList<String> haystack,int low, int high, String needle) {
        //if array is in order then perform binary search on the array
        if (high>=low){  
            //calculate mid
            int mid = low + (high - low)/2;  
            //if needle equals string in middle return mid
            String midString = haystack.get(mid);
            if (midString.equals(needle)){  
            return mid + 1;  
            }  
            //if haystack.get(mid) comes before needle in alphabetical order, then needle is in left half of array
            if (midString.compareTo(needle) > 0){  
            	return binarySearch(haystack,low, mid-1, needle);//recursively search for needle  
            }
          //needle is in right half of the array
            else{  
            	return binarySearch(haystack,mid+1, high, needle);//recursively search for needle 
            }  
        }  
        return -1;  

	}
	
	/** The selectionSort algorithm that linearly traverses the arrayList to sort it
	 * 
	 * @param strArr the arrayList to be sorted
	 * @return the sorted arrayList
	 */
	public static ArrayList<String> selectionSort(ArrayList<String> strArr){
		
		// Find string reference that should go in every index
		for(int i=0;i < strArr.size(); i ++) {
			// Find the minimum of a string (e.g. a), assumme this is index i
			int min = i;
			String minStr = strArr.get(min); 
			for(int j=i+1 ; j < strArr.size(); j++) {
				if(strArr.get(j).compareTo(minStr) < 0){
					min = j;
					minStr = strArr.get(j);
					String swapStr = strArr.get(i);
					// Swap
					strArr.set(i, minStr);
					strArr.set(j, swapStr);
				}
			}
		}
		return strArr;
	}
	
}
