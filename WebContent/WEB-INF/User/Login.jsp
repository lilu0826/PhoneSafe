<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 引入bootstrap -->
<link href="/PhoneSafe/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<script src="/PhoneSafe/bootstrap-3.3.7-dist/js/jquery-3.3.1.min.js"></script> 
<script src="/PhoneSafe/bootstrap-3.3.7-dist/js/bootstrap.js"></script> 



</head>
<body>

	<h1>已经登录后显示的界面${sessionScope.LoginNum}</h1>



<div class="modal fade" tabindex="-1" role="dialog" id="yulan">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">请先登录</h4>
      </div>
		<div class="modal-body">
			<div class="form-horizontal">
				<div class="form-group">
					<label for="Num" class="col-sm-3 control-label">安全手机号码:</label> 
					<div class="col-sm-6">
						<input type="number" class="form-control" id="Num" placeholder="请输入安全手机号码">
					</div>
					<div class="col-sm-3">
						<button id="GetVerify" class="btn btn-default">发送验证码</button>
					</div>	
				</div>
				
				<div class="form-group">
					<label for="SafeNum" class="col-sm-3 control-label">验证码:</label>
				
					<div class="col-sm-3">
						<input
						id="Verify" type="number" class="form-control"
						placeholder="请输入验证码">
					</div>
			</div>	
			</div>
				</div>
				<div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="add()">登录</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
<script type="text/javascript">
var countdown = 30;
$(function(){	
	${sessionScope.isLogin? "" : "$('#yulan').modal({backdrop: 'static', keyboard: false});"}

	$("#GetVerify").click(function(){
		var SafeNum = $("#Num").val();
		
		if(SafeNum != ""){
			$.get("user/getVerify?safeNum="+SafeNum,
					function (data){
						if(data.flag == 1){
							var i = setInterval(function() { 
								if (countdown == 0) { 
									$("#GetVerify").attr('disabled',false); 
							        //obj.removeattr("disabled"); 
							        $("#GetVerify").text("发送验证码");
							        countdown = 30; 
							        clearInterval(i);
							    } else { 
							    	$("#GetVerify").attr('disabled',true);
							    	$("#GetVerify").text("重新发送(" + countdown + ")");
							        countdown--; 
							    }
									 }
							    ,1000) 
							
						}
						else{
							alert("验证码发送失败,可能是手机号有误或不存在！");
						}
						
						
						
					},
					"json"
					);
			
		}
		else{ alert("请输入手机号码");}
		
		
	});
	
	
	
});


function add(){//添加文章按钮激活
	var SafeNum = $("#Num").val();//安全手机号
	var verify = $("#Verify").val();//验证码
	
	if(verify != ""){
		//alert(title);
		//alert(content);//执行新建文章操作
		//addArticle?art.title=123&art.content=456
		$.post("user/UserLogin",
				{"safeNum" : SafeNum ,"verify" : verify},
				function (data){
					if(data.flag == 1){
						$('#yulan').modal('toggle');
						alert('登录成功');
						
					}
					else{
						$('#yulan').modal({backdrop: 'static', keyboard: false});
						alert("验证码错误！");
					}
					
					
					
				},
				"json"
				);

		//
	}
	else{
		
		$('#yulan').modal({backdrop: 'static', keyboard: false});
		alert("验证码不能为空!");
	}
	

	
}



</script>





