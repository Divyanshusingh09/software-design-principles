package com.lifeinbracket.creational.singleton;
/**
 * Double Checked Locking Singleton with reflection protection.
 *
 * This version keeps all benefits of DCL:
 * - lazy initialization
 * - thread safety
 * - better performance
 *
 * Additionally, it prevents creating a second object through reflection
 * by throwing an exception if constructor is called after instance creation.
 */
public class SingletonDCLReflectionSafe {

    private static volatile SingletonDCLReflectionSafe instance;

    private SingletonDCLReflectionSafe() {
        if (instance != null) {
            throw new RuntimeException(
                    "Singleton already created. Reflection is not allowed."
            );
        }
        System.out.println("SingletonDCLReflectionSafe object created: " + this);
    }

    public static SingletonDCLReflectionSafe getInstance() {
        if (instance == null) {
            synchronized (SingletonDCLReflectionSafe.class) {
                if (instance == null) {
                    instance = new SingletonDCLReflectionSafe();
                }
            }
        }
        return instance;
    }
}