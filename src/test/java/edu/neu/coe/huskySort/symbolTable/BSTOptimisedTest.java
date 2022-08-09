/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neu.coe.huskySort.symbolTable;

import edu.neu.coe.huskySort.util.PrivateMethodInvoker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author anvithalakshmisha
 */
public class BSTOptimisedTest {

    @Test
    public void testSetRoot1() throws Exception {
        BST<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        System.out.println(bst);
    }

    @Test
    public void testSetRoot2() throws Exception {
        BST<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node nodeX = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTOptimisedDelete.Node nodeY = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTOptimisedDelete.Node nodeZ = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        System.out.println(bst);
    }

    @Test
    public void testPut0() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        assertEquals(0, bst.size());
        bst.put("X", 42);
        assertEquals(1, bst.size());
    }

    @Test
    public void testPut1() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 99);
        BSTOptimisedDelete.Node root = (BSTOptimisedDelete.Node) tester.invokePrivate("getRoot");
        assertEquals("X", root.key);
        assertEquals("Y", root.larger.key);
        assertNull(root.smaller);
        assertEquals(2, bst.size());
    }

    @Test
    public void testPut2() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        BSTOptimisedDelete.Node root = (BSTOptimisedDelete.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertEquals("X", root.smaller.key);
        assertEquals("Z", root.larger.key);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPut3() throws Exception {
        BSTDetail<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        bst.put("Y", 42);
        BSTOptimisedDelete.Node root = (BSTOptimisedDelete.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertNull(root.smaller);
        assertNull(root.larger);
        bst.put("X", 99);
        assertEquals("X", root.smaller.key);
        bst.put("Z", 37);
        assertEquals("Z", root.larger.key);
        System.out.println(bst.toString());
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutN() throws Exception {
        BSTDetail<String, Integer> bst = new BSTOptimisedDelete<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        System.out.println(bst);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutAll() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 3);
        map.put("Goodbye", 5);
        map.put("Ciao", 6);
        BSTDetail<String, Integer> bst = new BSTOptimisedDelete<>();
        bst.putAll(map);
        System.out.println(bst);
        assertEquals(map.size(), bst.size());
    }

    @Test
    public void testDelete1() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.delete("X");
        assertNull(bst.root);
        assertEquals(0, bst.size());
    }

    @Test
    public void testDelete2() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 57);
        assertNotNull(bst.root.larger);
        bst.delete("Y");
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
        assertEquals(1, bst.size(node));
    }

    @Test
    public void testDelete3() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("W");
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
        assertEquals(1, bst.size(bst.root));
    }

    @Test
    public void testDelete4() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("A");
        assertEquals(2, bst.size(bst.root));
    }

    @Test
    public void testDelete5() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.deleteRandom("A");
        assertEquals(2, bst.size(bst.root));
    }

    @Test
    public void testDelete6() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node node = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.deleteBySize("A");
        assertEquals(2, bst.size(bst.root));
    }

    @Test
    public void testSize1() {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        for (int i = 0; i < 100; i++) {
            bst.put(Integer.toString(i), i);
        }
        assertEquals(100, bst.size());
    }

    @Test
    public void testSize2() {
        Random random = new Random(0L);
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        for (int i = 0; i < 100; i++) {
            bst.put(Integer.toString(random.nextInt(200)), i);
        }
        assertEquals(79, bst.size());
    }

    @Test
    public void testDepthKey1() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node nodeX = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTOptimisedDelete.Node nodeY = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTOptimisedDelete.Node nodeZ = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(1, bst.depth("X"));
        assertEquals(0, bst.depth("Y"));
        assertEquals(1, bst.depth("Z"));
        assertEquals(-1, bst.depth("A"));
    }

    @Test
    public void testDepthKey2() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(0, bst.depth("Hello"));
        assertEquals(1, bst.depth("Goodbye"));
        assertEquals(2, bst.depth("Ciao"));
    }

    @Test
    public void testDepth1() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTOptimisedDelete.Node nodeX = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTOptimisedDelete.Node nodeY = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTOptimisedDelete.Node nodeZ = (BSTOptimisedDelete.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(2, bst.depth());
    }

    @Test
    public void testDepth2() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(3, bst.depth());
    }

    @Test
    public void testOptimisedDeleteOfNode() throws Exception {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        assertEquals(0, bst.size(bst.root));
        bst.put("15", 15);
        bst.put("10", 10);
        bst.put("20", 20);
        bst.put("08", 8);
        bst.put("12", 12);
        bst.put("18", 18);
        bst.put("16", 16);
        bst.put("30", 30);

        assertEquals(8, bst.size(bst.root));

        // test : delete and replace with predecessor
        // accessing node with key"20"
        assertEquals(2, bst.size(bst.root.larger.smaller));
        assertEquals(1, bst.size(bst.root.larger.larger));
        bst.deleteBySize("20");
        assertEquals(1, bst.size(bst.root.larger.smaller));
        assertEquals(1, bst.size(bst.root.larger.larger));

        // test: delete and replace with successor
        bst.put("42", 42);

        // accessing node with key "30"
        assertEquals(0, bst.size(bst.root.larger.larger.smaller));
        assertEquals(1, bst.size(bst.root.larger.larger.larger));
        bst.delete("30");
        assertEquals(0, bst.size(bst.root.larger.larger.smaller));
        assertEquals(0, bst.size(bst.root.larger.larger.larger));

    }

    @Test
    public void runBenchmarks() throws IOException {
        String[] args = {"test"};
        BSTOptimisedDelete.main(args);
    }

    @Test
    public void testToString() {
        BSTOptimisedDelete<String, Integer> bst = new BSTOptimisedDelete<>();
        bst.put("15", 15);
        bst.put("10", 10);
        bst.put("20", 20);
        bst.put("08", 8);
        bst.toString();
    }
}
