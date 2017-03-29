package seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import seckill.bean.SuccesskilledBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	public SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() {
		int insertCount = successKilledDao.insertSuccessKilled(1000L, 13322233122L);
		System.out.println("insertCount = " + insertCount);
	}

	/*
	 * SuccessKilledBean 
	 * [seckillId=1000, 
	 *  userPhone=13322233121,
	 *  state=-1, 
	 *  createTime=Fri Mar 24 19:38:49 CST 2017, 
	 *  seckill=SecKillBean 
	 *  [seckillId=1000, 
	 *   name=1000元秒杀iphone6, 
	 *   number=100, 
	 *   createTime=Fri Mar 24 11:15:57 CST 2017, 
	 *   startTiem=Sat Apr 01 00:00:00 CST 2017, 
	 *   endTime=Sun Apr 02 00:00:00 CST 2017]]
	 * */
	@Test
	public void testQueryByIdWithSeckill() {
		SuccesskilledBean successkilledBean = successKilledDao.queryByIdWithSeckill(1000L,13322233122L);
		System.out.println(successkilledBean);
	}

}
