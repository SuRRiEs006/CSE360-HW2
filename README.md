# CSE360-HW2

# HW2 — Q&A CRUD + Validation (Java, Maven)

Standalone Java **console** app that demonstrates Create/Read/Update/Delete (CRUD) and **input validation** for:
- Single items: `Question`, `Answer`
- Collections (with subset views): `Questions`, `Answers`  
Includes JUnit 5 tests and UML (Class + Sequence) diagrams.

---

## Features

- **Questions**
  - Create, Read (by id), Update, Delete (cascade deletes answers)
  - Search & filter (keyword in title/body, status, tags)
  - Status: `OPEN`, `ANSWERED`, `CLOSED`

- **Answers**
  - Create (requires existing question), Read (by question), Update, Delete
  - **Accept exactly one** answer per question (enforced); on accept → question becomes `ANSWERED`

- **Validation**
  - Title: 1–120 chars; Body: 1–2000; Author: non-empty
  - `Answer.questionId` must reference an existing `Question`
  - Invalid status filters produce clear errors

---

## Prerequisites

- **Java 17+**
- **Maven 3.9+**

---

## Quick Start

```bash
# run tests
mvn test

# run the console app
mvn exec:java
