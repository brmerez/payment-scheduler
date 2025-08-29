<script setup lang="ts">
import Navbar from './Navbar.vue';

</script>

<template>
  <Navbar />
  <main class="px-[15vw] py-8 min-h-[100vw]">
    <slot />
  </main>
</template>