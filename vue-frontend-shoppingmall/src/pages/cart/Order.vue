<template>
  <body class="bg-light">
    <div class="container">
      <main>
        <div class="py-5 text-center">
          <h2>Checkout form</h2>
          <!-- <p>{{ items }}</p> -->
        </div>

        <div class="row g-5">
          <!-- Bill -->
          <div class="col-md-5 col-lg-4 order-md-last">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
              <span class="text-primary">Your cart</span>
              <span
                v-if="items.length > 1"
                class="badge bg-primary rounded-pill"
              >
                {{ items.length }}
              </span>
              <span v-else class="badge bg-primary rounded-pill"> 1 </span>
            </h4>
            <ul class="list-group mb-3">
              <li
                class="list-group-item lh-sm text-start"
                v-if="items.length > 1"
                v-for="(item, id) in items"
                :key="id"
              >
                <div>
                  <h6 class="my-0">{{ item.name }}</h6>
                </div>
                <div class="text-primary mt-1 text-end">
                  {{
                    formatCurrency(discountPrice(item.price, item.discount_per))
                  }}
                </div>
              </li>

              <li v-else class="list-group-item lh-sm text-start">
                <div>
                  <h6 class="my-0">{{ items.name }}</h6>
                </div>
                <div class="text-primary mt-1 text-end">
                  {{
                    formatCurrency(
                      discountPrice(items.price, items.discount_per),
                    )
                  }}
                </div>
              </li>

              <li
                class="list-group-item d-flex justify-content-between bg-light"
              >
                <div class="text-success">
                  <h6 class="my-0">Promo code</h6>
                </div>
                <span class="text-success">None</span>
              </li>
              <li class="list-group-item d-flex justify-content-between">
                <span>Total (USD)</span>
                <strong>{{ formatCurrency(getTotalPrice) }}</strong>
              </li>
            </ul>

            <form class="card p-2">
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  placeholder="Promo code"
                />
                <button type="submit" class="btn btn-secondary">Redeem</button>
              </div>
            </form>
          </div>
          <!-- End Bill -->

          <!-- Form -->
          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">Billing address</h4>
            <form class="needs-validation" novalidate>
              <div class="row g-3">
                <div class="col-12">
                  <label for="username" class="form-label">Username</label>
                  <div class="input-group has-validation">
                    <span class="input-group-text">@</span>
                    <input
                      type="text"
                      class="form-control"
                      id="username"
                      placeholder="Username"
                      required
                    />
                    <div class="invalid-feedback">
                      Your username is required.
                    </div>
                  </div>
                </div>

                <div class="col-12">
                  <label for="address" class="form-label">Address</label>
                  <input
                    type="text"
                    class="form-control"
                    id="address"
                    placeholder="1234 Main St"
                    required
                  />
                  <div class="invalid-feedback">
                    Please enter your shipping address.
                  </div>
                </div>
              </div>

              <hr class="my-4" />

              <h4 class="mb-3">Payment</h4>

              <div class="my-3">
                <div class="form-check">
                  <input
                    id="credit"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    checked
                    required
                  />
                  <label class="form-check-label" for="credit"
                    >Credit card</label
                  >
                </div>
                <div class="form-check">
                  <input
                    id="debit"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    required
                  />
                  <label class="form-check-label" for="debit">Debit card</label>
                </div>
                <div class="form-check">
                  <input
                    id="paypal"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    required
                  />
                  <label class="form-check-label" for="paypal">PayPal</label>
                </div>
              </div>

              <div class="row gy-3">
                <div class="col-md-6">
                  <label for="cc-name" class="form-label">Name on card</label>
                  <input
                    type="text"
                    class="form-control"
                    id="cc-name"
                    placeholder=""
                    required
                  />
                  <small class="text-muted"
                    >Full name as displayed on card</small
                  >
                  <div class="invalid-feedback">Name on card is required</div>
                </div>

                <div class="col-md-6">
                  <label for="cc-number" class="form-label"
                    >Credit card number</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    id="cc-number"
                    placeholder=""
                    required
                  />
                  <div class="invalid-feedback">
                    Credit card number is required
                  </div>
                </div>
              </div>

              <hr class="my-4" />

              <button class="w-100 btn btn-primary btn-lg mb-5" type="submit">
                Continue to checkout
              </button>
            </form>
          </div>
          <!-- End Form -->
        </div>
      </main>
    </div>
  </body>
</template>
<script>
import { formatCurrency } from "@/service/utilsService";

export default {
  created() {
    const items = this.$route.params.items;
    this.items = JSON.parse(items);
    // console.log(this.items);
  },
  computed: {
    getTotalPrice() {
      let totalPrice = 0;

      if (this.items.length > 1) {
        this.items.forEach((item) => {
          totalPrice =
            totalPrice + this.discountPrice(item.price, item.discount_per);
        });
      } else {
        totalPrice = this.discountPrice(
          this.items.price,
          this.items.discount_per,
        );
      }

      return totalPrice;
    },
  },

  methods: {
    discountPrice(price, discount) {
      return price - (price * discount) / 100;
    },
  },

  data() {
    return {
      items: [],
      formatCurrency,
    };
  },
};
</script>
<style scoped>
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}
</style>
