package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.PriceMsg;
import com.homvee.insurancesale.vos.PriceMsgVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceMsgDao extends JpaRepository<PriceMsg, Long> {

    List<PriceMsg> findByCarIdAndYn(Long carId, Integer yn);

    List<PriceMsg> findByFrameNoAndYn(String frameNo,Integer yn);

    List<PriceMsg> findByFrameNoAndYnOrderByPriceDateDesc(String frameNo, Integer yn);
}
