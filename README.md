# Playwright Java TestNG Allure Automation with Docker

This project is a test automation framework built using **Playwright**, **Java**, **TestNG**, and **Allure** for reporting.

## Features
- Cross-browser testing with Playwright.
- Supports headless and headed modes.
- TestNG for test execution and management.
- Allure for detailed test reporting.
- Docker support for running tests in isolated environments.

---

## Prerequisites
1. **Java**: Install JDK 11 or higher.
2. **Maven**: Install Maven for dependency management.
3. **Node.js**: Install Node.js (required for Playwright).
4. **Docker**: Install Docker if you want to run tests in containers.
5. **Allure**: Install Allure for generating test reports.

---

## Installation and Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo-name.git
   cd your-repo-name
   ```

2. Install Playwright dependencies:
   ```bash
   npx playwright install
   ```

3. Install Maven dependencies:
   ```bash
   mvn clean install
   ```

4. Verify setup:
   ```bash
   mvn test
   ```

---

## Running Tests Locally

### Default Configuration
- **Browser**: Chromium
- **Mode**: Headless

Run tests with the default configuration:
```bash
mvn test
```

### Running in Different Browsers
You can specify the browser using the `BROWSER` system property:
- **Chromium** (default):
  ```bash
  mvn test -DBROWSER=chromium
  ```
- **Firefox**:
  ```bash
  mvn test -DBROWSER=firefox
  ```
- **Webkit**:
  ```bash
  mvn test -DBROWSER=webkit
  ```

### Running in Headed Mode
To run tests in headed mode, set the `HEADLESS` property to `false`:
```bash
mvn test -DHEADLESS=false
```

---

## Running Tests in Docker
1. Build the Docker image:
   ```bash
   docker build -t playwright-java-tests .
   ```

2. Run tests in Docker:
   ```bash
   docker run --rm playwright-java-tests
   ```

3. Specify a browser in Docker:
   ```bash
   docker run --rm -e BROWSER=firefox playwright-java-tests
   ```

---

## Generating Allure Reports
1. Run tests and generate Allure results:
   ```bash
   mvn test
   ```

2. Serve the Allure report:
   ```bash
   allure serve target/allure-results
   ```

---

## Additional Notes
- Ensure Docker has access to the X11 server if running headed tests in Docker.
- Update the `testng.xml` file to configure test groups or parallel execution.
- For troubleshooting, check the logs in the `target` directory.