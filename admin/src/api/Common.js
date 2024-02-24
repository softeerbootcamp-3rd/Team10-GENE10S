import axios from "./Settings";

export async function callLogin(adminId, adminPwd) {
  try {
    const response = await axios.post("/v2/admin/account/login", {
      adminId,
      adminPwd,
    });

    return response.data;
  } catch (error) {
    console.error(error);
  }
}
