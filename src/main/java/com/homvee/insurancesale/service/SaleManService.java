package com.homvee.insurancesale.service;

import com.homvee.insurancesale.dao.entities.SaleMan;
import com.homvee.insurancesale.vos.SaleManVO;
import com.homvee.insurancesale.vos.UserVO;

public interface SaleManService extends BaseService<SaleMan, Long> {
    SaleManVO findByUserNameOrPhoneNum(String userName);

    int modifyPwdById(String newPwd, Long id);
}
