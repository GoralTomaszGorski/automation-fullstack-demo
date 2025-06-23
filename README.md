# Automation Fullstack Demo

Projekt pokazujący automatyzację testów UI, API oraz raportowanie z Allure.  
Testowany frontend: https://demoqa.com/text-box  
Testowane API: https://reqres.in/api/register

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
