import { paymentState } from "./paymentState";
import type { ScheduledPayment } from "./types/ScheduledPayment";

export async function getPaymentsForUser(accountNumber: string) {
  const res = await fetch(
    `http://localhost:8080/api/v1/payments/${accountNumber}`
  );

  let result: ScheduledPayment[];

  if (!res.ok) {
    throw new Error("Erro ao buscar pagamentos");
  }

  result = await res.json();
  paymentState.payments = result;
  return result;
}
