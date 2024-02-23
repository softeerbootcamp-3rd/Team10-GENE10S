import Axios from 'axios';

const axios = Axios.create({
  baseURL: `${process.env.REACT_APP_SERVER_URL}`,
  withCredentials: true,
  headers: {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_REDIRECT_URI}`
  }
});

// axios.interceptors.response.use(
//   response => {
//     // 200 OK
//     return response;
//   },
//   error => {
//     if (error.response.status === 401) {
//       window.location.href = '/';
//     }
//     return Promise.reject(error);
//   }
// );

export default axios;
/* github actions test */
