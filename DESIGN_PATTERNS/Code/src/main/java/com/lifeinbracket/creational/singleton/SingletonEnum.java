package com.lifeinbracket.creational.singleton;

/**
 * Enum-based Singleton.
 *
 * Why enum Singleton is special:
 * - only one instance is created by Java automatically
 * - thread-safe by default
 * - serialization-safe by default
 * - cloning is not allowed
 * - reflection cannot create enum instances in the normal way
 *
 * Why use enum?
 * This is the simplest and most robust way to implement Singleton in modern Java
 * when you do not specifically need lazy creation logic with a normal class.
 *
 * Important:
 * The enum constant itself is the Singleton instance.
 * Here, INSTANCE is the only object of this enum.
 */
public enum SingletonEnum {

    /**
     * The single instance of this Singleton.
     */
    INSTANCE;

    /**
     * Example business method.
     * Any shared logic or state can be placed inside the enum.
     */
    public void showMessage() {
        System.out.println("Using enum singleton: " + this);
    }
}
