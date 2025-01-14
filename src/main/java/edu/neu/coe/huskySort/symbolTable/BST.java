/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neu.coe.huskySort.symbolTable;

import java.util.Set;

/**
 *
 * @author anvithalakshmisha
 * @param <Key> the key type.
 * @param <Value> the value type.
 */
public interface BST<Key extends Comparable<Key>, Value> {

    /**
     * Get the value corresponding to key.
     *
     * @param key the key whose value is required.
     * @return the value.
     */
    Value get(Key key);

    /**
     * Insert or update the given key-value pair.
     *
     * @param key the key to be inserted/updated.
     * @param value the value to be retrieved by future calls of get(key).
     * @return the original value, if any, else null.
     */
    Value put(Key key, Value value);

    /**
     * @return The set of all keys.
     */
    Set<Key> keySet();

    /**
     * Delete the given key. CONSIDER returning the original value.
     *
     * @param key the key to be deleted.
     */
    void delete(Key key);
}
