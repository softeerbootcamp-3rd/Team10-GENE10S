import axios from 'axios';

export async function getCarList() {
  try {
    const response = await axios.get('/v1/reservation/car-list');

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export async function validCoupon(serialNumber) {
  try {
    const response = await axios.get('/v1/reservation/coupon/valid', {
      params: { couponNumber: serialNumber },
    });

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}
