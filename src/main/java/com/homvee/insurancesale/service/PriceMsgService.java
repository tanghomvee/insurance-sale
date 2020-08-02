package com.homvee.insurancesale.service;

import com.homvee.insurancesale.dao.entities.PriceMsg;
import com.homvee.insurancesale.vos.PriceMsgVO;

import java.util.List;

public interface PriceMsgService extends BaseService<PriceMsg, Long> {
    List<PriceMsgVO> findByFrameNo(String frameNo);
}
