import axios from './Settings';

export async function getReservationDetail(reservationId) {
  try {
    const response = await axios.get(`/v1/reservation/${reservationId}/detail`, {});

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export async function postProgress(reservationId, progress, detail) {
  try {
    const response = await axios.post(`/v2/admin/reservation/progress`, {
      reservationId: reservationId,
      progress: progress,
      detail: detail
    });

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export async function deleteProgress(stepId) {
  try {
    const response = await axios.delete(`/v2/admin/reservation/progress/${stepId}`);

    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function postMaintenanceImage(reservationId, status, image) {
  try {
    const formData = new FormData();
    formData.append('status', status);
    formData.append('image', image);

    const response = await axios.post(`/v2/admin/reservation/${reservationId}/image`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export async function deleteMaintenanceImage(imageId) {
  try {
    const response = await axios.delete(`/v2/admin/reservation/image/${imageId}`);

    return response.data;
  } catch (error) {
    console.error(error);
  }
}
