<template>
  <div class="main">
    <section class="py-4 text-center text-info container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Create Item Page</h1>
        </div>
      </div>
    </section>

    <!-- Form -->
    <div class="album py-5 bg-light">
      <div class="container">
        <form action="" class="mb-3">
          <div class="row">
            <!-- Left -->
            <div class="col-md-6">
              <div class="mb-3">
                <label for="" class="form-label"> Image Path </label>
                <input
                  type="text"
                  class="form-control"
                  v-model="item.img_path"
                />
              </div>
              <img v-if="checkAvatar" :src="item.img_path" alt="" class="" />
              <img
                v-else
                src="../../assets/image/add-image.png"
                alt=""
                class="opacity-50"
              />
            </div>
            <!-- Right -->
            <div class="col">
              <div class="mb-3">
                <label for="" class="form-label"> Name </label>
                <input type="text" class="form-control" v-model="item.name" />
              </div>

              <div class="mb-3">
                <label for="" class="form-label"> Price </label>
                <input type="text" class="form-control" v-model="item.price" />
              </div>

              <div class="mb-3">
                <label for="" class="form-label"> Discount </label>
                <input
                  type="text"
                  class="form-control"
                  v-model="item.discount_per"
                />
              </div>
            </div>
            <!-- End Form -->
          </div>

          <!-- Button -->
          <div class="mb-3">
            <button
              class="btn btn-primary me-2"
              :disabled="isFormValid"
              @click.prevent="addItem"
            >
              Create Page
            </button>
            <button
              class="btn btn-outline-success"
              @click.prevent="goToPagesList"
            >
              Back
            </button>
          </div>
          <!-- End Button -->
        </form>
      </div>
    </div>
    <!-- End Form -->
  </div>
</template>
<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { reactive, computed } from "vue";
import { toast } from "vue3-toastify";

import { useVuelidate } from "@vuelidate/core";
import { required } from "@vuelidate/validators";

export default {
  data() {
    return {};
  },

  computed: {
    checkAvatar() {
      if (this.item.img_path) {
        return true;
      } else {
        return false;
      }
    },
  },
  setup() {
    const router = useRouter();

    let item = reactive({
      name: "",
      img_path: "",
      price: 0,
      discount_per: 0,
    });

    const rules = computed(() => {});

    const v$ = useVuelidate(rules, item);

    const isFormValid = computed(
      () => !item.name || !item.img_path || !item.price || !item.discount_per,
    );

    const goToPagesList = () => {
      router.push({ name: "Main" });
    };

    const addItem = () => {
      axios
        .post("http://localhost:8080/api/add-item", item, {
          withCredentials: true,
        })
        .then((res) => {
          goToPagesList();
          toast.success("Create new item success!!");
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    return { item, addItem, isFormValid, goToPagesList, v$ };
  },
};
</script>
<style scoped>
img {
  width: 300px;
  height: 300px;
  object-fit: contain;
  padding: 20px;
  /* object-fit: cover; */
}
</style>
