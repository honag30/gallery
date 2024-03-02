import { defineStore } from "pinia";
import axios from "axios";

export const useAccountStore = defineStore("account", {
    state: () => ({
        account: {
            id: Number,
            accountDetail: Array
        }
    }),
    // computed
    getters: {

    },
    // methods
    actions: {
        setAccount(id) {
            this.account.id = id
        },
        SetDetailToNull() {
            this.account.accountDetail = []
            // console.log(this.account.accountDetail);
        },

        getAccount() {
            axios.get("http://localhost:8080/api/account/check",
                { withCredentials: true })
                .then((res) => {
                    this.account.accountDetail = res.data
                    // console.log(this.account.accountDetail);
                }).catch((err) => {
                    console.log(err);
                })
        }
    },

    persist: {
        enabled: true
    }
});