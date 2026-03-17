package com.lifeinbracket.creational.singleton;

/**
 * Double Checked Locking Singleton
 *
 * This version improves over the synchronized singleton by reducing
 * unnecessary locking.
 *
 * It ensures:
 * - thread safety
 * - lazy initialization
 * - better performance (no locking after instance is created)
 *
 * Important:
 * The instance variable must be declared as 'volatile' to prevent
 * instruction reordering issues.
 */
public class SingletonDCL {

    /**
     * volatile is very important here, The instance variable must be declared volatile to prevent instruction reordering issues that can lead to partially initialized objects.
     */
    private static volatile SingletonDCL instance;

    private SingletonDCL() {
        System.out.println("Object created: " + this);
    }

    public static SingletonDCL getInstance() {

        // First check (no locking)
        if (instance == null) {

            synchronized (SingletonDCL.class) {

                // Second check (with locking)
                if (instance == null) {
                    instance = new SingletonDCL();
                }
            }
        }

        return instance;
    }
}