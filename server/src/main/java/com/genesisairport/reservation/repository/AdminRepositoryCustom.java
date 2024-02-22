package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepositoryCustom {
    Page<AdminResponse.AccountDetail> findAccounts(Pageable pageable, AdminRequest.AccountDetail accountDetail);
}
