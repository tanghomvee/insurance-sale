package com.homvee.insurancesale.dao;

import com.homvee.insurancesale.dao.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    @Query(value = "select count(id) from t_appointment where DATE_FORMAT(end_date,'%Y-%m-%d')=DATE_FORMAT(?1,'%Y-%m-%d') and sale_man_id=?2 and yn =1" , nativeQuery = true)
    Long countAppointment( Date endDate, Long saleManId);

    @Query(value = "select count(id) from t_appointment where state='EXPIRED' AND sale_man_id=?1 and yn =1" , nativeQuery = true)
    Long countExpired(Long saleManId);

    @Modifying
    @Query(value = "update t_appointment set state='EXPIRED' ,change_time=NOW() where state='SUCCESS' and end_date <= ?1 and yn=1" , nativeQuery = true)
    Integer expired(Date date);


}
