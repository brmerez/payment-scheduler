import { paymentState } from "./paymentState";
import type { ScheduledPayment } from "./types/ScheduledPayment";

export async function getPaymentsForUser(accountNumber: string) {
  const res = await fetch(
    `http://localhost:8080/api/v1/payments/account/${accountNumber}/all`
  );

  let result: ScheduledPayment[];

  if (!res.ok) {
    throw new Error("Erro ao buscar pagamentos");
  }

  result = await res.json();
  paymentState.payments = result;
  return result;
}

export async function getPaymentById(id: number) {
  const res = await fetch(`http://localhost:8080/api/v1/payments/id/${id}`);
  let result: ScheduledPayment;

  if (!res.ok) {
    throw new Error("Erro ao buscar pagamentos");
  }

  result = (await res.json()) as ScheduledPayment;
  return result;
}

export async function schedulePayment(data: {
  senderAccount: string;
  receiverAccount: string;
  scheduledTo: string;
  amount: number;
}) {
  const res = await fetch(`http://localhost:8080/api/v1/payments/schedule`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    const resBody = await res.json();
    throw new Error("Erro ao agendar pagamento: " + resBody.error);
  }

  const result = (await res.json()) as ScheduledPayment;

  paymentState.payments?.push(result);

  return result.id;
}
