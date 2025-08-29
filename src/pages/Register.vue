<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import type User from "../lib/types/User";
import { registerUser } from "../lib/auth";
import MoneyInput from "../components/MoneyInput.vue";

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
  <h2>Cadastre-se!</h2>
  <section class="flex flex-col h-full items-center justify-center">
    <form @submit.prevent="handleRegister" class="flex flex-col gap-2 max-w-sm">
      <input v-model="name" type="text" placeholder="Nome" required
        class="border-2 border-indigo-700 p-2 rounded-2xl" />
      <MoneyInput v-model="initialBalance" />
      <button type="submit" class="bg-indigo-500 text-white p-2 rounded cursor-pointer">Cadastrar</button>
      <p v-if="error" class="text-red-500 ">{{ error }}</p>
    </form>

  </section>
</template>
