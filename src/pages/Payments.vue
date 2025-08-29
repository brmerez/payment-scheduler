<script setup lang="ts">
import { authState } from '../lib/authState';
import { getPaymentsForUser } from '../lib/payment';
import { paymentState } from '../lib/paymentState';

getPaymentsForUser(authState.user!.accountNumber)

</script>

<template>

  <div v-if="!paymentState.payments">
    <h4>Carregando...</h4>
  </div>

  <div v-else>
    <h1>Payments</h1>

  </div>

</template>