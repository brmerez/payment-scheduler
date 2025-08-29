<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { paymentState } from '../lib/paymentState';
import { authState } from '../lib/authState';
import { getPaymentsForUser } from '../lib/payment';
import PaymentDetailRow from '../components/PaymentDetailRow.vue';

const loading = ref(true);
const error = ref<string | null>(null);

// Fetch payments for the current user
async function fetchPayments() {
  if (!authState.user) return;

  loading.value = true;
  error.value = null;

  try {
    await getPaymentsForUser(authState.user.accountNumber);
  } catch (err: any) {
    error.value = err.message || 'Erro ao buscar pagamentos';
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  fetchPayments();
});
</script>

<template>
  <section class="flex flex-col gap-4">
    <h2 class="text-3xl font-bold text-indigo-900">Pagamentos Agendados</h2>

    <div v-if="loading" class="text-gray-500">Carregando pagamentos...</div>
    <div v-if="error" class="p-4 bg-red-300 text-white rounded">{{ error }}</div>

    <div class="grid grid-cols-6 text-center bg-indigo-900 text-white rounded-lg py-2">
      <p><strong>Entrada / Saída</strong></p>
      <p><strong>Beneficiado / Pagante:</strong> </p>
      <p><strong>Valor:</strong></p>
      <p><strong>Total:</strong></p>
      <p><strong>Agendado para:</strong></p>
      <p><strong>Pago:</strong> </p>
    </div>
    <div class="max-h-[70vh] overflow-y-scroll">
      <div v-if="paymentState.payments && paymentState.payments.length" class="flex flex-col gap-0.5">
        <div v-for="payment in paymentState.payments" :key="payment.id">
          <RouterLink :to="{ name: 'scheduled-payment', params: { id: payment.id } }">
            <PaymentDetailRow :payment="payment" />
          </RouterLink>
        </div>
      </div>
      <div v-else-if="!loading" class="text-gray-500">Nenhum pagamento encontrado.</div>
    </div>


  </section>
</template>
