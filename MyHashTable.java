/* TCSS 305 - Compressed Literature 2
 * MyHashTable Class
 */

import java.util.LinkedList;
import java.util.List;

/**
 * A hash table.
 * 
 * @author Rylie Nelson
 *
 * @param <K> The type of the Keys of this map.
 * @param <V> The type of the Values of this map.
 */

public class MyHashTable<K, V> {
	
	/**
	 * The "buckets" that entries are hashed into.
	 */
	
	private MyBucket<K, V>[] buckets;
	
	/**
	 * The number of entries that this MyHashTable contains.
	 */
	
	private int entries;
	
	/**
	 * Constructs a new MyHashTable with a given number of buckets.
	 * 
	 * @param capacity The number of buckets.
	 */
	
	public MyHashTable(int capacity) {
		buckets = new MyBucket[capacity];
		
		for (int i = 0; i < capacity; i++) {
			buckets[i] = new MyBucket<K, V>();
		}
	}
	
	/**
	 * Puts an entry into this MyHashTable. If the value already exists for a given key, we replace it.
	 * 
	 * @param searchKey The key of the entry we are putting in.
	 * @param newValue The value of the entry we are putting in.
	 */
	
	public void put(K searchKey, V newValue) {
		int hashValue = hash(searchKey);
		List<BucketNode<K, V>> theBucket = buckets[hashValue].bucketlist;
		if (theBucket.isEmpty()) {
			theBucket.add(new BucketNode<K, V>(searchKey, newValue));
			entries++;
		} else {
			boolean replaced = false;
			for (int i = 0; i < theBucket.size() || replaced; i++) {
				if (theBucket.get(i).key.equals(searchKey)) {
					theBucket.get(i).value = newValue;
					replaced = true;
				}
			}		
			if (!replaced) {
				theBucket.add(new BucketNode<K, V>(searchKey, newValue));
				entries++;
			}	
		}
	}
	
	/**
	 * Retrieves a value, given a key.
	 * 
	 * @param searchKey The key we are using to search.
	 * @return The value associated with the key.
	 */
	
	public V get(K searchKey) {
		int hashValue = hash(searchKey);
		
		for (int i = 0; i < buckets[hashValue].bucketlist.size(); i++) {
			if (buckets[hashValue].bucketlist.get(i).key.equals(searchKey)) {
				return buckets[hashValue].bucketlist.get(i).value;
			}
		}
		
		return null; //if we reach this point nothing was found, return null.
	}
	
	/**
	 * Prints out some interesting information about this MyHashTable.
	 */
	
	public void stats() {
		System.out.println("Hash Table Stats");
		System.out.println("===================");
		System.out.println("Number of Entries: " + entries);
		
		System.out.println("Number of Buckets: " + buckets.length);
		
		int[] histogram = new int[10];
		
		for (int i = 0; i < buckets.length; i++) {
			histogram[buckets[i].bucketlist.size()]++;
		}
		
		System.out.print("Histogram of Bucket Sizes: [" + histogram[0]);
		for (int i = 1; i < histogram.length; i++) {
			System.out.print(" ," + histogram[i]);
		}
		System.out.println("]");
		
		System.out.println("Fill Percentage: " + (((double) (buckets.length - histogram[0]) / buckets.length) * 100) + "%");
		
		System.out.println("Average Non-Empty Bucket: " + entries / ((double) (buckets.length - histogram[0])));
	}
	
	/**
	 * Hashes a key so that it can be placed into a bucket.
	 * 
	 * @param key The key to be hashed.
	 * @return The hash of the key.
	 */
	
	private int hash(K key) {
		return key.hashCode() % (buckets.length / 2) + (buckets.length / 2);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buckets.length; i++) {
			sb.append(buckets[i].bucketlist);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Represents a bucket of the MyHashTable.
	 * 
	 * @author Rylie Nelson
	 */
	
	private class MyBucket<K, V> {
		private List<BucketNode<K, V>> bucketlist;
		
		private MyBucket() {
			bucketlist = new LinkedList<BucketNode<K, V>>();
		}
		
		public String toString() {
			return bucketlist.toString();
		}
	}
	
	private class BucketNode<K, V> {
		private K key;
		private V value;
		
		private BucketNode(K thekey, V thevalue) {
			key = thekey;
			value = thevalue;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(" + key + ", " + value + ")");
			return sb.toString();
		}
	}

}
