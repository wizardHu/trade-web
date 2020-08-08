package com.wizard.schedule;

import com.wizard.service.ManualTradeService;
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

    @Resource
    private ManualTradeService manualTradeService;


    @Scheduled(cron = "0 0 22 * * ?")
    private void statisticBalance() {
        log.info("begin statisticBalance");
        statisticService.statisticBalance();
        log.info("end statisticBalance");
    }

    @Scheduled(cron = "0/1 * * * * ?")
    private void checkManualTrade() {
        log.info("begin checkManualTrade");
        manualTradeService.checkManualTrade();
        log.info("end checkManualTrade");
    }

}