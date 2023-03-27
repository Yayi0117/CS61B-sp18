package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = p.key.compareTo(key);
        if (cmp == 0) {
            return p.value;
        } else {
            if (cmp < 0) {
                return getHelper(key, p.right);
            } else {
                return getHelper(key, p.left);
            }
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        } else {
            int cmp = p.key.compareTo(key);
            if  (cmp > 0) {
                p.left = putHelper(key, value, p.left);
            } else if (cmp < 0) {
                p.right =  putHelper(key, value, p.right);
            }
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        Queue<K> queue = preOrderTraversal(root);
        for (K key : queue) {
            keyset.add(key);
        }
        return keyset;
    }

    private Queue<K> preOrderTraversal(Node p) {
        Queue<K> queue = new LinkedList<>();
        if (p != null) {
            preOrderTraversal(p.left);
            queue.add(p.key);
            preOrderTraversal(p.right);
        }
        return queue;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V result = get(key);
        root = removeHelper(key, root);
        return result;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left =  removeHelper(key, p.left);
        } else if (cmp > 0) {
            p.right =  removeHelper(key, p.right);
        } else {
            if (p.right == null) {
                p = p.left;
                size--;
            } else if (p.left == null) {
                p = p.right;
                size--;
            } else {
                Node temp = p;
                p = min(temp.right);
                p.right = romoveMin(temp.right);
                p.left = temp.left;
                size--;
            }
        }
        return p;
    }
    private Node min(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return min(p.left);
        }
    }

    private Node romoveMin(Node p) {
        if (p.left == null) return p.right;
        p.left = romoveMin(p.left);
        return p;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            root = removeHelper(key, root);
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        int removeTest = bstmap.remove("cat");
        bstmap.put("ant",1);
        //int removePairTest1 = bstmap.remove("fish",20);
        int removePairTest2 = bstmap.remove("fish",22);
    }
}
