package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.JumpQueueRecordModel;
import com.wizard.model.from.JumpQueueRecordQuery;
import com.wizard.service.JumpQueueRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hulujie
 * @description
 * @createTime 2020/6/22
 */
@RequestMapping("/jumpQueue")
@RestController
@Slf4j
public class JumpQueueController {

    @Resource
    private JumpQueueRecordService jumpQueueRecordService;

    @RequestMapping("/getJumpQueueRecord")
    public CommonListResult<JumpQueueRecordModel> getJumpQueueRecord(JumpQueueRecordQuery jumpQueueRecordQuery) {
        log.info("getJumpQueueRecord req={}",jumpQueueRecordQuery);
        return jumpQueueRecordService.getJumpQueueRecordList(jumpQueueRecordQuery);
    }

}
