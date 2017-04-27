package com.ydj.zhuaqu.ali1688;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class Ali1688CheckCodeFormData {

	private String action = "";
    private String event_submit_do_query = "";
    private String smPolicy = "";
    private String smReturn = "";
    private String smApp = "";
    private String smCharset = "";
    private String smTag = "";
    private String smSign = "";
    private String identity = "";
    private String captcha = "";
    private String sessionid = "";
    

    private String url = "";
    
	/**
	 * @param action
	 * @param event_submit_do_query
	 * @param smPolicy
	 * @param smReturn
	 * @param smApp
	 * @param smCharset
	 * @param smTag
	 * @param smSign
	 * @param identity
	 * @param captcha
	 */
	public Ali1688CheckCodeFormData(String action,
			String event_submit_do_query, String smPolicy, String smReturn,
			String smApp, String smCharset, String smTag, String smSign,
			String identity, String captcha,String sessionid,String url) {
		super();
		this.action = action;
		this.event_submit_do_query = event_submit_do_query;
		this.smPolicy = smPolicy;
		this.smReturn = smReturn;
		this.smApp = smApp;
		this.smCharset = smCharset;
		this.smTag = smTag;
		this.smSign = smSign;
		this.identity = identity;
		this.captcha = captcha;
		this.sessionid = sessionid;
		this.url = url;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEvent_submit_do_query() {
		return event_submit_do_query;
	}
	public void setEvent_submit_do_query(String event_submit_do_query) {
		this.event_submit_do_query = event_submit_do_query;
	}
	public String getSmPolicy() {
		return smPolicy;
	}
	public void setSmPolicy(String smPolicy) {
		this.smPolicy = smPolicy;
	}
	public String getSmReturn() {
		return smReturn;
	}
	public void setSmReturn(String smReturn) {
		this.smReturn = smReturn;
	}
	public String getSmApp() {
		return smApp;
	}
	public void setSmApp(String smApp) {
		this.smApp = smApp;
	}
	public String getSmCharset() {
		return smCharset;
	}
	public void setSmCharset(String smCharset) {
		this.smCharset = smCharset;
	}
	public String getSmTag() {
		return smTag;
	}
	public void setSmTag(String smTag) {
		this.smTag = smTag;
	}
	public String getSmSign() {
		return smSign;
	}
	public void setSmSign(String smSign) {
		this.smSign = smSign;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
