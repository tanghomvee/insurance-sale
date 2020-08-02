package com.homvee.insurancesale.vos;

import lombok.Data;

@Data
public class UserDataVO extends BaseVO{

    /**自带车牌*/
    private String carNo;
    /**车辆品牌*/
    private String brand;
    /**识别代码*/
    private String frameNo;
    /**发动机号*/
    private transient String engineNo;
    /**身份证号*/
    private  transient String idNo;
    /**所有人*/
    private transient String owerName;
    /**发牌日期*/
    private transient String licenseDate;
    /**住所*/
    private transient String addr;
    /**手机号码*/
    private transient String phoneNum;
    /**保险公司*/
    private String company;
    /**商业险保单号*/
    private String viPolicyNo;
    /**终保日期*/
    private String finalInsDate;
    /**车辆使用价值*/
    private String useVal;
    /**商业险原价*/
    private String viAmount;
    /**商业险折扣*/
    private String viDiscount;
    /**商业险优惠后*/
    private String payViAmt;
    /**交强险*/
    private String ciAmount;
    /**车船税*/
    private String vvt;
    /**内容*/
    private String content;
    private String matchState;
}
