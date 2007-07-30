/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;


/**
 * @author wyatt
 * 
 * Each Transaction contains one or more TransactionSections.  This defines the source,
 * to, and from for a single part of a split transaction.
 */
public class TransactionSplit {

	private Long amount;
	private Source from;
	private Source to;

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Source getFrom() {
		return from;
	}
	public void setFrom(Source from) {
		this.from = from;
	}
	public Source getTo() {
		return to;
	}
	public void setTo(Source to) {
		this.to = to;
	}
}
