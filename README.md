# WebTables Automation Test Suite

This project contains automated UI tests for the WebTables page using Selenium WebDriver, Maven, and JUnit 5.

---
## Project Structure (POM – Page Object Model)

``` bash
📁 project-root/
│
├── 📁 src/
│   ├── 📁 main/
│   │   └── 📁 java/
│   │       └── org/
│   │           └── example/
│   │               ├── 📁 controller/         # Business logic and service coordination
│   │               ├── 📁 model/              # Data models (POJOs)
│   │               ├── 📁 pages/              # Page classes (UI locators & actions)
│   │               ├── 📁 utils/              # Utility classes (e.g., driver factory)
│   │               
│   │
│   ├── 📁 test/
│   │   └── 📁 java/
│   │       └── org/
│   │           └── example/
│   │               ├── 📁 UITests/            # UI automation tests
│   │               ├── 📁 APITests/           # API test classes
│   │               └── 📁 SmokeTests/         # Critical path tests (UI/API)
│

│
├── 📄 pom.xml                                 # Maven project config and dependencies
├── 📄 README.md                               # Project description, setup, and usage
└── 📄 ReTRO.md                                 # Retrospective notes, bugs, and improvements

```
## 🛠 Technologies Used

- **Java 21**
- **Maven** (build and dependency management)
- **Selenium WebDriver** (browser automation)
- **JUnit 5** (test framework)
- **GitHub** (version control)

---

## 📋 Prerequisites

- Java 21 installed and configured (`java -version` to check)
- Maven 3.8 or higher installed (`mvn -v` to check)
- Chrome browser installed
- ChromeDriver matching your Chrome version (automatically managed if using WebDriverManager)

---
# Design of Testing (How to Test)

## 1. How Testing Will Be Performed

- **Testing Type:** Automated UI Functional Testing.
- **Tools & Technologies:**
   - **Language:** Java 21
   - **Testing Framework:** JUnit 5
   - **Automation Library:** Selenium WebDriver
   - **Build Tool:** Maven
   - **Version Control:** Git (GitHub)
- **Test Approach:**
   - Each test will launch the browser using a centralized `WebDriverFactory`.
   - Page Object Model (POM) will be used to isolate page interactions and ensure reusable, maintainable code.
   - Tests will be executed locally through Maven, with plans to support CI via GitHub Actions later.
   - Assertions will validate application behavior based on the expected outcomes for each scenario.

---

## 2. Test Deliverables

- ✅ **Automation Test Suite Codebase** (Java + Selenium + JUnit 5)
- ✅ **Test Case Scripts** (for Add, Edit, Delete, and Search functionality)
- ✅ **README.md** with setup and execution instructions
- ✅ **Test Execution Report** (generated via  Allure )

---

## 3. Test Completion Criteria

Testing will be considered complete when the following conditions are met:

- 100% of planned test cases are implemented and executed.
- All critical defects are fixed and retested.
- The test pass rate is ≥ 95% (with justified exclusions).
- The application under test behaves as expected under the defined scenarios.

---

## 5. Test Data Requirements

- Valid employee entries (name, email, age, salary, department)
- Edge cases (long names, invalid emails, boundary salary values)
- Test data may be hardcoded or read from external sources (e.g., CSV/JSON)

---

## 6. Test Environment Requirements

- **Local Machine Setup:**
   - JDK 21
   - Maven 3.8+
   - Chrome Browser (latest stable)
   - Internet Connection (for demo site)

- Optional: GitHub Actions setup for future CI/CD integration.

---

## 7. Retesting and Regression Testing

- **Retesting:** On failed tests after bug fixes.
- **Regression Testing:** After major UI/functionality changes.

---

## 8. Suspension and Resumption Criteria

### Suspension Criteria:
- Environment becomes unstable.
- Critical defect blocks further testing (e.g., site is down).

### Resumption Criteria:
- Blockers are resolved.
- Test environment is stable.
- QA Lead approval.

---
# Automated Test Suite for Web Table Management Interface

| Test suite ID | WS001                                                                                                                                                                                                     |
|---------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Title**    | Verify CRUD operations and search on Web Tables page (`https://demoqa.com/webtables`)                                                                                                                     |
| **Precondition** | - Browser with Java & Selenium setup<br>- Web page loaded (`https://demoqa.com/webtables`)                                                                                                                |
| **Test data**  | - Valid employee data with name, email, age, salary, department<br>- Invalid emails: `abc@@example`, `no-at-symbol.com`<br>- Negative age values: `-1`, `-10`<br>- Special characters for search: `%$#@!` |

---

## Test Cases 

| Test Case ID | Title                                    | Steps                                                                                              | Expected Result                                                   |
|--------------|------------------------------------------|--------------------------------------------------------------------------------------------------|------------------------------------------------------------------|
| TC1          | Add a new employee record                | 1. Open the web tables page<br>2. Click "Add" button<br>3. Fill all required fields with valid data<br>4. Submit form | New record appears in the table with the entered details         |
| TC1.1        | Add new record with missing mandatory fields | 1. Click "Add" button<br>2. Leave required fields (e.g., First Name or Email) empty<br>3. Submit form | Form validation error displayed, record is NOT added             |
| TC1.2        | Add new record with invalid email format | 1. Click "Add" button<br>2. Enter invalid email (e.g., `abc@@example`)<br>3. Submit form            | Email format validation error displayed, record is NOT added     |
| TC1.3        | Add new record with negative age         | 1. Click "Add" button<br>2. Enter negative number in Age field<br>3. Submit form                   | Validation error or rejected input, record is NOT added          |
| TC2          | Edit an existing record’s salary         | 1. Locate a record<br>2. Click "Edit" button<br>3. Change the salary field<br>4. Save changes      | Updated salary value is visible in the record row                |
| TC2.1        | Edit record with invalid salary (text instead of number) | 1. Click "Edit" on a record<br>2. Enter non-numeric value in Salary<br>3. Save changes             | Validation error displayed, changes are NOT saved                 |
| TC3          | Delete a record                          | 1. Locate a record<br>2. Click "Delete" button                                                    | Record no longer appears in the table                            |
| TC4          | Search/filter by name or email           | 1. Enter a valid name or email in the search box                                                 | Table filters and shows only matching records                    |
| TC4.1        | Search with invalid input (special characters, SQL injection attempt) | 1. Enter special characters or malicious input in search box<br>2. Observe results               | No crash; safe error handling; no records shown or normal empty results |



## 🚀 How to Run Tests

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/webtable-tests.git
   cd webtable-tests
2. **Compile the project**
   ```bash
   mvn clean compile
3. **Run tests with Maven**
   ```bash 
      mvn test


## 📊 How to Open Allure Report
### Installation
https://allurereport.org/docs/install/

## Getting started with Allure JUnit 5
#### First, add Allure dependencies to the pom.xml
```bash
  <dependency>
   <groupId>io.qameta.allure</groupId>
   <artifactId>allure-junit5</artifactId>
   <version>2.29.1</version>
   <scope>test</scope>
</dependency>
 ```


Make sure to run your tests first so that Allure can generate the result files:

1. **Run Your Tests**
   ```bash
   mvn clean compile
2. **Generate  the Report**
   ```bash
   allure generate
3. **Open the report**
   ```bash 
      allure open

   
