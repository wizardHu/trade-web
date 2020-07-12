package com.wizard.controller;


import com.wizard.model.CommonListResult;
import com.wizard.model.PriceModel;
import com.wizard.service.HuoBiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/price")
@RestController
@Slf4j
public class PriceController {

    @Resource
    private HuoBiService huoBiService;

    @RequestMapping("/getNowPrice")
    public CommonListResult<PriceModel> getNowPrice() {
        return huoBiService.getNowPrice();
    }

}
