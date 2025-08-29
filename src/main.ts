import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./lib/router";

import {
  MdPaymentsSharp,
  MdAccountcircle,
  BiInfoCircle,
  MdInfooutline,
  RiErrorWarningLine,
  CoTransfer,
} from "oh-vue-icons/icons";
import { addIcons, OhVueIcon } from "oh-vue-icons";

addIcons(
  MdPaymentsSharp,
  MdAccountcircle,
  BiInfoCircle,
  MdInfooutline,
  RiErrorWarningLine,
  CoTransfer
);

const app = createApp(App);

app.use(router);
app.component("v-icon", OhVueIcon);
app.mount("#app");
