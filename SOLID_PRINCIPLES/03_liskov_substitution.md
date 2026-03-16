
# 🔵 Liskov Substitution Principle (LSP)

> 💡 **Definition**  
> **"Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program."**  
> — *Barbara Liskov*

The **Liskov Substitution Principle (LSP)** is the **third principle in SOLID**.

It states that:

```text
A child class should be able to replace its parent class
without breaking the program behavior.
```

In simple words:

> **If class B extends class A, then B should behave like A.**

---

# 🎯 Core Idea

If a program expects an object of type **Parent**, then it should work correctly even if we pass a **Child object**.

Example:

```java
void processPayment(Payment payment)
```

If `CreditCardPayment` extends `Payment`, then the program should work correctly when we pass:

```java
processPayment(new CreditCardPayment())
```

If the behavior breaks, **LSP is violated**.

---

# 🧠 Why LSP is Important

Many developers misuse inheritance.

They write:

```text
A looks similar to B
→ let's extend B
```

But inheritance is not just about **code reuse**.

Inheritance means:

```text
The child must behave like the parent
```

If the child changes expected behavior, then the design becomes dangerous.

---

# ⚠️ Classic Example — Rectangle & Square

This is the **most famous example of LSP violation**.

## Parent Class

```java
class Rectangle {

    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}
```

---

## Child Class

```java
class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
```

This seems logical because:

```
Square IS-A Rectangle
```

But watch what happens.

---

# 🚨 Problem

```java
class Test {

    static void useRectangle(Rectangle rectangle) {

        rectangle.setWidth(5);
        rectangle.setHeight(10);

        if(rectangle.getArea() != 50) {
            throw new RuntimeException("Unexpected area!");
        }
    }
}
```

### Case 1 — Rectangle

```
width = 5
height = 10
area = 50
```

Correct.

### Case 2 — Square

```
width = 10
height = 10
area = 100
```

Program breaks.

This means:

```
Square cannot safely replace Rectangle
```

So **LSP is violated**.

---

# ❌ Why This is a Violation

Client code assumed:

```
width and height can change independently
```

But square changes that assumption.

So the subclass **breaks the contract of the parent class**.

---

# ✅ Correct Design

Instead of inheritance, separate abstractions.

```java
interface Shape {
    int getArea();
}
```

```java
class Rectangle implements Shape {

    private int width;
    private int height;

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}
```

```java
class Square implements Shape {

    private int side;

    Square(int side) {
        this.side = side;
    }

    public int getArea() {
        return side * side;
    }
}
```

Now both shapes behave correctly.

---

# 💡 Real Life Example — Birds

Imagine this design:

```text
Bird
 ├── Sparrow
 └── Penguin
```

Parent class:

```java
class Bird {
    void fly() {
        System.out.println("Flying");
    }
}
```

Sparrow works fine.

But penguin cannot fly.

```java
class Penguin extends Bird {

    @Override
    void fly() {
        throw new UnsupportedOperationException();
    }
}
```

Now client code breaks.

---

# 🚨 Problem

```java
void makeBirdFly(Bird bird) {
    bird.fly();
}
```

Passing `Penguin` will crash the system.

So the inheritance hierarchy is wrong.

---

# ✅ Better Design

Separate behavior.

```java
class Bird {
}
```

```java
interface Flyable {
    void fly();
}
```

```java
class Sparrow extends Bird implements Flyable {

    public void fly() {
        System.out.println("Sparrow flying");
    }
}
```

```java
class Penguin extends Bird {
}
```

Now penguin is not forced to fly.

This respects **LSP**.

---

# 📊 Design Comparison

## ❌ Before

```
Bird
 ├── Sparrow
 └── Penguin → fly() breaks
```

## ✅ After

```
Bird
 ├── Sparrow implements Flyable
 └── Penguin
```

---

# 🧠 Rules of LSP

A child class should:

✔ not break parent behavior  
✔ not throw new unexpected exceptions  
✔ not weaken expected results  
✔ not strengthen input conditions  
✔ maintain parent class contract  

---

# 🔗 LSP and Inheritance

LSP teaches us:

```
Inheritance must represent true behavior compatibility.
```

Just because two objects are related in the real world does not mean inheritance is correct in code.

---

# 🔗 LSP and Polymorphism

LSP ensures **polymorphism works safely**.

Example:

```java
Shape shape = new Rectangle();
Shape shape2 = new Square();
```

Both should behave correctly.

---

# 🧪 Testing Benefit

When LSP is followed:

- polymorphism becomes reliable
- tests remain stable
- child classes behave predictably

When LSP is violated:

- bugs appear at runtime
- unexpected crashes happen
- code becomes fragile

---

# 🚨 Signs LSP Is Violated

You may be breaking LSP if:

- subclass throws `UnsupportedOperationException`
- subclass changes expected behavior
- client code checks type using `instanceof`
- special conditions needed for subclasses

---

# 📈 Visual Architecture

```
                +-------+
                | Shape |
                +-------+
                  /   \
                 /     \
                /       \
        +-----------+  +-----------+
        | Rectangle |  |  Square   |
        +-----------+  +-----------+
```

Both behave correctly through the **Shape abstraction**.

---

# 🧠 One-line answer

> Liskov Substitution Principle states that child classes should be able to replace parent classes without breaking program correctness.

### Better answer

LSP ensures correct use of inheritance.  
If a subclass changes the expected behavior of the parent in a way that breaks client code, then the inheritance hierarchy is wrong.

---

# 🚀 Key Takeaway

```text
Child classes should extend behavior
not break behavior.
```

That is the essence of **Liskov Substitution Principle**.
