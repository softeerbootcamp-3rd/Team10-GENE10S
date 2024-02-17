import Axios from 'axios';

const axios = Axios.create({
  baseURL: 'http://api.reservation.genesis-airport.com:8080/',
});

export default axios;
