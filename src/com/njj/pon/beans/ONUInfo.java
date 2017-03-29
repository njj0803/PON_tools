package com.njj.pon.beans;

public class ONUInfo {
	
	private String loid;
	private String number;
	private int oldSvlan;
	private int newSvlan;
	private int cvlan;
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
	public int getOldSvlan() {
		return oldSvlan;
	}
	public void setOldSvlan(int oldSvlan) {
		this.oldSvlan = oldSvlan;
	}
	public int getNewSvlan() {
		return newSvlan;
	}
	public void setNewSvlan(int newSvlan) {
		this.newSvlan = newSvlan;
	}
	public int getCvlan() {
		return cvlan;
	}
	public void setCvlan(int cvlan) {
		this.cvlan = cvlan;
	}
	@Override
	public String toString() {
		return "ONUInfo [loid=" + loid + ", number=" + number + ", oldSvlan=" + oldSvlan + ", newSvlan=" + newSvlan
				+ ", cvlan=" + cvlan + "]";
	}
	
	

}