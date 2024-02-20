import Axios from 'axios';

const axios = Axios.create({
  baseURL: `${process.env.REACT_APP_SERVER_URL}`,
  withCredentials: true,
  headers: {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_REDIRECT_URI}`
  }
});

export default axios;