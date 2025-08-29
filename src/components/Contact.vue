<script setup lang="ts">
import type User from '../lib/types/User';

interface Props {
  contact: Omit<User, "balance">;
}

const { contact } = defineProps<Props>();

</script>

<template>
  <div
    class="p-5 flex flex-1 cursor-pointer items-center justify-between gap-2 rounded-2xl shadow border-indigo-900 border-2 font-bold hover:bg-indigo-900 hover:text-white transition-colors">
    <div class="">
      <p class="text-2xl">{{ contact.nome }}</p>
      <p class="text-gray-400">{{ contact.accountNumber }}</p>
    </div>
    <v-icon name="co-transfer" scale="2" />
  </div>
</template>