package com.lifeinbracket.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

/**
 * Fully protected Singleton implementation.
 *
 * This class combines all improvements discussed so far:
 *
 * 1. Lazy initialization
 *    - object is created only when first needed
 *
 * 2. Thread safety
 *    - uses synchronized block
 *
 * 3. Better performance
 *    - uses Double Checked Locking so locking does not happen on every call
 *
 * 4. Reflection protection
 *    - constructor throws exception if an instance already exists
 *
 * 5. Serialization protection
 *    - readResolve() ensures deserialization returns the same singleton instance
 *
 * 6. Cloning protection
 *    - clone() is overridden to prevent creation of a new object
 *
 * Note:
 * This is a strong practical Singleton implementation,
 * but the most robust and simplest modern solution is still Enum Singleton.
 */
public class SingletonFullyProtected implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * volatile is required for Double Checked Locking.
     *
     * It prevents instruction reordering and ensures visibility
     * of the latest value across threads.
     */
    private static volatile SingletonFullyProtected instance;

    /**
     * Private constructor prevents direct object creation using new.
     *
     * Reflection protection:
     * If reflection tries to create another object after one already exists,
     * this constructor will throw an exception.
     */
    private SingletonFullyProtected() {
        if (instance != null) {
            throw new RuntimeException(
                    "Singleton instance already exists. Reflection is not allowed."
            );
        }
        System.out.println("SingletonFullyProtected object created: " + this);
    }

    /**
     * Returns the single instance of this class.
     *
     * Double Checked Locking flow:
     * 1. First null check avoids locking on every call
     * 2. Synchronized block ensures only one thread creates the object
     * 3. Second null check ensures object is created only once
     *
     * @return the only instance of SingletonFullyProtected
     */
    public static SingletonFullyProtected getInstance() {
        if (instance == null) {
            synchronized (SingletonFullyProtected.class) {
                if (instance == null) {
                    instance = new SingletonFullyProtected();
                }
            }
        }
        return instance;
    }

    /**
     * Serialization protection.
     *
     * During deserialization, Java may create a new object from the byte stream.
     * This method ensures that the existing singleton instance is returned instead.
     *
     * @return the one true singleton instance
     */
    @Serial
    private Object readResolve() {
        return getInstance();
    }

    /**
     * Cloning protection.
     *
     * If clone() is allowed normally, it can create a second object,
     * which breaks the Singleton rule.
     *
     * Here we block cloning completely.
     *
     * @return never returns normally
     * @throws CloneNotSupportedException always thrown to prevent cloning
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning is not allowed for Singleton");
    }

    public void showMessage() {
        System.out.println("Using singleton object: " + this);
    }
}
