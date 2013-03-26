/*
 * Created on Dec 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.exports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.plugin.api.BuddiExportPlugin;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginException;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginMessage;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransactionSplit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.digitalcave.moss.swing.MossDocumentFrame;

public class ExportBuddiLiveData extends BuddiExportPlugin {

	@Override
	public void exportData(ImmutableDocument model, MossDocumentFrame callingFrame, File file) throws PluginException, PluginMessage {
		try {
			final JSONObject result = new JSONObject();
			for (ImmutableAccount account : model.getImmutableAccounts()) {
				final JSONObject a = new JSONObject();
				a.put("accountType", account.getAccountType().getName());
				a.put("deleted", account.isDeleted());
				a.put("name", account.getName());
				a.put("startBalance", formatCurrency(new BigDecimal(account.getStartingBalance()).divide(new BigDecimal(100))));
				a.put("startDate", formatDate(account.getStartDate()));
				a.put("type", account.getAccountType().isCredit() ? "C" : "D");
				a.put("uuid", account.getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", "").replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
				result.append("accounts", a);
			}
			
			final List<ImmutableBudgetCategory> rootCategories = new ArrayList<ImmutableBudgetCategory>();
			for (ImmutableBudgetCategory category : model.getImmutableBudgetCategories()) {
				if (category.getParent() == null) rootCategories.add(category);
			}
			result.put("categories", getCategories(rootCategories));
			
			for (ImmutableBudgetCategory category : model.getImmutableBudgetCategories()) {
				for (Date d : category.getBudgetedDates()) {
					final JSONObject e = new JSONObject();
					e.put("amount", formatCurrency(new BigDecimal(category.getAmount(d)).divide(new BigDecimal(100))));
					e.put("category", category.getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
					e.put("date", formatDate(d));
					result.append("entries", e);
				}
			}
			
			//Splits transactions are not visible to plugins, so we have to go to the raw object
			for (ScheduledTransaction scheduledTransaction : ((Document) model.getRaw().getDocument()).getScheduledTransactions()){
				final JSONObject st = new JSONObject();
				st.put("deleted", scheduledTransaction.isDeleted());
				st.put("description", scheduledTransaction.getDescription());
				st.put("number", scheduledTransaction.getNumber());
				st.put("uuid", scheduledTransaction.getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
				
				//Here is the stuff over and above the base transactions
				st.put("scheduleName", scheduledTransaction.getScheduleName());
				st.put("scheduleDay", scheduledTransaction.getScheduleDay());
				st.put("scheduleWeek", scheduledTransaction.getScheduleWeek());
				st.put("scheduleMonth", scheduledTransaction.getScheduleMonth());
				st.put("frequencyType", scheduledTransaction.getFrequencyType());
				st.put("startDate", formatDate(scheduledTransaction.getStartDate()));
				st.put("endDate", formatDate(scheduledTransaction.getEndDate()));
				st.put("lastCreatedDate", formatDate(scheduledTransaction.getLastDayCreated()));
				st.put("message", scheduledTransaction.getMessage());
				
				//Here we have to do some work, since the data models differ a bit.
				// If there are no splits, then our life is easy: just add a single
				// split with the from / to sources as indicated.
				if (scheduledTransaction.getFromSplits().size() == 0 && scheduledTransaction.getToSplits().size() == 0){
					final JSONObject s = new JSONObject();
					s.put("amount", formatCurrency(new BigDecimal(scheduledTransaction.getAmount()).divide(new BigDecimal(100))));
					s.put("from", scheduledTransaction.getFrom().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
					s.put("to", scheduledTransaction.getTo().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
					s.put("memo", scheduledTransaction.getMemo());
					st.append("splits", s);
				}
				//If we only have one of either to or from splits, then things are 
				// still not too complicated; we can just add one split for each
				// one, with the other side being the single source
				else if (scheduledTransaction.getFromSplits().size() > 0 ^ scheduledTransaction.getToSplits().size() > 0){
					boolean fromSplits = scheduledTransaction.getFromSplits().size() > 0;
					for (TransactionSplit split : fromSplits ? scheduledTransaction.getFromSplits() : scheduledTransaction.getToSplits()) {
						final JSONObject s = new JSONObject();
						s.put("amount", formatCurrency(new BigDecimal(split.getAmount()).divide(new BigDecimal(100))));
						s.put("from", fromSplits ? split.getSource().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", "") : scheduledTransaction.getFrom().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
						s.put("to", !fromSplits ? split.getSource().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", "") : scheduledTransaction.getTo().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
						s.put("memo", scheduledTransaction.getMemo());
						st.append("splits", s);
					}
				}
				//If both to and from contain splits, then we have a bit of a problem,
				// since the Buddi Live data model doesn't really handle that concept.
				// The closest we can get to it is to make a cross product of splits.
				// The balances should work out, but it is potentially going to be very ugly.
				//TODO For now we throw an error in this condition.
				else {
					throw new PluginException("Both from / to splits on a given transaction are not currently supported.");
				}
				result.append("scheduledTransactions", st);
			}
			
			for (ImmutableTransaction transaction : model.getImmutableTransactions()) {
				final JSONObject t = new JSONObject();
				t.put("date", formatDate(transaction.getDate()));
				t.put("deleted", transaction.isDeleted());
				t.put("description", transaction.getDescription());
				t.put("number", transaction.getNumber());
				t.put("uuid", transaction.getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
				//Here we have to do some work, since the data models differ a bit.
				// If there are no splits, then our life is easy: just add a single
				// split with the from / to sources as indicated.
				if (transaction.getImmutableFromSplits().size() == 0 && transaction.getImmutableToSplits().size() == 0){
					final JSONObject s = new JSONObject();
					s.put("amount", formatCurrency(new BigDecimal(transaction.getAmount()).divide(new BigDecimal(100))));
					s.put("from", transaction.getFrom().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
					s.put("to", transaction.getTo().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
					s.put("memo", transaction.getMemo());
					t.append("splits", s);
				}
				//If we only have one of either to or from splits, then things are 
				// still not too complicated; we can just add one split for each
				// one, with the other side being the single source
				else if (transaction.getImmutableFromSplits().size() > 0 ^ transaction.getImmutableToSplits().size() > 0){
					boolean fromSplits = transaction.getImmutableFromSplits().size() > 0;
					for (ImmutableTransactionSplit split : fromSplits ? transaction.getImmutableFromSplits() : transaction.getImmutableToSplits()) {
						final JSONObject s = new JSONObject();
						s.put("amount", formatCurrency(new BigDecimal(split.getAmount()).divide(new BigDecimal(100))));
						s.put("from", fromSplits ? split.getSource().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", "") : transaction.getFrom().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
						s.put("to", !fromSplits ? split.getSource().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", "") : transaction.getTo().getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
						s.put("memo", transaction.getMemo());
						t.append("splits", s);
					}
				}
				//If both to and from contain splits, then we have a bit of a problem,
				// since the Buddi Live data model doesn't really handle that concept.
				// The closest we can get to it is to make a cross product of splits.
				// The balances should work out, but it is potentially going to be very ugly.
				//TODO For now we throw an error in this condition.
				else {
					throw new PluginException("Both from / to splits on a given transaction are not currently supported.");
				}
				result.append("transactions", t);
			}

			final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(result.toString());
			writer.flush();
			writer.close();
		}
		catch (JSONException e){
			throw new PluginException(e);
		}
		catch (IOException e){
			throw new PluginException(e);
		}
	}
	
	private JSONArray getCategories(List<ImmutableBudgetCategory> categories) throws JSONException {
		final JSONArray result = new JSONArray();
		for (ImmutableBudgetCategory category : categories) {
			final JSONObject c = new JSONObject();
			c.put("name", category.getName());
			c.put("deleted", category.isDeleted());
			c.put("periodType", category.getBudgetPeriodType().getBudgetCategoryType().getKey());
			c.put("type", category.isIncome() ? "I" : "E");
			c.put("uuid", category.getUid().replaceAll("org.homeunix.thecave.buddi.model.impl.*Impl-", ""));
			if (category.getAllImmutableChildren().size() > 0) c.put("categories", getCategories(category.getAllImmutableChildren()));
			result.put(c);
		}
		return result;
	}
	

	@Override
	public boolean isPromptForFile() {
		return true;
	}
	
	@Override
	public String[] getFileExtensions() {
		return new String[]{".json"};
	}
	
	public String getName() {
		return "Export Buddi Live data";
	}
	
	private static String formatDate(Date date){
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	private static String formatCurrency(BigDecimal value){
		if (value == null) return null;
		return value.toPlainString();
	}
}
