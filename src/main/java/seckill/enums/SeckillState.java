package seckill.enums;

public enum SeckillState {
	
	SUCCESS(1,"Seckill Success!"),
	END(0,"Seckill Closed"),
	REPEAT_KILL(-1,"Repeat Seckill"),
	DATA_REWRITE(-2,"Data Rewrite"),
	INNER_ERROR(-3,"inner error");
	
	private int state;
	private String stateInfo;
	
	private SeckillState(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillState stateOf(int index){
		for(SeckillState state : values()){
			if(state.getState() == index)
				return state;
		}
		return null;
	}
}
