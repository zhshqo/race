package com.cnnp.race.demo.vo;

public class AjaxRespVo {

	boolean status = true;
	String failReason;
	Object result;
	
	private AjaxRespVo() {}
	
	public static AjaxRespVo success(Object obj) {
		AjaxRespVo vo = new AjaxRespVo();
		vo.setResult(obj);
		return vo;
	}
	
	public static AjaxRespVo fail(String failReason) {
		AjaxRespVo vo = new AjaxRespVo();
		vo.setStatus(false);
		vo.setFailReason(failReason);
		return vo;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
