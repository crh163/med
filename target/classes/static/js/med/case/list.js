
var prefix = ctx + "med/case";
$(function() {
	var userId = $('#userId').val();
	if (userId !== '') {
		$(".crh_hidden").hide();
	}
	load();
	layui.use('upload', function(){
		var upload = layui.upload;
		var uploadInst = upload.render({
			elem: '#upload' //绑定元素
			,url: ctx + 'file/medCase/upload' //上传接口
			,method: 'POST'
			,accept: 'file'
			,size: 50
			,before: function(obj){
				layer.load();
			}
			,done: function(res){//上传完毕回调
				layer.closeAll('loading');
				if (res.code != 200) {
					layer.msg(res.msg);
				} else {
					reLoad();
				}
			}
			,error: function(){//请求异常回调
				layer.closeAll('loading');
				layer.msg('网络异常，请稍后重试！');
			}
		});
	});
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
						doctorName : $('#doctorName').val(),
						userId : $('#userId').val()
					};
				},
				columns : [
					{
						field : 'idCheck',
						checkbox : true
					},
                    {
                        field : 'inPatientFlow',
                        title : '病人编号'
                    },
					{
						field : 'recordownName',
						title : '记录人员姓名'
					},
					{
						field : 'doctorName',
						title : '医生姓名'
					},
					{
						field : 'courseType',
						title : '行为'
					},
					{
						field : 'courseContent',
						title : '病情简述'
					},
					{
						field : 'deptName',
						title : '科室名称'
					},
					{
						field : 'areaName',
						title : '病室名称'
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							return '<a class="btn btn-success btn-sm" href="#" title="查看" href="#" onclick="lookInfo(\''
								+ row.id + '\')"><i class="fa fa-search"> 查看</i></a> ';
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
		title : '详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '620px' ],
		content : prefix + '/info?id=' + id
	});
}

function caseDownload() {
	$.ajax({
		type : "POST",
		url : ctx + "file/medCase/download",
		success : function(data) {
			if (data.code == 200) {
				window.open(data.data);
				// window.location.href = data.data;
			} else {
				parent.layer.msg(data.msg);
			}
		}
	});
}

