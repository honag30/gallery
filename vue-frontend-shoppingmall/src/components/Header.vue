<template>
  <header class="p-2 bg-dark text-white">
    <div class="container">
      <div
        class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start"
      >
        <ul
          class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0 fs-5"
        >
          <li href="#" class="nav-link px-2 text-white">
            <i class="fa-brands fa-think-peaks"></i>
          </li>
          <li>
            <router-link class="text-decoration-none" :to="{ name: 'Main' }">
              <div href="#" class="nav-link px-2 text-white">Gallery</div>
            </router-link>
          </li>
        </ul>

        <div class="text-end">
          <div v-if="store.$state.account.id" class="d-flex align-items-center">
            <div class="mx-4 d-flex">
              <router-link :to="{ name: 'Cart' }">
                <i class="icon fa-solid fa-cart-shopping"></i>
              </router-link>
            </div>
            <div class="me-2">
              <img :src="setAccountDetail.avt_path" alt="" />
              <span class="mx-3">{{ setAccountDetail.username }}</span>
            </div>
            <button
              type="button"
              class="btn btn-secondary ms-3"
              @click.prevent="logout()"
            >
              Logout
            </button>
          </div>

          <div v-else-if="!store.$state.account.id">
            <router-link :to="{ name: 'Login' }">
              <button type="button" class="btn btn-primary">Login</button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import router from "@/routes/routes";
import { useAccountStore } from "@/store/AccountStore";
import axios from "axios";

export default {
  data() {
    return {
      accountDetail: [],
    };
  },
  computed: {
    setAccountDetail() {
      return this.store.$state.account.accountDetail;
    },
  },
  setup() {
    const store = useAccountStore();

    const logout = () => {
      axios
        .post("http://localhost:8080/api/account/logout", null, {
          withCredentials: true,
        })
        .then((res) => {
          store.setAccount(res.data);
          router.push({ name: "Login" });
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return { store, logout };
  },
};
</script>

<style scoped>
.text-end img {
  width: 40px;
  height: 40px;
  border-radius: 100%;
  object-fit: cover;
}

.icon {
  font-size: 20px;
  color: greenyellow;
}
</style>
