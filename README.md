# Assignment 6: Smart Code Inspection & Compliance Engine

## 1. Core Architecture & Visitor Pattern
- **Problem:** Avoid `instanceof` checks, large `switch` statements, and mixing inspection logic directly inside element classes, adhering to the Open/Closed Principle (OCP).
- **Context:** The project contains various software file elements (`SourceCodeFile`, `TestFile`, `ConfigurationFile`, `DependencyFile`, `Directory`) that must be processed by different operations (Security Audit, Statistics, Quality Gates, Compliance, License Audit).
- **Pattern Mechanism:** The Visitor pattern uses **double dispatch** to decouple operations from the object structure. Elements accept a visitor, and the visitor executes the appropriate overloaded `visit(...)` method for that concrete element type.

---

## 2. Element Hierarchy & Structure
- **Interface:** `ProjectElement` defines `void accept(ProjectVisitor visitor);`[cite: 1].
- **Concrete Elements:**
    - `SourceCodeFile` (lines of code, complexity, security flags)[cite: 1]
    - `TestFile` (test counts, lines)[cite: 1]
    - `ConfigurationFile` (secure settings)[cite: 1]
    - `DependencyFile` (list of libraries, vulnerability flags)[cite: 1]
    - `Directory` (contains nested `ProjectElement` items, supporting recursive traversal)[cite: 1]

---

## 3. Difference between Visitor and Strategy
- **Visitor:** Focuses on adding new **operations** over a stable object hierarchy without changing the classes of those objects.
- **Strategy:** Focuses on selecting and swapping interchangeable **algorithms** at runtime to achieve a specific single task.

---

## 4. SOLID Principles Applied
- **Single Responsibility Principle (SRP):** Each visitor handles a single specific concern (e.g., `SecurityAuditVisitor` handles only security checks).
- **Open/Closed Principle (OCP):** New visitors (like `LicenseAuditVisitor`) can be added without modifying any existing element classes.
- **Liskov Substitution Principle (LSP):** Concrete elements and visitors can be replaced seamlessly through their respective interfaces.
- **Interface Segregation Principle (ISP):** `ProjectVisitor` and `ProjectElement` define focused, cohesive contracts.
- **Dependency Inversion Principle (DIP):** High-level modules and operations depend on abstractions (`ProjectElement`, `ProjectVisitor`).

---

## 5. UML Diagrams
### Class Diagram
![Class Diagram](docs/uml/uml1.png)

### Sequence Diagram
![Sequence Diagram](docs/diagrams/diagram.png)

---

## 6. How to Add a New Visitor vs Element
- **Adding a New Visitor:** Create a class implementing `ProjectVisitor` and implement all `visit(...)` methods. **No modification to existing elements is required.**
- **Adding a New Element (e.g., `DatabaseMigrationFile`):** Requires modifying the `ProjectVisitor` interface and **every single concrete visitor** to implement `visit(DatabaseMigrationFile)`. This highlights the limitation of the Visitor pattern when the element structure changes frequently.

---

## 7. Architecture Limitations
- Visitor is a poor choice if the object structure changes frequently, because adding a new concrete element forces updates across all existing visitor implementations.

---

## 8. README Questions & Answers

1. **What problem does Visitor solve in your project?**  
   It decouples complex operations (security audits, statistics, quality gates) from the data structure, preventing bloated element classes and eliminating `instanceof` / `switch` type-checking blocks[cite: 1].
2. **Where are Element and Visitor?**  
   Elements are located in `com.inspection.engine.elements`, and Visitors are located in `com.inspection.engine.visitors`.
3. **What is double dispatch?**  
   A mechanism where the executed operation depends on *two* message receptions: first, the concrete type of the element receiving `accept(visitor)`, and second, the concrete type of the visitor receiving `visit(this)`.
4. **Why not put all operations directly inside the Element classes?**  
   It would violate the Single Responsibility Principle, clutter element classes with unrelated code (security, stats, compliance), and violate OCP whenever a new check is added.
5. **Is it easier to add a new Visitor or a new Element? Why?**  
   It is much easier to add a new **Visitor**, because it requires zero modifications to existing element classes. Adding a new Element requires updating the Visitor interface and all existing visitors.
6. **When would Visitor be a bad choice?**  
   When the domain model (element hierarchy) is unstable and frequently changes or expands with new element types.
7. **How is Visitor different from Strategy?**  
   Visitor targets a stable structure of heterogeneous objects to perform multi-type operations, whereas Strategy encapsulates a single algorithm to swap behavior dynamically.
8. **Which SOLID principles can you identify?**  
   Open/Closed Principle (OCP), Single Responsibility Principle (SRP), Dependency Inversion Principle (DIP), Liskov Substitution Principle (LSP), and Interface Segregation Principle (ISP).

---

## 9. Test Results
All JUnit 5 tests execute successfully:
- Element acceptance & nested directories -> PASSED
- `SecurityAuditVisitor` checks -> PASSED
- `ProjectStatisticsVisitor` calculations -> PASSED
- `QualityGateVisitor` (PASS and FAIL scenarios) -> PASSED
- `ComplianceVisitor` rule validations -> PASSED
- Additional `LicenseAuditVisitor` -> PASSED
- Edge cases (empty directories, invalid inputs) -> PASSED

---

## 10. How to Run
```bash
# Run unit tests
mvn clean test

# Compile and execute the fintech project demo
mvn compile exec:java -Dexec.mainClass="com.inspection.engine.Main"