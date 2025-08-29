import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue";
import Payments from "../pages/Payments.vue";
import Register from "../pages/Register.vue";
import { isLoggedIn } from "./auth";

const routes = [
  { path: "/", name: "home", component: Home, meta: { requiresAuth: true } },
  {
    path: "/payments",
    name: "payments",
    component: Payments,
    meta: { requiresAuth: true },
  },
  {
    path: "/register",
    name: "register",
    component: Register,
    meta: { guestOnly: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, _, next) => {
  if (to.meta.requiresAuth && !isLoggedIn()) {
    next({ name: "register" });
  } else if (to.meta.guestOnly && isLoggedIn()) {
    next({ name: "register" });
  } else {
    next();
  }
});

export default router;
