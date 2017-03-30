//存放主要交互逻辑的js代码
//javascript 模块化（包.类.方法）

var seckill = {
	//封装秒杀相关ajax的url
	URL:{
			now:function(){
				return '/seckill/time/now';
			},
			exposer:function(seckillId){
				return '/seckill/' + seckillId + '/exposer';
			},
			execution:function(seckillId,md5){
				return '/seckill/' + seckillID + '/' + md5 + '/execution';
			}
		},
	//验证手机号
	validatePhone: function (phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//详情页秒杀逻辑
	detail:{
		init: function(params){
            //手机验证和登录,计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
			var userPhone = $.cookie('userPhone');
			//验证手机号
			if(!seckill.validatePhone(userPhone)){
				//绑定手机，控制输出
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show:true,//显示弹出层
					backdrop:'static',//禁止位置关闭
					keyboard:false//关闭键盘事件
				});
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhoneKey').val();
					console.log("inputPhone = " + inputPhone);
					if(seckill.validatePhone(inputPhone)){
						//电话写入cookie
						$.cookie('userPhone', inputPhone,{expires:7,path:'/seckill'});
						//验证通过，刷新页面
						window.location.reload();
					}else{
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}
			//已经登陆
			//计时交互
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					//时间判断，计时交互
					seckill.countDown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log("result = " + result);
					alert('result: ' + result);
				}
			});
		},
		countDown: function(seckillId,nowTime,startTime,endTime){
			console.log(seckillId + '_' + nowTime + '_' + startTime + '_' + endTime);
			var seckillBox = $('#seckill-box');
			if(nowTime>endTime){
				//seckill is closed
				seckillBox.html('秒杀结束');
			}else if(nowTime<startTime){
				//seckill does not start
				var killTime = new Date(startTime + 1000);
				seckillBox.countdown(killTime,function(event){
					var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				}).on('finish.countdown',function(){
	                //时间完成后回调事件
	                //获取秒杀地址,控制现实逻辑,执行秒杀
					console.log('________finish.countdown');
					seckill.handlerSeckill(seckillId,seckillBox);
				});
			}else{
				//秒杀开始
				seckill.handlerSeckill(seckillId,seckillBox);
			}
		},
		handlerSeckill: function(seckillId,node){
			//获取秒杀地址,控制显示器,执行秒杀
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">秒杀开始</button>');
			
			$.get(seckill.URL.exposer(seckillId),{},function(result){
				if(result && result['success']){
					var exposer = result['data'];
					if(exposer['exposed']){
						//开启秒杀
						//获取秒杀地址
						var md5 = exposer['md5'];
						var killUrl = seckill.URL.execution(seckillId, md5);
						console.log("killUrl: " + killUrl);
						$('#killBtn').one('click',function(){
							//执行秒杀请求
							//1.禁用按钮
							$(this).addClass('disabled');//,<-$(this)===('#killBtn')->
							//2.发送秒杀请求执行秒杀
							$.post(killUrl,{},function(){
								if(result && result['success']){
									var killResult = result['data'];
									var killState = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									//显示秒杀结果
									node.html('<span class="label label-success">' + stateInfo + '</span>');
								}
							});
						});
						node.show();
					}else{
						//由于浏览器即使偏差等原因，未开启秒杀
						var now = exposer['now'];
						var start = exposer['start'];
						var end  =exposer['end'];
						seckill.countDown(seckillId,now,start,end);
					}
				}else{
					console.log('result = ' + result);
				}
			})
		}
	}
}