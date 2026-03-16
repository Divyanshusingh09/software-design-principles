
# 🟣 Interface Segregation Principle (ISP)

> **"Clients should not be forced to depend on methods they do not use."**  


The **Interface Segregation Principle** states that instead of creating one **large general-purpose interface**, we should create **smaller and more specific interfaces**.

```text
Many client-specific interfaces are better than one general interface.
```

---

# 🎯 Core Idea

A class should **only implement methods it actually needs**.

If a class is forced to implement methods it does not use, the design is poor.

---

# ⚠️ Example Without ISP

```java
interface Worker {

    void work();

    void eat();

    void sleep();
}
```

Now two implementations:

```java
class HumanWorker implements Worker {

    public void work() {
        System.out.println("Human working");
    }

    public void eat() {
        System.out.println("Human eating");
    }

    public void sleep() {
        System.out.println("Human sleeping");
    }
}
```

```java
class RobotWorker implements Worker {

    public void work() {
        System.out.println("Robot working");
    }

    public void eat() {
        throw new UnsupportedOperationException();
    }

    public void sleep() {
        throw new UnsupportedOperationException();
    }
}
```

### 🚨 Problem

Robot does **not eat or sleep**, but it is forced to implement those methods.

This violates **Interface Segregation Principle**.

---

# ❌ Problems in This Design

| Issue | Explanation |
|------|-------------|
| Unused methods | Robot doesn't need eat() |
| Unsupported operations | Exceptions thrown |
| Confusing API | Interface contains unrelated behavior |
| Difficult maintenance | Changes affect many classes |

---

# ✅ Applying ISP

Split the interface into smaller ones.

```java
interface Workable {
    void work();
}
```

```java
interface Eatable {
    void eat();
}
```

```java
interface Sleepable {
    void sleep();
}
```

---

# Implementation

```java
class HumanWorker implements Workable, Eatable, Sleepable {

    public void work() {
        System.out.println("Human working");
    }

    public void eat() {
        System.out.println("Human eating");
    }

    public void sleep() {
        System.out.println("Human sleeping");
    }
}
```

```java
class RobotWorker implements Workable {

    public void work() {
        System.out.println("Robot working");
    }
}
```

Now no class is forced to implement unnecessary methods.

---

# 💡 Real Life Example

Think of a **remote control**.

A universal remote with **100 buttons** forces users to interact with many unnecessary controls.

Instead we design:

- TV Remote
- AC Remote
- Speaker Remote

Each has **only relevant buttons**.

That is ISP.

---

# 📊 Before vs After

## ❌ Before

```
Worker
 ├ work()
 ├ eat()
 └ sleep()

HumanWorker
RobotWorker (forced methods)
```

## ✅ After

```
Workable
Eatable
Sleepable

HumanWorker → Workable + Eatable + Sleepable
RobotWorker → Workable
```

---

# 🧠 Short Answer

> Interface Segregation Principle states that clients should not be forced to depend on methods they do not use.  
Instead of one large interface, we should design smaller, more specific interfaces.

---

# 🚀 Key Takeaway

```text
Keep interfaces small
Keep interfaces focused
Avoid forcing unnecessary methods
```

That is the essence of **Interface Segregation Principle**.
