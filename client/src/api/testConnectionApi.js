import axios from './Settings';

export async function testConnectionApi() {
  try {
    const response = await axios.get('/hello');

    return response;
  } catch (error) {
    console.error(error);
  }
}
