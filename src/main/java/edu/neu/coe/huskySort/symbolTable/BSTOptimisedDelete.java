package edu.neu.coe.huskySort.symbolTable;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author anvithalakshmisha
 */
public class BSTOptimisedDelete<Key extends Comparable<Key>, Value> implements BSTDetail<Key, Value> {

    @Override
    public Boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * This implementation of putAll ensures that the keys are inserted into
     * this BST in random order.
     *
     * @param map a map of key value pairs
     */
    @Override
    public void putAll(Map<Key, Value> map) {
        List<Key> ks = new ArrayList<>(map.keySet());
        Collections.shuffle(ks);
        for (Key k : ks) {
            put(k, map.get(k));
        }
    }

    @Override
    public int size() {
        return root != null ? root.count : 0;
    }

    @Override
    public void inOrderTraverse(BiFunction<Key, Value, Void> f) {
        doTraverse(0, root, f);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public Value put(Key key, Value value) {
        NodeValue nodeValue = put(root, key, value);
        if (root == null) {
            root = nodeValue.node;
        }
        if (nodeValue.value == null) {
            root.count++;
        }
        return nodeValue.value;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    public void deleteRandom(Key key) {
        root = deleteRandom(root, key);
    }

    public void deleteBySize(Key key) {
        root = deleteBySize(root, key);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    @Override
    public Set<Key> keySet() {
        return null;
    }

    /**
     * Method to yield the depth of a key, relative to the root.
     *
     * @param key the key whose depth we are interested in.
     * @return the depth of the key (root: 0) otherwise -1 if key is not found.
     */
    public int depth(Key key) {
        try {
            return depth(root, key);
        } catch (DepthException e) {
            return -1;
        }
    }

    public BSTOptimisedDelete() {
    }

    public BSTOptimisedDelete(Map<Key, Value> map) {
        this();
        putAll(map);
    }

    Node root = null;

    private Value get(Node node, Key key) {
        Node result = getNode(node, key);
        return result != null ? result.value : null;
    }

    private Node getNode(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cf = key.compareTo(node.key);
        if (cf < 0) {
            return getNode(node.smaller, key);
        } else if (cf > 0) {
            return getNode(node.larger, key);
        } else {
            return node;
        }
    }

    /**
     * Method to put the key/value pair into the subtree whose root is node.
     *
     * @param node  the root of a subtree
     * @param key   the key to insert
     * @param value the value to associate with the key
     * @return a tuple of Node and Value: Node is the
     */
    private NodeValue put(Node node, Key key, Value value) {
        // If node is null, then we return the newly constructed Node, and value=null
        if (node == null) {
            return new NodeValue(new Node(key, value, 0), null);
        }
        int cf = key.compareTo(node.key);
        if (cf == 0) {
            // If keys match, then we return the node and its value
            NodeValue result = new NodeValue(node, node.value);
            node.value = value;
            return result;
        } else if (cf < 0) {
            // if key is less than node's key, we recursively invoke put in the smaller subtree
            NodeValue result = put(node.smaller, key, value);
            if (node.smaller == null) {
                node.smaller = result.node;
            }
            if (result.value == null) {
                result.node.count++;
            }
            return result;
        } else {
            // if key is greater than node's key, we recursively invoke put in the larger subtree
            NodeValue result = put(node.larger, key, value);
            if (node.larger == null) {
                node.larger = result.node;
            }
            if (result.value == null) {
                result.node.count++;
            }
            return result;
        }
    }

    public int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return (size(node.smaller) + 1 + size(node.larger));
        }
    }

    // CONSIDER this should be an instance method of Node.
    private Node delete(Node x, Key key) {
        // FIXME by replacing the following code
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.smaller = delete(x.smaller, key);
        else if (cmp > 0) x.larger = delete(x.larger, key);
        else {
            if (x.larger == null) return x.smaller;
            if (x.smaller == null) return x.larger;
//            favour the larger node
            Node t = x;
            x = min(t.larger);
            x.larger = deleteMin(t.larger);
            x.smaller = t.smaller;
        }
        x.count = size(x.smaller) + size(x.larger) + 1;
        return x;
        // END
    }

    private Node deleteRandom(Node x, Key key) {
        // FIXME by replacing the following code
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.smaller = deleteRandom(x.smaller, key);
        else if (cmp > 0) x.larger = deleteRandom(x.larger, key);
        else {
            if (x.larger == null) return x.smaller;
            if (x.smaller == null) return x.larger;
            Random rd = new Random(); // creating Random object
            boolean rand = rd.nextBoolean();
            if (rand) {
                Node t = x;
                x = min(t.larger);
                x.larger = deleteMin(t.larger);
                x.smaller = t.smaller;
            } else {
                Node t = x;
                x = min(t.smaller);
                x.smaller = deleteMin(t.smaller);
                x.larger = t.larger;
            }
        }
        x.count = size(x.smaller) + size(x.larger) + 1;
        return x;
        // END
    }

    private Node deleteBySize(Node x, Key key) {
        // FIXME by replacing the following code
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.smaller = deleteBySize(x.smaller, key);
        else if (cmp > 0) x.larger = deleteBySize(x.larger, key);
        else {
            if (x.larger == null) return x.smaller;
            if (x.smaller == null) return x.larger;
            if (depth(x.larger) > depth(x.smaller)) {
                Node t = x;
                x = min(t.larger);
                x.larger = deleteMin(t.larger);
                x.smaller = t.smaller;
            } else {
                Node t = x;
                x = min(t.smaller);
                x.smaller = deleteMin(t.smaller);
                x.larger = t.larger;
            }
        }
        x.count = size(x.smaller) + size(x.larger) + 1;
        return x;
        // END
    }

    private Node deleteMin(Node x) {
        if (x.smaller == null) {
            return x.larger;
        }
        x.smaller = deleteMin(x.smaller);
        x.count = 1 + size(x.smaller) + size(x.larger);
        return x;
    }

    private Node deleteMax(Node x) {
        if (x.larger == null) {
            return x.smaller;
        }
        x.larger = deleteMax(x.larger);
        x.count = 1 + size(x.smaller) + size(x.larger);
        return x;
    }

    private Node min(Node x) {
        if (x == null) {
            throw new RuntimeException("min not implemented for null");
        } else if (x.smaller == null) {
            return x;
        } else {
            return min(x.smaller);
        }
    }

    private Node max(Node x) {
        if (x == null) {
            throw new RuntimeException("max not implemented for null");
        } else if (x.larger == null) {
            return x;
        } else {
            return max(x.larger);
        }
    }

    /**
     * Do a generic traverse of the binary tree starting with node
     *
     * @param q    determines when the function f is invoked ( lt 0: pre, ==0: in,
     *             gt 0: post)
     * @param node the node
     * @param f    the function to be invoked
     */
    private void doTraverse(int q, Node node, BiFunction<Key, Value, Void> f) {
        if (node == null) {
            return;
        }
        if (q < 0) {
            f.apply(node.key, node.value);
        }
        doTraverse(q, node.smaller, f);
        if (q == 0) {
            f.apply(node.key, node.value);
        }
        doTraverse(q, node.larger, f);
        if (q > 0) {
            f.apply(node.key, node.value);
        }
    }

    /**
     * Yield the total depth of this BST. If root is null, then depth will be 0.
     *
     * @return the total number of levels in this BST.
     */
    public int depth() {
        return depth(root);
    }

    private int depth(Node node) {
        if (node == null) {
            return 0;
        }
        int depthS = depth(node.smaller);
        int depthL = depth(node.larger);
        return 1 + Math.max(depthL, depthS);
    }

    private class NodeValue {

        private final Node node;
        private final Value value;

        NodeValue(Node node, Value value) {
            this.node = node;
            this.value = value;
        }

        @Override
        public String toString() {
            return node + "<->" + value;
        }
    }

    class Node {

        Node(Key key, Value value, int depth) {
            this.key = key;
            this.value = value;
            this.depth = depth;
        }

        final Key key;
        Value value;
        final int depth;
        Node smaller = null;
        Node larger = null;
        int count = 0;

    }

    private Node makeNode(Key key, Value value, int depth) {
        return new Node(key, value, depth);
    }

    private Node getRoot() {
        return root;
    }

    private void setRoot(Node node) {
        if (root == null) {
            root = node;
            root.count++;
        } else {
            root = node;
        }
    }

    private void show(Node node, StringBuffer sb, int indent) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(node.key);
        sb.append(": ");
        sb.append(node.value);
        sb.append("\n");
        if (node.smaller != null) {
            sb.append("  ".repeat(Math.max(0, indent + 1)));
            sb.append("smaller: ");
            show(node.smaller, sb, indent + 1);
        }
        if (node.larger != null) {
            sb.append("  ".repeat(Math.max(0, indent + 1)));
            sb.append("larger: ");
            show(node.larger, sb, indent + 1);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        show(root, sb, 0);
        return sb.toString();
    }

    private int depth(Node node, Key key) throws DepthException {
        if (node == null) {
            throw new DepthException();
        }
        int cf = key.compareTo(node.key);
        if (cf < 0) {
            return 1 + depth(node.smaller, key);
        } else if (cf > 0) {
            return 1 + depth(node.larger, key);
        } else {
            return 0;
        }
    }

    private static class DepthException extends Exception {

        public DepthException() {
        }
    }

    private void runMain(String fnName) {
        BSTOptimisedDelete<Integer, String> bst = new BSTOptimisedDelete<>();
        Integer[] n = {200, 350, 500, 650, 800, 950};
        Random random = new Random(0L);
        for (Integer integer : n) {
            int count = integer / 2;
            List<Integer> keys = new ArrayList<>();
            System.out.println("For n = " + integer);
            for (int i = 0; i < integer; i++) {
                int rand = random.nextInt(100000000);
                bst.put(rand, Integer.toString(rand));
                keys.add(rand);
            }
            for (int i = 0; i < count; i++) {
                if (!keys.isEmpty()) {
                    int key = random.nextInt(keys.size());
                    if (fnName.equalsIgnoreCase("Hibbard deletion")) {
                        bst.delete(keys.get(key));
                    } else if (fnName.equalsIgnoreCase("Delete random")) {
                        bst.deleteRandom(keys.get(key));
                    } else {
                        bst.deleteBySize(keys.get(key));
                    }
                    keys.remove(key);
                }
            }
            System.out.println(fnName + " " + bst.depth());
        }
    }

    public static void main(String[] args) throws IOException {

        BSTOptimisedDelete<Integer, String> bst = new BSTOptimisedDelete<>();
        bst.runMain("Hibbard deletion");
        bst.runMain("Delete random");
        bst.runMain("Delete by size");
    }
}

