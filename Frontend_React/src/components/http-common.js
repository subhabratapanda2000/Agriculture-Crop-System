import axios from "axios";
import authHeader from "../services/auth-header";

export default axios.create({
  baseURL: "http://localhost:8056/jwtsecurity/",
  headers: {
    headers: authHeader(),
  },
});
