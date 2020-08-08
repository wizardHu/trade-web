package com.wizard.controller;

import com.wizard.model.CommonResult;
import com.wizard.model.from.ManualTradeAdd;
import com.wizard.service.ManualTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/manualTrade")
@RestController
@Slf4j
public class ManualTradeController {

    @Resource
    private ManualTradeService manualTradeService;

    @RequestMapping("/buy")
    public CommonResult buy(ManualTradeAdd manualTradeAdd){
        log.info("buy req={}",manualTradeAdd);
        return manualTradeService.buy(manualTradeAdd);
    }

}
