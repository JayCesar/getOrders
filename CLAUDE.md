# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build (skips tests)
./gradlew build -x test

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "jaycesar.dev.SomeTest"

# Run locally (requires DB_PASSWORD env var)
DB_PASSWORD=yourpassword ./gradlew bootRun --args='--spring.profiles.active=local'

# Build Docker image
docker build -t getorders-api .
```

## Architecture

This service follows **hexagonal (ports-and-adapters) architecture**:

```
core/domain/        — pure data classes, no framework dependencies
core/ports/         — interfaces: CustomerService, ReportService, CustomerRepository, ReportRepository
application/        — service implementations (CustomerServiceImpl, ReportServiceImpl)
adapters/inbound/   — Spring REST controllers
adapters/outbound/  — JPA entities + Spring Data repositories implementing core port interfaces
```

The dependency rule flows inward: controllers depend on port interfaces, service impls depend on repository port interfaces, and JPA adapters implement those interfaces. Nothing in `core/` imports Spring or JPA.

## Database

Connects to an **existing** MySQL RDS instance (`db_cb4cards` database) — no RDS resource is created by Terraform. Two tables: `delivery_reports` and `affected_customers`. Schema is in `src/main/resources/schema.sql` and applied automatically only when running with the `local` profile (`ddl-auto: none` in production).

Uses the **AWS MySQL JDBC driver** (`software.aws.rds.jdbc.mysql.Driver`) rather than the standard connector, even locally. The JDBC URL format is `jdbc:mysql:aws://` not `jdbc:mysql://`.

Several columns in `delivery_reports` are stored as raw JSON strings in the entity (`criticalRegions`, `criticalCarriers`, etc.) and deserialized to `Any?` via Jackson in `ReportRepositoryImpl.parseJson()`. The domain model holds `Any?` for these fields.

## API surface

All endpoints are on port 8080. JSON uses `snake_case` (configured globally via `SNAKE_CASE` naming strategy).

| Method | Path | Notes |
|--------|------|-------|
| GET | `/reports` | `limit`, `offset` params |
| GET | `/reports/trend` | `limit` param |
| GET | `/reports/{id}` | |
| GET | `/reports/{id}/regions` | returns `criticalRegions` JSON |
| GET | `/reports/{id}/carriers` | returns `criticalCarriers` JSON |
| GET | `/reports/{id}/reasons` | returns `topFailureReasons` JSON |
| GET | `/reports/{id}/customers` | `page`, `per_page`, `search`, `region`, `whatsapp_sent` |
| PATCH | `/customers/{id}/whatsapp-sent` | marks WhatsApp notification as sent |

`CustomerController` uses JPA `Specification` for dynamic filtering on the customers list.

## Infrastructure

Terraform in `infra/terraform/` deploys to **AWS ECS Fargate** behind **API Gateway v2** (HTTP API with VPC Link). Required variables at `terraform apply`:

- `db_host` — hostname of the existing RDS instance
- `db_password` — database password
- `ecr_account_id` — AWS account ID for the ECR image URI

The ECS task passes `DB_HOST` and `DB_PASSWORD` as environment variables to the container.
