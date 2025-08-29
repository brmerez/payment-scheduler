import { reactive, watch } from "vue";
import type User from "./types/User";

export const authState = reactive<{ user: User | null }>({
  user: JSON.parse(localStorage.getItem("user") || "null"),
});

watch(
  () => authState.user,
  (newVal) => {
    if (newVal) {
      localStorage.setItem("user", JSON.stringify(newVal));
    } else {
      localStorage.removeItem("user");
    }
  },
  { deep: true }
);
