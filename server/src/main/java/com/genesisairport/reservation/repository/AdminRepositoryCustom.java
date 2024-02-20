package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;

import java.util.List;

public interface AdminRepositoryCustom {
    List<AdminResponse.AccountDetail> findAccounts(AdminRequest.AccountDetail accountDetail);
}
