<template>
  <header-component></header-component>
  <slot />

  <footer-component></footer-component>
</template>
<script>
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";

export default {
  components: {
    "header-component": Header,
    "footer-component": Footer,
  },
};
</script>
