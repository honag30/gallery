<template>
  <footer class="footer mt-auto text-muted py-3">
    <div class="container">
      <div class="d-flex justify-content-center align-items-center">
        <div href="#" class="nav-link px-2 fs-4 text-info">
          <i class="fa-brands fa-think-peaks"></i>
        </div>
        <div>
          <a href="#" class="nav-link px-2 fs-4 text-info disabled">Gallery</a>
        </div>
      </div>
      <ul class="footer__list text-info">
        <li class="footer__item">Disclaimer</li>
        <li class="footer__item">Privacy Policy</li>
        <li class="footer__item">Terms of Use</li>
        <li class="footer__item">Contact Us</li>
      </ul>
    </div>
  </footer>
</template>
<script>
export default {};
</script>
<style scoped>
.footer__list {
  display: flex;
  list-style: none;
  justify-content: center;
  align-items: center;
}
.footer__item {
  text-decoration: none;
  margin: 5px 16px;
}
</style>
