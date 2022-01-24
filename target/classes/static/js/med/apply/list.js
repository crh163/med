
var prefix = ctx + "med/caseApply";
$(function() {
	load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'post', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				singleSelect : false, // 设置为true将禁止多选
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				search : false, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				queryParams : function(params) {
					return {
						pageSize : params.limit,
						page : 1,
					};
				},
				columns : [
					{
						field : 'idCheck',
						checkbox : true
					},
					{
						field : 'username',
						title : '申请人用户名'
					},
					{
						field : 'name',
						title : '申请人姓名'
					},
					{
						field : 'caseUsername',
						title : '患者用户名'
					},
					{
						field : 'caseName',
						title : '患者姓名'
					},
					{
						field : 'createDate',
						title : '发起时间'
					},
					{
						field : 'status',
						title : '状态',
						formatter : function(value, row, index) {
							return value===1?'不同意':(value===2?'已同意':'待同意');
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							if (row.status != 2) {
								return '';
							}
							return '<a class="btn btn-success btn-sm" href="#" title="查看" href="#" onclick="lookInfo(\''
								+ row.caseId + '\')"><i class="fa fa-search"> 查看</i></a> ';
						}
					}]
			});
}

function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function lookInfo(id) {
	top.layer.open({
		type : 2,
		title : '病例列表',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : 'med/case/list?userId=' + id
	});
}

function insertRow() {
	layer.prompt({
		title:'输入病人用户名',
		btn2:function(index){
		}
	},function(val,index){
		$.post(prefix+'/insert',{username:val},function(res){
			if (res.code != 200) {
				layer.msg(res.msg);
			} else {
				reLoad();
				layer.close(index);
			}
		});
	})
}

