# Playwright Java TestNG Allure Automation with Docker

![GitHub Actions](https://github.com/yuriysoftwaretester772/playwright-java-testng-docker/actions/workflows/playwright-test.yml/badge.svg?branch=master)

This project is a test automation framework built using **Playwright**, **Java**, **TestNG**, and **Allure** for reporting.

## Features

- Cross-browser testing with Playwright
- Supports headless and headed modes
- TestNG for test execution and management
- Allure for detailed test reporting
- Docker support for isolated test environments
- Multiple TestNG suites for different test categories

## Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: For dependency management
- **Node.js**: Required for Playwright
- **Docker**: For containerized test runs
- **Allure**: To generate reports

## Installation and Setup

```bash
git clone https://github.com/your-repo-name.git
cd your-repo-name
npx playwright install
mvn clean install
mvn test
```

## Running Tests Locally

### Default Configuration

- **Browser**: Chromium
- **Mode**: Headless
- **Suite**: `testng-regression.xml`

Run tests:

```bash
mvn test
```

### Running Specific TestNG Suites

```bash
# Regression Suite
mvn test -DsuiteXmlFile=testng-regression.xml

# API Suite
mvn test -DsuiteXmlFile=testng-api.xml
```

### Running in Different Browsers

```bash
# Chromium (default)
mvn test -DBROWSER=chromium

# Firefox
mvn test -DBROWSER=firefox

# Webkit
mvn test -DBROWSER=webkit
```

### Running in Headed Mode

```bash
mvn test -DHEADLESS=false
```

## Running Tests in Docker

### Build Docker Image

```bash
docker build -t playwright-tests .
```

### Run Tests

```bash
docker run --rm playwright-tests
```

### Run Specific Suites in Docker

```bash
# Regression
docker run --rm playwright-tests mvn test -DsuiteXmlFile=testng-regression.xml

# API
docker run --rm playwright-tests mvn test -DsuiteXmlFile=testng-api.xml
```

### Run in Different Browsers in Docker

```bash
docker run --rm -e BROWSER=firefox playwright-tests
```

## Generating Allure Reports

```bash
mvn test
allure serve target/allure-results
```

## Notes

- For headed Docker tests, ensure Docker has access to an X11 server
- Modify `testng.xml` to configure test groups or parallelism
- Troubleshooting logs are in the `target` directory
