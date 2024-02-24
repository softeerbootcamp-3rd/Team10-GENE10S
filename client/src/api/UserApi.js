import axios from '../api/Settings';

export async function getUserInfo() {
  try {
    const response = await axios.get('v1/user/info');

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}
