package topwords;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;

/**
 * This class creates a HashMap of the words and their respective occurrence in a given text file.
 * The entryset of the said HashMap can be retrieved with the method "getHashMapSet".
 * 
 * the file path is given to the constructor at object creation.
 * 
 * @author Giorgio Simoni
 */
public class WordCounter {	
	//Regex pattern to separate words
	private Pattern separator = Pattern.compile("[\\s.,;:!?\"'()\\[\\]{}<>/\\\\|@#$%^&*+=`~]+");
	
	//Regex pattern to check if a give word is valid. if the pattern finds a match in a word that word isnt valid.	 
	private Pattern wordValidator = Pattern.compile( "[^\\x20-\\x7E\\t\\r\\n]|\r\n|\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]");
	
	//HashMap to be populated
	private HashMap<String, Integer> countedWords = new HashMap<>();
	private String filePath;
	
	/** 
	 * @return returns the an immutable copy of the entryset of the HashMap.
	 */
	public Set<Map.Entry<String, Integer>> getHashMapSet(){ 
		Set<Map.Entry<String, Integer>> result = new HashSet<>();
	    for (Map.Entry<String, Integer> e : countedWords.entrySet()) {
	        result.add(new SimpleImmutableEntry<>(e.getKey(), e.getValue()));
	    }
	    return Collections.unmodifiableSet(result);		
	}
	
	/**
	 * This method reads the file found at "filePath" and creates a HashMap
	 * containing the words and their respective occurrence.
	 */
	public void countWords() {
		//opens the file
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {				
			String line;
			
			//reads line by line from the file
			while((line = reader.readLine()) != null) {
				//the line is diveded into an array of words
				String[] words = fetchWords(line);
				/*
				 * checks if each word is valid, valid words are inserted in the HashMap,
				 * if the word is already in the HashMap then it's occurrence is increased.
				 */
				for(String word : words) {
					if(this.wordValidityCheck(word) && !(word.isBlank())) { this.countWord(word); }					
				}
				
			}			
		}catch(FileNotFoundException e) { System.out.println("File not Found"); }
		 catch(IOException e) { System.out.println("Something went wrong"); }
	}
	
	/**
	 * This method splits the text line give into an array of words.
	 * 
	 * @param line a line of characters read from the text file at "filePath".
	 * @return returns an array of string that represents the words in the text line.
	 */
	private String[] fetchWords(String line) { return line.split(separator.pattern()); }
	
	/**
	 * This method uses the word give and if not present in the HashMap
	 * adds it to it, if present increases the occurrence by 1 unit.
	 * 
	 * @param word the word that needs to be added/counted in the HashMap.
	 */
	private void countWord(String word) {
		int occurence;					
		if(!countedWords.containsKey(word)) { occurence = 1; } 							//word not present
		else 								{ occurence = 1 + countedWords.get(word); } //word present
		countedWords.put(word, occurence);				
	}
	
	/**
	 * This method checks if the given word is valid or not based on the
	 * "wordValidator" Regex pattern.
	 * 
	 * @param word word to be checked
	 * @return false if the Regex pattern finds a match. true otherwise
	 */
	private boolean wordValidityCheck(String word) { return !(wordValidator.matcher(word).find()); }
	
	/** 
	 * @param filePath path of the file where the words will be counted.
	 */
	public void setPath(String filePath) { this.filePath = filePath;}
	
	/**
	 * Default constructor
	 */
	public WordCounter() { this.setPath(""); }	
	
	/**
	 * This is a constructor for the "WordCounter" class,
	 * it needs a string to be given as parameter, said string is 
	 * the path of the file where the words will be counted.
	 * 
	 * @param filePath path of the file where the words will be counted.
	 */
	public WordCounter(String filePath) { this.setPath(filePath); }
}
