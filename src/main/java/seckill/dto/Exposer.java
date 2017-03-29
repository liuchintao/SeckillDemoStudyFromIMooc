package seckill.dto;

/**
 * @author Magister
 *
 */
public class Exposer {
	
	//是否开启秒杀
	private boolean exposed;
	
	//id
	private long seckillId;
	
	//一种加密措施
	private String md5;
	
	
	//当前时间(毫秒)
	private long now;
	
	//秒杀开始时间
	private long start;
	
	//秒杀结束时间
	private long end;

	public Exposer(boolean exposed, long seckillId, String md5) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.md5 = md5;
	}

	public Exposer(boolean exposed, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", seckillId=" + seckillId + ", md5=" + md5 + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}
	
	
}
