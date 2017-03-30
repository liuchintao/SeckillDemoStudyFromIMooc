package seckill.service;

import java.util.List;

import seckill.bean.SeckillBean;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillCloseException;
import seckill.exception.SeckillException;

public interface SeckillService {
	
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	public List<SeckillBean> getSeckillList();
	
	/**
	 * 查询限制内的所有秒杀记录
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<SeckillBean> getSeckillList(int offset, int limit );
	
	/**
	 * 查询单个秒杀记录
	 * @return
	 */
	public SeckillBean getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,String md5)
		throws SeckillException, RepeatSeckillException, SeckillCloseException;
	
	public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone,String md5);
}
