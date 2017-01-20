package bean;

import java.util.Date;

public class LogContent {
	
	private String action;
	private String result;
	private Date s_time;
	private Date e_time;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getS_time() {
		return s_time;
	}
	public void setS_time(Date s_time) {
		this.s_time = s_time;
	}
	public Date getE_time() {
		return e_time;
	}
	public void setE_time(Date e_time) {
		this.e_time = e_time;
	}
	
}
