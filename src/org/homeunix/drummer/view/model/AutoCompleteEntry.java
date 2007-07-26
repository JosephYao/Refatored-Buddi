/*
 * Created on Jul 25, 2007 by wyatt
 */
package org.homeunix.drummer.view.model;

import org.homeunix.drummer.model.Transaction;

public class AutoCompleteEntry {
	private String description;
	private String number;
	private long amount;
	private String to;
	private String from;

	public AutoCompleteEntry(Transaction t) {
		description = t.getDescription();
		number = t.getNumber();
		amount = t.getAmount();
		to = t.getTo().toString();
		from = t.getFrom().toString();
	}
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
