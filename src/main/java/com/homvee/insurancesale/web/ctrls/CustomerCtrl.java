package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.service.AppointmentService;
import com.homvee.insurancesale.service.CustomerService;
import com.homvee.insurancesale.vos.CustomerVO;
import com.homvee.insurancesale.vos.Msg;
import com.homvee.insurancesale.vos.PageVO;
import com.homvee.insurancesale.web.BaseCtrl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = {"/customer"})
@Slf4j
public class CustomerCtrl extends BaseCtrl {

    @Resource
    private CustomerService customerService;

    @Resource
    private AppointmentService appointmentService;

    public static final Integer MAX = 20;

    private Map<Long , Counter> COUNT = new ConcurrentHashMap<>(15);

    @PostMapping(path = "/list/{pageSize}/{pageNum}")
    public Msg list(@RequestBody CustomerVO vo ,
                   @PathVariable("pageNum") Integer pageNum,
     @PathVariable("pageSize") Integer pageSize){

        Msg msg = chk();
        if (!msg.isSuccess()){
            return msg;
        }

        PageVO<CustomerVO> vos = customerService.list(vo , getUser().getId(),pageNum ,pageSize);
        return Msg.success(vos);
    }
    @GetMapping(path = "/get")
    public Msg one(Long id){

        CustomerVO vos = customerService.getById(id);
        return Msg.success(vos);
    }

    private Msg chk(){
        Set<Long> keys = COUNT.keySet();
        for (Long key : keys){
            Counter counter = COUNT.get(getUser().getId());
            if (counter == null){
                COUNT.remove(key);
            }
            int seconds = Seconds.secondsBetween(DateTime.now(),counter.getRecordTime()).getSeconds();
            if (seconds >= 24*60*60){
                COUNT.remove(key , counter);
            }
        }

        Counter counter = COUNT.get(getUser().getId());
        if (counter == null){
            counter = new Counter();
            COUNT.put(getUser().getId() , counter);
        }
        if (counter.getCnt() > MAX){
            return Msg.error("查询次数已用完");
        }
        counter.incr();
        return Msg.success();
    }

    public  class Counter{
        private AtomicInteger cnt = new AtomicInteger(0);
        private DateTime recordTime = DateTime.now();


        public int getCnt() {
            return cnt.get();
        }

        public int incr() {
            return this.cnt.incrementAndGet();
        }

        public DateTime getRecordTime() {
            return recordTime;
        }
    }
}
