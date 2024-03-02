<template>
  <component :is="layout">
    <router-view></router-view>
  </component>
</template>

<script>
import { useRoute } from "vue-router";
import { computed, watch } from "vue";

import { PUBLIC_LAYOUT } from "./utils/constants.js";
import axios from "axios";

import { useAccountStore } from "./store/AccountStore";

export default {
  setup() {
    const route = useRoute();

    const store = useAccountStore();

    const check = () => {
      axios
        .get("http://localhost:8080/api/account/check", {
          withCredentials: true,
        })
        .then((res) => {
          store.setAccount(res.data || null);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    watch(route, () => {
      check();
    });

    return {
      layout: computed(() => (route.meta.layout || PUBLIC_LAYOUT) + "-layout"),
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #c9d6ff !important;
  background: linear-gradient(to right, #e2e2e2, #c9d6ff) !important;
}

.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

.btn {
  margin: 4px;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}
</style>
