package com.lifeinbracket.creational.singleton;

import java.io.Serializable;

/**
 * Double Checked Locking Singleton (VULNERABLE VERSION)
 *
 * This class demonstrates a Singleton implementation that:
 * - uses lazy initialization
 * - is thread-safe (Double Checked Locking)
 * - uses volatile to prevent instruction reordering
 *
 * HOWEVER, this version is intentionally NOT fully protected.
 *
 * It can still be broken by:
 * 1. Serialization
 * 2. Cloning
 *
 * (Reflection is partially handled via constructor guard)
 *
 * ----------------------------------------------------------
 * WHY THIS CLASS EXISTS?
 * ----------------------------------------------------------
 * This class is used to demonstrate that:
 *
 * Even a well-designed Singleton (DCL + volatile) is NOT enough
 * unless we handle edge cases like:
 * - serialization
 * - cloning
 * - reflection
 *
 * ----------------------------------------------------------
 * PROBLEMS IN THIS CLASS
 * ----------------------------------------------------------
 *
 * 1. Serialization Issue:
 *    When this object is serialized and then deserialized,
 *    Java creates a NEW object instead of returning the existing instance.
 *
 *    Reason:
 *    - Deserialization bypasses the getInstance() method
 *    - JVM reconstructs the object from byte stream
 *
 *    Result:
 *    - Singleton is broken
 *
 *
 * 2. Cloning Issue:
 *    This class implements Cloneable and allows cloning.
 *
 *    clone() internally creates a NEW object in memory
 *    without calling the constructor.
 *
 *    Result:
 *    - Singleton is broken again
 *
 *
 *
 *
 * ----------------------------------------------------------
 * KEY LEARNING
 * ----------------------------------------------------------
 *
 * Singleton is NOT just about:
 * - private constructor
 * - static instance
 *
 * It must also handle:
 * - multithreading
 * - reflection
 * - serialization
 * - cloning
 *
 * This class shows what happens if we ignore serialization and cloning.
 *
 *
 * ----------------------------------------------------------
 * NEXT STEP (FIXED VERSION)
 * ----------------------------------------------------------
 *
 * To make this class fully safe:
 *
 * - Override readResolve() → fixes serialization
 * - Override clone() → throw exception or return same instance
 *
 * OR use:
 * - Enum Singleton (best and simplest solution)
 */
public class SingletonDCLSerializableClonableIssue implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * volatile ensures:
     * - visibility across threads
     * - prevents instruction reordering
     *
     * Required for Double Checked Locking to work correctly.
     */
    private static volatile SingletonDCLSerializableClonableIssue instance;

    /**
     * Private constructor prevents direct instantiation.
     *
     * Reflection protection:
     * If an instance already exists, block further creation.
     */
    private SingletonDCLSerializableClonableIssue() {

        if (instance != null) {
            throw new RuntimeException(
                    "Singleton already created. Reflection is not allowed."
            );
        }

        System.out.println("Singleton object created: " + this);
    }

    /**
     * Returns the single instance of the class.
     *
     * Double Checked Locking:
     * - First check avoids unnecessary synchronization
     * - Second check ensures only one object is created
     *
     * @return singleton instance
     */
    public static SingletonDCLSerializableClonableIssue getInstance() {

        if (instance == null) {
            synchronized (SingletonDCLSerializableClonableIssue.class) {

                if (instance == null) {
                    instance = new SingletonDCLSerializableClonableIssue();
                }
            }
        }

        return instance;
    }

    /**
     * Cloning implementation (PROBLEMATIC)
     *
     * This method calls super.clone(), which creates a NEW object
     * in memory without invoking the constructor.
     *
     * Because of this:
     * - A second instance is created
     * - Singleton guarantee is broken
     *
     * @return a new object (breaks Singleton)
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // ❌ creates new instance → breaks Singleton
    }
}