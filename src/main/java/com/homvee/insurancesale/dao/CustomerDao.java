package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.Customer;
import com.homvee.insurancesale.vos.CustomerVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerDao extends JpaRepository<Customer, Long> {


    @Query(value = "select * from t_data where yn=1 and frame_no=?1 order by id desc limit 1" , nativeQuery = true)
    Customer findByFrameNo(String frameNo);

    Customer findByIdAndYn(Long id, Integer yn);


    @Query(value = "select * from t_data where yn=1 " +
            " AND IF(:#{#vo.frameNo} IS NOT NULL, frame_no=:#{#vo.frameNo},1)  " +
            " AND IF(:#{#vo.carNo} IS NOT NULL, car_no=:#{#vo.carNo},1)  " +
            " AND IF(:#{#vo.ownerName} IS NOT NULL, owner_name=:#{#vo.ownerName},1) " +
            " AND IF(:#{#vo.phoneNum} IS NOT NULL, phone_num=:#{#vo.phoneNum},1) " +
            " AND id not in (select car_id from t_appointment where yn=1 and sale_man_id=:saleManId)"
            ,

            nativeQuery = true)
    Page<Customer> listNotAppointmentCustomer(@Param("vo") CustomerVO vo,@Param("saleManId") Long saleManId, Pageable pageReq);
}
