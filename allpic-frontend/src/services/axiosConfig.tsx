import Axios from "axios";
import Configuration from "./Configuration";

const axios = Axios.create({
  baseURL: Configuration.APIAddress,
  timeout: Configuration.APITimeout,
});

export default axios;
