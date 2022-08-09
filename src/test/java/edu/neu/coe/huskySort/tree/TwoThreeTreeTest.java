/**
 * @author Prasad Pathak
 */

package edu.neu.coe.huskySort.tree;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class TwoThreeTreeTest {

    @Test
    public void testInsert(){
        TwoThreeTree ttt = new TwoThreeTree();
        ttt.insert(1);
        assertTrue(ttt.search(1));
    }

    @Test
    public void testInsertNegative(){
        TwoThreeTree ttt = new TwoThreeTree();
        ttt.insert(1);
        assertFalse(ttt.search(5));
    }

    @Test
    public void testInsertMultiple(){
        TwoThreeTree ttt = new TwoThreeTree();
        ttt.insert(1);
        ttt.insert(2);
        ttt.insert(3);
        ttt.insert(4);
        ttt.insert(5);
        ttt.insert(6);
        ttt.insert(7);
        ttt.insert(8);
        assertTrue(ttt.search(5));
    }

    @Test
    public void testMaxHeight(){
        TwoThreeTree ttt = new TwoThreeTree();
        int treeSize = 200;
        Random r =new Random();
        for (int i = 0; i < treeSize; i++) {
            ttt.insert(r.nextInt(treeSize * 3));
        }
        int treeDepth = ttt.height();
        // Theoretically Max Depth of 23Tree cannot exceed Log2(n+1)
        int theoreticalMaxDepth = (int) (Math.log(treeSize + 1) / Math.log(2));
        assertTrue(treeDepth < theoreticalMaxDepth);
    }

    @Test
    public void testDelete(){
        TwoThreeTree ttt = new TwoThreeTree();
        ttt.insert(1);
        ttt.insert(2);
        ttt.insert(3);
        ttt.remove(2);
        assertFalse(ttt.search(2));
    }

    @Test
    public void testDeleteMultiple(){
        TwoThreeTree ttt = new TwoThreeTree();
        int treeSize = 200;
        Random r =new Random(5468546L);
        int[] inputKeys = new int[treeSize];
        for (int i = 0; i < treeSize; i++) {
            int key = r.nextInt(treeSize * 28549);
            inputKeys[i] = key;
            ttt.insert(key);
        }
        // Random Delete
        for (int i: inputKeys) {
            ttt.remove(i);
        }
        assertTrue(ttt.numberOfNodes() < treeSize);
    }


    @Test
    public void testNoOfNodes(){
        TwoThreeTree ttt = new TwoThreeTree();
        int treeSize = 200;
        Random r =new Random(5468546L);
        int[] inputKeys = new int[treeSize];
        for (int i = 0; i < treeSize; i++) {
            ttt.insert(r.nextInt(treeSize * 28549));
        }
        System.out.println(ttt.numberOfNodes());
        assertTrue(ttt.numberOfNodes() <= treeSize);
    }

    @Test
    public void runBenchmarks() {
        String[] args = {"test"};
        BST23TreeBenchmark.main(args);
    }

    @Test
    public void testTreePrint() {
        TwoThreeTree ttt = new TwoThreeTree();
        int treeSize = 20;
        Random r =new Random(5468546L);
        int[] inputKeys = new int[treeSize];
        for (int i = 0; i < treeSize; i++) {
            ttt.insert(r.nextInt(treeSize * 28549));
        }
        ttt.bfsList();
        ttt.keyOrderList();
    }
}
