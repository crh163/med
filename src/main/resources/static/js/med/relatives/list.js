
var prefix = ctx + "med/relatives";
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
                        title : '用户名'
                    },
					{
						field : 'name',
						title : '用户名称'
					},
					{
						field : 'roleId',
						title : '角色',
						formatter : function(value, row, index) {
							return value==1?'管理员':(value=='2'?'医生':(value=='3'?'病人':'亲属'));
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							if (row.roleId == '3') {
								return '';
							}
							return '<a class="btn btn-warning btn-sm" href="#" title="删除" href="#" onclick="deleteRow(\''
								+ row.id + '\')"><i class="fa fa-remove"> 删除</i></a> ';
						}
					}]
			});
}

function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function deleteRow(id) {
	$.ajax({
		type : "POST",
		url : prefix + "/delete",
		data : {
			id : id
		},
		error : function() {
			top.layer.msg("系统错误");
		},
		success : function(data) {
			if (data.code == 200) {
				top.layer.msg("删除成功");
				reLoad();
			} else {
				top.layer.msg(data.msg);
			}
		}
	});
}

function insertRow() {
	layer.prompt({
		title:'输入亲属用户名',
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
