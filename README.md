# Automation Fullstack Demo

This repository presents skills in **automated testing**, including:

- UI testing with Selenium
- API testing with RestAssured
- CSV-based data-driven tests
- Reporting with Allure and ExtentReports
- Performance testing (JMeter – in progress)

## swagger documentation
<https://reqres.in/api-docs/>
we need add:
header("x-api-key", API_KEY)
for free tests

## Technologie

- Java 21
- Selenium WebDriver
- RestAssured
- JUnit 5
- Allure Report
- Maven
- GitHub Actions

## ▶️ How to run tests

Make sure you have **Java 21** and **Maven** installed.

````bash
mvn clean test
````

## 📊 How to generate Allure Report

````bash
# 1) run tests
mvn clean test

# 2) generate static report
allure generate target/allure-results --clean -o target/allure-report

# 3) open in browser (Windows example)
allure open target/allure-report
````
or after:
mvn clean test
allure generate target/allure-results --clean -o target/allure-report
open /target/allure-report/index.html by live server


Note:

On macOS/Linux you’d use open or xdg-open instead of start.

The open /target/... you tried isn’t a PowerShell built‑in, so Windows threw that error.

## 🧪 Performance Testing (JMeter)

You can test API performance using Apache JMeter.

Planned JMeter Scenarios:
Login API Load Test

URL: <https://reqres.in/api/login>

Method: POST

Payload from CSV (username/password)

Ramp-up: 1–50 users over 30 seconds

User Registration Spike Test

Endpoint: /api/register

Test sudden increase in concurrent users

Static Resource Load Test

Test loading time of HTML/JS/CSS from simple frontend

## 📂 Project Structure
```
automation-fullstack-demo/
├── src/
│   └── test/
│       └── java/
│           └── pl/
│               └── goral/
│                   ├── ui/                  # UI test automation
│                   │   ├── pages/           # Page Object Model (POM) classes
│                   │   ├── tests/           # Selenium tests with JUnit/TestNG
│                   │   └── utils/           # Utilities and helpers for UI tests
│                   ├── api/                 # API test automation
│                   │   ├── tests/           # REST API tests (RestAssured / OkHttp)
│                   │   └── utils/           # Data generators, API utilities
│                   └── performance/         # Performance testing
│                       └── jmeter/          # JMeter .jmx test plans
├── resources/                              # Configuration files and test data
├── reports/                                # Allure and JMeter reports
├── .github/
│   └── workflows/
│       └── test-run.yml                    # GitHub Actions pipeline (CI/CD)
├── config.properties                       # Custom test configuration
├── pom.xml                                 # Maven configuration
└── README.md                               # Project documentation

```

## Legend

>📁 **Legend**:
>
>
> - `ui/` – testy E2E z użyciem Selenium WebDriver
> - `api/` – testy REST API
> - `performance/jmeter/` – scenariusze obciążeniowe
> - `reports/` – Allure / JMeter HTML reports
> - `.github/workflows/` – pipeline CI/CD
