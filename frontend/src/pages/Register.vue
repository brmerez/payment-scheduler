<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import type User from "../lib/types/User";
import { registerUser } from "../lib/auth";
import MoneyInput from "../components/MoneyInput.vue";
import Button from "../components/Button.vue";

const router = useRouter();

const name = ref<string>("");
const initialBalance = ref<number>(0);
const error = ref<string | null>(null);

async function handleRegister() {
  try {
    const user: User = await registerUser({
      nome: name.value,
      initialBalance: initialBalance.value
    });

    localStorage.setItem("user", JSON.stringify(user));
    router.push("/");
  } catch (err: any) {
    error.value = err.message;
  }
}
</script>

<template>
  <section class="flex flex-col h-full items-center justify-center gap-5">
    <h2 class="text-3xl font-bold text-indigo-900 text-center">Cadastre-se!</h2>
    <form @submit.prevent="handleRegister" class="flex flex-col gap-2 max-w-sm bg-indigo-900 rounded-lg p-4">
      <label class="w-full text-center text-white font-bold">Nome:</label>
      <input v-model="name" type="text" placeholder="Nome" required class="border-2 p-3 text-white rounded-xl" />
      <div class="h-4"></div>
      <label class="text-center text-white font-bold">Saldo Inicial:</label>
      <MoneyInput v-model="initialBalance" :class="'text-white'" />
      <div class="h-4"></div>
      <button type="submit" class="bg-amber-500 text-white p-2 rounded cursor-pointer">Cadastrar</button>
      <p v-if="error" class="text-red-500 ">{{ error }}</p>
    </form>

  </section>
</template>
