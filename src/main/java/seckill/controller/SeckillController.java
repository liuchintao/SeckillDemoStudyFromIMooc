package seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import seckill.bean.SeckillBean;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.dto.SeckillResult;
import seckill.enums.SeckillState;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillCloseException;
import seckill.service.SeckillService;

@Controller
//@RequestMapping("/myseckill")//url:/模块/资源/{id}/细分	/seckill/list/1000/execution
public class SeckillController {
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model){
		//get list page
		List<SeckillBean> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		System.out.println(list.get(0).getStartTime());
		return "list";
	}
	
	@RequestMapping(value="/{seckillId}/detail", method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model){
		if(seckillId == null){	
			return "redirect:seckill/list";
		}
		SeckillBean seckill = seckillService.getById(seckillId);
		if(seckillId == null){	
			return "forward:seckill/list";
		}
		model.addAttribute("seckill",seckill);
		return "detail";
	}
	
	//ajax,json 暴露秒杀接口
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.POST, produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result;
		try{
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true,exposer);			
		}catch(Exception e){
			e.printStackTrace();
			result = new SeckillResult<Exposer>(false,e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/{seckillId}/{md5}/execution",method=RequestMethod.POST,
			produces="application/json;charset=UTF-8")
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId, 
			@PathVariable("md5")String md5, 
			@CookieValue(value="killPhone",required=false)Long phone){
		if(phone == null){
			return new SeckillResult<SeckillExecution>(false,"unregister");
		}
		try{
			SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true,seckillExecution);
		}catch(RepeatSeckillException e){
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,seckillExecution);
		}catch(SeckillCloseException e){
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.END);
			return new SeckillResult<SeckillExecution>(true,seckillExecution);
		}catch(Exception e){
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true,seckillExecution);
		}
	}
}
