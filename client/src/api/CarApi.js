import axios from 'axios';

export async function registerCar({ sellName, plateNumber }) {
  try {
    const requestBody = {
      sellName: sellName,
      plateNumber: plateNumber,
    };

    axios.post('v2/car', requestBody, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (error) {
    console.error(error);
  }
}

export async function deleteCar(carId) {
  try {
    axios.delete(`v2/car/${carId}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (error) {
    console.error(error);
  }
}
