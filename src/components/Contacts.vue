<script setup lang="ts">
import { RouterLink } from 'vue-router';
import { authState } from '../lib/authState';
import type User from '../lib/types/User';
import Contact from './Contact.vue';
;
interface Props {
  contacts: Omit<User, "balance">[]
}

const props = defineProps<Props>();
const filtered = props.contacts.filter((c: Omit<User, "balance">) => c.accountNumber !== authState.user!.accountNumber);

</script>

<template>
  <template v-for="contact in filtered" class="">
    <RouterLink :to="{ name: 'schedule-payment-to-account', params: { accountNumber: contact.accountNumber } }">
      <Contact :contact="contact" />
    </RouterLink>
  </template>
</template>