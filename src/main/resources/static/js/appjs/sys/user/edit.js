
function update() {
	if ($("#username").val() == ''){
		top.layer.msg("用户名不能为空");
		return;
	}
	if ($("#name").val() == ''){
		top.layer.msg("姓名不能为空");
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "sys/user/update",
		data : {
			username : $("#username").val(),
			name : $("#name").val(),
			id : $("#userId").val(),
			roleId : $("input:radio[name=roleId]:checked").val(),
			status : $("input:radio[name=status]:checked").val(),
		},
		async : false,
		success : function(data) {
			if (data.code == 200) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}