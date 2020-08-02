package com.homvee.insurancesale.service;

import com.homvee.insurancesale.dao.entities.Customer;
import com.homvee.insurancesale.vos.CustomerVO;
import com.homvee.insurancesale.vos.PageVO;

public interface CustomerService extends BaseService<Customer, Long> {
    PageVO<CustomerVO> list(CustomerVO vo, Integer pageNum, Integer pageSize);

    Customer findByFrameNo(String frameNo);

    CustomerVO getById(Long id);
}
