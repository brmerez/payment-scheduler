<script setup lang="ts">
import { ref, watch } from 'vue';
import { getContact } from '../lib/contacts';
import VueDatePicker from "@vuepic/vue-datepicker";
import '@vuepic/vue-datepicker/dist/main.css'
import MoneyInput from '../components/MoneyInput.vue';
import Button from '../components/Button.vue';
import { schedulePayment } from '../lib/payment';
import { authState } from '../lib/authState';
import { formatDate } from '../lib/formatter';
import { useRouter } from 'vue-router';
import TaxBracketTable from '../components/TaxBracketTable.vue';

const props = defineProps<{ accountNumber: string }>();
const { nome, accountNumber } = getContact(props.accountNumber);

const selectedDate = ref<Date | null>(null);
const value = ref<number>(0);
const enableSend = ref<boolean>(false);
const error = ref<string>("");

watch([value, selectedDate], ([newValue, newDate]) => {
  enableSend.value = false;

  if (newValue > 0 && newDate != null) {
    enableSend.value = true;
  }
});

const router = useRouter();

async function submit() {
  schedulePayment({
    senderAccount: authState.user!.accountNumber,
    receiverAccount: accountNumber,
    amount: value.value,
    // @ts-ignore
    scheduledTo: formatDate(selectedDate.value)
  }).then((id: number) => {

    router.push({
      name: 'scheduled-payment',
      params: { id },
      query: { success: "true" }
    });

  }).catch((err: Error) => {
    value.value = 0;
    selectedDate.value = null;
    error.value = err.message
  })
}

</script>


<template>
  <section class="flex flex-col gap-4">
    <div class="flex flex-col gap-2">
      <h4 class="text-4xl text-bold">Transferindo para <span class="text-indigo-800">{{ nome }}</span></h4>
      <span class="text-gray-400">{{
        accountNumber }}</span>

    </div>

    <div class="h-5"></div>
    <div class=" p-4 bg-amber-600 rounded-xl text-white flex items-center gap-2 col-span-2">
      <v-icon name="ri-error-warning-line" />
      <p class="">Certifique-se que você terá o saldo disponível para a transferência
        na data marcada!</p>
    </div>
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 p-4 border-2 border-indigo-800 rounded-xl">
      <div class="flex flex-col gap-2">
        <label for="">Valor:</label>
        <MoneyInput v-model="value" />
      </div>
      <div class="flex flex-col gap-2 ">
        <label class="text-bold">Data da Transferência:</label>
        <VueDatePicker v-model="selectedDate" :format="`dd/MM/yyyy hh:mm`" :enable-time="true" :time-interval="10"
          :use-seconds="false"
          :input-class="'bg-transparent border rounded px-2 py-1 w-full text-right placeholder-gray-400'"
          :min-date="new Date()" :calendar-class="'shadow-lg rounded p-2'" />
      </div>
    </div>
    <div v-if="error" class=" p-4 bg-red-500 rounded text-white flex items-center gap-2 col-span-2">
      <v-icon name="ri-error-warning-line" />
      <p class="">{{ error }}</p>
    </div>

    <Button :disabled="!enableSend" :onClick="submit">Transferir</Button>

  </section>
  <section class="flex flex-col gap-5 pt-3">
    <div class="p-4 bg-indigo-400 rounded-xl text-white flex items-center gap-2">
      <v-icon name="md-infooutline" />
      <p class="">Ao agendar um pagamento serão cobradas taxas de acordo com a tabela a seguir</p>
    </div>
    <TaxBracketTable />
  </section>

</template>