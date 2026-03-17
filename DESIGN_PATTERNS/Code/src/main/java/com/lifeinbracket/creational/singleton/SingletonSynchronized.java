package com.lifeinbracket.creational.singleton;

/**
 * Thread-safe Lazy Singleton
 *
 * This version fixes the issue present in the basic lazy singleton implementation.
 *
 * In the lazy singleton, the object is created only when it is needed for the
 * first time, but that version is not thread-safe.
 *
 * If multiple threads call getInstance() at the same time, more than one object
 * may be created.
 *
 * To fix that issue, this version makes the getInstance() method synchronized.
 * Because of synchronization, only one thread can execute this method at a time.
 *
 * This guarantees:
 * - only one instance is created
 * - lazy initialization is preserved
 * - thread safety is achieved
 *
 * Note:
 * Although this version is correct and thread-safe, it has a performance cost.
 * Every call to getInstance() acquires a lock, even after the object has already
 * been created.
 * It synchronized the whole method instead of the particular piece of code.
 *
 * The next optimized version will solve that performance issue.
 */
public class SingletonSynchronized {

    /**
     * The instance is not created immediately.
     * It will be created only when first requested.
     */
    private static SingletonSynchronized instance;

    /**
     * Private constructor prevents object creation from outside the class.
     */
    private SingletonSynchronized() {
        System.out.println("SingletonSynchronized object created");
    }

    /**
     * Synchronized method ensures that only one thread can execute this
     * method at a time.
     *
     * This makes the lazy singleton thread-safe.
     *
     * @return the single instance of SingletonSynchronized
     */
    public static synchronized SingletonSynchronized getInstance() {
        if (instance == null) {
            instance = new SingletonSynchronized();
        }
        return instance;
    }
}