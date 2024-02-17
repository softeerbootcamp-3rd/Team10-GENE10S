import Axios from 'axios';

const axios = Axios.create({
  baseURL: 'http://genesis-airport-react-app-user.s3-website.ap-northeast-2.amazonaws.com/',
});

export default axios;
