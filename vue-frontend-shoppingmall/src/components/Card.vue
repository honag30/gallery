<template>
  <div class="card shadow-sm">
    <!-- Image -->
    <span
      class="img"
      :style="{ backgroundImage: `url(${item.img_path})` }"
    ></span>
    <!-- End Image -->

    <div class="card-body">
      <div class="card-text d-flex justify-content-between m-1 mb-3">
        <!-- Title -->
        <div class="title">
          <span> {{ item.name }} </span>
        </div>
        <!-- End Title -->

        <!-- Discount -->
        <div
          class="discount bg-danger text-white px-1 rounded-3"
          v-if="checkDiscount"
        >
          <span> -{{ item.discount_per }}% </span>
        </div>
        <!-- End Discount -->
      </div>
      <div class="d-flex justify-content-between align-items-center">
        <!-- Action -->
        <div class="btn-group">
          <router-link
            :to="{
              name: 'Order',
              params: { items: JSON.stringify(item) },
            }"
          >
            <button type="button" class="btn btn-sm btn-primary">Buy</button>
          </router-link>
          <button
            type="button"
            class="btn btn-sm btn-warning me-2"
            @click.prevent="addToCart(item.id)"
          >
            <i class="fa-solid fa-cart-plus"></i>
          </button>
        </div>
        <!-- End Action -->

        <!-- Price -->
        <small class="text-muted">
          <span class="" v-if="checkDiscount">
            <s> {{ formatCurrency(item.price) }} </s>
          </span>
          <span v-if="checkDiscount" class="fs-6"> - </span>
          <span class="fs-6 text-danger fw-bold">
            {{
              formatCurrency(
                item.price - (item.price * item.discount_per) / 100,
              )
            }}
          </span>
        </small>
        <!-- End Price -->
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { formatCurrency } from "@/service/utilsService";
import { toast } from "vue3-toastify";

export default {
  props: {
    item: Object,
  },

  data() {
    return {
      discount_value: 0,
    };
  },

  setup() {
    const addToCart = (itemId) => {
      axios
        .post(`http://localhost:8080/api/cart/item/${itemId}`, itemId, {
          withCredentials: true,
        })
        .then((res) => {
          toast.success("Add to cart success!!");
          // console.log(res);
        })
        .catch((err) => {
          toast.error("Bad Request!!");
          console.log(err);
        });
    };

    return { formatCurrency, addToCart };
  },

  computed: {
    checkDiscount() {
      if (this.item.discount_per > 0) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style scoped>
.btn {
  max-width: 65px;
}
.btn-primary {
  width: 80px;
  margin-right: 5px;
}
.card .img {
  display: inline-block;
  widows: 100%;
  height: 300px;
  background-size: cover;
  background-position: center;
}
</style>
