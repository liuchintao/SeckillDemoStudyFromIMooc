delimiter $$
#row_count() 0:未修改数据 <0:sql错误/未执行sql >0:修改的行数
CREATE procedure try.execute_seckill
	(in v_seckill_id bigint, in v_seckill_phone bigint,
	 in v_kill_time timestamp, out r_result int)
     begin
     declare insert_count int default 0;
     START TRANSACTION;
     insert ignore into try.success_killed(seckill_id,user_phone,create_time)
     value(v_seckill_id,v_seckill_phone,v_kill_time);
     SELECT row_count() into insert_count;
     IF(insert_count = 0) THEN
		rollback;
        #重复秒杀
        set r_result = -1;
	 ELSEIF(insert_count < 0) THEN
		rollback;
        set r_result = -2;
	 ELSE 
		update try.seckill
        SET try.seckill.number = try.seckill.number - 1
        WHERE seckill.seckillId = v_seckill_id
			AND seckill.end_time > v_kill_time
            AND seckill.start_time < v_kill_time;
		SELECT row_count() into insert_count;
		IF(insert_count = 0) THEN
			rollback;
			#秒杀结束
			set r_result = 0;
		ELSE IF(insert_count < 0) THEN
			rollback;
			set r_result = -2;
		ELSE
			COMMIT;
			set r_result = 1;
		END IF;
	 END IF;
	 end IF;
    end;
$$
     