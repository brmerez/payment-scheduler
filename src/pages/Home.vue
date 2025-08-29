<script setup lang="ts">
import { authState } from '../lib/authState';
import { formatCurrency } from '../lib/formatter';
import Button from '../components/Button.vue';

const user = authState.user;

</script>

<template>
  <section class="py-5 flex flex-col gap-7">
    <h4 class="text-5xl font-bold text-indigo-900">
      Olá, {{ user!.nome }}!
    </h4>
    <p>Número de conta: {{ user!.accountNumber }}</p>
    <p class="text-2xl">Você possui <span class="font-bold text-green-700">{{ formatCurrency(user!.balance) }}</span> de
      saldo disponível.</p>

    <div class="w-full flex items-center gap-3">
      <RouterLink to="/payments/new"><Button>Agendar Pagamento</Button></RouterLink>
      <RouterLink to="/payments"><Button>Ver Pagamentos Agendados</Button></RouterLink>
    </div>
  </section>
</template>

<style scoped></style>