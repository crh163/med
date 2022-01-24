
var prefix = ctx + "med/caseAgree";
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
							if (row.status !== 0 || row.ownStatus !== 0) {
								return '';
							}
							return '<a class="btn btn-primary btn-sm" href="#" title="同意" href="#" onclick="agree(\''
								+ row.id + '\',1)"><i class="fa fa-check"> 同意</i></a> ' +
								'<a class="btn btn-warning btn-sm" href="#" title="不同意" href="#" onclick="agree(\''
								+ row.id + '\',0)"><i class="fa fa-times"> 不同意</i></a> ';
						}
					}]
			});
}

function agree(id, type) {
	$.ajax({
		type : "POST",
		url : prefix + "/agree",
		data : {
			id : id,
			type : type
		},
		error : function() {
			top.layer.msg("系统错误");
		},
		success : function(data) {
			if (data.code == 200) {
				top.layer.msg("操作成功");
				reLoad();
			} else {
				top.layer.msg(data.msg);
			}
		}
	});
}

function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}


