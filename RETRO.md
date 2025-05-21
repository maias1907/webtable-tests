# 🧠 Retrospective - Web Tables Automation Project

## ✅ What Went Well

- Successfully automated key test scenarios including:
    - Adding a new employee record
    - Editing an employee's salary
    - Deleting a record
    - Searching/filtering records by name or email
- Page Object Model (POM) was applied, making the test code modular and maintainable.
- GitHub repository was properly initialized with clean structure and version control.
- Maven was used to manage dependencies effectively.

## ❗ What Was Challenging

- **Element identification:** Locating the correct elements in the dynamic web table structure was tricky due to similar class names and deeply nested elements.
- **Handling dynamic rows:** Some tests initially failed because "pad rows" (placeholders) were being picked up, causing flaky results.
- **Wait conditions:** Needed to introduce explicit waits to handle DOM readiness and avoid `NoSuchElementException`.

## 💡 What I Learned

- The importance of using `WebDriverWait` with proper conditions (like `visibilityOfElementLocated`) to make tests stable.
- How to split logic cleanly between test classes and page object classes using the Page Object Model.
- Using `@BeforeEach` and `@AfterEach` in JUnit 5 to keep tests isolated and consistent.
- How to structure a Maven project properly for automated testing.

## 🔄 What I'd Improve Next Time

- Add full HTML test reports (e.g., using Allure or ExtentReports) to make test results more readable.
- Extract test data to external files (like JSON or CSV) for better scalability and data-driven testing.
- Write negative test cases and validation for edge cases (e.g., searching with empty strings, invalid emails).
- Add CI integration using GitHub Actions for automated test runs on each push.

---

### 🙌 Final Thoughts

This project gave me a solid understanding of building a real-world automation test suite from scratch using Selenium, Java, and JUnit. It also helped improve my debugging, test structuring, and Git collaboration skills.
