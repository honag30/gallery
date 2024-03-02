<template>
  <div class="main">
    <section class="py-4 text-center text-info container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Cart Page</h1>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <table
          v-if="state.cartItems.length > 0"
          class="table table-hover align-middle my-5"
        >
          <!-- Caption -->
          <caption>
            <div class="d-flex justify-content-between align-items-center my-3">
              <span class="mx-5 fs-5 text-danger fw-bold"
                >Total price: {{ formatCurrency(getTotalPrice) }}</span
              >
              <div class="mx-5">
                <router-link
                  :to="{
                    name: 'Order',
                    params: { items: JSON.stringify(state.cartItems) },
                  }"
                >
                  <button class="btn btn-warning">Buy All</button>
                </router-link>
                <button
                  class="btn btn-outline-secondary"
                  @click.prevent="cancelAll()"
                >
                  Cancel All
                </button>
              </div>
            </div>
          </caption>
          <!-- End Caption -->
          <!-- Table Content -->
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Item Name</th>
              <th scope="col">Price</th>
              <th scope="col">Discount</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(cartItem, id) in state.cartItems" :key="id">
              <th scope="row">{{ id + 1 }}</th>
              <!-- + DB_ID: {{ cartItem.id }} -->
              <!-- Name -->
              <td class="name">
                <span>{{ cartItem.name }}</span>
                <div class="image">
                  <img :src="cartItem.img_path" alt="" />
                </div>
              </td>
              <!-- End Name -->
              <!-- Item Price -->
              <td v-if="cartItem.discount_per > 0">
                <span class="fs-6 text-secondary">
                  <s>{{ formatCurrency(cartItem.price) }}</s>
                </span>
                <i class="fa-solid fa-arrow-right mx-2 text-secondary"></i>
                <span class="text-primary">
                  {{
                    formatCurrency(
                      discountPrice(cartItem.price, cartItem.discount_per),
                    )
                  }}</span
                >
              </td>
              <td v-else-if="cartItem.discount_per == 0">
                <span class="text-primary">
                  {{ formatCurrency(cartItem.price) }}
                </span>
              </td>
              <!-- End Item Price -->

              <!-- Discount -->
              <td>
                <span class="text-primary" v-if="cartItem.discount_per > 0">
                  OFF {{ cartItem.discount_per }}%
                </span>
                <span
                  class="text-secondary"
                  v-else-if="cartItem.discount_per == 0"
                  >NONE</span
                >
              </td>
              <!-- End Discount -->

              <td>
                <router-link
                  :to="{
                    name: 'Order',
                    params: { items: JSON.stringify(cartItem) },
                  }"
                >
                  <button class="btn btn-sm btn-success">
                    <i class="fa-solid fa-cart-shopping"></i>
                  </button>
                </router-link>
                <button
                  class="btn btn-sm btn-warning"
                  @click.prevent="deleteItem(cartItem.id)"
                >
                  <i class="fa-solid fa-xmark"></i>
                </button>
              </td>
            </tr>
          </tbody>
          <!-- End Table Content -->
        </table>
        <div v-else class="my-5">
          <h3 class="text-danger my-5">Your cart is Empty!!</h3>
          <router-link :to="{ name: 'Main' }">
            <button class="btn btn-primary my-5">Main Page</button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { reactive, computed } from "vue";
import { formatCurrency } from "@/service/utilsService";
import { toast } from "vue3-toastify";

export default {
  setup() {
    const state = reactive({
      cartItems: [],
    });

    const getCartItems = () => {
      axios
        .get("http://localhost:8080/api/cart/items", { withCredentials: true })
        .then(({ data }) => {
          state.cartItems = data;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    getCartItems();

    const discountPrice = (price, discount) => {
      return price - (price * discount) / 100;
    };

    const getTotalPrice = computed(() => {
      let totalPrice = 0;
      state.cartItems.forEach((item) => {
        totalPrice =
          totalPrice + (item.price - (item.price * item.discount_per) / 100);
      });
      return totalPrice;
    });

    const deleteItem = (itemId) => {
      axios
        .post(`http://localhost:8080/api/cart/item/${itemId}/delete`, itemId, {
          withCredentials: true,
        })
        .then((res) => {
          // console.log(res);
          toast.success("Delete success!!");
          getCartItems();
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const cancelAll = () => {
      axios
        .post("http://localhost:8080/api/cart/item/deleteAll", null, {
          withCredentials: true,
        })
        .then((res) => {
          // console.log(res);
          toast.success("Delete success!!");
          getCartItems();
        })
        .catch((err) => {
          console.log(err);
        });
    };

    return {
      state,
      formatCurrency,
      deleteItem,
      getTotalPrice,
      discountPrice,
      cancelAll,
    };
  },
};
</script>

<style scoped>
td {
  position: relative;
}
.image img {
  position: absolute;
  display: none;
  width: 200px;
  height: auto;
  background-size: contain;
  background-position: center;
  left: 20%;
  top: 65px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);

  animation: notifyAnimate ease 0.3s;
}
.name:hover .image img {
  display: inline-block;
  z-index: 100;
}

@keyframes notifyAnimate {
  from {
    opacity: 0;
    transform: scale(0);
  }

  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
