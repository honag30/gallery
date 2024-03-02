<template>
  <body>
    <div class="container" id="container" :class="{ active: status }">
      <!-- Signup -->
      <div class="form-container sign-up">
        <form action="">
          <h1>Create Account</h1>
          <div class="social-icons">
            <a href="#" class="icon">
              <i class="fa-brands fa-google-plus-g"></i>
            </a>
            <a href="#" class="icon">
              <i class="fa-brands fa-facebook"></i>
            </a>
            <a href="#" class="icon">
              <i class="fa-brands fa-github"></i>
            </a>
            <a href="#" class="icon"> <i class="fa-brands fa-twitter"></i> </a>
          </div>
          <span>or use your email for registration</span>
          <input type="text" placeholder="Username" />
          <input type="text" placeholder="Email" />
          <input type="text" placeholder="Password" />
          <router-link :to="{ name: 'Main' }">
            <button>Sign Up</button>
          </router-link>
        </form>
      </div>
      <!-- End Signup -->

      <!-- Signin -->
      <div class="form-container sign-in">
        <form action="">
          <h1>Sign In</h1>
          <div class="social-icons">
            <a href="#" class="icon">
              <i class="fa-brands fa-google-plus-g"></i>
            </a>
            <a href="#" class="icon">
              <i class="fa-brands fa-facebook"></i>
            </a>
            <a href="#" class="icon">
              <i class="fa-brands fa-github"></i>
            </a>
            <a href="#" class="icon"> <i class="fa-brands fa-twitter"></i> </a>
          </div>
          <span>or use your email for sign in</span>
          <input type="email" placeholder="Email" v-model="state.form.email" />
          <input
            type="password"
            placeholder="Password"
            v-model="state.form.password"
          />
          <a href="">Forgot your password?</a>
          <button @click.prevent="submit()">Sign In</button>
        </form>
      </div>
      <!-- End Signip -->

      <!-- Toggle Section -->
      <div class="toggle-container">
        <div class="toggle">
          <!-- sign in -->
          <div class="toggle-panel toggle-left">
            <h1>Welcome Back!</h1>
            <p>Enter your personal details to use all of site features</p>
            <button class="hide" @click="clickSignIn()" id="login">
              Sign In
            </button>
          </div>

          <!-- sign up -->
          <div class="toggle-panel toggle-right">
            <h1>Hello Friend!</h1>
            <p>
              Register with your personal details to use all of site features
            </p>
            <button class="hide" id="register" @click="clickSignUp()">
              Sign Up
            </button>
          </div>
        </div>
      </div>
      <!-- End Toggle Section -->
    </div>
  </body>
</template>
<script>
import router from "@/routes/routes";
import { useAccountStore } from "@/store/AccountStore";
import axios from "axios";
import { reactive } from "vue";

export default {
  data() {
    return {
      status: false,
    };
  },

  setup() {
    const state = reactive({
      form: {
        email: "",
        password: "",
      },
    });

    const store = useAccountStore();

    const submit = () => {
      axios
        .post("http://localhost:8080/api/account/login", state.form, {
          withCredentials: true,
        })
        .then((res) => {
          store.getAccount();
          store.setAccount(res.data);
          router.push({ name: "Main" });
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return { state, submit };
  },

  methods: {
    clickSignIn() {
      this.status = false;
    },

    clickSignUp() {
      this.status = true;
    },
  },
};
</script>

<!-- Style -->
<style scoped>
body {
  background-color: #c9d6ff;
  background: linear-gradient(to right, #e2e2e2, #c9d6ff);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
}

.container {
  background-color: #fff;
  border-radius: 30px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
  /* padding-top: 100px; */
}

.container p {
  font-size: 14px;
  line-height: 20px;
  letter-spacing: 0.3px;
  margin: 20px 0;
}

.container span {
  font-size: 12px;
  margin-bottom: 10px;
}

.container a {
  color: var(--text-color);
  font-size: 13px;
  text-decoration: none;
  margin: 15px 0 10px;
}

.container button {
  background-color: #512da8;
  color: #fff;
  font-size: 12px;
  padding: 10px 45px;
  border: 1px solid transparent;
  border-radius: 8px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin-top: 10px;
  cursor: pointer;
}

.container button:hover {
  background-color: #5c6bc0;
}

.container button.hide {
  background-color: transparent;
  border-color: #fff;
}

.container button.hide:hover {
  background-color: #5c6bc0;
}

.container form {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  height: 100%;
}

.container input {
  background-color: #eee;
  border: none;
  margin: 8px 0;
  padding: 10px 15px;
  font-size: 13px;
  border-radius: 8px;
  width: 100%;
  outline: none;
}

.container input:focus {
  border: 2px solid #512da8;
  box-shadow: 0 1px 1rem rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.active .sign-in {
  transform: translateX(100%);
}

.sign-up {
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.active .sign-up {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: move 0.6s;
}

@keyframes move {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }
  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}

.social-icons {
  margin: 20px 0;
}
.social-icons a:hover {
  background-color: #d7dcff;
}

.social-icons a {
  border: 1px solid #ccc;
  border-radius: 20%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 3px;
  width: 40px;
  height: 40px;
  font-size: 14px;
}

.toggle-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: all 0.6s ease-in-out;
  border-radius: 150px 0 0 100px;
  z-index: 1000;
}

.container.active .toggle-container {
  transform: translateX(-100%);
  border-radius: 0 150px 100px 0;
}

.toggle {
  background-color: #512da8;
  height: 100%;
  background: linear-gradient(to right, #5c6bc0, #512da8);
  color: #fff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: all 0.6s ease-in-out;
}

.container.active .toggle {
  transform: translateX(50%);
}

.toggle-panel {
  position: absolute;
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 30px;
  text-align: center;
  top: 0;
  transform: translateX(0);
  transition: all 0.6s ease-in-out;
}

.toggle-left {
  transform: translateX(-200%);
}

.container.active .toggle-left {
  transform: translateX(0);
}

.toggle-right {
  right: 0;
  transform: translateX(0);
}

.container.active .toggle-right {
  transform: translateX(200%);
}
</style>
