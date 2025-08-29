import type User from "./User";

export interface ScheduledPayment {
  senderAccount: User;
  recipientAccount: User;
  amount: number;
  taxes: number;
  totalAmount: number;
  scheduledAt: string;
  scheduledTo: string;
  fulfilled: boolean;
}
