# Finance Backend API Testing Guide

You can use these `curl` commands in your terminal or use them as a reference to build requests in an API tool like Postman. 

> [!IMPORTANT]
> The server stores JWTs statelessly. Once you register and log in, copy the `token` string from the JSON response and replace `<YOUR_JWT_TOKEN>` in all subsequent requests.

---

## 1. Authentication & Users

### Register an Admin Account
```bash
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "email": "admin@example.com",
    "password": "password123",
    "role": "ADMIN"
}'
```

### Register an Analyst Account
```bash
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "email": "analyst@example.com",
    "password": "password123",
    "role": "ANALYST"
}'
```

### Register a Viewer Account
```bash
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "email": "viewer@example.com",
    "password": "password123",
    "role": "VIEWER"
}'
```

### Log In (Get Token)
*Change the email here to login as Admin, Analyst, or Viewer to retrieve their respective token.*
```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{
    "email": "viewer@example.com",
    "password": "password123"
}'
```
*(Copy the `token` from this response)*

---

## 2. Testing Roles

Once you have logged in as `viewer@example.com` and copied your Viewer token, try the following requests:

### View All Records (SUCCESS for Viewer, Analyst, Admin)
```bash
curl -X GET http://localhost:8080/api/records \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

### View Dashboard Summary (FAILS for Viewer / SUCCESS for Analyst, Admin)
*Viewers will get a `403 Forbidden` here, but Analysts will get `200 OK`.*
```bash
curl -X GET http://localhost:8080/api/dashboard/summary \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

### Create a Record (FAILS for Viewer, Analyst / SUCCESS for Admin)
*Only admins can create records. Trying this as a Viewer or Analyst will return `403 Forbidden`.*
```bash
curl -X POST http://localhost:8080/api/records \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
-d '{
    "amount": 5000.00,
    "type": "INCOME",
    "category": "Salary",
    "date": "2026-04-01",
    "notes": "Testing RBAC"
}'
```
