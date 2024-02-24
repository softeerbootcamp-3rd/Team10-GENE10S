import axios from '../api/Settings';
import { formatDate } from '../utils/dateUtils';

export async function getAvailableDate(shopName) {
  try {
    const response = await axios.get('/v1/reservation/date', {
      params: { repairShop: shopName },
    });

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export async function getAvailableTime(year, month, day, shopName) {
  try {
    const response = await axios.get('/v1/reservation/time', {
      params: { repairShop: shopName, date: formatDate(new Date(year, month, day)) },
    });

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

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

export async function postReservation(reservationInfo) {
  try {
    const response = await axios.post('/v1/reservation', reservationInfo);

    return response.data;
  } catch (error) {
    console.error(error);
  }
}
