import Axios from 'axios';

const axios = Axios.create({
  baseURL: `${process.env.REACT_APP_SERVER_URL}`,
  withCredentials: true,
  headers: {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_REDIRECT_URI}`,
    'Access-Control-Allow-Credentials': "true"
  }
});

export default axios;
/* github actions test */
