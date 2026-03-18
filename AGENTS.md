# Repository Guidelines

## Project Structure & Module Organization
This is a Maven-based Gatling performance testing project. It follows the standard Maven layout for test simulations:
- **Simulations**: Located in `src/test/java/`. These classes define the load tests.
- **Resources**: `src/test/resources/` contains `gatling.conf` for global settings and `logback-test.xml` for logging configuration.
- **Package Configuration**: The `.gatling/` directory contains enterprise package metadata.

## Build, Test, and Development Commands
The project uses the Maven Wrapper (`./mvnw` or `mvnw.cmd`).
- **Run all simulations**: `./mvnw gatling:test`
- **Run a specific simulation**: `./mvnw gatling:test -Dgatling.simulationClass=example.BasicSimulation`
- **Compile code**: `./mvnw compile`
- **List Gatling goals**: `./mvnw help:describe -Dplugin=gatling`

## Coding Style & Naming Conventions
- **Language**: Java 11.
- **Standard**: Follow standard Java naming conventions (PascalCase for classes, camelCase for methods/variables).
- **Simulation Naming**: Classes should end with `Simulation` suffix (e.g., `BasicSimulation`).

## Testing Guidelines
- **Framework**: Gatling (Java API).
- **Simulation Structure**: Simulations must extend `io.gatling.javaapi.core.Simulation`.
- **Results**: Reports are generated automatically in the `target/gatling/` directory after execution.

## Commit & Pull Request Guidelines
The project follows **Conventional Commits**:
- `feat:` for new simulations or features.
- `chore:` for maintenance tasks.
- `chore(deps):` for dependency updates (commonly used by Dependabot).
- `fix:` for bug fixes in simulations.
