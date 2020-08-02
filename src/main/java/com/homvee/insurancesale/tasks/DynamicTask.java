package com.homvee.insurancesale.tasks;

import com.homvee.insurancesale.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class DynamicTask  implements SchedulingConfigurer {
    @Resource
    private AppointmentService appointmentService;
    @Value("${task.cron}")
    private String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            log.info("执行检查已经过期的预约记录开始");
             appointmentService.expired(DateTime.now().toDate());
            log.info("执行检查已经过期的预约记录结束");
        }, triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext));

    }
}
