package com.homvee.insurancesale.dao.entities;

import com.homvee.insurancesale.enums.AppointmentSateEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 预约记录
 */
@Entity
@Table(name = "t_appointment")
@Data
public class Appointment extends BaseEntity {

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
    /**预约时间*/
    private Date appointmentDate;
    /**预约备注*/
    private String note;
}
