package com.homvee.insurancesale.vos;

import com.homvee.insurancesale.enums.AppointmentSateEnum;
import lombok.Data;

import java.util.Date;

/**
 * 预约记录
 */

@Data
public class AppointmentVO extends BaseVO {

    /**预约人：销售人员*/
    private Long saleManId;
    /**被预约：车主*/
    private Long carId;
    /**车牌号*/
    private String carNo;
    /**车架号*/
    private String frameNo;
    /**车主姓名*/
    private String ownerName;
    /**预约状态
     * @see AppointmentSateEnum
     * */
    private String state;
    private String stateTxt;
    /**预约开始时间*/
    private Date startDate;
    /**预约结束时间*/
    private Date endDate;
    private Date appointmentDate;
    /**预约备注*/
    private String note;
}
