/*
 * Created on May 17, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.logic.MainBudgetFrame;

public class AboutDialog extends AbstractBudgetDialog {
	public static final long serialVersionUID = 0;
	
	private final JButton okButton;
	
	public AboutDialog(){
		super(MainBudgetFrame.getInstance());
		
		okButton = new JButton(Translate.inst().get(TranslateKeys.OK));
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		
		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append(
				"<html><center><h1>")
				.append(	Translate.inst().get(TranslateKeys.BUDDI))
				.append("</h1>");

		StringBuffer sbVersion = new StringBuffer();
		sbVersion.append(
				"<html><h5>")
				.append(Translate.inst().get(TranslateKeys.VERSION))
				.append(" ")
				.append(Buddi.version)
				.append("</h5></html");
		JLabel version = new JLabel(sbVersion.toString());
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(buttonPanel, BorderLayout.EAST);
		bottomPanel.add(version, BorderLayout.WEST);
		
		JLabel title = new JLabel(sbTitle.toString());
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);
		
		StringBuffer sbText = new StringBuffer();
		sbText.append(
				"<html><center>")
				.append(Translate.inst().get(TranslateKeys.ABOUT_TEXT))
				.append("<br><br>")
				.append(Translate.inst().get(TranslateKeys.ABOUT_COPYRIGHT))
				.append("<br>&lt;")
				.append(Translate.inst().get(TranslateKeys.ABOUT_EMAIL))
				.append("&gt;<br><a href='")
				.append(Translate.inst().get(TranslateKeys.ABOUT_WEBPAGE))
				.append("'>")
				.append(Translate.inst().get(TranslateKeys.ABOUT_WEBPAGE))
				.append("</a><br><br>")
				.append(Translate.inst().get(TranslateKeys.ABOUT_GPL))
				.append("</center></html>");

		JLabel text = new JLabel(sbText.toString());
		
		JPanel inlayPanel = new JPanel(new BorderLayout());
		inlayPanel.setBorder(BorderFactory.createTitledBorder(""));
		inlayPanel.add(text, BorderLayout.CENTER);
		inlayPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		this.getRootPane().setDefaultButton(okButton);
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(inlayPanel, BorderLayout.CENTER);
		
		initActions();
		
		openWindow();
	}
	
	@Override
	protected AbstractBudgetDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog.this.setVisible(false);
				AboutDialog.this.dispose();
			}
		});
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		return this;
	}

	@Override
	public AbstractBudgetDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog updateContent() {
		return this;
	}
}
