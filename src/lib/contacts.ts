import { contactState } from "./contactsState";
import type User from "./types/User";

export async function getContacts(): Promise<Omit<User, "balance">[]> {
  const res = await fetch("http://localhost:8080/api/v1/accounts");

  if (!res.ok) {
    throw new Error("Failed to fetch contacts!");
  }

  const data = (await res.json()) as User[];
  contactState.contacts = data;
  return data;
}
