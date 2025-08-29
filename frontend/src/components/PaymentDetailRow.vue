<script setup lang="ts">
import { authState } from '../lib/authState';
import { formatBRDate, formatCurrency } from '../lib/formatter';
import type { ScheduledPayment } from '../lib/types/ScheduledPayment';

const { payment } = defineProps<{ payment: ScheduledPayment }>();
const userIsSender = payment.senderAccount.accountNumber === authState.user!.accountNumber;

</script>

<template>
  <div class="grid grid-cols-6 text-center items-center border-2 rounded-lg text-white" :class="userIsSender
    ? 'bg-red-400 '
    : 'bg-green-400 '">
    <div class="flex items-center justify-center h-full w-full">
      <v-icon :name="userIsSender ? 'bi-box-arrow-in-right' : 'bi-box-arrow-in-left'" scale="2" />
    </div>
    <p class=""><strong>{{ userIsSender ? payment.recipientAccount.nome : payment.senderAccount.nome }}</strong></p>
    <p>{{ formatCurrency(payment.amount) }}</p>
    <p>{{ formatCurrency(userIsSender ? payment.totalAmount : payment.amount) }}</p>
    <p>{{ formatBRDate(payment.scheduledTo) }}</p>
    <p>{{ payment.fulfilled ? 'Sim' : 'Não' }}</p>
  </div>
</template>