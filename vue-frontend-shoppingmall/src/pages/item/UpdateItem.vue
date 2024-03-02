<template>
  <div class="main">
    <section class="py-4 text-center text-info container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Update Item Page</h1>
        </div>
      </div>
    </section>

    <!-- Form -->
    <div class="album py-5 bg-light">
      <div class="container">
        <form action="" class="mb-3">
          <div class="row">
            <!-- <p>{{ item }}</p> -->
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
                class=""
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
              class="btn btn-secondary me-2"
              :disabled="isFormValid"
              @click.prevent="saveItem"
            >
              Update
            </button>
            <button
              class="btn btn-outline-success"
              @click.prevent="goToItemList"
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
import { useRouter, useRoute } from "vue-router";
import { reactive, computed } from "vue";
import { toast } from "vue3-toastify";

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
    const route = useRoute();

    let item = reactive({
      name: "",
      img_path: "",
      price: 0,
      discount_per: 0,
    });

    const isFormValid = computed(
      () => !item.name || !item.img_path || !item.price || !item.discount_per,
    );

    const goToItemList = () => {
      router.push({ name: "ItemList" });
    };

    const id = route.params.id;

    const showItem = () => {
      axios
        .get(`http://localhost:8080/api/update-item/${id}`, id, {
          withCredentials: true,
        })
        .then((res) => {
          // item = res.data;
          item.name = res.data.name;
          item.img_path = res.data.img_path;
          item.price = res.data.price;
          item.discount_per = res.data.discount_per;

          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    };
    showItem();

    const saveItem = () => {
      axios
        .put(`http://localhost:8080/api/save-item/${id}`, item, {
          withCredentials: true,
        })
        .then((res) => {
          goToItemList();
          toast.success("Update new item success!!");
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return { item, saveItem, isFormValid, goToItemList };
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
