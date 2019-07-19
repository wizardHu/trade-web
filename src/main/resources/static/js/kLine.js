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
	
	
	cc = [ {
		name : 'XX标点2',
		coord : [ '07-17 15:10:00', 3.6 ],
		value : 3.6,
		itemStyle : {
			normal : {
				color : 'rgb(41,60,85)'
			}
		}
	}]
	
	myChart.showLoading();
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/tradeData/getKline?symbol=eosusdt&period=1min&size=2000",
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
				
				list.push(list2)
			}
			
			data0 = splitData(list)
			
			
			$.ajax({
				type : "POST",
				dataType : "json",
				async : false,
				url : "/tradeData/symbolRecordData",
				data : {
					"symbol" : "eos"
				},
				success : function(data2) {
					
					for(i=0;i<data2.resultList.length;i++){
						var dict ={}
						var indexData = data2.resultList[i]
						dict["name"]=indexData.timeStamp
						dict["coord"]=[indexData.timeStampStr]
						dict["name"]=indexData.timeStamp
					}
					
					
					
					{
						name : 'XX标点',
						coord : [ '07-18 15:10:00', 3.8 ],
						value : 3.8,
						itemStyle : {
							normal : {
								color : 'rgb(41,60,85)'
							}
						}
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
				},
				grid : {
					left : '10%',
					right : '10%',
					bottom : '15%'
				},
				xAxis : {
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
				yAxis : {
					scale : true,
					splitArea : {
						show : true
					}
				},
				dataZoom : [ {
					type : 'inside',
					start : 50,
					end : 100
				}, {
					show : true,
					type : 'slider',
					y : '90%',
					start : 50,
					end : 100
				} ],
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
								data : [{
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
								} ],
								tooltip : {
									formatter : function(param) {
										return param.name + '<br>'
												+ (param.data.coord || '');
									}
								}
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
						},

				]
			};
			
			for(i=0;i<cc.length;i++){
				option.series[0].markPoint.data.push(cc[i])
			}
			
			myChart.setOption(option);
		}
	});
	
})

function splitData(rawData) {
	    var categoryData = [];
	    var values = []
	    for (var i = 0; i < rawData.length; i++) {
	        categoryData.push(rawData[i].splice(0, 1)[0]);
	        values.push(rawData[i])
	    }
	    return {
	        categoryData: categoryData,
	        values: values
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
