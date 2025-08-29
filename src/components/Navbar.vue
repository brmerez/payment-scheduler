<script setup lang="ts">
import { authState } from '../lib/authState';


</script>


<template>
  <nav class="w-full bg-indigo-900 text-white font-bold px-[15vw] min-h-[60px] flex items-center gap-4 shadow sticky">
    <RouterLink to="/" class="flex items-center gap-2 border-r-2 border-white pr-4">
      <v-icon name="md-payments-sharp" scale="2" class="text-amber-300" />
      <h4 class="text-2xl">pay.co</h4>
    </RouterLink>
    <template v-if="authState.user">
      <RouterLink to="/payments" class="hover:text-amber-300 transition-colors">pagamentos</RouterLink>
      <RouterLink to="/reports" class="hover:text-amber-300 transition-colors">extratos</RouterLink>
      <div class="flex-1"></div>
      <RouterLink to="/account" class="hover:text-amber-300 transition-colors">
        minha conta
      </RouterLink>
    </template>
    <template v-else>
      <div class="flex-1"></div>
      <RouterLink to="/register" class="hover:text-amber-300 transition-colors">cadastrar</RouterLink>
    </template>
  </nav>
</template>