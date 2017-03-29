#创建表
create table seckill(
seckill_id bigint not null AUTO_INCREMENT COMMENT '商品库存id',
name varchar(100) not null COMMENT '商品名称',
number int not null COMMENT '商品数量',
create_time timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
start_time datetime not null COMMENT '秒杀开始时间',
end_time datetime not null COMMENT '秒杀结束时间',
primary key(seckill_id),
key idx_start_time(start_time),
key idx_create_time(create_time),
key idx_end_tiem(end_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT'秒杀库存表';

#插入数据
insert into 
	seckill(name,number,start_time,end_time)
values
('1000元秒杀iphone6',100,'2017-04-01 00:00:00','2017-04-02 00:00:00'),
('500元秒杀ipad2',200,'2017-04-01 00:00:00','2017-04-02 00:00:00'),
('300元秒杀XiaoMi2s',300,'2017-04-01 00:00:00','2017-04-02 00:00:00'),
('100元秒杀HMiNote',500,'2017-04-01 00:00:00','2017-04-02 00:00:00');

#秒杀成功明细表
#用户认证相关信息
create table success_killed(
seckill_id bigint not null COMMENT '商品库存id',
user_phone bigint not null COMMENT '用户手机号',
state tinyint not null DEFAULT -1 COMMENT'状态提示：-1无效，0成功，1已付款，2已发货',
create_time timestamp not null COMMENT'创建时间',
primary key(seckill_id,user_phone),#联合主键
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';