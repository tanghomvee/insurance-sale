package com.homvee.insurancesale.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.homvee.insurancesale.dao.PriceMsgDao;
import com.homvee.insurancesale.dao.entities.PriceMsg;
import com.homvee.insurancesale.enums.YNEnum;
import com.homvee.insurancesale.service.PriceMsgService;
import com.homvee.insurancesale.vos.PriceMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PriceMsgServiceImpl extends BaseServiceImpl<PriceMsg ,Long> implements PriceMsgService {
    @Resource
    private PriceMsgDao priceMsgDao;

    @Override
    public List<PriceMsgVO> findByFrameNo(String frameNo) {
        List<PriceMsg> datas = priceMsgDao.findByFrameNoAndYnOrderByPriceDateDesc(frameNo, YNEnum.YES.getVal());
        if (CollectionUtils.isEmpty(datas)){
            return Lists.newArrayList();
        }
        return Lists.transform(datas, data -> {
            PriceMsgVO vo = new PriceMsgVO();
            BeanUtils.copyProperties(data,vo);
            return vo;
        });
    }
}
