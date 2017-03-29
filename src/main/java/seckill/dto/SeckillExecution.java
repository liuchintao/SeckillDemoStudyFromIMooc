package seckill.dto;

import seckill.bean.SuccesskilledBean;
import seckill.enums.SeckillState;

/**
 * 封装秒杀后结果
 * @author Magister
 *
 */
public class SeckillExecution {
	
	//id
	private long seckillId;
	
	//秒杀执行结果状态
	private int state;
	
	//状态信息
	private String stateInfo;
	
	//秒杀成功对象
	private SuccesskilledBean successKilled;
	
	public SeckillExecution(long seckillId, SeckillState state) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
	}

	public SeckillExecution(long seckillId, SeckillState state, SuccesskilledBean successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
		this.successKilled = successKilled;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccesskilledBean getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccesskilledBean successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
	
	
}
