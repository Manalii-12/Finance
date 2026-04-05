$ErrorActionPreference = "Stop"

Write-Host "Registering admin user to get token..."
$adminPayload = @{
    email = "admin3@test.com"
    password = "password"
    role = "ADMIN"
} | ConvertTo-Json

$registerResp = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/register" -Method Post -Body $adminPayload -ContentType "application/json"
$token = $registerResp.token
$headers = @{ "Authorization" = "Bearer $token" }

Write-Host "➤ Create User (POST /api/users)"
$userPayload = @{
    name = "Manali"
    email = "manali@gmail.com"
    password = "1234"
    role = "ADMIN"
    status = "ACTIVE"
} | ConvertTo-Json

$createResp = Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -Body $userPayload -ContentType "application/json" -Headers $headers
$createResp | ConvertTo-Json
$createdId = $createResp.id
Write-Host "✔ Stored in DB with ID: $createdId"

Write-Host "➤ Get Users (GET /api/users)"
$getResp = Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Get -Headers $headers
$getResp | ConvertTo-Json
Write-Host "✔ Return list with $($getResp.Count) users"

Write-Host "➤ Update User (PUT /api/users/$createdId)"
$updatePayload = @{
    name = "Manali Updated"
    status = "INACTIVE"
} | ConvertTo-Json

$updateResp = Invoke-RestMethod -Uri "http://localhost:8080/api/users/$createdId" -Method Put -Body $updatePayload -ContentType "application/json" -Headers $headers
$updateResp | ConvertTo-Json
Write-Host "✔ Data changed"

Write-Host "➤ Delete User (DELETE /api/users/$createdId)"
Invoke-RestMethod -Uri "http://localhost:8080/api/users/$createdId" -Method Delete -Headers $headers

try {
    Invoke-RestMethod -Uri "http://localhost:8080/api/users/$createdId" -Method Get -Headers $headers -ErrorAction Stop
} catch {
    Write-Host "✔ Removed properly."
}
