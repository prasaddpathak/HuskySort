package edu.neu.coe.huskySort.tree;
import edu.neu.coe.huskySort.symbolTable.BSTOptimisedDelete;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Prasad Pathak
 */

public class BST23TreeBenchmark {

    Random r =new Random();
    final int[] treeSizes = {1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144};
    final int avgRuns = 29;
    private static final DecimalFormat df = new DecimalFormat("0.00");


    public void benchmarkTreeInsert() {

        for (int treeSize : treeSizes) {
            // Preparing input array and tree
            System.out.println("-------------------------------------------------------");
            System.out.println("Array Size: " + treeSize);

            long sumTimeElapsedBST = 0L;
            long sumTimeElapsedTTT = 0L;

            // Preparing insert keys
            for (int j = 0; j < avgRuns; j++) {

                int[] inputKeys = new int[treeSize];
                for (int i = 0; i < treeSize; i++) {
                    inputKeys[i] = r.nextInt(treeSize * 3);
                }

                TwoThreeTree ttt = new TwoThreeTree();
                BSTOptimisedDelete<Integer, Integer> bst = new BSTOptimisedDelete<>();

                // Inserting BST
                long startBST = System.nanoTime();
                for (int i : inputKeys) {
                    bst.put(i,i);
                }
                long endBST = System.nanoTime();
                sumTimeElapsedBST += endBST - startBST;

                // Searching TTT
                long startTTT = System.nanoTime();
                for (int i : inputKeys) {
                    ttt.insert(i);
                }
                long endTTT = System.nanoTime();
                sumTimeElapsedTTT += endTTT - startTTT;

            }

            // Print benchmark PER KEY search
            System.out.println("BST - Avg Insert Time per key(nS): " + sumTimeElapsedBST/(avgRuns * treeSize));
            System.out.println("TTT - Avg Insert Time per key(nS): " + sumTimeElapsedTTT/(avgRuns * treeSize));
        }
    }

    public void benchmarkTreeSearch() {

        for (int treeSize : treeSizes) {
            // Preparing input array and tree
            System.out.println("-------------------------------------------------------");
            System.out.println("Array Size: " + treeSize);
            int[] input = new int[treeSize];
            for (int i = 0; i < treeSize; i++) {
                input[i] = r.nextInt(treeSize * 7);
            }
            TwoThreeTree ttt = new TwoThreeTree();
            BSTOptimisedDelete<Integer, Integer> bst = new BSTOptimisedDelete<>();
            for (int i : input) {
                ttt.insert(i);
                bst.put(i, i);
            }
            long sumTimeElapsedBST = 0L;
            long sumTimeElapsedTTT = 0L;

            // Preparing search keys
            for (int j = 0; j < avgRuns; j++) {
                int[] searchKeys = new int[treeSize* 7];
                for (int i = 0; i < searchKeys.length; i++) {
                    searchKeys[i] = r.nextInt(treeSize * 13);
                }
                // Searching BST
                long startBST = System.nanoTime();
                for (int i : searchKeys) {
                    bst.contains(i);
                }
                long endBST = System.nanoTime();
                sumTimeElapsedBST += endBST - startBST;

                // Searching TTT
                long startTTT = System.nanoTime();
                for (int i : searchKeys) {
                    ttt.search(i);
                }
                long endTTT = System.nanoTime();
                sumTimeElapsedTTT += endTTT - startTTT;

            }

            // Print benchmark PER KEY search
            System.out.println("BST - Avg Search Time per key(nS): " + sumTimeElapsedBST/(avgRuns * treeSize*7));
            System.out.println("TTT - Avg Search Time per key(nS): " + sumTimeElapsedTTT/(avgRuns * treeSize*7));
        }
    }

    public void benchmarkTreeHeight() {
        for (int treeSize : treeSizes) {
            double tttDepth = 0.0;
            double bstDepth = 0.0;

            for (int j = 0; j < avgRuns ; j++) {
                int[] input = new int[treeSize];
                for (int i = 0; i < treeSize; i++) {
                    input[i] = r.nextInt(treeSize * 7);
                }
                TwoThreeTree ttt = new TwoThreeTree();
                BSTOptimisedDelete<Integer, Integer> bst = new BSTOptimisedDelete<>();

                for (int i : input) {
                    ttt.insert(i);
                    bst.put(i, i);
                }
                bstDepth += bst.depth();
                tttDepth += ttt.height();

            }
            System.out.println("-------------------------------------------------------");
            System.out.println("Tree Size: " + treeSize);
            System.out.println("Avg BST Depth: " + df.format(bstDepth/avgRuns) );
            System.out.println("Abg TTT Depth: " + df.format(tttDepth/avgRuns) );
        }
    }

    public static void main(String args[]) {
        BST23TreeBenchmark tree = new BST23TreeBenchmark();
        System.out.println("Averaging benchmark over " + tree.avgRuns + " runs");
        tree.benchmarkTreeInsert();
        tree.benchmarkTreeHeight();
        tree.benchmarkTreeSearch();
    }
}
