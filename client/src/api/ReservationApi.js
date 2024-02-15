import axios from 'axios';

const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 해줌
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
}

export async function getAvailableTime(year, month, day) {
  try {
    const response = await axios.get('/v1/reservation/time', {
      params: { repairShop: '블루핸즈 인천공항점',
                date: formatDate(new Date(year, month, day)) }
    })

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
