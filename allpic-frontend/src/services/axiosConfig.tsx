import Axios from "axios";

const axios = Axios.create({
  baseURL: process.env.API_ADDRESS as string,
  timeout: parseInt(process.env.API_TIMEOUT ?? '5'),
});

export default axios;
