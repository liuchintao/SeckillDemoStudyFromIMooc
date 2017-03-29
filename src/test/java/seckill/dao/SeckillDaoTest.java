package seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import seckill.bean.SeckillBean;

/**
 * 
 * @author Magister
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1000;
		SeckillBean seckill = seckillDao.queryById(id);
		System.out.println(seckill.toString());
	}

	@Test
	public void testQueryAll() {
		int offset = 0;
		int limit = 5;
		List<SeckillBean> seckill = seckillDao.queryAll(offset, limit);
		for(SeckillBean sb : seckill){
			System.out.println(sb);
		}
	}
	
	@Test
	public void testReduceNumber() {
		Date day = new Date();
		int updateCount = seckillDao.reduceNumber(1000L, day);
		System.out.println("updateCount = " + updateCount);
	}

}
