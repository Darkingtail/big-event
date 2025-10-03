# Repository Guidelines

## Project Structure & Module Organization
This Spring Boot service keeps code under `src/main/java/org/example`. Controllers expose HTTP endpoints in `controller`, business logic sits in `service` with implementations under `service/impl`, persistence mappers live in `mapper`, and shared DTOs are in `pojo`. Utilities belong in `utils`. Configuration files stay in `src/main/resources`, with `application.yml` supplying default MySQL settings. Tests mirror the package layout under `src/test/java`. Database fixtures are in `db/big_event.sql`, and API reference material lives in `docs`.

## Build, Test, and Development Commands
Use `mvn clean package` to produce the runnable JAR in `target/`. Run `mvn spring-boot:run` during development for hot reload friendly execution. Execute `mvn test` before pushing to ensure JUnit suites pass. When iterating quickly, `mvn package -DskipTests` is acceptable but never merge without a full test run.

## Coding Style & Naming Conventions
Follow standard Java formatting with four-space indentation and braces on the same line. Classes stay in `UpperCamelCase`, methods and fields in `lowerCamelCase`, and constants in `UPPER_SNAKE_CASE`. Web endpoints should continue to use `@RequestMapping`-scoped controllers under `/user` and related prefixes. Leverage Lombok annotations already present (for example, `@AllArgsConstructor`) instead of manually generating boilerplate. Keep request/response payloads wrapped by `Result` for consistent API responses.

## Testing Guidelines
All unit tests should reside beside the related package in `src/test/java`. Stick with the existing JUnit 3 style (`extends TestCase`) or migrate whole packages to JUnit 5 in one pull request. Name new classes with the `*Test` suffix, add focused assertions, and create helper data builders when mocking MyBatis calls. Run `mvn test` locally and attach relevant coverage screenshots if instrumentation is involved.

## Commit & Pull Request Guidelines
Commits follow a Conventional Commit pattern (`type(scope): summary`), as seen in `chore(workspace): ...`. Keep messages imperative and scoped. Pull requests need a short problem statement, bullet summary of changes, confirmation of local test results, and links to tracking issues. Include screenshots or API samples when the change affects HTTP responses.

## Configuration & Security Tips
Do not commit real database credentials; override the defaults in `application.yml` via environment variables or a local profile. Keep generated artifacts and IDE files out of version control by honouring `.gitignore`. Before sharing database dumps, scrub user data and document any schema migrations in `docs`.

