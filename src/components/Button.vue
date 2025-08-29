<script setup lang="ts">
const props = defineProps<{
  disabled?: boolean;
  onClick?: () => void;
}>();
</script>

<template>
  <button :disabled="props.disabled" @click="props.onClick && props.onClick()" class="p-4 border-2 font-extrabold rounded-2xl cursor-pointer shadow 
           hover:border-indigo-900 hover:bg-indigo-900 hover:text-white transition-colors
           disabled:bg-gray-300 disabled:text-gray-500 disabled:border-gray-300 disabled:cursor-not-allowed">
    <slot />
  </button>
</template>
