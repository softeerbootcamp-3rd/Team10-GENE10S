import axios from 'axios';

export async function getCarList() {
  try {
    const response = await axios.get('/v1/reservation/car-list');

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}
