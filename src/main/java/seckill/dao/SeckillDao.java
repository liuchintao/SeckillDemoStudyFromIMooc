package seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import seckill.bean.SeckillBean;

public interface SeckillDao {
	
	public int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime") Date killTime);
	
	public SeckillBean queryById(long seckillId);
	
	public List<SeckillBean> queryAll(@Param("offset")int offset,@Param("limit") int limit);
}
