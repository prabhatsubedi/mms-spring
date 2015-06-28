package com.mms.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table (name="purchased_items")
public class PurchasedItems implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer purchasedItemsId;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Purchase.class)
	@JoinColumn(name="purchaseId", referencedColumnName="purchaseId") // new column of this name is created in this table
	private Purchase purchase;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Item.class)
	@JoinColumn(name="itemId", referencedColumnName="itemId") // new column of this name is created in this table
	private Item item;
	
	private Integer qty;
	private Integer rate;
	private Integer amount;
	private String batchNo;
	private String manufacturedDate;
	private String expiryDate;
	
	public Integer getPurchasedItemsId() {
		return purchasedItemsId;
	}
	public void setPurchasedItemsId(Integer purchasedItemsId) {
		this.purchasedItemsId = purchasedItemsId;
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
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
	
}
