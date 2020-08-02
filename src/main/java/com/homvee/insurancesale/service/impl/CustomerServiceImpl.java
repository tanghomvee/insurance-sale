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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    public PageVO<CustomerVO> list(CustomerVO vo, Integer pageNum, Integer pageSize) {
        Pageable pageReq = build(pageNum, pageSize);
        Customer data = new Customer();
        BeanUtils.copyProperties(vo , data);
        data.setYn(YNEnum.YES.getVal());

        Example<Customer> cond = Example.of(data, ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("crateTime")
                .withMatcher("carNo", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("ownerName", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("phoneNum", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("yn", ExampleMatcher.GenericPropertyMatchers.exact())
        );

        Page<Customer> all = customerDao.findAll(cond ,pageReq);

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
