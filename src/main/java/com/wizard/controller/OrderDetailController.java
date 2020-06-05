package com.wizard.controller;

import com.wizard.model.CommonListResult;
import com.wizard.model.OrderDetailModel;
import com.wizard.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hulujie
 * @description
 * @createTime 2020/6/5
 */
@RequestMapping("/orderDetail")
@RestController
@Slf4j
public class OrderDetailController {

    @Resource
    private OrderDetailService orderDetailService;

    @RequestMapping("/orderDetail")
    public CommonListResult<OrderDetailModel> orderDetail(String orderId) {
        log.info("orderDetail req={}",orderId);
        return orderDetailService.getOrderDetailModels(orderId);
    }
}
