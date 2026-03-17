package com.lifeinbracket.creational.singleton;

/**
 * This class demonstrates the default behavior of object creation in Java.
 *
 * Each time the `new` keyword is used, a completely new instance of the class
 * is created in heap memory. These instances are independent of each other
 * and do not share state.
 *
 * While this is the expected behavior in most scenarios, it can become a problem
 * when the application requires only a single shared instance of a class.
 *
 * Creating multiple instances unnecessarily can lead to:
 * - Increased memory usage
 * - Inconsistent or duplicated state across objects
 * - Difficulty in managing shared resources (e.g., configuration, logging)
 *
 * This example highlights why patterns like Singleton are useful when only
 * one instance is required across the application.
 */
public class NormalClass {
    int id;
    String name;
}
