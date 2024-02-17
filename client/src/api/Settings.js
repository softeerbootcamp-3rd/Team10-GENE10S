import Axios from 'axios';

const axios = Axios.create({
  baseURL: 'http://api.reservation.genesis-airport.com/',
});

export default axios;
