import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./lib/router";
import VueDatePicker from "@vuepic/vue-datepicker";

import {
  MdPaymentsSharp,
  MdAccountcircle,
  BiInfoCircle,
  MdInfooutline,
  RiErrorWarningLine,
  CoTransfer,
  BiBoxArrowInLeft,
  BiBoxArrowInRight,
} from "oh-vue-icons/icons";
import { addIcons, OhVueIcon } from "oh-vue-icons";

addIcons(
  MdPaymentsSharp,
  MdAccountcircle,
  BiInfoCircle,
  MdInfooutline,
  RiErrorWarningLine,
  CoTransfer,
  BiBoxArrowInLeft,
  BiBoxArrowInRight
);

const app = createApp(App);

app.use(router);
app.component("v-icon", OhVueIcon);
app.component("VueDatePicker", VueDatePicker);
app.mount("#app");
