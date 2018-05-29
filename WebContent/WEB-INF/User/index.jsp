<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" /> 
<title>用户主页</title>
<!-- 引入bootstrap -->
<link href="/PhoneSafe/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<script src="/PhoneSafe/bootstrap-3.3.7-dist/js/jquery-3.3.1.min.js"></script> 
<script src="/PhoneSafe/bootstrap-3.3.7-dist/js/bootstrap.js"></script> 
<script src="/PhoneSafe/bootstrap-3.3.7-dist/js/vue.js"></script> 
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5sj5VxG97yN5GMYYqExEl9QLS8SnaHCR"></script>

<style type="text/css">
#allmap {height: 600px;}
#allmap label{max-width:none;}
</style>

</head>
<body>

<div class="container">
	<div class="row">
	<h1 align="center" id="title">登录的用户：${sessionScope.LoginNum}</h1>
	</div>

	<div class="row">

			<div class="col-sm-6">
				<div class="row">
				<h5 style="display: inline">实时位置获取(位置上传时间：<span id="showtime" style="color: red"></span>):</h5>
				</div>
			<div id="allmap" class="row col-sm-12"></div>
			</div>
			<div class="col-sm-6">
			<div class="row">
				<h5>图片查看（点击图片可以放大）：</h5>
				</div>
			<div class="row col-sm-12" >			
			
        <img id="Myimg" style="max-height: 400px; max-width: 300px;" 
           src="/PhoneSafe/api/GetPicture" />
        
        
			</div>
			
			</div>
			
			</div>
			
</div>






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

<div id="img-modal" class="modal fade">
  <div id="img-dialog" class="modal-dialog">
    <div id="img-content" class="modal-content">
        <img id="img-zoom" src="/PhoneSafe/api/GetPicture" style="max-height: 800px; max-width: 600px;">
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>
</html>
<script type="text/javascript">
var countdown = 30;
$(function(){	
	$('#Myimg').click(function(){
		$('#img-modal').modal();
	});
	
	
	
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
	
	GetPosition();
	
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
						location.reload();//刷新页面
						
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
//百度地图API功能
var map = new BMap.Map("allmap");
//下面的标注是
var b = 0;
function GetPosition(){
	$.get("/PhoneSafe/api/GetPosition",
			function(data){
		if(data.flag = 1){
			$("#showtime").text(data.position_time);
			map.clearOverlays();//清楚覆盖物
			//map.centerAndZoom("成都",15);      // 初始化地图,用城市名设置地图中心点
			var point = new BMap.Point(data.position.X, data.position.Y);
			if(data.position_time != b){map.centerAndZoom(point, 15);}//变化时设置
			
			map.enableScrollWheelZoom(true);

			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);               // 将标注添加到地图中
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

			var label = new BMap.Label("您的手机当前位置",{offset:new BMap.Size(20,-10)});
			marker.setLabel(label); 
			b = data.position_time;	
		}
		setTimeout(GetPosition, 3000);

			
	},
	"json");
}










</script>





