package com.genesisairport.reservation.common.util;


import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    public static Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Long) session.getAttribute("userId");
        }
        return null;
    }

    public static Long getAdminIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                return Long.TYPE.cast(session.getAttribute("adminId"));
            }
            return null;
        } catch (ClassCastException e) {
            throw new GeneralException(ResponseCode.UNAUTHORIZED, "관리자 정보가 존재하지 않습니다.");
        }
    }

}
