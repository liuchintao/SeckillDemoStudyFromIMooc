package seckill.dao;

import org.apache.ibatis.annotations.Param;

import seckill.bean.SuccesskilledBean;

public interface SuccessKilledDao {
	public int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
	
	public SuccesskilledBean queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
