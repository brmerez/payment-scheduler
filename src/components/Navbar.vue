<script setup lang="ts">
import { authState } from '../lib/authState';
import { formatCurrency } from '../lib/formatter';


</script>


<template>
  <nav
    class="w-full bg-indigo-900 text-white font-bold px-[5vw] md:px-[10vw] lg:px-[15vw] xl:px-[20vw] min-h-[60px] flex items-center gap-4 shadow sticky top-0">
    <RouterLink to="/" class="flex items-center gap-2 border-r-2 border-white pr-4">
      <v-icon name="md-payments-sharp" scale="2" class="text-amber-300" />
      <h4 class="text-2xl">pay.co</h4>
    </RouterLink>
    <template v-if="authState.user">
      <RouterLink to="/payments/schedule" class="hover:text-amber-300 transition-colors">agendar pagamento</RouterLink>
      <RouterLink to="/payments" class="hover:text-amber-300 transition-colors">pagamentos</RouterLink>
      <div class="flex-1"></div>
      <p>{{ authState.user.nome }} · {{ authState.user.accountNumber }} · <span class="text-green-500">{{
        formatCurrency(authState.user.balance) }}</span>
      </p>
    </template>
    <template v-else>
      <div class="flex-1"></div>
      <RouterLink to="/register" class="hover:text-amber-300 transition-colors">cadastrar</RouterLink>
    </template>
  </nav>
</template>