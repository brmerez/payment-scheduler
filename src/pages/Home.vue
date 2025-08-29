<script setup lang="ts">
import { authState } from '../lib/authState';

const user = authState.user;

</script>

<template>
  <section>
    <h4 class="text-5xl font-bold text-indigo-900">
      Olá, {{ user!.nome }}!
    </h4>
  </section>
</template>

<style scoped></style>