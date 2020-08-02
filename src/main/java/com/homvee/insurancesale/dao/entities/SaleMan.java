package com.homvee.insurancesale.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sale_man")
@Data
public class SaleMan extends BaseEntity {
    private String userName;
    private String userPwd;
    private String phoneNum;
    private String realName;
    private String bankNum;
    private String idNum;


}
