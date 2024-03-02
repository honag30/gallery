import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./routes/routes.js";
import piniaPersist from 'pinia-plugin-persist'

// import layout
import { registerGlobalComponent } from "./utils/import.js";

const app = createApp(App);


import 'vue3-toastify/dist/index.css';
import Vue3Toasity from 'vue3-toastify';

app.use(Vue3Toasity,
    {
        autoClose: 2000,
        style: {
            opacity: '1',
            userSelect: 'initial',
        },
        // ...
    })

app.use(router);

const pinia = createPinia()
pinia.use(piniaPersist)

app.use(pinia);

// use layout
registerGlobalComponent(app);

app.mount("#app");
