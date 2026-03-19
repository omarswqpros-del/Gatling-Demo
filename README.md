# Gatling Maven Plugin Demo - AceToys E-Commerce Load Testing Suite 🛒📊

[![Maven](https://img.shields.io/badge/Maven-3.9%2B-orange.svg)](https://maven.apache.org/)
[![Gatling](https://img.shields.io/badge/Gatling-3.15.0-blue.svg?logo=gatling)](https://gatling.io/)
[![Java](https://img.shields.io/badge/Java-11%2B-brightgreen.svg)](https://openjdk.org/)
[![License](https://img.shields.io/badge/License-Apache--2.0-yellow.svg)](https://github.com/gatling/gatling-maven-plugin-demo-java/blob/main/LICENSE)

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [Simulations](#simulations)
- [Configuration](#configuration)
- [Data Feeders](#data-feeders)
- [Architecture](#architecture)
- [Running Tests](#running-tests)
- [Reports & Assertions](#reports--assertions)
- [Customization](#customization)
- [Troubleshooting](#troubleshooting)
- [References](#references)

## Overview

This is an advanced **Gatling Maven plugin demo** in **Java 11+**, simulating realistic e-commerce load on **AceToys.uk** (toy store). It demonstrates:

- Modular page objects & reusable chains.
- Dynamic user journeys (browse/abandon/purchase).
- Multiple injection profiles (open/closed workloads).
- Feeders (CSV/JSON), sessions, assertions.

Includes:

- Basic API example (`example.BasicSimulation`).
- Full e-commerce suite (`acetoys.AceToysSimulation`).

**Target**: Stress test browsing, cart, login/checkout under various loads.

## Features

- **Modular Design**: Page objects (`Product`, `Cart`, etc.), scenarios (`TestScenario`), populations (`TestPopulation`).
- **Randomized Journeys**: 60% browse, 30% abandon, 10% purchase (customizable).
- **Load Profiles**: Instant, ramp, constant/sec, closed-model.
- **Assertions**: Mean RT <3s, 99% success, max RT <5s.
- **Parameterized**: Override via `-DUSERS=50 -DTEST_TYPE=RAMP_USERS`.
- **Maven Wrapper**: `./mvnw gatling:test` (no Maven install needed).

## Quick Start

1. **Clone/Navigate**:

   ```bash
   #!/bin/bash
   cd 'c:/Testing Tools/Gatling/gatling-maven-plugin-demo-java'
   ```

2. **Run Basic Test**:

   ```bash
   #!/bin/bash
   ./mvnw clean gatling:test -Dgatling.simulationClass=example.BasicSimulation
   ```

3. **Run AceToys** (default instant 5 users):

   ```bash
   #!/bin/bash
   ./mvnw clean gatling:test -Dgatling.simulationClass=acetoys.AceToysSimulation
   ```

## Simulations

| Simulation | Class | Description | Default Load |
| :--- | :--- | :--- | :--- |
| **Basic** | `example.BasicSimulation` | Simple API session fetch from `api-ecomm.gatling.io`. VU from `-Dvu=1`. | 1 VU atOnce |
| **AceToys** | `acetoys.AceToysSimulation` | E-comm: Home → Browse → Add cart → Login → Checkout → Logout. | Instant 5 users, assertions |

**AceToys Journey** (loop during 60s):

```text
initSession → browseStore | abandonBasket | completePurchase (random weighted)
```

## Configuration

- **`pom.xml`**: Gatling 3.15.0, plugin 4.21.3.

- **`gatling.conf`**: Standard (customize caching, SSL, etc.).

## Data Feeders

| File | Type | Usage |
| ------ | ------ | ------- |
| `categoryDetails.csv` | CSV | Category browsing (`feed(csv("categoryDetails.csv"))`). |
| `productDetails.json` | JSON | Product details (`feed(jsonFile("productDetails.json"))`). |

## Architecture

```text
AceToysSimulation
├── httpProtocol (baseUrl, headers, inferHtmlResources)
├── TestScenario.{default/highPurchase}LoadTest
│   └── during(60s).on(randomSwitch(browse/abandon/purchase))
├── TestPopulation.{instantUsers/rampUsers/etc.}
└── Page Objects: StaticPages → Category → Product → Cart → Customer
    └── UserSession (csrf, basketTotal, loggedIn)
```

## Running Tests

**Examples**:

```bash
#!/bin/bash
# Ramp 10 users over 10s
./mvnw gatling:test -Dgatling.simulationClass=acetoys.AceToysSimulation -DTEST_TYPE=RAMP_USERS -DUSERS=10

# Complex: 10-20 ups/sec
./mvnw gatling:test -DTEST_TYPE=COMPLEX_INJECTION -DDURATION=120

# High purchase focus, custom base
./mvnw gatling:test -DTEST_TYPE=CLOSED_MODEL -DbaseURL=https://staging.acetoys.uk
```

**All Sims**:

```bash
#!/bin/bash
./mvnw gatling:test
```

## Reports & Assertions

- **Location**: `target/gatling/ace-toys-simulation-YYYYMMDD-HHMMSS/`.
- **Key Metrics**: RT graphs, errors, assertions (global/forAll).
- **View**: Open `index.html` in browser.

## Customization

- **Props**: `-DUSERS=50 -DRAMP_DURATION=30 -DTEST_TYPE=...` (instantUsers|RAMP_USERS|COMPLEX_INJECTION|CLOSED_MODEL).
- **Add Scenarios**: Extend `Simulation`, `setUp(scn.injectOpen(...)).protocols(http)`.
- **Data**: Edit CSVs/JSONs; add feeders.
- **Thresholds**: Modify assertions in `AceToysSimulation`.

## Troubleshooting

| Issue | Solution |
| ------- | ---------- |
| Maven not found | Use `./mvnw` (Windows: `mvnw.cmd`). |
| OOM/High CPU | Increase JVM: `-Xmx4g -Xms2g`. |
| SSL errors | `gatling.ssl.useInsecureTrustManager=true`. |
| No reports | Check `target/gatling/`; run `clean`. |
| Custom base | `-DBASEURL=https://your-site.com`. |

**Logs**: `logback-test.xml` (DEBUG: add `<logger name="io.gatling" level="DEBUG"/>`).

## References

- [Gatling Docs](https://gatling.io/docs/gatling/reference/current/)
- [Java DSL](https://gatling.io/docs/gatling/reference/current/extensions/java_dsl/)
- [Maven Plugin](https://gatling.io/docs/gatling/reference/current/integrations/maven/)
- [AceToys Site](https://acetoys.uk) (Application Under Test).

## Contributors

We'd like to thank the contributors who help maintain and improve this project:

- [OmarAlsweiti](https://github.com/omarswqpros-del) - Active contributor and maintainer
- [Stephane Landelle](https://github.com/slandelle) - Founder of Gatling
- [Obero](https://github.com/Obero)

---
