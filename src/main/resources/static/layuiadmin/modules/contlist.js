/** layuiAdmin.std-v1.4.0 LPPL License By https://www.layui.com/admin/ */
;layui.define(["table", "form"], function(t) {
    var e = layui.$
        , i = layui.table
        , n = layui.form;

    i.render({
        elem: "#LAY-app-content-list",
        url:  "/tradeData/buyData",
        cols: [[{
            type: "checkbox",
            fixed: "left"
        }, {
            field: "id",
            title: "id",
            sort: !0
        }, {
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
            width: 180
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
            "del" === t.event ? layer.confirm("确定删除此文章？", function(e) {
                t.del(),
                    layer.close(e)
            }) : "edit" === t.event && layer.open({
                type: 2,
                title: "编辑文章",
                content: "../../../views/app/content/listform.html?id=" + e.id,
                maxmin: !0,
                area: ["550px", "550px"],
                btn: ["确定", "取消"],
                yes: function(e, i) {
                    var l = window["layui-layer-iframe" + e]
                        , a = i.find("iframe").contents().find("#layuiadmin-app-form-edit");
                    l.layui.form.on("submit(layuiadmin-app-form-edit)", function(i) {
                        var l = i.field;
                        t.update({
                            label: l.label,
                            title: l.title,
                            author: l.author,
                            status: l.status
                        }),
                            n.render(),
                            layer.close(e)
                    }),
                        a.trigger("click")
                }
            })
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
                width: 200
            }, {
                field: "sellOrderId",
                title: "卖订单号",
                width: 200
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
            elem: "#LAY-app-content-comm",
            url: layui.setter.base + "json/content/comment.js",
            cols: [[{
                type: "checkbox",
                fixed: "left"
            }, {
                field: "id",
                width: 100,
                title: "ID",
                sort: !0
            }, {
                field: "reviewers",
                title: "评论者",
                minWidth: 100
            }, {
                field: "content",
                title: "评论内容",
                minWidth: 100
            }, {
                field: "commtime",
                title: "评论时间",
                minWidth: 100,
                sort: !0
            }, {
                title: "操作",
                width: 150,
                align: "center",
                fixed: "right",
                toolbar: "#table-content-com"
            }]],
            page: !0,
            limit: 10,
            limits: [10, 15, 20, 25, 30],
            limits: [10, 15, 20, 25, 30],
            text: "对不起，加载出现异常！",
            parseData: function(res){ //res 即为原始返回的数据
                for(var i=0;i<res.resultList.length;i++){
                    var buyIndex = res.resultList[i].buyIndex
                    res.resultList[i].buyIndex = formatDate(new Date(buyIndex*1000))
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
        i.on("tool(LAY-app-content-comm)", function(t) {
            t.data;
            "del" === t.event ? layer.confirm("确定删除此条评论？", function(e) {
                t.del(),
                    layer.close(e)
            }) : "edit" === t.event && layer.open({
                type: 2,
                title: "编辑评论",
                content: "../../../views/app/content/contform.html",
                area: ["450px", "300px"],
                btn: ["确定", "取消"],
                yes: function(t, e) {
                    var n = window["layui-layer-iframe" + t]
                        , l = "layuiadmin-app-comm-submit"
                        , a = e.find("iframe").contents().find("#" + l);
                    n.layui.form.on("submit(" + l + ")", function(e) {
                        e.field;
                        i.reload("LAY-app-content-comm"),
                            layer.close(t)
                    }),
                        a.trigger("click")
                },
                success: function(t, e) {}
            })
        }),
        t("contlist", {})
});

function formatDate(now) {
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}

function renderTime(date) {
    var dateee = new Date(date).toJSON();
    return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}