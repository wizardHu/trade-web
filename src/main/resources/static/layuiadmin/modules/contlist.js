/** layuiAdmin.std-v1.4.0 LPPL License By https://www.layui.com/admin/ */
;layui.define(["table", "form"], function(t) {
    var e = layui.$
        , i = layui.table
        , n = layui.form;

    i.render({
        elem: "#LAY-app-content-list",
        url:  "/tradeData/buyData",
        cols: [[ {
            field: "symbol",
            title: "交易对",
            minWidth: 80
        }, {
            field: "price",
            title: "当前价格",
            width: 100
        }, {
            field: "oriPrice",
            title: "原始价格",
            width: 100

        }, {
            field: "minTradePrice",
            title: "最低交易价格",
            width: 120

        }, {
            field: "createTime",
            title: "交易时间",
            sort: !0,
            width: 180
        }, {
            field: "amount",
            title: "交易数量",
            minWidth: 100,
            align: "center"
        }, {
            field: "lastPrice",
            title: "近一次交易价格",
            width: 100
        }, {
            field: "status",
            title: "状态",
            width: 100,
            templet: "#statusTpl"
        }, {
            field: "updateTime",
            title: "更新时间",
            width: 180
        }, {
            field: "orderId",
            title: "订单id",
            width: 180,
            templet: "#orderIdTpl"
        }, {
            field: "minIncome",
            title: "最低收入",
            width: 100
        }, {
            title: "操作",
            minWidth: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-content-list"
        }]],
        page: true,
        limits: [10, 15, 20, 25, 30],
        text: "对不起，加载出现异常！",
        parseData: function(res){ //res 即为原始返回的数据
            for(var i=0;i<res.resultList.length;i++){

                res.resultList[i].createTime = renderTime(res.resultList[i].createTime)
                if(res.resultList[i].updateTime == null){
                    res.resultList[i].updateTime =""
                }else{
                    res.resultList[i].updateTime = renderTime(res.resultList[i].updateTime)
                }
            }

            return {
                "code": res.code, //解析接口状态
                "msg": res.description, //解析提示文本
                "count": res.totalCount, //解析数据长度
                "data": res.resultList //解析数据列表
            };
        } ,request: {
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    }),
        i.on("tool(LAY-app-content-list)", function(t) {
            var e = t.data;

            if("del" === t.event){
                layer.prompt({title: '输入任何口令，并确认', formType: 1}, function(pass, index){
                    layer.close(index);
                    layui.$.ajax({
                        url: '/tradeData/delBuyData',
                        dataType: 'json',
                        type: 'get',
                        data:{"id":e.id,"passWord":pass},
                        success: function (data) {
                            if(data.isSuccess){
                                layer.alert("删除成功");
                                i.reload('LAY-app-content-list')
                            }else {
                                layer.alert(data.description);
                            }
                        }
                    })
                });
            }

        }),
        i.render({
            elem: "#LAY-app-buy-sell-history-record-list",
            url:  "/tradeData/buySellHistoryData",
            cols: [[{
                field: "symbol",
                title: "交易对",
                width: 100
            }, {
                field: "type",
                width: 100,
                title: "类型",
                templet: "#typeTpl"
            }, {
                field: "buyPrice",
                title: "买入价格",
                width: 100
            }, {
                field: "sellPrice",
                title: "卖出价格",
                width: 100
            }, {
                field: "amount",
                title: "数量",
                width: 110
            }, {
                field: "profit",
                title: "收益",
                width: 110,
                templet: "#profitTpl"
            }, {
                field: "profitPercentage",
                title: "收益百分比",
                width: 110,
                templet: "#profitPreTpl"
            }, {
                field: "buyOrderId",
                title: "买订单号",
                width: 200,
                templet: "#buyOrderIdTpl"
            }, {
                field: "sellOrderId",
                title: "卖订单号",
                width: 200,
                templet: "#sellOrderIdTpl"
            }, {
                field: "createTime",
                title: "创建时间",
                width: 200
            }]],
            page: true,
            text: "对不起，加载出现异常！",
            parseData: function(res){ //res 即为原始返回的数据
                for(var i=0;i<res.resultList.length;i++){
                    res.resultList[i].createTime = renderTime(res.resultList[i].createTime)
                    if(res.resultList[i].sellPrice == 0){
                        res.resultList[i].sellPrice = "-"
                    }
                    if(res.resultList[i].sellOrderId == 0){
                        res.resultList[i].sellOrderId = "-"
                    }
                    if(!res.resultList[i].profit){
                        res.resultList[i].profit = "-"
                        res.resultList[i].profitPercentage = "-"
                    }else {
                        res.resultList[i].profitPercentage = res.resultList[i].profitPercentage+"%"
                    }

                }

                return {
                    "code": res.code, //解析接口状态
                    "msg": res.description, //解析提示文本
                    "count": res.totalCount, //解析数据长度
                    "data": res.resultList //解析数据列表
                };
            } ,request: {
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
        }),

        i.render({
            elem: "#LAY-app-order-detail-list",
            cols: [[{
                field: "type",
                width: 100,
                title: "type",
                templet: "#typeTpl"
            }, {
                field: "createTime",
                title: "createTime",
                minWidth: 100
            }, {
                field: "operPrice",
                title: "operPrice",
                minWidth: 100
            }, {
                field: "amount",
                title: "amount",
                minWidth: 100,
                sort: !0
            }, {
                field: "from",
                title: "from",
                minWidth: 100,
                templet: "#fromTpl"
            }]],
            text: "对不起，加载出现异常！",
            parseData: function(res){ //res 即为原始返回的数据
                for(var i=0;i<res.resultList.length;i++){
                    res.resultList[i].createTime = renderTime(res.resultList[i].createTime)
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.description, //解析提示文本
                    "count": res.totalCount
                };
            }
        })
        ,
        t("contlist", {})
});


function renderTime(date) {
    var dateee = new Date(date).toJSON();
    return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}