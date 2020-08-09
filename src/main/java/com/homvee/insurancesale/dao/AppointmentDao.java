package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.Appointment;
import com.homvee.insurancesale.vos.AppointmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    @Query(value = "select count(id) from t_appointment where DATE_FORMAT(appointment_date,'%Y-%m-%d')=DATE_FORMAT(?1,'%Y-%m-%d') and sale_man_id=?2 and yn =1" , nativeQuery = true)
    Long countAppointment( Date appointmentDate, Long saleManId);

    @Query(value = "select count(id) from t_appointment where state='EXPIRED' AND sale_man_id=?1 and yn =1" , nativeQuery = true)
    Long countExpired(Long saleManId);

    @Modifying
    @Query(value = "update t_appointment set state='EXPIRED' ,change_time=NOW() where state='SUCCESS' and appointment_date <= ?1 and yn=1" , nativeQuery = true)
    Integer expired(Date date);

    @Query(value = "select * from t_appointment where yn=1 " +
            " AND IF(:#{#vo.state} IS NOT NULL, state=:#{#vo.state},1)  " +
            " AND IF(:#{#vo.carNo} IS NOT NULL, car_no=:#{#vo.carNo},1)  " +
            " AND IF(:#{#vo.ownerName} IS NOT NULL, owner_name=:#{#vo.ownerName},1) " +
           // " AND IF(:#{#vo.phoneNum} IS NOT NULL, phone_num=:#{#vo.phoneNum},1) " +
            " AND sale_man_id=:#{#vo.saleManId}" +
            " AND IF(:#{#vo.appointmentDate} IS NOT NULL, " +
            "DATE_FORMAT(appointment_date,'%Y-%m-%d')=DATE_FORMAT(:#{#vo.appointmentDate},'%Y-%m-%d'),1)"
            ,

            nativeQuery = true)
    Page<Appointment> listPage(@Param("vo") AppointmentVO vo, Pageable pageReq);
}
