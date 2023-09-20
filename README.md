# empik GitHub User Information Service

## Overview

This application is a RESTful service that fetches and provides user information from the GitHub API.

It responds with the following details for a given GitHub user login:

- Id
- Login
- Name
- Type
- Avatar URL
- Created At
- Calculations

## API Endpoint

### `GET /users/{login}`

**Request Parameters:**

- `{login}`: GitHub user login.

**Response Example:**

```json
{
    "id": "...",
    "login": "...",
    "name": "...",
    "type": "...",
    "avatarUrl": "...",
    "createdAt": "...",
    "calculations": "..."
}
```
## Swagger:
http://localhost:8080/swagger-ui/index.html

## H2:
http://localhost:8080/h2-console/

username: sa

password: password