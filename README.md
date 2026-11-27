---
type: Reference
title: VolMy Service
description: Entry point for local use and architecture documentation of the VolMy backend service.
tags: [readme, project, api]
generated: { by: artisyn-harness/4.2, at: 2026-11-27T13:34:23Z }
updated: { by: artisyn-harness/4.2, at: 2026-11-27T14:00:59Z }
---

# VolMy Service

VolMy Service is a backend service for managing fundraising campaigns and donors. It provides donor and fundraising HTTP operations, workbook import, and lottery requests.

## Fundraising imports

Workbook imports keep the first occurrence of each exact name, skip names already in the repository, then call `saveAll` once for the new fundraisings. The database enforces name uniqueness; adding that constraint to a database with duplicate names fails during constraint creation. The changeset has no explicit duplicate precondition or custom reconciliation diagnostic. See [XLSX import behavior](docs/System%20Architecture%20%28LLD%29/VolMy%20API%20application/03%20XLSX%20Import.md).

## Run locally

The Docker image uses the Maven-built application jar. Build it first, then start the API application and PostgreSQL services from the project root:

```sh
mvn package
docker compose up --build
```

Stop the services with `docker compose down`. Run the configured Maven test lifecycle with `mvn test`.

The donor API is available on `http://localhost:8080`, and fundraising resources are available on `http://localhost:8081/fundraisings`.

## Documentation

- [Project documentation](docs/README.md)
- [Target Architecture (HLD)](docs/Target%20Architecture%20%28HLD%29/README.md)
- [Active HLD container: VolMy API application](docs/Target%20Architecture%20%28HLD%29/v1/Containers/VolMy%20API%20application.md)
- [System Architecture (LLD)](docs/System%20Architecture%20%28LLD%29/README.md)
- [VolMy API application component overview](docs/System%20Architecture%20%28LLD%29/VolMy%20API%20application/00%20Overview.md)
