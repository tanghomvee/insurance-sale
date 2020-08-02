package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.service.CustomerService;
import com.homvee.insurancesale.vos.CustomerVO;
import com.homvee.insurancesale.vos.Msg;
import com.homvee.insurancesale.vos.PageVO;
import com.homvee.insurancesale.web.BaseCtrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = {"/customer"})
@Slf4j
public class CustomerCtrl extends BaseCtrl {

    @Resource
    private CustomerService customerService;


    @PostMapping(path = "/list/{pageSize}/{pageNum}")
    public Msg list(@RequestBody CustomerVO vo ,
                   @PathVariable("pageNum") Integer pageNum,
     @PathVariable("pageSize") Integer pageSize){

        PageVO<CustomerVO> vos = customerService.list(vo , pageNum ,pageSize);
        return Msg.success(vos);
    }
    @GetMapping(path = "/get")
    public Msg one(Long id){

        CustomerVO vos = customerService.getById(id);
        return Msg.success(vos);
    }

}
