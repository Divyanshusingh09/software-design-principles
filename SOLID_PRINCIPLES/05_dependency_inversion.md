
# 🔴 Dependency Inversion Principle (DIP)

> **"High-level modules should not depend on low-level modules. Both should depend on abstractions."**  

The **Dependency Inversion Principle** says that high-level business logic should **not depend directly on low-level implementation classes**.

Instead both should depend on **abstractions (interfaces)**.

---

# 🎯 Core Idea

```text
High Level Module
        ↓
     Interface
        ↑
Low Level Module
```

Instead of:

```
High Level → Low Level
```

We do:

```
High Level → Interface ← Low Level
```

---

# ⚠️ Example Without DIP

```java
class MySQLDatabase {

    void save(String data) {
        System.out.println("Saved to MySQL");
    }
}
```

```java
class OrderService {

    private MySQLDatabase database = new MySQLDatabase();

    void placeOrder(String order) {
        database.save(order);
    }
}
```

### 🚨 Problem

If tomorrow we change database to:

- PostgreSQL
- MongoDB
- Oracle

We must modify **OrderService**.

High-level module depends directly on low-level module.

---

# ✅ Applying DIP

Create an abstraction.

```java
interface Database {

    void save(String data);
}
```

---

# Implementations

```java
class MySQLDatabase implements Database {

    public void save(String data) {
        System.out.println("Saved to MySQL");
    }
}
```

```java
class MongoDatabase implements Database {

    public void save(String data) {
        System.out.println("Saved to MongoDB");
    }
}
```

---

# High-Level Module

```java
class OrderService {

    private Database database;

    OrderService(Database database) {
        this.database = database;
    }

    void placeOrder(String order) {
        database.save(order);
    }
}
```

Now `OrderService` depends on **Database interface**, not on a specific database.

---

# 💡 Real Life Example

Think about a **phone charger**.

Your phone does not depend on a specific charger brand.

Instead it depends on a **USB standard**.

Different chargers implement that standard.

```
Phone → USB Interface ← Charger
```

This is Dependency Inversion Principle.

---

# 📊 Before vs After

## ❌ Before

```
OrderService → MySQLDatabase
```

Tightly coupled.

## ✅ After

```
OrderService → Database Interface ← MySQLDatabase
                               ← MongoDatabase
```

Loosely coupled.

---

# 🧠 Benefits of DIP

✔ Low coupling  
✔ Flexible architecture  
✔ Easy testing  
✔ Easy replacement of implementations  
✔ Works well with Dependency Injection

---

# 🧪 Testing Advantage

We can easily use **mock implementations**.

```java
class MockDatabase implements Database {

    public void save(String data) {
        System.out.println("Mock save");
    }
}
```

This helps in **unit testing**.

---

# 🧠 Short Answer

> Dependency Inversion Principle states that high-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

---

# 🚀 Key Takeaway

```text
Depend on abstractions
Not on concrete implementations
```

That is the heart of **Dependency Inversion Principle**.
