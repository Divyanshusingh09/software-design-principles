# 📘 SOLID Principles -- Foundations

> Introduced by **Robert C. Martin (Uncle Bob)**

SOLID is a set of **five object‑oriented design principles** that help
developers build **clean, flexible, scalable and maintainable software
systems**.

These principles guide developers in writing code that is:

-   easy to understand
-   easy to maintain
-   easy to extend
-   loosely coupled
-   highly cohesive

------------------------------------------------------------------------

# 🧠 What Does SOLID Stand For?

  Letter   Principle
  -------- ---------------------------------
  **S**    Single Responsibility Principle
  **O**    Open Closed Principle
  **L**    Liskov Substitution Principle
  **I**    Interface Segregation Principle
  **D**    Dependency Inversion Principle

------------------------------------------------------------------------

# 🎯 Why SOLID Principles Are Used?

SOLID principles help in building software that is:

### 1️⃣ Easy to Maintain

Code becomes easier to modify when requirements change.

### 2️⃣ Easy to Extend

New features can be added without modifying existing code.

### 3️⃣ Loosely Coupled

Modules depend less on each other.

### 4️⃣ Highly Cohesive

Each class focuses on a single responsibility.

### 5️⃣ Easy to Test

Unit testing becomes easier because dependencies are minimized.

### 6️⃣ Scalable System Design

Large applications remain manageable as they grow.

------------------------------------------------------------------------

# ⚠️ Example Problem Without SOLID

``` java
class OrderService {

    void createOrder(){}

    void saveToDatabase(){}

    void sendEmail(){}

}
```

### Problems

-   Multiple Responsibilities
-   Hard to Modify
-   Hard to Test
-   High Coupling

------------------------------------------------------------------------

# ✅ Better Design With SOLID

``` java
class OrderService {
    void createOrder(){}
}
```

``` java
class OrderRepository {
    void saveToDatabase(){}
}
```

``` java
class EmailService {
    void sendEmail(){}
}
```

### Benefits

✔ Clear responsibilities\
✔ Easy maintenance\
✔ Better testability\
✔ Flexible architecture

------------------------------------------------------------------------

# 🔗 Relationship With Coupling and Cohesion

SOLID principles help achieve:

    High Cohesion
    Low Coupling

These two concepts are the **foundation of good software design**.

------------------------------------------------------------------------

# 📌 Foundations for SOLID Principles

Before understanding SOLID principles we must understand:

-   Cohesion
-   Coupling

These directly affect **software quality**.

------------------------------------------------------------------------

# 🧩 Cohesion

> Cohesion is the degree to which elements of a module are related to
> each other.

If the responsibilities inside a class are strongly related, the class
has:

**High Cohesion**

------------------------------------------------------------------------

# 🗑 Garbage Bag Analogy

Imagine a garbage bag containing:

-   food waste
-   plastic
-   iron
-   paper

All mixed together → **Low Cohesion**

Now separate them:

  Bag   Content
  ----- ------------
  1     Plastic
  2     Food Waste
  3     Paper
  4     Metal

Now items inside each container are related → **High Cohesion**

------------------------------------------------------------------------

# 💻 Cohesion Example in Code

``` java
class Square {

    getArea()

    getPerimeter()

    draw()

    rotate()

}
```

### Responsibilities

  Method           Responsibility
  ---------------- ----------------
  getArea()        Geometry
  getPerimeter()   Geometry
  draw()           Rendering
  rotate()         Rendering

This mixes multiple responsibilities → **Low Cohesion**

------------------------------------------------------------------------

# ✅ Improved Design

``` java
class Square {

    double getArea(){}
    double getPerimeter(){}

}
```

``` java
class SquareRenderer {

    void draw(){}
    void rotate(){}

}
```

Now:

Square → Geometry logic\
SquareRenderer → Rendering logic

**High Cohesion achieved**

------------------------------------------------------------------------

# 🔗 Coupling

> Coupling is the degree of dependency between classes.

If one class depends heavily on another → **High Coupling**

Goal in software design:

    Low Coupling
    High Cohesion

------------------------------------------------------------------------

# 🎯 Ideal Software Architecture

A well‑designed system should look like:

            +------------------+
            |   OrderService   |
            +------------------+
                    |
                    |
            +------------------+
            | OrderRepository  |
            +------------------+
                    |
                    |
            +------------------+
            |   EmailService   |
            +------------------+

Each class has **one clear responsibility**.

------------------------------------------------------------------------

# 🧠 One Line Answer

**SOLID principles are five object‑oriented design principles used to
create maintainable, scalable and loosely coupled software systems.**

------------------------------------------------------------------------

