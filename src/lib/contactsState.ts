import { reactive, watch } from "vue";
import type User from "./types/User";

export const contactState = reactive<{
  contacts: Omit<User, "balance">[] | null;
}>({
  contacts: JSON.parse(localStorage.getItem("contacts") || "null"),
});

watch(
  () => contactState.contacts,
  (newVal) => {
    if (newVal) {
      localStorage.setItem("contacts", JSON.stringify(newVal));
    } else {
      localStorage.removeItem("contacts");
    }
  },
  { deep: true }
);
