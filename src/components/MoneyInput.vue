<script setup lang="ts">
import { ref, watch } from "vue";

// Props for v-model
const props = defineProps<{ modelValue?: number }>();
const emit = defineEmits<{
  (e: "update:modelValue", value: number): void;
}>();

// Internal value in cents
const internalValue = ref(props.modelValue ?? 0);

// Sync parent → child
watch(
  () => props.modelValue,
  (val) => {
    internalValue.value = val ?? 0;
  }
);

// Emit internal changes → parent
watch(internalValue, (val) => {
  emit("update:modelValue", Number(val));
});

// Handle user input
function onInput(e: Event) {
  const input = e.target as HTMLInputElement;
  // Remove all non-digits
  const numeric = input.value.replace(/\D/g, "");
  internalValue.value = numeric ? parseInt(numeric) : 0;
}

// Format cents → BRL for display
function formatDisplay(value: number) {
  return (value / 100).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
}
</script>

<template>
  <input type="text" class="border rounded-lg px-3 py-2 w-full text-right" :value="formatDisplay(internalValue)"
    @input="onInput" />
</template>
