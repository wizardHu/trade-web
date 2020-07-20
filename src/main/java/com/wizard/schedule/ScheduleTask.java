package com.wizard.schedule;

import com.wizard.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ScheduleTask {

    @Resource
    private StatisticService statisticService;


    @Scheduled(cron = "0 0 23 * * ?")
    private void statisticBalance() {
        log.info("begin statisticBalance");
        statisticService.statisticBalance();
        log.info("end statisticBalance");
    }

}