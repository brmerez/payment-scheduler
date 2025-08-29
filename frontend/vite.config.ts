import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tailwindcss from "@tailwindcss/vite";
import path from "path";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), tailwindcss()],
  build: {
    outDir: path.resolve(__dirname, "../backend/src/main/resources/static"),
    emptyOutDir: true,
  },
});
