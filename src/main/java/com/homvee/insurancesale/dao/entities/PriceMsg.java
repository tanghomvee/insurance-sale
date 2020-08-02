package com.homvee.insurancesale.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_price_msg")
@Data
public class PriceMsg extends BaseEntity {
    private Long carId;
    private String frameNo;
    private String content;
    private Date priceDate;
}
