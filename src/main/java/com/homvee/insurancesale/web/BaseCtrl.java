package com.homvee.insurancesale.web;

import com.homvee.insurancesale.constants.SessionKey;
import com.homvee.insurancesale.vos.SaleManVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Copyright (c) 2018$. ddyunf.com all rights reserved
 *
 * @author Homvee.Tang(tanghongwei @ ddcloudf.com)
 * @version V1.0
 * @Description TODO(用一句话描述该文件做什么)
 * @date 2018-08-16 09:34
 */
public class BaseCtrl {

    @Resource
    protected HttpSession session;



    public BaseCtrl() {

    }


    protected SaleManVO getUser() {

        return (SaleManVO) session.getAttribute(SessionKey.USER);

    }

    protected void setUser(SaleManVO user) {
      session.setAttribute(SessionKey.USER , user);
    }

}
