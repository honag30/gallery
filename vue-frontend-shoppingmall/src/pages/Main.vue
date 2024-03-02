<template>
  <div class="main">
    <section class="py-4 text-center container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Gallery</h1>
          <p class="lead text-muted">
            Works of art often last forever, or nearly so. But exhibitions
            themselves, especially gallery exhibitions, are like flowers; they
            bloom and then they die, then exist only as memories, or pressed in
            magazines and books.
          </p>
          <div class="search my-3 d-flex align-items-center">
            <form class="w-100 me-3">
              <input
                type="search"
                class="form-control"
                placeholder="Search..."
              />
            </form>
            <button class="btn btn-success">
              <i class="fa-solid fa-magnifying-glass"></i>
            </button>
          </div>
          <div>
            <router-link :to="{ name: 'CreateItem' }">
              <button class="btn btn-primary">
                <span class="me-2">Upload Art</span>
                <i class="fa-solid fa-arrow-up"></i>
              </button>
            </router-link>
            <router-link :to="{ name: 'ItemList' }">
              <button class="btn btn-secondary">
                <span class="me-2">Gallery</span>
                <i class="fa-solid fa-plus"></i>
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, id) in state.items" :key="id">
            <card-component :item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Card from "@/components/Card.vue";
import { reactive } from "vue";
import ItemsService from "../service/ItemsService";

export default {
  components: {
    "card-component": Card,
  },
  setup() {
    const state = reactive({
      items: [],
    });

    const getItems = () => {
      ItemsService.getItemUrl()
        .then((res) => {
          state.items = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    getItems();

    return { state };
  },
};
</script>
