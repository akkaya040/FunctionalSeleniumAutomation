# Functional Selenium Web UI Automation

A functional Selenium-based UI test automation framework for [sahibinden.com](https://www.sahibinden.com), built with Java 17, Maven, and the **Page Object Model (POM)** design pattern.

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Maven | 3.x | Build & dependency management |
| Selenium | 4.41.0 | Browser automation |
| WebDriverManager | 6.3.3 | Automatic driver binary management |
| JUnit 5 | 5.10.1 | Test framework |
| AssertJ | 3.27.7 | Fluent assertions |
| Allure | 2.25.0 | Test reporting |
| Lombok | 1.18.30 | Boilerplate reduction (`@Slf4j`) |
| Logback / SLF4J | 1.4.14 / 2.0.17 | Logging |

## Prerequisites

- **Java 17+** — [Download](https://adoptium.net/)
- **Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **Google Chrome** (default browser) or Firefox
- **Allure CLI** (optional, for report generation) — [Install guide](https://allurereport.org/docs/install/)

## Project Structure

```
src/
├── main/java/com/sahibinden/test/
│   ├── base/
│   │   └── BasePage.java          # Abstract base for all page objects
│   │                              # (common helpers: findElement, click, waits)
│   ├── driver/
│   │   └── DriverManager.java     # ThreadLocal WebDriver lifecycle management
│   └── jLogger.java               # Static SLF4J logging wrapper
│
└── test/java/com/sahibinden/test/
    ├── base/
    │   └── BaseTest.java          # JUnit lifecycle (@BeforeEach / @AfterEach)
    ├── pages/
    │   ├── HomePage.java          # Page object: sahibinden.com home page
    │   └── LoginPage.java         # Page object: login page
    └── tests/
        └── HomePageTest.java      # Test class for home page scenarios
```

## Design Pattern: Page Object Model

Each page of the application is represented by a dedicated class that:

- Keeps **locators** (`By`) as `private static final` fields — not shared globally.
- Exposes **action methods** that return the next logical page object, enabling fluent chaining.
- Extends `BasePage`, which provides common Selenium helpers (`findElement`, `click`, `untilElementAppear`) and a pre-configured `WebDriverWait`.

```java
// Example: fluent test using POM
new HomePage(driver)
    .open()
    .closeAd()
    .clickLoginButton()   // returns LoginPage
    .enterEmail("user@example.com")
    .enterPassword("secret")
    .submit();
```

### Driver lifecycle

`DriverManager` uses a `ThreadLocal<WebDriver>` so each parallel test thread gets its own isolated browser instance. `BaseTest` handles init/quit automatically via JUnit 5 lifecycle hooks — no driver management needed in test classes.

## Running Tests

**Run all tests:**
```bash
mvn test
```

**Run a specific test class:**
```bash
mvn test -Dtest=HomePageTest
```

**Run a specific test method:**
```bash
mvn test -Dtest=HomePageTest#testOpenHomePage
```

**Run with Firefox instead of Chrome:**
Change the browser in `BaseTest.setUp()`:
```java
driver = DriverManager.initDriver(DriverManager.BrowserType.FIREFOX);
```

## Parallel Execution

Parallel execution is pre-configured in `pom.xml` via Maven Surefire and JUnit 5 platform properties:

- Up to **4 concurrent threads**
- Both classes and methods run concurrently
- Thread safety guaranteed by `ThreadLocal<WebDriver>` in `DriverManager`

## Test Reporting (Allure)

Allure results are written to `target/allure-results` automatically during `mvn test`.

**Generate and open the report:**
```bash
mvn allure:report        # generates static report in target/site/allure-maven-plugin/
mvn allure:serve         # generates and opens a live report in your browser
```

## Adding a New Page Object

1. Create a new class in `src/test/java/com/sahibinden/test/pages/` extending `BasePage`.
2. Declare locators as `private static final By` fields.
3. Implement action methods; return `this` to stay on the same page, or `new NextPage(driver)` when navigating away.
4. Add test methods in a new or existing class under `src/test/java/com/sahibinden/test/tests/` that extends `BaseTest`.

```java
public class SearchResultsPage extends BasePage {

    private static final By RESULT_ITEMS = By.cssSelector(".searchResultsItem");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public int getResultCount() {
        return driver.findElements(RESULT_ITEMS).size();
    }
}
```
