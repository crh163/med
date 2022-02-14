function getCheckedRoles() {
	var adIds = "";
	$("input:radio[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}
function save() {
	$("#roleIds").val(getCheckedRoles());
	if ($("#roleIds").val() == ''){
		top.layer.msg("所属角色不能为空");
		return;
	}
	if ($("#username").val() == ''){
		top.layer.msg("用户名不能为空");
		return;
	}
	if ($("#name").val() == ''){
		top.layer.msg("姓名不能为空");
		return;
	}
	if ($("#password").val() == ''){
		top.layer.msg("密码不能为空");
		return;
	}
	$.ajax({
		type : "POST",
		url : ctx + "sys/user/add",
		async : false,
		data : {
			username : $("#username").val(),
			name : $("#name").val(),
			password : $("#password").val(),
			roleId : $("#roleIds").val(),
			status : $("input:radio[name=status]:checked").val()
		},
		success : function(data) {
			if (data.code == 200) {
				top.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				top.layer.alert(data.msg);
			}
		}
	});

}