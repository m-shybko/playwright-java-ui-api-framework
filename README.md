# Playwright Java Demo

A demo automation framework in Java: UI and API test automation, both built on **Playwright**, combined in a single Maven project.

UI tests run against [saucedemo.com](https://www.saucedemo.com/), API tests run against [fakestoreapi.com](https://fakestoreapi.com/).

## Stack

- Java 25
- Playwright (Java)
- JUnit 5
- Jackson (JSON serialization for API tests)
- Allure (reporting)
- Maven

## Project structure

```
src/test/java/com/example/playwrightdemo/
├── config/            # config reading (config.properties + system properties)
├── ui/
│   ├── pages/         # Page Objects (LoginPage, InventoryPage, CartPage, Checkout*Page...)
│   │   └── components/# reusable page components (InventoryItem)
│   ├── data/          # test users (TestUser, TestUsers)
│   ├── enums/          # helper enums (SortOption)
│   └── tests/         # UI tests + BaseTest with browser setup
└── api/
    ├── clients/       # API clients (AuthClient, CartClient, ProductClient, UserClient)
    ├── models/        # request/response DTOs (auth, cart, product, user)
    └── tests/         # API tests + BaseApiTest
```

## Running the tests

```
.\mvnw.cmd clean verify
```

Run a single class:

```
.\mvnw.cmd test -Dtest=LoginTests
```

### Browser configuration

Defaults live in `src/test/resources/config.properties` and can be overridden with `-D`:

```
.\mvnw.cmd clean verify -Dbrowser=firefox -Dheadless=false -Dslowmo=100
```

Supported browsers: `chromium`, `firefox`, `webkit`.

## Allure report

Test results are written to `target/allure-results`. Generate and view the report:

```
.\mvnw.cmd allure:report
.\mvnw.cmd allure:serve
```