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
- GitHub Actions (CI/CD) + GitHub Pages (report hosting)

## Project structure

```
src/test/java/com/example/playwrightdemo/
├── config/             # config reading (config.properties + system properties)
├── ui/
│   ├── pages/          # Page Objects (LoginPage, InventoryPage, CartPage, Checkout*Page...)
│   │   └── components/ # reusable page components (InventoryItem)
│   ├── data/           # test users (TestUser, TestUsers)
│   ├── enums/          # helper enums (SortOption)
│   └── tests/          # UI tests + BaseTest with browser setup
└── api/
    ├── clients/        # API clients (AuthClient, CartClient, ProductClient, UserClient)
    ├── models/         # request/response DTOs (auth, cart, product, user)
    └── tests/          # API tests + BaseApiTest
```

## Running the tests

All commands below use Unix syntax (`./mvnw`). On Windows, replace `./mvnw` with `.\mvnw.cmd`.

```
./mvnw clean verify
```

Run a single class:

```
./mvnw test -Dtest=LoginTests
```

### Browser configuration

Defaults live in `src/test/resources/config.properties` and can be overridden with `-D`:

```
./mvnw clean verify -Dbrowser=firefox -Dheadless=false -Dslowmo=100
```

Supported browsers: `chromium`, `firefox`, `webkit`.

### Parallel execution

Parallel mode is controlled by the `parallel` Maven property (each UI test class launches its own isolated `Browser` instance in `@BeforeAll`, so classes don't share browser/page state).

```
./mvnw clean verify -Dparallel=true
```

Off by default locally; on by default in CI.

## CI/CD: GitHub Actions

UI tests run automatically in GitHub Actions on every push and pull request, using the [tests workflow](.github/workflows/tests.yaml).

- **Automatic runs** (push / PR): Chromium, parallel execution enabled.
- **Manual runs** (`workflow_dispatch`): pick the browser (`chromium` / `firefox` / `webkit`) and toggle parallel execution.
- Playwright browsers and OS-level dependencies are installed fresh in each run.
- API tests run locally only and are not part of the CI pipeline: `fakestoreapi.com` sits behind a JS challenge that blocks requests coming from GitHub-hosted runners.

### Allure report on GitHub Pages

After each run, an Allure report is generated from the test results and published to GitHub Pages on push or manual run against `master`:

**Live report:** https://m-shybko.github.io/playwright-java-ui-api-framework/

To view a report locally instead:

```
./mvnw allure:report
./mvnw allure:serve
```
