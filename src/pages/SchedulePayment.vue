<script setup lang="ts">
import { onMounted } from 'vue';
import Contacts from '../components/Contacts.vue';
import { authState } from '../lib/authState';
import { getContacts } from '../lib/contacts';
import { contactState } from '../lib/contactsState';
import { onBeforeRouteUpdate } from "vue-router";

onMounted(() => {
  getContacts();
})


onBeforeRouteUpdate(() => {
  getContacts();
});

</script>

<template>

  <section class="flex flex-col gap-5">
    <h4 class="text-3xl font-bold text-indigo-900">
      Agendar Pagamento
    </h4>
    <section v-if="contactState.contacts && authState.user" class="flex flex-col gap-4">
      <h4 class="flex-1 text-center p-3 bg-indigo-900 text-white text-bold rounded-xl">Contatos Frequentes:</h4>
      <div class="grid grid-cols-2 lg:grid-cols-3 gap-2">
        <Contacts :contacts="contactState.contacts" />
      </div>
    </section>

  </section>

</template>