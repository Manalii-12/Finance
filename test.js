async function runTest() {
  console.log("➤ Registering admin...");
  const adminPayload = {
    name: "Admin User",
    email: "admin_test_" + Date.now() + "@gmail.com",
    password: "password",
    role: "ADMIN"
  };

  const regRes = await fetch("http://localhost:8080/api/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(adminPayload)
  });
  
  if (!regRes.ok) {
    console.error("Register failed", await regRes.text());
    return;
  }
  
  const token = (await regRes.json()).token;
  const headers = { 
    "Content-Type": "application/json",
    "Authorization": "Bearer " + token 
  };

  console.log("\n➤ Create User (POST /api/users)");
  const createRes = await fetch("http://localhost:8080/api/users", {
    method: "POST",
    headers,
    body: JSON.stringify({
      name: "Manali",
      email: "manali_" + Date.now() + "@gmail.com",
      password: "1234",
      role: "ADMIN",
      status: "ACTIVE"
    })
  });
  
  const createdUser = await createRes.json();
  console.log("✔ Created User:", createdUser);
  const id = createdUser.id;

  console.log("\n➤ Get Users (GET /api/users)");
  const getRes = await fetch("http://localhost:8080/api/users", { headers });
  const users = await getRes.json();
  console.log("✔ Users list count:", users.length);

  console.log(`\n➤ Update User (PUT /api/users/${id})`);
  const updateRes = await fetch(`http://localhost:8080/api/users/${id}`, {
    method: "PUT",
    headers,
    body: JSON.stringify({ name: "Manali Updated", status: "INACTIVE" })
  });
  console.log("✔ Updated User:", await updateRes.json());

  console.log(`\n➤ Delete User (DELETE /api/users/${id})`);
  const delRes = await fetch(`http://localhost:8080/api/users/${id}`, {
    method: "DELETE",
    headers
  });
  console.log("✔ Delete status:", delRes.status);
}

runTest().catch(console.error);
