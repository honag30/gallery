import axios from "axios";

const ITEMS_API_BASE_URL = "http://localhost:8080/api/items";

class ItemsService {
  getItemUrl() {
    return axios.get(ITEMS_API_BASE_URL);
  }
}

export default new ItemsService();
