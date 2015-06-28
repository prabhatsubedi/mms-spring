package com.mms.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="sales")
public class Sales implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer salesId;
	private String date;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Cashier.class)
	@JoinColumn(name="cashierId", referencedColumnName="cashierId") // new column of this name is created in this table
	private Cashier cashier;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="sales", orphanRemoval = true /*It deletes Sold Item too */) // No need to create column in table Sales, coz it is mapped by sales name of SoldItems Table
	private List<SoldItems> soldItems;
	
	private Integer netAmount;
	private Integer discount;
	private String invoiceNo;
	private String salesType;
	private String customerName;
	
	public Integer getSalesId() {
		return salesId;
	}
	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Cashier getCashier() {
		return cashier;
	}
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	public Integer getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Integer netAmount) {
		this.netAmount = netAmount;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
