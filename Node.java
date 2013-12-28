/* TCSS 342 - Compressed Literature
 * Node class
 */

/**
 * The Node used by SimpleTrees.
 * 
 * @author Rylie Nelson
 * @version Autumn 2013
 */

public class Node implements Comparable {
	
	/**
	 * The character in this Node.
	 */
	
	public String word;
	
	/**
	 * The frequency of the character.
	 */
	
	public Integer frequency;
	
	/**
	 * This node's left child.
	 */
	
	public Node left_child;
	
	/**
	 * This node's right child.
	 */
	
	public Node right_child;
	
	/**
	 * Constructs a new Node.
	 * 
	 * @param the_character The character of this node.
	 * @param the_frequency The frequency of the character.
	 */
	
	public Node(String the_word, Integer the_frequency) {
		word = the_word;
		frequency = the_frequency;
		
		left_child = null;
		right_child = null;
	}

	@Override
	public int compareTo(Object o) {
		Node n = (Node) o;
		return frequency - n.frequency;
	}
	
	@Override
	public String toString() {
		return  "\"" + word + "\"";
	}
}
