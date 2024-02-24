import axios from '../api/Settings'

export async function getAccountList ({
  page = 1,
  size = 10,
  adminId = '',
  adminName = '',
  phoneNumber = '',
  sortColumn = '',
  sortDirection = ''
}) {
  try {
    const params = {
      page,
      size,
      adminId,
      adminName,
      phoneNumber,
      sortColumn,
      sortDirection
    }

    const response = await axios.get('v2/admin/account', {
      params: params
    })

    return response
  } catch (error) {
    console.error('Error fetching account list:', error)
    throw error
  }
}
