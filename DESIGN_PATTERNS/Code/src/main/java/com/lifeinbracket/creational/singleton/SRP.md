# 🔐 Singleton Pattern

> 🚀 Not just a pattern — a **design decision** that impacts scalability, testability, and architecture.

---

## 📚 Table of Contents

* [What is Singleton?](#-what-is-singleton)
* [Why do we need it?](#-why-do-we-need-it)
* [Core Idea](#-core-idea)
* [Evolution (Problem → Fix → Trade-off)](#-evolution-problem--fix--trade-off)
* [Ways Singleton Breaks](#-ways-singleton-breaks)
* [Fully Protected Singleton](#-fully-protected-singleton)
* [Enum Singleton (Best)](#-enum-singleton-best)
* [Real-World Examples (Java + Spring)](#-real-world-examples-java--spring)
* [System Design Perspective](#-system-design-perspective)
* [Singleton vs SOLID](#-singleton--solid)
* [Pros & Cons](#-pros--cons)
* [When to Use / Avoid](#-when-to-use--avoid)
* [Quick Revision Cheatsheet](#-quick-revision-cheatsheet)

---

# 📌 What is Singleton?

Singleton ensures:

> ✅ Only **one instance** exists
> ✅ Provides a **global access point**

---

# 🎯 Why do we need it?

Some resources must be shared:

* 🗄️ DB connection / pool
* ⚙️ Config manager
* 📊 Logging system
* 🌐 Thread pools

---

## ❌ Without Singleton

```java
Config c1 = new Config();
Config c2 = new Config();
```

👉 ❌ Multiple instances → inconsistent state + wasted memory

---

# 🧠 Core Idea

| Step                | Purpose                   |
| ------------------- | ------------------------- |
| private constructor | prevent external creation |
| static instance     | hold single object        |
| public method       | controlled access         |

---

# 🔄 Evolution (Problem → Fix → Trade-off)

---

## 1️⃣ Eager Initialization

```java
private static final Singleton instance = new Singleton();
```

✔ Simple + Thread-safe
❌ Creates object even if unused

---

## 2️⃣ Lazy Initialization

```java
if (instance == null) {
    instance = new Singleton();
}
```

✔ Memory efficient
❌ Not thread-safe

---

## 3️⃣ Synchronized Method

```java
public static synchronized Singleton getInstance()
```

✔ Thread-safe
❌ Performance overhead

---

## 4️⃣ Double Checked Locking (DCL)

```java
if (instance == null) {
    synchronized (Singleton.class) {
        if (instance == null) {
            instance = new Singleton();
        }
    }
}
```

✔ Thread-safe + efficient

---

## 5️⃣ volatile

```java
private static volatile Singleton instance;
```

✔ Prevents instruction reordering

---

# 🔓 Ways Singleton Breaks

---

## 🪞 Reflection

👉 bypass private constructor

## 💾 Serialization

👉 creates new object

## 🧬 Cloning

👉 duplicate instance

---

# 🛡️ Fully Protected Singleton

✔ DCL + volatile
✔ constructor guard
✔ readResolve()
✔ clone protection

❗ Still complex and error-prone

---

# 👑 Enum Singleton (Best)

```java
public enum Singleton {
    INSTANCE;
}
```

---

## 🔥 Why Enum Wins

* JVM-controlled instantiation
* No reflection creation
* Serialization safe
* Clone safe
* Minimal code

> 💡 “Best practice in modern Java”

---

# 🌍 Real-World Examples (Java + Spring)

---

## 🔹 Spring Beans

```java
@Bean
public MyService myService() {
    return new MyService();
}
```

👉 Default scope = **Singleton**

---

## 🔹 Logger

```java
Logger logger = LoggerFactory.getLogger(MyClass.class);
```

👉 Shared instance

---

## 🔹 Runtime

```java
Runtime runtime = Runtime.getRuntime();
```

👉 Classic Singleton

---

## 🔹 Cache / Config

* Redis clients
* App configs
* Feature flags

---

# 🏗️ System Design Perspective

---

## ❗ Important Reality

> Singleton is **per JVM**, NOT global system-wide

---

## 🚫 In Distributed Systems

❌ Each service instance has its own Singleton

👉 So:

* Not globally unique
* Not shared across services

---

## ✅ Real Solution for Global “Singleton”

* Distributed cache (Redis)
* Zookeeper / coordination service
* DB-based locks

---

# 🧩 Singleton & SOLID

---

## ❗ SRP Violation

Singleton often:

* controls instance
* contains logic

👉 Violates SRP

---

## ❗ DIP Violation

Hard-coded global access:

```java
Singleton.getInstance()
```

👉 tight coupling

---

## ✅ Better Approach

* Use dependency injection
* Avoid direct Singleton calls

---

# ⚖️ Pros & Cons

---

## ✅ Pros

* controlled access
* memory efficient
* shared state
* easy to use

---

## ❌ Cons

* global state
* hard testing
* tight coupling
* hidden dependencies
* bad for scalability

---

# ⚖️ When to Use / Avoid

---

## ✅ Use When

* config manager
* logger
* cache
* connection pool

---

## ❌ Avoid When

* need multiple instances
* unit testing heavy
* distributed system
* high scalability required


---

## 💬 Best Singleton?

> Enum Singleton — thread-safe, serialization-safe, reflection-resistant.


---

## 💬 Is Singleton always good?

> No. It introduces global state and tight coupling.
> # ⚠️ Why Singleton Can Be Problematic (Deep Dive)

---

## 🌍 1. Global State Problem

Singleton behaves like a **controlled global variable**.

```java
Config config = Config.getInstance();
```

This can be accessed and modified from **anywhere in the application**.

---

### 🔴 Why this is dangerous?

#### ❌ Hidden Data Flow

You don’t know:

* who changed the state
* when it changed
* why it changed

```java
Config.getInstance().setMode("TEST");
```

Later:

```java
if (Config.getInstance().getMode().equals("PROD")) {
    // unexpected behavior
}
```

👉 Debugging becomes extremely difficult.

---

#### ❌ Side Effects Across Modules

One module modifies Singleton → affects all others

```java
Cache.getInstance().clear();
```

💥 Suddenly:

* APIs slow down
* data missing
* unexpected failures

---

#### ❌ Hard to Reason About

Instead of local logic:

> “This function depends on these inputs”

It becomes:

> “This function may depend on some global state modified elsewhere”

---

---

## 🔗 2. Tight Coupling Problem

### ❌ Direct Dependency on Concrete Class

```java
public class OrderService {

    public void placeOrder() {
        PaymentService ps = PaymentService.getInstance();
        ps.pay();
    }
}
```

👉 `OrderService` is tightly coupled to `PaymentService`

---

### 🔴 Why this is bad?

#### ❌ Hard to Replace Implementation

If tomorrow:

* new payment provider
* mock service
* external API

👉 You must modify existing code

---

#### ❌ Violates Dependency Inversion Principle (DIP)

Instead of:

```java
PaymentService ps = PaymentService.getInstance();
```

Better:

```java
public OrderService(PaymentService ps) {
    this.ps = ps;
}
```

👉 depend on abstraction, not concrete

---

#### ❌ Hidden Dependency

Method signature:

```java
placeOrder()
```

But internally depends on:

```java
PaymentService.getInstance()
```

👉 Dependency is **invisible**

---

---

## 🧪 3. Testing Becomes Difficult

Singleton makes unit testing harder.

### ❌ Problem

```java
PaymentService.getInstance()
```

👉 Cannot easily replace with:

* mock
* stub
* fake implementation

---

### ❌ Shared State Between Tests

```java
SingletonCache.getInstance().put("A", 1);
```

Next test:

```java
SingletonCache.getInstance().get("A"); // unexpected value
```

👉 Tests become **dependent and flaky**

---

### ❌ No Isolation

Each test should be independent, but Singleton breaks this rule.

---

---

## 📦 4. Poor Scalability in Distributed Systems

Singleton is:

> ✅ Single instance per JVM
> ❌ NOT single instance per system

---

### ❌ In Microservices

If you have:

* 5 service instances
* each has its own Singleton

👉 You now have **5 "singletons"**

---

### ❌ Not Suitable For:

* distributed cache
* global locks
* shared counters

---

### ✅ Better Alternatives

* Redis
* Database
* Distributed locks (Zookeeper, etc.)

---

---

## ⚙️ 5. Concurrency Risks

Shared state + multiple threads:

```java
counter++;
```

👉 Can lead to:

* race conditions
* inconsistent state

---

---

## ⚖️ 6. Overuse / Misuse

Many developers use Singleton:

* just to avoid object creation
* as a shortcut for global access

👉 This leads to:

* poor design
* rigid architecture
* maintenance nightmare

---

---

# 🧠 Better Approach (Modern Design)

---

## ✅ Dependency Injection (Preferred)

Instead of:

```java
PaymentService ps = PaymentService.getInstance();
```

Use:

```java
public class OrderService {

    private final PaymentService ps;

    public OrderService(PaymentService ps) {
        this.ps = ps;
    }
}
```

---

## 💡 Benefits of DI

* ✅ Loose coupling
* ✅ Easy testing
* ✅ Clear dependencies
* ✅ Better scalability

---

# 🏁 Final Insight

> Singleton simplifies access but introduces hidden complexity.

---

## 🔥

> Singleton introduces global state and tight coupling, making systems harder to test, debug, and scale. That’s why modern architectures prefer dependency injection and stateless design over direct Singleton usage.


---

## 💬 Singleton in Spring?

> Default bean scope is Singleton, but managed via IoC container.

---

## 💬 Singleton in microservices?

> Not global — per instance only.

---

# 🧠 Quick Revision Cheatsheet

---

## 🔑 Core

* One instance
* Global access

---

## 🔥 Best Implementation

👉 `enum Singleton`

---

## ⚠️ Breaking Points

* Reflection
* Serialization
* Cloning

---

## 🛡️ Fixes

* constructor guard
* readResolve()
* clone override

---

## 🚀 Final Tip

> Prefer **Dependency Injection over Singleton** in modern systems.

---

# 🏁 Final Takeaway

> Singleton is powerful but dangerous if misused.
> Use it when necessary — avoid it when scalable design matters.

---

⭐ **Golden Line:**

> “Singleton is a controlled global state. prefer enum implementation for safety, but in scalable systems rely more on dependency injection and stateless design.”

---
