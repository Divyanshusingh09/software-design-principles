# 🟡 Single Responsibility Principle (SRP)

> 💡 **Definition**  
> **"A class should have only one reason to change."**  

The **Single Responsibility Principle (SRP)** says that a class, module, or function should focus on **one responsibility only**.

In simple words:

```text
One class = One job
```

If a class handles multiple responsibilities, then changes from different parts of the system start hitting the **same class** again and again.  
That makes code harder to maintain, harder to test, and more likely to break.

---

# 🎯 Core Idea

A responsibility does **not** mean just "one method".

A responsibility means:

- one business role
- one area of concern
- one reason to change

So SRP says:

> Keep related behavior together, and unrelated behavior separate.

---

# 🧠 Why SRP is Important

When one class does too many things:

- ❌ logic gets mixed
- ❌ code becomes harder to understand
- ❌ changes become risky
- ❌ testing becomes difficult
- ❌ bugs spread more easily

SRP improves:

- ✅ readability
- ✅ maintainability
- ✅ testability
- ✅ high cohesion
- ✅ cleaner architecture

---

# ⚠️ Example Without SRP

Suppose we have a class like this:

```java
class OrderService {

    void createOrder() {
        // business logic
    }

    void saveToDatabase() {
        // persistence logic
    }

    void sendEmail() {
        // notification logic
    }

    void generateInvoicePdf() {
        // report / document logic
    }
}
```

At first glance this may look okay, but this class is doing **too many jobs**.

---

# ❌ Problems in This Design

| Method | Responsibility |
|------|------|
| `createOrder()` | Business logic |
| `saveToDatabase()` | Database / persistence |
| `sendEmail()` | Notification |
| `generateInvoicePdf()` | Reporting / document generation |

That means this class has **multiple reasons to change**.

### Different possible changes

- order creation rules change
- database technology changes
- email format changes
- PDF format changes

All those changes affect the **same class**.

This is a clear **SRP violation**.

---

# 📌 Why This is Bad

A class like this becomes:

- tightly packed
- less reusable
- difficult to test
- difficult to debug
- difficult to scale

Even a small change may impact unrelated behavior.

---

# ✅ Applying SRP

We separate each responsibility into its own class.

## 1️⃣ Order Business Logic

```java
class OrderService {

    void createOrder() {
        System.out.println("Order created");
    }
}
```

## 2️⃣ Database Responsibility

```java
class OrderRepository {

    void save() {
        System.out.println("Order saved to database");
    }
}
```

## 3️⃣ Notification Responsibility

```java
class EmailService {

    void sendOrderConfirmation() {
        System.out.println("Order confirmation email sent");
    }
}
```

## 4️⃣ Document Responsibility

```java
class InvoicePdfGenerator {

    void generate() {
        System.out.println("Invoice PDF generated");
    }
}
```

Now each class has **one clear responsibility**.

---

# 📊 Before vs After SRP

## ❌ Before

```text
OrderService
 ├── createOrder()
 ├── saveToDatabase()
 ├── sendEmail()
 └── generateInvoicePdf()
```

One class doing many jobs.

## ✅ After

```text
OrderService         → business logic
OrderRepository      → persistence
EmailService         → notifications
InvoicePdfGenerator  → document generation
```

Clean separation of concerns.

---

# 💡 Real Life Example – Hospital

Think about a **hospital**.

Would one single person do all of these?

- check patients
- perform surgery
- manage billing
- clean rooms
- give medicines
- maintain records

No.

Responsibilities are separated:

- doctor
- nurse
- billing staff
- cleaner
- pharmacist
- receptionist

Each role has **one focused responsibility**.

That is exactly what SRP says.

```text
One role = One job
```

---

# 💡 Real Life Example – Restaurant

In a restaurant:

- **chef** cooks food
- **waiter** takes order
- **cashier** handles payment
- **manager** supervises operations

If one person tries to do everything, chaos happens.

Same in software.

If one class tries to do everything, maintenance becomes painful.

---

# 🧩 Another Code Example – Invoice

## ❌ Bad Design

```java
class InvoiceService {

    void calculateTotal() {
        // business calculation
    }

    void saveInvoice() {
        // save in DB
    }

    void printInvoice() {
        // print invoice
    }

    void sendInvoiceEmail() {
        // email invoice
    }
}
```

This class has many responsibilities.

---

## ✅ Better Design

```java
class InvoiceCalculator {
    double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}
```

```java
class InvoiceRepository {
    void saveInvoice() {
        System.out.println("Invoice saved");
    }
}
```

```java
class InvoicePrinter {
    void printInvoice() {
        System.out.println("Invoice printed");
    }
}
```

```java
class InvoiceEmailService {
    void sendInvoiceEmail() {
        System.out.println("Invoice email sent");
    }
}
```

---

# 🧠 Important Point

SRP does **not** mean:

> one class should have only one method

That is wrong.

A class can have multiple methods, as long as all methods support **one responsibility**.

### Example

```java
class UserValidator {

    boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    boolean isPhoneValid(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8;
    }
}
```

This class has multiple methods, but all methods belong to **validation**.

So it still follows SRP.

---

# 🔗 SRP and Cohesion

SRP strongly supports **high cohesion**.

If a class has one focused responsibility, then all methods inside it are strongly related.

```text
High Cohesion = Related behavior stays together
```

That makes code cleaner and easier to maintain.

---

# 🔗 SRP and Coupling

SRP also helps reduce unnecessary coupling.

When responsibilities are separated properly:

- classes depend on fewer things
- change impact becomes smaller
- code becomes easier to reuse

---

# 🧪 Testing Advantage

A class with one responsibility is much easier to test.

For example:

- testing `InvoiceCalculator` is easy
- testing `InvoiceService` that also does DB + email + printing is much harder

Small focused classes are better for **unit testing**.

---

# 🚨 Signs SRP is Being Violated

You should be careful if you hear sentences like:

- "This class handles everything"
- "Just add one more method here"
- "This utility class does all project work"
- "Any change in report logic breaks email logic"
- "We are afraid to touch this class"

These are common warning signs of SRP violation.

---

# 📈 Visual Diagram

## ❌ Before SRP

```text
              +------------------+
              |   OrderService   |
              +------------------+
              | createOrder()    |
              | saveToDatabase() |
              | sendEmail()      |
              | generatePdf()    |
              +------------------+
```

## ✅ After SRP

```text
   +------------------+     +--------------------+
   |   OrderService   |     |  OrderRepository   |
   +------------------+     +--------------------+
   | createOrder()    |     | save()             |
   +------------------+     +--------------------+

   +------------------+     +----------------------+
   |   EmailService   |     | InvoicePdfGenerator  |
   +------------------+     +----------------------+
   | sendEmail()      |     | generate()           |
   +------------------+     +----------------------+
```

---

# 🧠 Interview Answer

### One-line answer

> Single Responsibility Principle says that a class should have only **one reason to change**.

### Better interview answer

SRP means a class should focus on one responsibility only.  
If one class handles business logic, database logic, notification logic, and reporting logic together, it becomes hard to maintain and test.  
So we separate those concerns into different classes, which improves cohesion, readability, and testability.

---

# 🚀 Practical Java Example

```java
class OrderService {
    public void createOrder() {
        System.out.println("Creating order...");
    }
}
```

```java
class OrderRepository {
    public void saveOrder() {
        System.out.println("Saving order...");
    }
}
```

```java
class NotificationService {
    public void sendNotification() {
        System.out.println("Sending notification...");
    }
}
```

```java
class InvoiceService {
    public void generateInvoice() {
        System.out.println("Generating invoice...");
    }
}
```

Each class now has a single clear purpose.

---

# ✅ Key Takeaway

```text
Do one thing
Do it well
Keep responsibilities separate
```

That is the heart of **Single Responsibility Principle**.
