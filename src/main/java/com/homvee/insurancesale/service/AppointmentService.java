package com.homvee.insurancesale.service;

import com.homvee.insurancesale.dao.entities.Appointment;
import com.homvee.insurancesale.vos.AppointmentVO;
import com.homvee.insurancesale.vos.PageVO;

import java.util.Date;

public interface AppointmentService extends BaseService<Appointment, Long> {

    Long save(AppointmentVO vo);

    PageVO<AppointmentVO> list(AppointmentVO vo, Integer pageNum, Integer pageSize);

    Integer edit(AppointmentVO vo);

    Long count(AppointmentVO vo);

    Long countExpired(Long sale);

    Integer expired(Date date);
}
