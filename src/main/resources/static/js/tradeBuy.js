$(function() {
	ajaxT();
});
function ajaxT() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/tradeData/buyData",
		success : function(data) {
			createShowingTable(data.resultMap);
		}
	});
}
// 动态的创建一个table
function createShowingTable(data) {

	var all = "";

	for (index = 0; index < Object.keys(data).length; index++) {
		var tableStr = "<table class='tab-list' width='99%'>";
		tableStr = tableStr + "<tr class='list-header'>" + "<td >time</td>"
				+ "<td >price</td>" + "<td >predictNextSell</td>"+ "<td >amount</td>" + "<td >symbol</td>"
				+ "</tr>";

		for (i = 0; i < Object.values(data)[index].length; i++) {
			tableStr = tableStr
					+ "<tr>"
					+ "<td>"
					+ getDate("yyyy-mm-dd",
							Object.values(data)[index][i].timeStamp * 1000)
					+ "</td>" + "<td>" + Object.values(data)[index][i].price
					+ "</td>" + "<td>" + Object.values(data)[index][i].price* 1.015
					+ "</td>" + "<td>" + Object.values(data)[index][i].amount
					+ "</td>" + "<td>" + Object.values(data)[index][i].symbol
					+ "</td>" + "</tr>";
		}

		var statisticsModel = "";

		$.ajax({
			type : "POST",
			dataType : "json",
			async : false,
			url : "/tradeData/getPresentPrice",
			data : {
				"symbol" : Object.values(data)[index][0].symbol
			},
			success : function(data2) {
				statisticsModel = "presentPrice : "
						+ data2.result 
			}
		});

		tableStr = tableStr + "</table> </br>";
		tableStr = tableStr + statisticsModel + "</br></br>";

		all = all + tableStr
	}

	// 添加到div中
	$("#tableAjax").html(all);
}

// 转换年月日方法
function getDate(format, str) {
	var oDate = new Date(str), oYear = oDate.getFullYear(), oMonth = oDate
			.getMonth() + 1, oDay = oDate.getDate(), oHour = oDate.getHours(), oMin = oDate
			.getMinutes(), oSec = oDate.getSeconds();
	if (format == 'yyyy-mm-dd') {
		oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' '
				+ getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSec);// 最后拼接时间
	} else if (format == 'yyyy/mm/dd') {
		oTime = oYear + '/' + getzf(oMonth) + '/' + getzf(oDay) + ' '
				+ getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSec);// 最后拼接时间
	}
	return oTime;
};
// 补0操作
function getzf(num) {
	if (parseInt(num) < 10) {
		num = '0' + num;
	}
	return num;
}
