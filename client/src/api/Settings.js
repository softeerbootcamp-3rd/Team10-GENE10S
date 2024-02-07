import Axios from 'axios';

const axios = Axios.create({
  baseURL: 'http://localhost:8080/',
});

export default axios;
