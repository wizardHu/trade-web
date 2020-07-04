package com.wizard.controller;


import com.wizard.model.CommonListResult;
import com.wizard.service.KLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/kLine")
@RestController
@Slf4j
public class KLineController {

    @Resource
    private KLineService kLineService;

    @RequestMapping("/getKline")
    public CommonListResult<Object[]> getKline(String symbol, String time) {
        log.info("getKline symbol={} time={}",symbol,time);
        return kLineService.getKline(symbol,time);
    }

}
