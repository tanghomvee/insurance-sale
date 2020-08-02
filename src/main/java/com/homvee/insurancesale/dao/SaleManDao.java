package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.SaleMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SaleManDao extends JpaRepository<SaleMan, Long> {

    SaleMan findByUserNameAndYn(String acctName, Integer yn);

    SaleMan findByPhoneNumAndYn(String phoneNum,Integer yn);

    @Modifying
    @Query(value = "update t_sale_man set user_pwd=?1 ,change_time=now() where id in(?2)" , nativeQuery = true)
    int modifyUserPwdById(String newPwd, Long id);
}
