<script setup lang="ts">
import { ref } from "vue";
import { formatCurrency } from "../lib/formatter";
import type TaxBracket from "../lib/types/TaxBracket";


// Example data
const brackets = ref<TaxBracket[]>([
  { min_days: 0, max_days: 0, fixed_rate: 300, percentage_rate: 2.5 },
  { min_days: 1, max_days: 10, fixed_rate: 1200, percentage_rate: 0.0 },
  { min_days: 11, max_days: 20, fixed_rate: 0, percentage_rate: 8.2 },
  { min_days: 21, max_days: 30, fixed_rate: 0, percentage_rate: 6.9 },
  { min_days: 31, max_days: 40, fixed_rate: 0, percentage_rate: 4.7 },
  { min_days: 41, max_days: 50, fixed_rate: 0, percentage_rate: 1.7 },
]);
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
      <tbody class="divide-y divide-gray-200 bg-white">
        <tr v-for="(bracket, index) in brackets" :key="index" class="hover:bg-gray-50">
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.min_days }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.max_days }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ formatCurrency(bracket.fixed_rate) }}</td>
          <td class="px-4 py-2 text-sm text-gray-700">{{ bracket.percentage_rate }}%</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>