package com.lifeinbracket.creational.singleton;

/**
 * Singleton is a creational design pattern that ensures:
 *
 * Only one instance of a class exists in the entire application
 * and provides a global access point to that instance.
 *
 * This class demonstrates the basic Singleton design pattern.
 *
 * Why do we need it?
 * In some cases, creating multiple objects of a class is unnecessary
 * or even harmful. For example:
 * - configuration manager
 * - logger
 * - cache manager
 * - shared service objects
 *
 * In this basic version:
 * - the constructor is made private so no other class can create objects using `new`
 * - a single static instance is created inside the class
 * - a public static method is provided to access that same instance
 *
 * Note:
 * This is a basic Singleton implementation meant to explain the core idea first.
 * Later, we can see how this can be broken using reflection, serialization,
 * and multithreading, and then fix those issues step by step.
 */
public class SingletonBasic {

    int id;
    String name;

    /**
     * The single instance of the class is created eagerly
     * when the class is loaded.
     */
    private static final SingletonBasic instance = new SingletonBasic();

    /**
     * Private constructor prevents object creation from outside the class.
     */
    private SingletonBasic() {
    }

    /**
     * Returns the single shared instance of this class.
     *
     * @return the only instance of SingletonBasic
     */
    public static SingletonBasic getInstance() {
        return instance;
    }
}