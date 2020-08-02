package com.homvee.insurancesale.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sale_man_ins_account")
@Data
public class SaleManInsCompanyAcct extends BaseEntity {

    private Long saleManId;
    private Long insAcctId;


}
