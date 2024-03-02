import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/home",
    name: "Main",
    component: () => import("../pages/Main.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/items",
    name: "ItemList",
    component: () => import("../pages/item/ItemList.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/item/create",
    name: "CreateItem",
    component: () => import("../pages/item/CreateItem.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/item/update/:id",
    name: "UpdateItem",
    component: () => import("../pages/item/UpdateItem.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/cart",
    name: "Cart",
    component: () => import("../pages/cart/Cart.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/order/:items+",
    name: "Order",
    component: () => import("../pages/cart/Order.vue"),
    meta: {
      layout: "admin",
    },
  },
  {
    path: "/",
    name: "Login",
    component: () => import("../pages/auth/Login.vue"),
    meta: {
      layout: "default",
    },
  },
];

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    document.getElementById('app').scrollIntoView({ behavior: 'smooth' });
  }
});

export default router;
