package com.homvee.insurancesale.vos;

import lombok.Data;

import java.util.Date;

@Data
public class PriceMsgVO extends BaseVO {
    private Long carId;
    private String frameNo;
    private String content;
    private Date priceDate;
}
