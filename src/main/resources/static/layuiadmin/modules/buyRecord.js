layui.use(['form', 'upload', 'layer'], function () {

    var form = layui.form;

//检查项目添加到下拉框中
    layui.$.ajax({
        url: '/transactionConfig/getSymbolSelectDate',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#symbolSelect').append(new Option(item.value, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");

//重新渲染 固定写法
        }
    })

})