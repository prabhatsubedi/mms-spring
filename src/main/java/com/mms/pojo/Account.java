package com.mms.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private String date;
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Party.class)
	@JoinColumn(name="partyId", referencedColumnName="partyId")// new column will be created in this Table
	private Party party;
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Cashier.class)
	@JoinColumn(name="cashierId", referencedColumnName="cashierId") //new column will be created in this Table
	private Cashier cashier;
	private String invoiceNo;
	private String desription;
	private Integer amount;
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Cashier getCashier() {
		return cashier;
	}
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	
	
}
