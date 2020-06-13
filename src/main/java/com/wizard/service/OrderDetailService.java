package com.wizard.service;

import com.wizard.model.*;
import com.wizard.model.from.BuyRecordQuery;
import com.wizard.model.from.BuySellHistoryRecordQuery;
import com.wizard.model.from.StopLossHistoryRecordQuery;
import com.wizard.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderDetailService {

    @Resource
    private BuyRecordService buyRecordService;

    @Resource
    private StopLossRecordService stopLossRecordService;

    @Resource
    private JumpQueueRecordService jumpQueueRecordService;

    @Resource
    private SellOrderRecordService sellOrderRecordService;

    /**
     * 获取某个订单的详情
     * @param orderId
     * @return
     */
    public CommonListResult<OrderDetailModel> getOrderDetailModels(String orderId){

        List<OrderDetailModel> list = new ArrayList<OrderDetailModel>();

        //1 获取订单状态
        OrderStatusModel orderStatusModel = buildOrderStatus(orderId);
//
        if(orderStatusModel == null ) return CommonListResult.getFailedResult(Constant.ReturnCodeEnum.FAIL.getCode(),"订单不存在");

        //2 获取订单所有交易记录
        BuySellHistoryRecordQuery buySellHistoryRecordQuery = new BuySellHistoryRecordQuery();
        buySellHistoryRecordQuery.setOrderId(orderId);
        CommonListResult<BuySellHistoryRecordModel>  buySellHistoryRecordModels = buyRecordService.getBuySellHistoryRecordList(buySellHistoryRecordQuery);

        list.addAll(buildOrderDetaiByBuySellHistoryRecords(buySellHistoryRecordModels));

        StopLossHistoryRecordQuery stopLossHistoryRecordQuery = new StopLossHistoryRecordQuery();
        stopLossHistoryRecordQuery.setOriOrderId(orderId);

        CommonListResult<StopLossHistoryRecordModel>  stopLossHistoryRecordModels = stopLossRecordService.getStopLossHistoryRecordList(stopLossHistoryRecordQuery);

        list.addAll(buildOrderDetaiByStopLossHistoryRecordRecords(stopLossHistoryRecordModels));

        Collections.sort(list, Comparator.comparing(OrderDetailModel::getCreateTime));

        CommonListResult result = CommonListResult.getSuccListResult();
        result.setResultList(list);
        result.setResult(orderStatusModel);
        return result;
    }

    private OrderStatusModel buildOrderStatus(String orderId) {
        OrderStatusModel orderStatusModel = new OrderStatusModel();
        BuyRecordQuery buyRecordQuery = new BuyRecordQuery();
        buyRecordQuery.setOrderId(orderId);
        CommonListResult<BuyRecordModel> buyRecordModelCommonListResult = buyRecordService.getBuyRecordList(buyRecordQuery);
        if(buyRecordModelCommonListResult != null && !CollectionUtils.isEmpty(buyRecordModelCommonListResult.getResultList())){
            BuyRecordModel buyRecord = buyRecordModelCommonListResult.getResultList().get(0);
            orderStatusModel.setOrderId(buyRecord.getOrderId());
            orderStatusModel.setStatus(buyRecord.getStatus());
            orderStatusModel.setSymbol(buyRecord.getSymbol());
            orderStatusModel.setDesc(Constant.BuyRecordStatusEnum.convert(buyRecord.getStatus()).getDesc());
        }else {//待交易表没有的话，就查交易记录表

            BuySellHistoryRecordQuery buySellHistoryRecordQuery = new BuySellHistoryRecordQuery();
            buySellHistoryRecordQuery.setOrderId(orderId);
            CommonListResult<BuySellHistoryRecordModel>  buySellHistoryRecordModels = buyRecordService.getBuySellHistoryRecordList(buySellHistoryRecordQuery);
            if(buySellHistoryRecordModels != null && !CollectionUtils.isEmpty(buySellHistoryRecordModels.getResultList())){
                BuySellHistoryRecordModel buySellHistoryRecordModel = buySellHistoryRecordModels.getResultList().get(0);

                orderStatusModel.setOrderId(orderId);
                orderStatusModel.setStatus(Constant.BuyRecordStatusEnum.TRADE_SUCCESS.getStatus());
                orderStatusModel.setSymbol(buySellHistoryRecordModel.getSymbol());
                orderStatusModel.setDesc(Constant.BuyRecordStatusEnum.TRADE_SUCCESS.getDesc());
            }else {
                return null;
            }
        }

        return orderStatusModel;
    }

    private Collection<? extends OrderDetailModel> buildOrderDetaiByStopLossHistoryRecordRecords(CommonListResult<StopLossHistoryRecordModel> stopLossHistoryRecordModels) {

        List<OrderDetailModel> list = new ArrayList<OrderDetailModel>();

        if(stopLossHistoryRecordModels != null && !CollectionUtils.isEmpty(stopLossHistoryRecordModels.getResultList())) {
            List<StopLossHistoryRecordModel> stopLossHistoryRecordModelList = stopLossHistoryRecordModels.getResultList();

            for (StopLossHistoryRecordModel stopLossHistoryRecordModel : stopLossHistoryRecordModelList) {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.setFrom(Constant.STOP_LOSS_FROM);
                orderDetailModel.setAmount(stopLossHistoryRecordModel.getAmount());
                orderDetailModel.setCreateTime(stopLossHistoryRecordModel.getCreateTime());

                orderDetailModel.setSymbol(stopLossHistoryRecordModel.getSymbol());
                orderDetailModel.setType(stopLossHistoryRecordModel.getType());
                orderDetailModel.setOperPrice(stopLossHistoryRecordModel.getOperPrice());
                orderDetailModel.setOrderId(stopLossHistoryRecordModel.getOriOrderId());
                list.add(orderDetailModel);
            }
        }

        return list;
    }

    private List<OrderDetailModel> buildOrderDetaiByBuySellHistoryRecords(CommonListResult<BuySellHistoryRecordModel> buySellHistoryRecordModels){
        List<OrderDetailModel> list = new ArrayList<OrderDetailModel>();

        if(buySellHistoryRecordModels != null && !CollectionUtils.isEmpty(buySellHistoryRecordModels.getResultList())){
            List<BuySellHistoryRecordModel> buySellHistoryRecordModelList = buySellHistoryRecordModels.getResultList();

            for (BuySellHistoryRecordModel buySellHistoryRecordModel : buySellHistoryRecordModelList) {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.setFrom(Constant.BUY_SELL_FROM);
                orderDetailModel.setAmount(buySellHistoryRecordModel.getAmount());
                orderDetailModel.setCreateTime(buySellHistoryRecordModel.getCreateTime());

                orderDetailModel.setSymbol(buySellHistoryRecordModel.getSymbol());
                orderDetailModel.setType(buySellHistoryRecordModel.getType());
                if(buySellHistoryRecordModel.getType() == Constant.BUY_TYPE){
                    orderDetailModel.setOperPrice(buySellHistoryRecordModel.getBuyPrice());
                    orderDetailModel.setOrderId(buySellHistoryRecordModel.getBuyOrderId());
                }else {
                    orderDetailModel.setOperPrice(buySellHistoryRecordModel.getSellPrice());
                    orderDetailModel.setOrderId(buySellHistoryRecordModel.getSellOrderId());
                }
                list.add(orderDetailModel);
            }
        }
        return list;
    }

}
