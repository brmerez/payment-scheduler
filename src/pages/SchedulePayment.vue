<script setup lang="ts">
import { onMounted } from 'vue';
import Contacts from '../components/Contacts.vue';
import TaxBracketTable from '../components/TaxBracketTable.vue';
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
    <h4 class="text-5xl font-bold text-indigo-900">
      Agendar Pagamento
    </h4>
    <hr />
    <section v-if="contactState.contacts && authState.user" class="flex flex-col gap-4">
      <h4 class="flex-1 text-center p-3 bg-indigo-900 text-white text-bold rounded">Contatos Frequentes:</h4>
      <div class="grid grid-cols-2 lg:grid-cols-3 gap-2">
        <Contacts :contacts="contactState.contacts" />
      </div>
    </section>
    <section class="flex flex-col gap-5">
      <div class="p-4 bg-amber-600 rounded text-white flex items-center gap-2">
        <v-icon name="ri-error-warning-line" />
        <p class="">Certifique-se que você terá o saldo disponível para a transferência
          na data marcada!</p>
      </div>
      <div class="p-4 bg-indigo-400 rounded text-white flex items-center gap-2">
        <v-icon name="md-infooutline" />
        <p class="">Ao agendar um pagamento serão cobradas taxas de acordo com a tabela a seguir</p>
      </div>
      <TaxBracketTable />
    </section>

  </section>

</template>