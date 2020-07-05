package com.wizard.controller;

import com.wizard.model.CommonResult;
import com.wizard.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/statistic")
@RestController
@Slf4j
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @RequestMapping("/statisticProfit")
    public CommonResult statisticProfit() {
        log.info("statisticProfit ");
        return statisticService.statisticProfit();
    }


}
