import { reactive, watch } from "vue";
import type { ScheduledPayment } from "./types/ScheduledPayment";

export const paymentState = reactive<{ payments: ScheduledPayment[] | null }>({
  payments: JSON.parse(localStorage.getItem("payments") || "[]"),
});

watch(
  () => paymentState.payments,
  (newVal) => {
    if (newVal) {
      localStorage.setItem("payments", JSON.stringify(newVal));
    } else {
      localStorage.removeItem("payments");
    }
  },
  { deep: true }
);
