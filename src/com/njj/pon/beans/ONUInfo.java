package com.njj.pon.beans;

public class ONUInfo {
	
	private String loid;
	private String number;
	private String oldSvlan;
	private String newSvlan;
	private String cvlan;
	private String oldPort;
	private String newPort;
	
	public String getLoid() {
		return loid;
	}
	public void setLoid(String loid) {
		this.loid = loid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOldSvlan() {
		return oldSvlan;
	}
	public void setOldSvlan(String oldSvlan) {
		this.oldSvlan = oldSvlan;
	}
	public String getNewSvlan() {
		return newSvlan;
	}
	public void setNewSvlan(String newSvlan) {
		this.newSvlan = newSvlan;
	}
	public String getCvlan() {
		return cvlan;
	}
	public void setCvlan(String cvlan) {
		this.cvlan = cvlan;
	}
	public String getOldPort() {
		return oldPort;
	}
	public void setOldPort(String oldPort) {
		this.oldPort = oldPort;
	}
	public String getNewPort() {
		return newPort;
	}
	public void setNewPort(String newPort) {
		this.newPort = newPort;
	}
	@Override
	public String toString() {
		return "ONUInfo [loid=" + loid + ", number=" + number + ", oldSvlan=" + oldSvlan + ", newSvlan=" + newSvlan
				+ ", cvlan=" + cvlan + ", oldPort=" + oldPort + ", newPort=" + newPort + "]";
	}
	
	
	

}