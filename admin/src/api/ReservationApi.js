import axios from './Settings';

export async function getReservationDetail(reservationId) {
  try {
    const response = await axios.get(`/v1/reservation/${reservationId}/detail`, {});

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}
