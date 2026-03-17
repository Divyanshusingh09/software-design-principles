package com.lifeinbracket.creational.singleton;

/**
 * Lazy Singleton
 *
 * Problem solved compared to basic Singleton:
 * In the basic Singleton, the instance is created as soon as the class is loaded,
 * even if the application never uses it.
 *
 * This version delays object creation until it is actually needed.
 *
 * Benefit:
 * - saves memory if the object is never used
 * - avoids early object creation
 *
 * Important:
 * This version is not thread-safe.
 * If multiple threads call getInstance() at the same time,
 * more than one object may be created.
 *
 * We will fix that in the next version.
 */
public class SingletonLazy {

    /**
     * Instance is not created immediately.
     * It starts as null and will be created only on first request.
     */
    private static SingletonLazy instance;

    /**
     * Private constructor prevents external object creation.
     */
    private SingletonLazy() {
        System.out.println("SingletonLazy object created");
    }

    /**
     * Creates the object only when it is needed for the first time.
     *
     * @return the single instance of SingletonLazy
     */
    public static SingletonLazy getInstance() {
        if (instance == null) {
            try {
                // Delay added only to make the race condition easier to observe
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            instance = new SingletonLazy();
        }
        return instance;
    }
}