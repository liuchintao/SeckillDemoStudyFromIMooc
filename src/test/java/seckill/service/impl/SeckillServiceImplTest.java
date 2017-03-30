package seckill.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import seckill.bean.SeckillBean;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillCloseException;
import seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration({"classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<SeckillBean> results = seckillService.getSeckillList();
		System.out.println(results);
	} 

	@Test
	public void testGetById() {
		SeckillBean result = seckillService.getById(1000L);
		System.out.println(result);
	}

	@Test
	public void testSeckillLogic(){
		Exposer exposer = seckillService.exportSeckillUrl(1000L);
		if(exposer.isExposed()){
			System.out.println(exposer);
			
			long userPhone = 13022538771L;
			String md5 = exposer.getMd5();
			
			try{
				SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, userPhone, md5);
				System.out.println(seckillExecution);
			}catch (RepeatSeckillException e)
            {
                e.printStackTrace();
            }catch (SeckillCloseException e1)
            {
                e1.printStackTrace();
            }
        }else {
            //秒杀未开启
            System.out.println(exposer);
        }
	}
	
	@Test
	public void testLogicKillProcedure(){
		long seckillId = 1000L;
		long phone = 13110012120l;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()){
			String md5 = exposer.getMd5();	
			SeckillExecution se =  seckillService.executeSeckillByProcedure(seckillId, phone, md5);
			logger.info(se.getStateInfo());
		}
	}
}
