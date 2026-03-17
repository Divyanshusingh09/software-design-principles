package com.lifeinbracket.creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

public class SRP {

    public static void main(String[] args) throws Exception {
        showNormalClassBehavior();
        showBasicSingleton();
        showLazySingleton();
        showLazySingletonThreadSafetyIssue();
        showSynchronizedSingleton();
        showDclSingleton();
        showReflectionBreak();
        showReflectionSafeVersion();
        showSerializationAndCloneProtection();
        testFullyProtectedSingleton();
        enumBasedSRP();
    }

    private static void enumBasedSRP() {
        SingletonEnum s1 = SingletonEnum.INSTANCE;
        SingletonEnum s2 = SingletonEnum.INSTANCE;

        s1.showMessage();

        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("s1 == s2 -> " + (s1 == s2)); // true
    }

    /**
     * Default Java behavior:
     * every use of 'new' creates a different object.
     */
    private static void showNormalClassBehavior() {
        printSection("Normal class behavior");

        NormalClass n1 = new NormalClass();
        NormalClass n2 = new NormalClass();

        System.out.println("n1 == n2 -> " + (n1 == n2)); // false
    }

    /**
     * Basic Singleton:
     * only one object is returned, but it is created eagerly.
     */
    private static void showBasicSingleton() {
        printSection("Basic Singleton");

        // SingletonBasic s0 = new SingletonBasic(); // Compile-time error: constructor is private
        SingletonBasic s1 = SingletonBasic.getInstance();
        SingletonBasic s2 = SingletonBasic.getInstance();

        System.out.println("s1 == s2 -> " + (s1 == s2)); // true
    }

    /**
     * Lazy Singleton:
     * object is created only when first needed.
     */
    private static void showLazySingleton() {
        printSection("Lazy Singleton");

        SingletonLazy sl1 = SingletonLazy.getInstance();
        SingletonLazy sl2 = SingletonLazy.getInstance();

        System.out.println(sl1.getClass().getName() + " -> " + (sl1 == sl2)); // true
    }

    /**
     * Lazy Singleton issue:
     * not thread-safe in concurrent environment.
     */
    private static void showLazySingletonThreadSafetyIssue() throws InterruptedException {
        printSection("Lazy Singleton thread-safety issue");

        Runnable task = () -> {
            SingletonLazy obj = SingletonLazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " -> " + obj);
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    /**
     * Synchronized Singleton:
     * fixes thread-safety issue, but synchronizes every call as well as whole method is synchronized.
     */
    private static void showSynchronizedSingleton() {
        printSection("Synchronized Singleton");

        SingletonSynchronized ss1 = SingletonSynchronized.getInstance();
        SingletonSynchronized ss2 = SingletonSynchronized.getInstance();

        System.out.println("ss1 == ss2 -> " + (ss1 == ss2)); // true
    }

    /**
     * Double Checked Locking:
     * thread-safe + avoids locking on every access.
     */
    private static void showDclSingleton() {
        printSection("Double Checked Locking Singleton");

        SingletonDCL sdl1 = SingletonDCL.getInstance();
        SingletonDCL sdl2 = SingletonDCL.getInstance();

        System.out.println("sdl1 == sdl2 -> " + (sdl1 == sdl2)); // true
    }

    /**
     * Reflection can break Singleton by accessing private constructor.
     */
    private static void showReflectionBreak() throws Exception {
        printSection("Reflection breaks Singleton");

        SingletonDCL sdcl1 = SingletonDCL.getInstance();

        Constructor<SingletonDCL> constructor = SingletonDCL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        SingletonDCL sdcl2 = constructor.newInstance();

        System.out.println("s1 = " + sdcl1);
        System.out.println("s2 = " + sdcl2);
        System.out.println("s1 == s2 -> " + (sdcl1 == sdcl2)); // false
    }

    /**
     * Reflection-safe version:
     * constructor guards against second object creation.
     */
    private static void showReflectionSafeVersion() throws Exception {
        printSection("Reflection-safe Singleton");

        SingletonDCLReflectionSafe sr1 = SingletonDCLReflectionSafe.getInstance();

        Constructor<SingletonDCLReflectionSafe> constructor1 =
                SingletonDCLReflectionSafe.class.getDeclaredConstructor();

        constructor1.setAccessible(true);

        try {
            SingletonDCLReflectionSafe sr2 = constructor1.newInstance();
            System.out.println("s1 = " + sr1);
            System.out.println("s2 = " + sr2);
        } catch (Exception e) {
            System.out.println("Reflection attack blocked -> " + e.getCause());
        }
    }

    /**
     * Demonstrates protection against:
     * - serialization
     * - cloning
     */
    private static void showSerializationAndCloneProtection() throws Exception {
        printSection("Serialization-safe and clone-safe Singleton");

        SingletonDCLSerializableClonableIssue sds1 =
                SingletonDCLSerializableClonableIssue.getInstance();

        // Serialize
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("singleton-safe.ser")
        );
        out.writeObject(sds1);
        out.close();

        // Deserialize
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("singleton-safe.ser")
        );
        SingletonDCLSerializableClonableIssue sds2 =
                (SingletonDCLSerializableClonableIssue) in.readObject();
        in.close();

        System.out.println("After serialization:");
        System.out.println("s1 = " + sds1);
        System.out.println("s2 = " + sds2);
        System.out.println("s1 == s2 -> " + (sds1 == sds2)); // true if readResolve() is used

        // Clone attack
        SingletonDCLSerializableClonableIssue src1 =
                SingletonDCLSerializableClonableIssue.getInstance();

        try {
            SingletonDCLSerializableClonableIssue src2 =
                    (SingletonDCLSerializableClonableIssue) src1.clone();

            System.out.println("After cloning:");
            System.out.println("s1 = " + src1);
            System.out.println("s2 = " + src2);
            System.out.println("s1 == s2 -> " + (src1 == src2));
        } catch (Exception e) {
            System.out.println("Clone attack blocked -> " + e.getMessage());
        }
    }

    private static void testFullyProtectedSingleton() throws Exception {

        printSection("FULLY PROTECTED SINGLETON TEST");

        // 1. Normal usage
        SingletonFullyProtected s1 = SingletonFullyProtected.getInstance();
        SingletonFullyProtected s2 = SingletonFullyProtected.getInstance();

        System.out.println("Normal check (s1 == s2) -> " + (s1 == s2)); // true

        // ----------------------------------------------------
        // 2. Reflection attack
        // ----------------------------------------------------
        try {
            Constructor<SingletonFullyProtected> constructor =
                    SingletonFullyProtected.class.getDeclaredConstructor();

            constructor.setAccessible(true);

            SingletonFullyProtected s3 = constructor.newInstance();

            System.out.println("Reflection created object -> " + s3);

        } catch (Exception e) {
            System.out.println("Reflection attack blocked -> " + e.getCause());
        }

        // ----------------------------------------------------
        // 3. Serialization attack
        // ----------------------------------------------------
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("singleton-final.ser")
        );
        out.writeObject(s1);
        out.close();

        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("singleton-final.ser")
        );

        SingletonFullyProtected s4 =
                (SingletonFullyProtected) in.readObject();

        in.close();

        System.out.println("After serialization:");
        System.out.println("s1 = " + s1);
        System.out.println("s4 = " + s4);
        System.out.println("s1 == s4 -> " + (s1 == s4)); // should be true

        // ----------------------------------------------------
        // 4. Clone attack
        // ----------------------------------------------------
        try {
            SingletonFullyProtected s5 =
                    (SingletonFullyProtected) s1.clone();

            System.out.println("Clone created object -> " + s5);

        } catch (Exception e) {
            System.out.println("Clone attack blocked -> " + e.getMessage());
        }

        System.out.println("\n✅ FINAL RESULT: Singleton is fully protected");
    }

    /**
     * Small helper to clearly separate each demo in console output.
     */
    private static void printSection(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(title);
        System.out.println("==================================================");
    }
}