package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDao extends JpaRepository<Customer, Long> {


    @Query(value = "select * from t_data where yn=1 and frame_no=?1 order by id desc limit 1" , nativeQuery = true)
    Customer findByFrameNo(String frameNo);

    Customer findByIdAndYn(Long id, Integer yn);
}
