package com.homvee.insurancesale.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_ins_company")
@Data
public class InsCompany extends BaseEntity {
    private String insCode;
    private String insName;
    private String insLogo;
    private String priceUrl;
}
