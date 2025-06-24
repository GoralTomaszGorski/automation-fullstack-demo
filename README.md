# Automation Fullstack Demo

Projekt pokazujący automatyzację testów UI, API oraz raportowanie z Allure.  
Testowane API: https://reqres.in/api/register

## swagger documentation
https://reqres.in/api-docs/
we need add: 
header("x-api-key", API_KEY)
for free tests
userdata cant by generated because there isn't DB in application
we cant use 
protected static UserDto user = UserGenerator.generate();
it is recommended to use
protected static UserDto user = new UserDto("eve.holt@reqres.in", "cityslicka");


## Technologie
- Java 17
- Selenium WebDriver
- RestAssured
- JUnit 5
- Allure Report
- Maven
- GitHub Actions

## Uruchomienie testów lokalnie

```bash
mvn clean test
````


## 📂 Project Structure

```

automation-fullstack-demo/
├── src/
│   └── test/
│       └── java/
│           └── pl/
│               └── goral/
│                   ├── ui/
│                   │   ├── pages/       # Page Object Model (POM)
│                   │   ├── tests/       # Testy UI (Selenium, JUnit/TestNG)
│                   │   └── utils/       # Utils dla testów UI
│                   ├── api/
│                   │   ├── tests/       # Testy REST API (OkHttp, REST-assured)
│                   │   └── utils/       # Generatory danych, helpery API
│                   └── performance/
│                       └── jmeter/      # Pliki JMX dla testów wydajnościowych
├── resources/                         # Pliki konfiguracyjne i dane testowe
├── reports/                           # Wygenerowane raporty (Allure, JMeter)
├── .github/
│   └── workflows/
│       └── test-run.yml              # GitHub Actions CI/CD pipeline
├── config.properties                  # Własne właściwości (URL, credentials)
├── pom.xml                            # Maven: zależności i konfiguracja
└── README.md                          # Dokumentacja projektu


> 📁 **Legend**:
> - `ui/` – testy E2E z użyciem Selenium WebDriver
> - `api/` – testy REST API
> - `performance/jmeter/` – scenariusze obciążeniowe
> - `reports/` – Allure / JMeter HTML reports
> - `.github/workflows/` – pipeline CI/CD
