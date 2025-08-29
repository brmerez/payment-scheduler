import {
  createRouter,
  createWebHistory,
  type RouteRecordRaw,
} from "vue-router";
import Home from "../pages/Home.vue";
import Payments from "../pages/Payments.vue";
import Register from "../pages/Register.vue";
import { getCurrentUser, isLoggedIn } from "./auth";
import SchedulePayment from "../pages/SchedulePayment.vue";
import SchedulePaymentToUser from "../pages/SchedulePaymentToUser.vue";
import ScheduledPayment from "../pages/ScheduledPayment.vue";

const routes: RouteRecordRaw[] = [
  { path: "/", name: "home", component: Home, meta: { requiresAuth: true } },
  {
    path: "/payments",
    name: "payments",
    component: Payments,
    meta: { requiresAuth: true },
  },

  {
    path: "/payments/schedule",
    name: "schedule-payment",
    component: SchedulePayment,
    meta: { requiresAuth: true },
  },
  {
    path: "/payments/schedule/:accountNumber",
    name: "schedule-payment-to-account",
    component: SchedulePaymentToUser,
    meta: { requiresAuth: true },
    props: true,
  },
  {
    path: "/payments/:id",
    name: "scheduled-payment",
    component: ScheduledPayment,
    meta: { requiresAuth: true },
    props: true,
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
  if (to.meta.requiresAuth && isLoggedIn()) {
    getCurrentUser();
  }

  if (to.meta.requiresAuth && !isLoggedIn()) {
    next({ name: "register" });
  } else if (to.meta.guestOnly && isLoggedIn()) {
    next({ name: "register" });
  } else {
    next();
  }
});

export default router;
