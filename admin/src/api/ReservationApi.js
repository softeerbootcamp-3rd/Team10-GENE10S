import axios from "./Setting";

export async function getReservationList( shopName, startPickUpDateTime, endPickUpDateTime, startReturnDateTime, 
    endReturnDateTime, customerName, sellName, stage, sortColumn, sortAscending, pageSize, pageNumber) {

        let sortDirection;
        if (sortAscending === true) {
            sortDirection = "asc";
        } else if (sortAscending === false) {
            sortDirection = "desc";
        } else {
            sortDirection = "";
        }
        
        try {
            const response = await axios.get('/v2/admin/reservation/all', {
                params: { 
                    shopName: shopName,
                    startPickUpDateTime: startPickUpDateTime,
                    endPickUpDateTime: endPickUpDateTime,
                    startReturnDateTime: startReturnDateTime,
                    endReturnDateTime: endReturnDateTime,
                    customerName: customerName,
                    sellName: sellName,
                    stage: stage,
                    sortColumn: sortColumn,
                    sortDirection: sortDirection,
                    pageSize: pageSize,
                    pageNumber: pageNumber 
                }
            });

            return response;
            
        } catch (error) {
            console.error(error);
        }
}
