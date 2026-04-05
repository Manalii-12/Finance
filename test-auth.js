async function run() {
  const adminPayload = {
    email: "admin@example.com",
    password: "password123",
    role: "ADMIN"
  };

  const res = await fetch("http://localhost:8080/api/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(adminPayload)
  });
  
  console.log("Status:", res.status);
  console.log("Response:", await res.text());
}
run();
