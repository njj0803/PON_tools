package com.njj.pon.beans;

public class ONUInfo {
	
	private String loid;
	private String number;
	private int svlan;
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
	public int getSvlan() {
		return svlan;
	}
	public void setSvlan(int svlan) {
		this.svlan = svlan;
	}
	public int getCvlan() {
		return cvlan;
	}
	public void setCvlan(int cvlan) {
		this.cvlan = cvlan;
	}
	@Override
	public String toString() {
		return "ONUInfo [loid=" + loid + ", number=" + number + ", svlan=" + svlan + ", cvlan=" + cvlan + "]";
	}
	
	

}
