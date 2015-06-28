package com.ics.mms.context;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CashierForm {
	private Integer cashierId;
	@NotEmpty
	@NotNull
	@Size(min = 1, max = 50)
	private String cashierName;
	public Integer getCashierId() {
		return cashierId;
	}
	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	
}
