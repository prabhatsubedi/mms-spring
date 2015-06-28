package com.mms.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="sold_items")
public class SoldItems implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer soldItemsId;
	
	@ManyToOne(targetEntity=Sales.class)
	@JoinColumn(name="salesId", referencedColumnName="salesId") // new column of this name is created in this table
	private Sales sales;
	
	@ManyToOne(targetEntity=Item.class)
	@JoinColumn(name="itemId", referencedColumnName="itemId") // new column of this name is created in this table
	private Item item;
	
	private Integer qty;
	private Integer rate;
	private Integer amount;
	private String batchNo;
	private String manufacturedDate;
	private String expiryDate;
	
	public Integer getSoldItemsId() {
		return soldItemsId;
	}
	public void setSoldItemsId(Integer soldItemsId) {
		this.soldItemsId = soldItemsId;
	}
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getManufacturedDate() {
		return manufacturedDate;
	}
	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
