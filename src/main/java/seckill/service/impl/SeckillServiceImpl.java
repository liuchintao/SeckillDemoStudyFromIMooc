package seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import seckill.bean.SeckillBean;
import seckill.bean.SuccesskilledBean;
import seckill.dao.SeckillDao;
import seckill.dao.SuccessKilledDao;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.enums.SeckillState;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillCloseException;
import seckill.exception.SeckillException;
import seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	//log instance
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	//setting salt figure mixes the value of md5;
	private String salt = "sajdfiu29385p34qf[=-UUESIJ@y4371^*%";

	public List<SeckillBean> getSeckillList() {
		return seckillDao.queryAll(0,5);
	}

	public SeckillBean getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		SeckillBean seckillBean = seckillDao.queryById(seckillId);
		if(seckillBean == null)		return new Exposer(false,seckillId);
		Date startTime = seckillBean.getStartTime();
		Date endTime = seckillBean.getEndTime();
		Date nowTime = new Date();
		
		if(startTime.getTime() > nowTime.getTime() 
				|| endTime.getTime() < nowTime.getTime()){
			return new Exposer(false,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5 = this.getMD5(seckillId);
		Exposer exposer = new Exposer(true, seckillId, md5);
		return exposer;
	}

	   /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatSeckillException, SeckillCloseException {
		if(md5 == null || !md5.equals(this.getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite!");
		}
		Date now = new Date();
		//执行秒杀逻辑：减库存+记录购买信息
		try{
			int updateCount = seckillDao.reduceNumber(seckillId, now);
			if(updateCount <= 0){
				throw new SeckillCloseException("seckill is closed!");
			}else{
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0)	throw new RepeatSeckillException("Repeat submmit!");
				else{
					SuccesskilledBean successkilledBean = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillState.SUCCESS, successkilledBean);
				}
			}
		}catch(SeckillCloseException e){
			throw e;
		}catch(RepeatSeckillException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SeckillException("seckill inner error : " + e.getMessage());
		}
	}

	public List<SeckillBean> getSeckillList(int offset, int limit) {
		List<SeckillBean> seckillList = null;
		if(offset >= 0 && limit >= 0){
			seckillList = seckillDao.queryAll(offset,limit);
		}
		return seckillList;
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "\\" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
		if(md5==null || !md5.equals(this.getMD5(seckillId))){
			return new SeckillExecution(seckillId,SeckillState.DATA_REWRITE);
		}
		Date now = new Date();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("seckillId", seckillId);
		params.put("userPhone", userPhone);
		params.put("killTime", now);
		params.put("result", null);
		try{
			seckillDao.executeProcedure(params);
			int result = MapUtils.getInteger(params, "result",-2);
			if(result == 1){
				SuccesskilledBean sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,SeckillState.SUCCESS,sk);
			}else{
				return new SeckillExecution(seckillId,SeckillState.stateOf(result));
			}
		}catch(Exception e){
			return new SeckillExecution(seckillId,SeckillState.INNER_ERROR);
		}
	}
	
}
