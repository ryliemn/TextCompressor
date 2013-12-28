/* TCSS 342 - Compressed Literature 2
 * SimpleTree class
 */

/**
 * A tree that is used to implement a Huffman tree.
 * 
 * @author Rylie Nelson
 * @version Autumn 2013
 */

public class SimpleTree implements Comparable {
	
	/**
	 * The Node that is the head of this tree.
	 */
	
	public Node HeadOfTree;
	
	/**
	 * The weight of the tree, which is the combined frequency of each node.
	 */
	
	public int WeightOfTree;
	
	/**
	 * Constructs a new SimpleTree.
	 * 
	 * @param newNode The head of the new tree.
	 */
	
	public SimpleTree(Node newNode) {
		HeadOfTree = newNode;
		WeightOfTree = newNode.frequency;
	}
	
	/**
	 * Combines two trees using Huffman tree invariants.
	 * 
	 * @param other_tree The tree to be combined with this one.
	 */
	
	public void combineTree(SimpleTree other_tree) {
		Node newHead = new Node(null, 0);
		newHead.left_child = HeadOfTree;
		newHead.right_child = other_tree.HeadOfTree;
		WeightOfTree = WeightOfTree + other_tree.WeightOfTree;
		HeadOfTree = newHead;
	}
	
	public String toString() {
		return HeadOfTree + " " + WeightOfTree + "\n";
	}

	@Override
	public int compareTo(Object o) {
		SimpleTree st = (SimpleTree) o;
		return WeightOfTree - st.WeightOfTree;
	}
	

}
