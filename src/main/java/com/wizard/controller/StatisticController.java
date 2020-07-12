package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.StatisticProfitModel;
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
    public CommonListResult<StatisticProfitModel> statisticProfit() {
        log.info("statisticProfit ");
        return statisticService.statisticProfit();
    }


}
