import { authState } from "./authState";
import type { CreateUser } from "./types/CreateUser";
import type User from "./types/User";

export function isLoggedIn() {
  return !!authState.user;
}

export async function registerUser(userData: CreateUser): Promise<User> {
  const res = await fetch("http://localhost:8080/api/v1/accounts", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });

  if (!res.ok) {
    throw new Error("Register failed!");
  }

  const data = (await res.json()) as User;
  authState.user = data;
  return data;
}

export function logout() {
  authState.user = null;
}

export function getCurrentUser(): User {
  const user = authState.user;

  if (!user) {
    throw new Error("No current user logged in");
  }

  return user;
}
