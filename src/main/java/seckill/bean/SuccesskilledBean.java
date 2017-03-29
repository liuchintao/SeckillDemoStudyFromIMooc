package seckill.bean;

import java.util.Date;

public class SuccesskilledBean {
	private long seckillId;
	
	private long userPhone;
	
	private short state;
	
	private Date createTime;
	
	private SeckillBean seckill;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SeckillBean getSeckill() {
		return seckill;
	}

	public void setSeckill(SeckillBean seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SuccessKilledBean [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}
	
	
}
