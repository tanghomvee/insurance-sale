package com.homvee.insurancesale.service.impl;

import com.homvee.insurancesale.dao.SaleManDao;
import com.homvee.insurancesale.dao.entities.SaleMan;
import com.homvee.insurancesale.dao.entities.User;
import com.homvee.insurancesale.enums.YNEnum;
import com.homvee.insurancesale.service.SaleManService;
import com.homvee.insurancesale.vos.SaleManVO;
import com.homvee.insurancesale.vos.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SaleManServiceImpl extends BaseServiceImpl<SaleMan,Long> implements SaleManService {
    @Resource
    private SaleManDao saleManDao;

    @Override
    public SaleManVO findByUserNameOrPhoneNum(String userName) {
        SaleMan saleMan = saleManDao.findByUserNameAndYn(userName, YNEnum.YES.getVal());
        if (saleMan == null){
            saleMan = saleManDao.findByPhoneNumAndYn(userName , YNEnum.YES.getVal());
        }
        if (saleMan == null){
            return null;
        }
        SaleManVO vo = new SaleManVO();
        BeanUtils.copyProperties(saleMan , vo);
        return vo;
    }

    @Override
    public int modifyPwdById(String newPwd, Long id) {
        return saleManDao.modifyUserPwdById(newPwd , id);
    }
}
