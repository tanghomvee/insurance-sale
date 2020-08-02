package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.enums.EncryptionEnum;
import com.homvee.insurancesale.service.SaleManService;
import com.homvee.insurancesale.vos.Msg;
import com.homvee.insurancesale.vos.SaleManVO;
import com.homvee.insurancesale.vos.UserVO;
import com.homvee.insurancesale.web.BaseCtrl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = {"/saleman"})
@Slf4j
public class SaleManCtrl extends BaseCtrl {

    @Resource
    private SaleManService saleManService;

    @PostMapping(path = {"/login"})
    public Msg login(@RequestBody SaleManVO vo){
        if (StringUtils.isEmpty(vo.getUserName())){
            return Msg.error("请输入账户名");
        }
        if (StringUtils.isEmpty(vo.getUserPwd())){
            return Msg.error("请输入账户密码");
        }

        SaleManVO saleManVO = saleManService.findByUserNameOrPhoneNum(vo.getUserName());

        if (saleManVO == null){
            return Msg.error("用户不存在");
        }
        String pwd = vo.getUserPwd();

        try {
            pwd = EncryptionEnum.RSA.decode(pwd);
            pwd = EncryptionEnum.MD5_2_BASE64.encrypt(pwd);
        } catch (Exception e) {
            log.error("解密错误:pwd={}" , pwd ,e);
            return Msg.error("密码错误");
        }
        if (!pwd.equals(saleManVO.getUserPwd())){
            return Msg.error("用户密码错误");
        }

        setUser(saleManVO);

        return Msg.success(saleManVO);
    }

    @PostMapping(path = {"/setting"})
    public Msg setting(String newPwd,String oldPwd){
        if (StringUtils.isEmpty(newPwd)){
            return Msg.error("请输入新密码");
        }
        if (StringUtils.isEmpty(oldPwd)){
            return Msg.error("请输入原始密码");
        }
        String pwd = newPwd;
        try {
            pwd = EncryptionEnum.RSA.decode(pwd);
            pwd = EncryptionEnum.MD5_2_BASE64.encrypt(pwd);
        } catch (Exception e) {
            log.error("解密错误:pwd={}" , pwd ,e);
            return Msg.error("密码错误");
        }
        int rs = saleManService.modifyPwdById(pwd , getUser().getId());

        return Msg.success();
    }



}
