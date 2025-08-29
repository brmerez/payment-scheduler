<script setup lang="ts">
import { ref, onMounted } from "vue";
import { formatCurrency } from "../lib/formatter";
import type TaxBracket from "../lib/types/TaxBracket";

const brackets = ref<TaxBracket[]>([]);

async function fetchBrackets() {
  try {
    const res = await fetch("http://localhost:8080/api/v1/tax-brackets");
    if (!res.ok) throw new Error("Failed to fetch tax brackets");
    const data: TaxBracket[] = await res.json();
    brackets.value = data;
  } catch (err) {
    console.error(err);
  }
}

onMounted(() => {
  fetchBrackets();
});
</script>

<template>
  <div class="overflow-x-auto">
    <table class="min-w-full divide-y divide-gray-200 border border-gray-200 rounded-lg">
      <thead class="bg-gray-50">
        <tr>
          <th colspan="2" class="px-4 py-2 text-center text-sm font-medium text-gray-700">
            Dias entre a Transferência
          </th>
          <th rowspan="2" class="px-4 py-2 text-left text-sm font-medium text-gray-700">
            Taxa fixa (em R$)
          </th>
          <th rowspan="2" class="px-4 py-2 text-left text-sm font-medium text-gray-700">
            Taxa Percentual
          </th>
        </tr>
        <tr>
          <th class="px-4 py-2 text-left text-sm font-medium text-gray-700">De</th>
          <th class="px-4 py-2 text-left text-sm font-medium text-gray-700">Até</th>
        </tr>
      </thead>
      <tbody v-if="brackets" class="divide-y divide-gray-200 bg-white">
        <tr v-for="(bracket, index) in brackets" :key="index" class="hover:bg-gray-50">
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.minDays }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.maxDays }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ formatCurrency(bracket.fixedRate) }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.percentageRate }}%</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>