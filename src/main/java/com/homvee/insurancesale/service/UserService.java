package com.homvee.insurancesale.service;

import com.homvee.insurancesale.dao.entities.User;
import com.homvee.insurancesale.vos.PageVO;
import com.homvee.insurancesale.vos.UserVO;

public interface UserService extends BaseService<User , Long> {
    UserVO findByUserNameOrPhoneNum(String acctName);


    int modifyPwdById(String newPwd, Long id);

    User save(String userName, String phoneNum);

    Integer del(Long[] ids);

    PageVO<UserVO> list(UserVO vo, Integer pageNum, Integer pageSize);

    UserVO findById(Long id);

    void save(UserVO userVO);

    void testTrx();
    void testTrx2();
}
