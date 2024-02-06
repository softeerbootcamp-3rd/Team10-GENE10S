import Axios from 'axios';

const axios = Axios.create({
  baseURL: 'http://172.20.10.2:8080/',
});

export default axios;
