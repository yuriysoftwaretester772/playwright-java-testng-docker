```markdown
# Playwright Java TestNG Allure Automation with Docker

![GitHub Actions](https://github.com/yuriysoftwaretester772/playwright-java-testng-docker/actions/workflows/playwright-test.yml/badge.svg)

This project is a test automation framework built using **Playwright**, **Java**, **TestNG**, and **Allure** for reporting.

## Features
- Cross-browser testing with Playwright.
- Supports headless and headed modes.
- TestNG for test execution and management.
- Allure for detailed test reporting.
- Docker support for running tests in isolated environments.
- Multiple TestNG suites for different test categories.

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
- **Suite**: `testng-regression.xml`

Run tests with the default configuration:
```bash
mvn test
```

### Running Specific TestNG Suites
You can specify a TestNG suite using the `-DsuiteXmlFile` property:
- **Regression Suite**:
  ```bash
  mvn test -DsuiteXmlFile=testng-regression.xml
  ```
- **API Suite**:
  ```bash
  mvn test -DsuiteXmlFile=testng-api.xml
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

### Build the Docker Image
Build the Docker image for the project:
```bash
docker build -t playwright-tests .
```

### Run Tests in Docker
Run tests in Docker with the default configuration:
```bash
docker run --rm playwright-tests
```

### Run Specific TestNG Suites in Docker
You can specify a TestNG suite in Docker using the `-DsuiteXmlFile` property:
- **Regression Suite**:
  ```bash
  docker run --rm playwright-tests mvn test -DsuiteXmlFile=testng-regression.xml
  ```
- **API Suite**:
  ```bash
  docker run --rm playwright-tests mvn test -DsuiteXmlFile=testng-api.xml
  ```

### Run Tests in Different Browsers in Docker
Specify the browser in Docker using the `BROWSER` environment variable:
- **Firefox**:
  ```bash
  docker run --rm -e BROWSER=firefox playwright-tests
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
- Update the `testng.xml` files to configure test groups or parallel execution.
- For troubleshooting, check the logs in the `target` directory.
```