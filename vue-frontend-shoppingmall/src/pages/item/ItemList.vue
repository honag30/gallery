<template>
  <div class="main">
    <section class="py-4 text-center text-info container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Item List Page</h1>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <table
          v-if="state.items.length > 0"
          class="table table-hover align-middle my-5"
        >
          <!-- Caption -->
          <!-- <caption>
            <div class="d-flex justify-content-between align-items-center my-3">
              <span class="mx-5 fs-5 text-danger fw-bold"
                >Total price: {{ formatCurrency(getTotalPrice) }}</span
              >
              <div class="mx-5">
                <router-link
                  :to="{
                    name: 'Order',
                    params: { items: JSON.stringify(state.items) },
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
          </caption> -->
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
            <tr v-for="(item, id) in state.items" :key="id">
              <th scope="row">{{ id + 1 }}</th>
              <!-- + DB_ID:
              {{
                item.id
              }} -->
              <!-- Name -->
              <td class="name">
                <span>{{ item.name }}</span>
                <div class="image">
                  <img :src="item.img_path" alt="" />
                </div>
              </td>
              <!-- End Name -->
              <!-- Item Price -->
              <td v-if="item.discount_per > 0">
                <span class="fs-6 text-secondary">
                  <s>{{ formatCurrency(item.price) }}</s>
                </span>
                <i class="fa-solid fa-arrow-right mx-2 text-secondary"></i>
                <span class="text-primary">
                  {{
                    formatCurrency(discountPrice(item.price, item.discount_per))
                  }}</span
                >
              </td>
              <td v-else-if="item.discount_per == 0">
                <span class="text-primary">
                  {{ formatCurrency(item.price) }}
                </span>
              </td>
              <!-- End Item Price -->

              <!-- Discount -->
              <td>
                <span class="text-primary" v-if="item.discount_per > 0">
                  OFF {{ item.discount_per }}%
                </span>
                <span class="text-secondary" v-else-if="item.discount_per == 0"
                  >NONE</span
                >
              </td>
              <!-- End Discount -->

              <td>
                <router-link
                  :to="{
                    name: 'UpdateItem',
                    params: { id: `${item.id}` },
                  }"
                >
                  <button class="btn btn-sm btn-success">
                    <i class="fa-solid fa-arrows-rotate"></i>
                  </button>
                </router-link>
                <button
                  class="btn btn-sm btn-danger"
                  @click.prevent="deleteItem(item.id)"
                >
                  <i class="fa-solid fa-trash"></i>
                </button>
              </td>
            </tr>
          </tbody>
          <!-- End Table Content -->
        </table>
        <div v-else class="my-5">
          <h3 class="text-danger my-5">Your item list is Empty!!</h3>
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
      items: [],
    });

    const getitems = () => {
      axios
        .get("http://localhost:8080/api/items", { withCredentials: true })
        .then(({ data }) => {
          state.items = data;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    getitems();

    const discountPrice = (price, discount) => {
      return price - (price * discount) / 100;
    };

    const getTotalPrice = computed(() => {
      let totalPrice = 0;
      state.items.forEach((item) => {
        totalPrice =
          totalPrice + (item.price - (item.price * item.discount_per) / 100);
      });
      return totalPrice;
    });

    const deleteItem = (itemId) => {
      axios
        .delete(`http://localhost:8080/api/remove-item/${itemId}`, itemId, {
          withCredentials: true,
        })
        .then((res) => {
          // console.log(res);
          toast.success("Delete success!!");
          getitems();
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const cancelAll = () => {
      //   axios
      //     .post("http://localhost:8080/api/cart/item/deleteAll", null, {
      //       withCredentials: true,
      //     })
      //     .then((res) => {
      //       // console.log(res);
      //       toast.success("Delete success!!");
      //       getitems();
      //     })
      //     .catch((err) => {
      //       console.log(err);
      //     });
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
