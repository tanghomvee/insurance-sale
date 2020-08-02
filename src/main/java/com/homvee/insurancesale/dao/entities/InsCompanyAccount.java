package com.homvee.insurancesale.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_ins_company_account")
@Data
public class InsCompanyAccount extends BaseEntity {
    private String insCode;
    private String insName;
    private Long insId;
    private String acctName;
    private String acctPwd;
}
