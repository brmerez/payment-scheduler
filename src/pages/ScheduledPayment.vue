<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import type { ScheduledPayment } from '../lib/types/ScheduledPayment';
import { getPaymentById } from '../lib/payment';

const route = useRoute();
const payment = ref<ScheduledPayment | null>(null);
const error = ref<string | null>(null);

const paymentId = Number(route.params.id);
const success = route.query.success === 'true';

onMounted(async () => {
  try {
    if (!paymentId) throw new Error("No payment ID provided");
    const data = await getPaymentById(paymentId);
    payment.value = data;
  } catch (err: any) {
    error.value = err.message || 'Erro ao buscar pagamento';
  }
});
</script>

<template>
  <section class="p-6 max-w-2xl mx-auto flex flex-col gap-4">
    <h2 class="text-3xl font-bold mb-4 text-indigo-900">Detalhes do Pagamento</h2>
    <hr>
    <div v-if="success" class="p-4 bg-green-200 text-green-800 rounded-xl mb-4">
      Pagamento agendado com sucesso!
    </div>
    <div v-if="error" class="p-4 bg-red-200 text-red-800 rounded mb-4">
      {{ error }}
    </div>

    <div v-if="payment" class="grid grid-cols-6 gap-3 bg-indigo-900 text-white p-6 rounded-xl shadow">
      <p class="col-span-6"><strong>ID:</strong> {{ payment.id }}</p>
      <p class="col-span-3"><strong>De (Sender):</strong> <br /> {{ payment.senderAccount.nome }} | #{{
        payment.senderAccount.accountNumber }}</p>
      <p class="col-span-3 text-right"><strong>Para (Recipient):</strong> <br />{{ payment.recipientAccount.nome }} |
        #{{
          payment.recipientAccount.accountNumber }}</p>
      <p class="col-span-2"><strong>Valor:</strong> <br />{{ (payment.amount / 100).toLocaleString('pt-BR', {
        style: 'currency', currency: 'BRL'
      }) }}</p>
      <p class="col-span-2 text-center"><strong>Taxas:</strong> <br />{{ (payment.taxes / 100).toLocaleString('pt-BR', {
        style: 'currency', currency: 'BRL'
      }) }}</p>
      <p class="col-span-2 text-right"><strong>Total:</strong> <br />{{ (payment.totalAmount /
        100).toLocaleString('pt-BR', {
          style: 'currency', currency:
            'BRL'
        }) }}</p>
      <p class="col-span-3"><strong>Agendado em:</strong> <br />{{ new Date(payment.scheduledAt).toLocaleString('pt-BR')
        }}
      </p>
      <p class="col-span-3 text-right"><strong>Agendado para:</strong> <br />{{ new
        Date(payment.scheduledTo).toLocaleString('pt-BR') }}
      </p>
      <p><strong>Pago:</strong> {{ payment.fulfilled ? 'Sim' : 'Não' }}</p>
    </div>

    <div v-else class="text-gray-500">
      Carregando pagamento...
    </div>
  </section>
</template>
