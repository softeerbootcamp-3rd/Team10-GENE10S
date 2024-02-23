import axios from './Settings';

export async function getAvailableTime(shopName, startDate, endDate) {
    try {
        const response = await axios.get(`/v2/admin/shop/available`,
            {
                params: {
                    shopName: shopName,
                    businessDayFrom: startDate,
                    businessDayTo: endDate
                }
            });

        return response.data.data;
    } catch (error) {
        console.error(error);
    }
}

export async function addAvailableTime(shopName, businessDay) {
    try {
        const response = await axios.post(`/v2/admin/shop/available`,
            {
                shopName: shopName,
                businessDay: businessDay,
            });

        return response.data.data;
    } catch (error) {
        console.error(error);
    }
}

export async function removeAvailableTime(shopName, businessDay) {
    try {
        const response = await axios.delete(`/v2/admin/shop/available`,
            {
                params: {
                    shopName: shopName,
                    businessDay: businessDay
                }
            });

        return response.data.data;
    } catch (error) {
        console.error(error);
    }
}

export async function removeAvailableTimeWithReserv(shopName, businessDay, message) {
    try {
        const response = await axios.delete(`/v2/admin/shop/cancel`,
            {
                params: {
                    shopName: shopName,
                    businessDay: businessDay,
                    message: message
                }
            });

        return response.data.data;
    } catch (error) {
        console.error(error);
    }
}
