/*
 * Created on Oct 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.text.AttributedString;

import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

public class BuddiPieSectionLabelGenerator extends StandardPieSectionLabelGenerator {
	public static final long serialVersionUID = 0;
	
	@SuppressWarnings("unchecked")
	public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
		return new AttributedString(generateSectionLabel(arg0, arg1));
	}
	
	@SuppressWarnings("unchecked")
	public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
		String value = TextFormatter.getFormattedCurrency(arg0.getValue(arg1).longValue(), false, false);
		String[] description = super.generateSectionLabel(arg0, arg1).split("!!!");
		return description[0] + ": "+ value + (description.length > 1 ? " (" + description[1] + "%)" : "");
	}
}
