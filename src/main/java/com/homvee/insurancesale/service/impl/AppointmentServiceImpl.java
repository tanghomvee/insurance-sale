package com.homvee.insurancesale.service.impl;

import com.google.common.base.Function;
import com.homvee.insurancesale.dao.AppointmentDao;
import com.homvee.insurancesale.dao.entities.Appointment;
import com.homvee.insurancesale.enums.AppointmentSateEnum;
import com.homvee.insurancesale.enums.YNEnum;
import com.homvee.insurancesale.service.AppointmentService;
import com.homvee.insurancesale.vos.AppointmentVO;
import com.homvee.insurancesale.vos.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment , Long> implements AppointmentService {

    @Resource
    private AppointmentDao appointmentDao;
    @Override
    public Long save(AppointmentVO vo) {
        Appointment data = new Appointment();
        BeanUtils.copyProperties(vo ,data);
        data = appointmentDao.save(data);
        return data.getId();
    }

    @Override
    public PageVO<AppointmentVO> list(AppointmentVO vo, Integer pageNum, Integer pageSize) {
        Pageable pageReq = build(pageNum, pageSize);
        Appointment data = new Appointment();
        BeanUtils.copyProperties(vo , data);
        data.setYn(YNEnum.YES.getVal());

//        Example<Appointment> cond = Example.of(data, ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withIgnorePaths("crateTime")
//                .withMatcher("carNo", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("ownerName", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("phoneNum", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("saleManId", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("yn", ExampleMatcher.GenericPropertyMatchers.exact())
//                .withMatcher("appointmentDate", ExampleMatcher.GenericPropertyMatchers.exact())
//        );
//
//        Page<Appointment> all = appointmentDao.findAll(cond ,pageReq);
        Page<Appointment> all = appointmentDao.listPage(vo ,pageReq);

        PageVO<AppointmentVO> pages = convert2PageVo(all , new Function<Appointment, AppointmentVO>() {
            @NullableDecl
            @Override
            public AppointmentVO apply(@NullableDecl Appointment tmpData) {
                AppointmentVO dataVO = new AppointmentVO();
                BeanUtils.copyProperties(tmpData ,dataVO);
                dataVO.setStateTxt(AppointmentSateEnum.valueOf(tmpData.getState()).getDetail());
                return dataVO;
            }
        });
        return pages;
    }

    @Transactional
    @Override
    public Integer edit(AppointmentVO vo) {
        Appointment appointment =  appointmentDao.getOne(vo.getId());
        appointment.setAppointmentDate(vo.getAppointmentDate());
        appointment.setState(vo.getState());
        appointment.setNote(vo.getNote());
        vo.setChangeTime(DateTime.now().toDate());
        appointmentDao.saveAndFlush(appointment);
        return 1;
    }

    @Override
    public Long count(AppointmentVO vo) {

        return appointmentDao.countAppointment(vo.getAppointmentDate(),vo.getSaleManId());
    }

    @Override
    public Long countExpired(Long sale) {
        return appointmentDao.countExpired(sale);
    }

    @Override
    @Transactional
    public Integer expired(Date date) {
        return appointmentDao.expired(date);
    }
}
