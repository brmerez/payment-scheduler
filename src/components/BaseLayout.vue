<script setup lang="ts">
import Footer from './Footer.vue';
import Navbar from './Navbar.vue';

</script>

<template>
  <Navbar />
  <main class="px-[5vw] md:px-[10vw] lg:px-[15vw] xl:px-[20vw] py-8 min-h-[calc(100vh-100px)]">
    <slot />
  </main>
  <Footer />
</template>