$(function() {
	ajaxT();
});
function ajaxT() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/tradeData/recordData",
		success : function(data) {
			createShowingTable(data.resultList);
		}
	});
}
// 动态的创建一个table
function createShowingTable(data) {
	
	var all = "";
	
	for(index = 0 ; index < data.length; index++) {
		var tableStr = "<table class='tab-list' width='99%'>";
		tableStr = tableStr
				+ "<tr class='list-header'>"
				+ "<td >id</td>"
				+ "<td >time</td>"
				+ "<td >type</td>"
				+ "<td >buyPrice</td>"
				+ "<td >amount</td>"
				+ "<td >sellPrice</td>"
				+ "<td >fee</td>"
				+ "<td >expend</td>"
				+ "<td >profit</td>" + "</tr>";
		var len = data[index].recordDataModelList.length;
		for (var i = 0; i < len; i++) {
			tableStr = tableStr
					+ "<tr>"
					+ "<td>" + data[index].recordDataModelList[i].id + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].timeStampStr + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].type + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].buyPrice + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].amount + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].sellPrice + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].fee + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].expend + "</td>"
					+ "<td>" + data[index].recordDataModelList[i].profit + "</td>"
					+ "</tr>";
		}
		
		var statisticsModel = "symbol : "+data[index].symbol+"   buyCount : "+data[index].statisticsModel.buyCount+"   sellCount : "+data[index].statisticsModel.sellCount
			+"   expend : "+data[index].statisticsModel.expend+"   surplus : "+data[index].statisticsModel.surplus
			+"   surplusAvgPrice : "+data[index].statisticsModel.surplusAvgPrice+"   tradeProfit : "+data[index].statisticsModel.tradeProfit;
		
		$.ajax({
			type : "POST",
			dataType : "json",
			async: false,
			url : "/tradeData/getPresentPrice",
			data:{"symbol":data[index].symbol},
			success : function(data2) {
				finalProfit = data[index].statisticsModel.expend + data[index].statisticsModel.surplus*data2.result*0.998
				statisticsModel = statisticsModel + "   presentPrice : "+data2.result + "   finalProfit : "+finalProfit
			}
		});
			
		
		tableStr = tableStr + "</table> </br>";
		tableStr = tableStr + statisticsModel+"</br></br>";
		
		
		all = all + tableStr
	}
	
	// 添加到div中
	$("#tableAjax").html(all);
}