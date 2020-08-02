package com.homvee.insurancesale.service.impl;

import com.google.common.base.Function;
import com.homvee.insurancesale.dao.CustomerDao;
import com.homvee.insurancesale.dao.entities.Customer;
import com.homvee.insurancesale.enums.YNEnum;
import com.homvee.insurancesale.service.CustomerService;
import com.homvee.insurancesale.vos.CustomerVO;
import com.homvee.insurancesale.vos.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CustomerServiceImpl extends BaseServiceImpl<Customer,Long> implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Override
    public PageVO<CustomerVO> list(CustomerVO vo,Long saleManId, Integer pageNum, Integer pageSize) {
        Pageable pageReq = build(pageNum, pageSize);

        Page<Customer> all =  customerDao.listNotAppointmentCustomer(vo,saleManId,pageReq);

        PageVO<CustomerVO> pages = convert2PageVo(all , new Function<Customer, CustomerVO>() {
            @NullableDecl
            @Override
            public CustomerVO apply(@NullableDecl Customer tmpData) {
                CustomerVO dataVO = new CustomerVO();
                BeanUtils.copyProperties(tmpData ,dataVO);
                return dataVO;
            }
        });





        return pages;
    }


    @Override
    public Customer findByFrameNo(String frameNo) {
        return customerDao.findByFrameNo(frameNo);
    }

    @Override
    public CustomerVO getById(Long id) {
         Customer customer =  customerDao.findByIdAndYn(id,YNEnum.YES.getVal());
         CustomerVO vo = new CustomerVO();
         BeanUtils.copyProperties(customer,vo);
        return vo;
    }
}
