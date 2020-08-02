package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.service.PriceMsgService;
import com.homvee.insurancesale.vos.Msg;
import com.homvee.insurancesale.vos.PriceMsgVO;
import com.homvee.insurancesale.web.BaseCtrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(path = {"/pricemsg"})
@Slf4j
public class PriceMsgCtrl extends BaseCtrl {

    @Resource
    private PriceMsgService priceMsgService;

    @GetMapping(path = {"/get"})
    public Msg get(String frameNo){
        if (StringUtils.isEmpty(frameNo)){
            return Msg.error("请输入账户名");
        }

        List<PriceMsgVO> vos = priceMsgService.findByFrameNo(frameNo);
        if (CollectionUtils.isEmpty(vos)){
            return Msg.success();
        }

        return Msg.success(vos.get(0));
    }




}
