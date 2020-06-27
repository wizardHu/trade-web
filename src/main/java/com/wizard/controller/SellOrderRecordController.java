package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.SellOrderRecordModel;
import com.wizard.model.from.SellOrderRecordQuery;
import com.wizard.service.SellOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hulujie
 * @description
 * @createTime 2020/6/22
 */
@RequestMapping("/sellOrderRecord")
@RestController
@Slf4j
public class SellOrderRecordController {

    @Resource
    private SellOrderRecordService sellOrderRecordService;

    @RequestMapping("/getSellOrderRecord")
    public CommonListResult<SellOrderRecordModel> getSellOrderRecord(SellOrderRecordQuery sellOrderRecordQuery) {
        log.info("getSellOrderRecord req={}",sellOrderRecordQuery);
        return sellOrderRecordService.getSellOrderRecordList(sellOrderRecordQuery);
    }

}
