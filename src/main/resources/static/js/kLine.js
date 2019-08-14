var upColor = '#ec0000';
var upBorderColor = '#8A0000';
var downColor = '#00da3c';
var downBorderColor = '#008F28';

// 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)

var data0 = "";
var myChart = echarts.init(document.getElementById('main'));
myChart.setOption({
    title: {
        text: '稍后'
    },
    tooltip: {},
    xAxis: {
        data: []
    },
    yAxis: {}
});


$(function() {
	
	
	myChart.showLoading();
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/tradeData/getKline?symbol=eosusdt&period=1min&size=50",
		success : function(data) {
			var list = []
			myChart.hideLoading();
			for(i=0;i<data.resultList.length;i++){
				var list2 = []
				list2.push(data.resultList[i][0])
				list2.push(parseFloat(data.resultList[i][1]))
				list2.push(parseFloat(data.resultList[i][2]))
				list2.push(parseFloat(data.resultList[i][3]))
				list2.push(parseFloat(data.resultList[i][4]))
				list2.push(Math.random()*1000)
				list2.push(Math.random()>0.5?1:-1)
				
				list.push(list2)
			}
			
			data0 = splitData(list)
			buySellPoint = []
			
			$.ajax({
				type : "POST",
				dataType : "json",
				async : false,
				url : "/tradeData/symbolRecordData",
				data : {
					"symbol" : "eosusdt"
				},
				success : function(data2) {
					
					for(i=0;i<data2.resultList.length;i++){
						var dict ={}
						var itemStyle = {}
						var normal ={}
						
						var indexData = data2.resultList[i]
						
						dict["name"]=indexData.timeStamp
						
						if(indexData.type == 1){
							dict["coord"]=[indexData.timeStampStr.substring(5),indexData.buyPrice]
							dict["value"]=indexData.buyPrice
							normal["color"]='rgb(50,205,50)'
						}else if(indexData.type == 0){
							dict["coord"]=[indexData.timeStampStr.substring(5),indexData.sellPrice]
							dict["value"]=indexData.sellPrice
						}
							
						itemStyle["normal"]=normal
						dict["itemStyle"]=itemStyle
						
						buySellPoint.push(dict)
					}
				}
			});
			
			

			option = {
					
				title : {
					text : 'k线',
					left : 0
				},
				tooltip : {
					trigger : 'axis',
					axisPointer : {
						type : 'cross'
					}
				},
				legend : {
					data : [ '日K', 'MA5', 'MA10', 'MA20', 'MA30' ]
				},toolbox: {
			        feature: {
			            dataZoom: {
			                yAxisIndex: false
			            },
			        }
			    },
			    grid: [
			           {
			               left: '10%',
			               right: '10%',
			               bottom: 200
			           },
			           {
			               left: '10%',
			               right: '10%',
			               height: 80,
			               bottom: 80
			           }
			       ],
				xAxis :[ {
					type : 'category',
					data : data0.categoryData,
					scale : true,
					boundaryGap : false,
					axisLine : {
						onZero : false
					},
					splitLine : {
						show : false
					},
					splitNumber : 20,
					min : 'dataMin',
					max : 'dataMax'
				},
				{
		            type: 'category',
		            gridIndex: 1,
		            scale: true,
		            boundaryGap : false,
		            axisLine: {onZero: false},
		            axisTick: {show: false},
		            splitLine: {show: false},
		            axisLabel: {show: false},
		            splitNumber: 20,
		            min: 'dataMin',
		            max: 'dataMax'
		        }],
				yAxis : [{
					scale : true,
					splitArea : {
						show : true
					}
				},
		        {
		            scale: true,
		            gridIndex: 1,
		            splitNumber: 2,
		            axisLabel: {show: false},
		            axisLine: {show: false},
		            axisTick: {show: false},
		            splitLine: {show: false}
		        }],
				dataZoom : [ {
					type : 'inside',
					xAxisIndex: [0, 1],
					start : 50,
					end : 100
				},{
		            show: true,
		            xAxisIndex: [0, 1],
		            type: 'slider',
		            bottom: 10,
		            start: 10,
		            end: 100,
		            handleSize: '105%'
		        } ]  ,
			    visualMap: {
			        show: false,
			        seriesIndex: 1,
			        dimension: 2,
			        pieces: [{
			            value: 1,
			            color: upColor
			        }, {
			            value: -1,
			            color: downColor
			        }]
			    },
				series : [
						{
							name : '日K',
							type : 'candlestick',
							data : data0.values,
							itemStyle : {
								normal : {
									color : upColor,
									color0 : downColor,
									borderColor : upBorderColor,
									borderColor0 : downBorderColor
								}
							},
							markPoint : {
								label : {
									normal : {
										formatter : function(param) {
											return param != null ? param.value : '';
										}
									}
								},
								data : [/*{
									name : 'highest value',
									type : 'max',
									valueDim : 'highest'
								}, {
									name : 'lowest value',
									type : 'min',
									valueDim : 'lowest'
								}, {
									name : 'average value on close',
									type : 'average',
									valueDim : 'close'
								} */],
								tooltip : {
									formatter : function(param) {
										return param.name + '<br>'
												+ (param.data.coord || '');
									}
								}
							}
						}
						,
				        {
							name: 'Volumn',
				            type: 'bar',
				            data : data0.amount,
				            stack: 'Volumn',
				            xAxisIndex:1,
				            yAxisIndex:1,
				            itemStyle: {
				                color: '#7fbe9e'
				            } 
				        },{
							name: 'Volumn2',
				            type: 'bar',
				            data : data0.amount,
				            stack: 'Volumn',
				            xAxisIndex:1,
				            yAxisIndex:1,
				            itemStyle: {
				                color: '#7fbe9e'
				            } 
				        }, {
							name : 'MA5',
							type : 'line',
							data : calculateMA(5),
							smooth : true,
							lineStyle : {
								normal : {
									opacity : 0.5
								}
							}
						}, {
							name : 'MA10',
							type : 'line',
							data : calculateMA(10),
							smooth : true,
							lineStyle : {
								normal : {
									opacity : 0.5
								}
							}
						}, {
							name : 'MA20',
							type : 'line',
							data : calculateMA(20),
							smooth : true,
							lineStyle : {
								normal : {
									opacity : 0.5
								}
							}
						}, {
							name : 'MA30',
							type : 'line',
							data : calculateMA(30),
							smooth : true,
							lineStyle : {
								normal : {
									opacity : 0.5
								}
							}
						}

				]
			};
			
			for(i=0;i<buySellPoint.length;i++){
				option.series[0].markPoint.data.push(buySellPoint[i])
			}
			
			myChart.setOption(option);
		}
	});
	
})

function splitData(rawData) {
	    var categoryData = [];
	    var values = []
	    var amount = []
	    var sign = []
	    for (var i = 0; i < rawData.length; i++) {
	    	var dataIndex = rawData[i].splice(0, 1)[0]
	        categoryData.push(dataIndex);
	        values.push(rawData[i])
	        amount.push([dataIndex,rawData[i][4],rawData[i][5]])
	    }
	    return {
	        categoryData: categoryData,
	        values: values,
	        amount:amount
	    };
	}

	function calculateMA(dayCount) {
	    var result = [];
	    for (var i = 0, len = data0.values.length; i < len; i++) {
	        if (i < dayCount) {
	            result.push('-');
	            continue;
	        }
	        var sum = 0;
	        for (var j = 0; j < dayCount; j++) {
	            sum += data0.values[i - j][1];
	        }
	        result.push(sum / dayCount);
	    }
	    return result;
	}
