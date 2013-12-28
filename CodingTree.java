/* TCSS 305 - Compressed Literature 2
 * CodingTree Class
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Utilizes a Huffman tree to compress a given array of characters.
 * 
 * @author Rylie Nelson
 * @version Autumn 2013
 */

public class CodingTree {
	
	/**
	 * The codes in string form
	 */

	protected String codeStr;
	
	/**
	 * A hash table of the codes
	 */
	
	protected MyHashTable<String, String> codes;
	
	/**
	 * A list of the codes
	 */
	
	private List<Code> mycodelist;
	
	/**
	 * Constructs a new CodingTree and encodes the given string.
	 * 
	 * @param the_string The string to be encoded.
	 */
	
	public CodingTree(String the_string) {
		codeStr = "";
		codes = new MyHashTable<String, String>(16384);
		mycodelist = new ArrayList<Code>();
		
		List<String> string_list = parseWordsIntoList(the_string);
		
		Map<String, Integer> stringFreqMap = wordsToFrequencyMap(string_list);
		
		List<SimpleTree> trees = convertMapToTrees(stringFreqMap);
		
		SimpleTree finalTree = combineTrees(trees);
		
		generateCodesFromTree(finalTree.HeadOfTree, "");
		
		populatecodeStr();
	}
	
	/**
	 * Parses an input string into words. Everything that is not an upper or lower case character
	 * or an apostrophe is treated as a "separator" and added as its own word.
	 * 
	 * @param s The string to be parsed.
	 * @return A list of the words in the string.
	 */
	
	private List<String> parseWordsIntoList(String s) {
		List<String> the_list = new ArrayList<String>();
		int tempC = 0;
		StringBuilder tempS = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			tempC = (int) s.charAt(i);
			if ((tempC >= 65 && tempC <= 90) || (tempC >= 97 && tempC <= 122) || tempC == 39) {
				tempS.append((char) tempC);
			} else if (tempS.length() > 0) {
				the_list.add(tempS.toString()); // if we hit a "separator" char, and we have a word being built up
				tempS = new StringBuilder();			 // we want to add it to our list, and then add the separator to the list
				tempS.append((char) tempC);
				the_list.add(tempS.toString());
				tempS = new StringBuilder();
			} else {
				tempS.append((char) tempC);		 // otherwise just add the seperator
				the_list.add(tempS.toString());
				tempS = new StringBuilder();
			}
		}
		return the_list;
	}
	
	/**
	 * Creates a map, where the keys are the words used in the list
	 * and the values are the frequencies of each word.
	 * 
	 * @param the_chars The list of words to be encoded
	 * @return The map of word frequencies
	 */
	
	private Map<String, Integer> wordsToFrequencyMap(List<String> the_words) {
		Map<String, Integer> freqMap = new HashMap<String, Integer>(); 
		
		for (String s : the_words) {
			if (freqMap.containsKey(s)) {
				freqMap.put(s, freqMap.get(s) + 1);
			} else {
				freqMap.put(s, 1);
			}
		}
		
		return freqMap;
	}
	
	/**
	 * Creates a single-node SimpleTree from each entry in the map.
	 * 
	 * @param the_map The frequency map of words
	 * @return A list of SimpleTrees, where each entry is a single node tree
	 */
	
	private List<SimpleTree> convertMapToTrees(Map<String, Integer> the_map) {
		List<SimpleTree> treeList = new ArrayList<SimpleTree>();
		for (Map.Entry<String, Integer> entry : the_map.entrySet()) {
			Node n = new Node(entry.getKey(), entry.getValue());
			SimpleTree tree = new SimpleTree(n);
			treeList.add(tree);
		}
		return treeList;
	}
	
	/**
	 * Combines all the SimpleTrees into a Huffman tree.
	 * 
	 * @param treeList The list of SimpleTrees.
	 * @return The Huffman tree produced from combining the list of trees.
	 */
	
	private SimpleTree combineTrees(List<SimpleTree> treeList) {
		PriorityQueue<SimpleTree> pq = new PriorityQueue<SimpleTree>();
		for (SimpleTree st : treeList) {
			pq.add(st);
		}
		SimpleTree st1 = null;
		SimpleTree st2 = null;
		while (pq.size() >= 2) {
			st1 = pq.remove();
			st2 = pq.remove();
			st1.combineTree(st2);
			pq.add(st1);
		}
		return st1;
	}
	
	/**
	 * Generates the code for each node in the tree and adds it to the
	 * list of codes.
	 * 
	 * @param n The node we are currently at.
	 * @param codeString The bitstring for the node we are at.
	 */
	
	private void generateCodesFromTree(Node n, String codeString) {
		if (n.word != null) {
			Code c = new Code(n.word, codeString);
			mycodelist.add(c);
			codes.put(n.word, codeString);
		} else {
			generateCodesFromTree(n.left_child, codeString + "0");
			generateCodesFromTree(n.right_child, codeString + "1");
		}
	}
	
	/**
	 * Populates the codeStr field.
	 */
	
	private void populatecodeStr() {
		StringBuilder sb = new StringBuilder();
		for (Code c : mycodelist) {
			sb.append(c.toString() + '\n');
		}
		codeStr = sb.toString();
	}

	
}
