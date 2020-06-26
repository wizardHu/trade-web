package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.StopLossRecordModel;
import com.wizard.model.from.StopLossRecordQuery;
import com.wizard.service.StopLossRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hulujie
 * @description
 * @createTime 2020/6/22
 */
@RequestMapping("/stopLoss")
@RestController
@Slf4j
public class StopLossController {

    @Resource
    private StopLossRecordService stopLossRecordService;

    @RequestMapping("/getStopLossRecord")
    public CommonListResult<StopLossRecordModel> getStopLossRecord(StopLossRecordQuery stopLossRecordQuery) {
        log.info("getStopLossRecord req={}",stopLossRecordQuery);
        return stopLossRecordService.getStopLossRecordList(stopLossRecordQuery);
    }

}
