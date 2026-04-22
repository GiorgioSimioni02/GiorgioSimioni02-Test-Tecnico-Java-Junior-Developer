package topwords;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.Iterator;


/**
 * 
 * This class prints to the console a ranking of the N most repeated words in a text file where N is "podium".
 * If multiple words have the same ranking all of them will be displayed.
 * 
 * This class has 2 possible arguments that can be given to it:
 * - the first encountered argument that's made entirely of digits is considered as the "podium"
 * - the first encountered argument that contains ".txt" is considered as the "filePath"
 * 
 * if the podium isn't given as an input it's default value is 6.
 * if the filePath isn's given as input the default one is "../input/text.txt".
 * 
 * @author Giorgio Simoni
 * 
 */
public class Topwords {
	//pattern to check if string is made of anything but a digit
	private static final Pattern digitChecker = Pattern.compile("[^\\d]");
	
	//default argument values
	private static final int defaultPodium = 6;
	private static final String defaultFilePath = "input/text.txt";
	
	//checks if the related argument was found yet or not
	private static boolean pathFound 	= false;
	private static boolean podiumFound = false;
	
	//saved arguments(args)
	private static String filePath;
	private static int podium;	
	
	//class entry function
	public static void main(String[] args) {		
		argsGetter(args);
		topWordsFromFile();		
	}
	
	/**
	 * This method looks at the arguments given to the class and
	 * saves certain ones based on a specific logic for each one
	 * 
	 * @param args the arguments given to class entry function(main)
	 */
	private static void argsGetter(String[] args) {
		//argument search
		for(String arg : args) {
			podiumArgChecker(arg);
			filePathArgChecker(arg);			
		}		
		
		//if certain arguments aren't found default values are used instead
		if(!pathFound)
			filePath = defaultFilePath;
		if(!podiumFound)
			podium = defaultPodium;
	}
	
	/**
	 * logic to check if the "filePath" argument was given or not
	 * 
	 * @param arg one of the arguments given at class entry point
	 */
	private static void filePathArgChecker(String arg) {
		if(!pathFound && arg.contains(".txt")){
			filePath = arg;
			pathFound = true;
		}
	}
	
	/**
	 * logic to check if the "podium" argument was given or not
	 * 
	 * @param arg one of the arguments given at class entry point
	 */
	private static void podiumArgChecker(String arg) {
		if(!podiumFound && !digitChecker.matcher(arg).find()) { 			
			podium = Integer.parseInt(arg);
			podiumFound = true;			
		}
	}
	
	/**
	 * This method utilizes the "WordCounter" object to have an HashMap
	 * of all of the words and their respective occurrence.
	 * With that HashMap it costructs a sorted TreeMap of the different
	 * occurrences and the words with each occurrence, it then prints the top ones
	 */	
	private static void topWordsFromFile() {
		WordCounter obj = new WordCounter(filePath);	
		obj.countWords();
		TreeMap<Integer, TreeSet<String>> sortedWords = sortWords(obj.getHashMapSet());
		printTopWords(sortedWords);
	}
	
	/**
	 * This method uses the entryset of HashMap of the words and their occurrence to
	 * create a sorted TreeMap. Each entry of the TreeMap
	 * is a pair of (Integer, TreeSet<String>) where the Integer(the key) is 
	 * the occurrence of the words in the reletad TreeSet.
	 * 
	 * @param countedWords the entryset of HashMap with the words and their occurrence
	 * @return returns the constructed TreeMap
	 */
	private static TreeMap<Integer, TreeSet<String>> sortWords(Set<Map.Entry<String, Integer>> countedWords) {
		TreeMap<Integer, TreeSet<String>> sortedWords = new TreeMap<>(Collections.reverseOrder());
		
		for(Map.Entry<String, Integer> entry : countedWords) {	
			/*
			 * if an entry with the current occurrence value isn't present
			 * it is created and inserted in the TreeMap
			 */
			if(!sortedWords.containsKey(entry.getValue())) {
				TreeSet<String> tempSet = new TreeSet<>();
				
				tempSet.add(entry.getKey());
				sortedWords.put(entry.getValue(), tempSet);
			}
			
			/*
			 * if it is present the current word is added to 
			 * the list of words related to the current occurrence value
			 */
			else sortedWords.get(entry.getValue()).add(entry.getKey());
		}
		return sortedWords;
	}
	
	/**
	 * This method prints each (occurrence, words) pair in the TreeMap as a list in order based
	 * on their occurrence value (highest occurrence is the first one and so on).
	 * 
	 * @param sortedWords 	The sorted TreeMap containing the occurrences and the
	 * 		  				relative words with said occurrences.
	 */
	private static void printTopWords(TreeMap<Integer, TreeSet<String>> sortedWords) {		
		Iterator<Map.Entry<Integer, TreeSet<String>>> it = sortedWords.entrySet().iterator();
		int totalEntries = sortedWords.size();
		
		//console output
		System.out.print("Top " + podium + " most repeated words in file");		
		if(totalEntries > podium)
			System.out.println(" [" + (totalEntries-podium) + " entry not shown]");
		else
			System.out.print("\n");
		System.out.println("Occurrence");
		
		while((podium > 0) && (it.hasNext())) {
			Map.Entry<Integer, TreeSet<String>> entry = it.next();	
			podium--;
			//prints each (occurence, words) pair
			printEntry(entry);			
			
		}
		
	}
	
	/**
	 * This method prints a single (occurrence, words) pair.
	 * 
	 * @param entry (occurrence, words) pair to be printed.
	 */
	private static void printEntry(Map.Entry<Integer, TreeSet<String>> entry) {		
		Iterator<String> words = entry.getValue().descendingIterator();
		int count = 0;
		
		System.out.print("-(" + entry.getKey() + ")     : ");
		while(words.hasNext()) {
			if(count == 10) {
				count = 0;
				System.out.print("\n           ");
			}
			System.out.print(words.next());
			if(words.hasNext()) System.out.print(" - ");
			count++;
		}
		System.out.print("\n\n");
	}
}

