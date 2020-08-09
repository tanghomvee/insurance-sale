package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.enums.YNEnum;
import com.homvee.insurancesale.service.AppointmentService;
import com.homvee.insurancesale.service.CustomerService;
import com.homvee.insurancesale.vos.AppointmentVO;
import com.homvee.insurancesale.vos.CustomerVO;
import com.homvee.insurancesale.vos.Msg;
import com.homvee.insurancesale.vos.PageVO;
import com.homvee.insurancesale.web.BaseCtrl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping(path = {"/appointment"})
@Slf4j
public class AppointmentCtrl extends BaseCtrl {

    @Resource
    private AppointmentService appointmentService;


    @PostMapping(path = "/add")
    public Msg add(@RequestBody AppointmentVO vo){
        if (vo.getCarId() == null){
            return Msg.error("客户信息错误");
        }
        vo.setSaleManId(getUser().getId());
        vo.setYn(YNEnum.YES.getVal());
        vo.setCreateTime(DateTime.now().toDate());
        appointmentService.save(vo);
        return Msg.success(vo);
    }
    @PostMapping(path = "/edit")
    public Msg edit(@RequestBody AppointmentVO vo){
        if (vo.getId() == null){
            return Msg.error("预约信息错误");
        }

        appointmentService.edit(vo);
        return Msg.success(vo);
    }
    @PostMapping(path = "/list/{pageSize}/{pageNum}")
    public Msg list(@RequestBody AppointmentVO vo ,
                    @PathVariable("pageNum") Integer pageNum,
                    @PathVariable("pageSize") Integer pageSize){

        vo.setSaleManId(getUser().getId());
        PageVO<AppointmentVO> vos = appointmentService.list(vo , pageNum ,pageSize);
        return Msg.success(vos);
    }
    @GetMapping(path = "/count")
    public Msg count( Integer delta){
        Date endDate = DateTime.now().plusDays(delta).toLocalDate().toDate();
        AppointmentVO vo  = new AppointmentVO();
        vo.setSaleManId(getUser().getId());
        vo.setYn(YNEnum.YES.getVal());
        vo.setAppointmentDate(endDate);
        Long cnt = appointmentService.count(vo);
        return Msg.success(cnt);
    }
    @GetMapping(path = "/expired")
    public Msg expired(){
        Long cnt = appointmentService.countExpired(getUser().getId());
        return Msg.success(cnt);
    }

}
