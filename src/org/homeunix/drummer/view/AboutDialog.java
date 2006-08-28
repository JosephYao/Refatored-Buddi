/*
 * Created on May 17, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.util.BrowserLauncher;
import org.homeunix.drummer.util.Log;

public class AboutDialog extends AbstractBudgetDialog {
	public static final long serialVersionUID = 0;
	
	private final JButton okButton;
	private final JButton donateButton;
	private final JLabel text;
	
	public AboutDialog(){
		super(MainBuddiFrame.getInstance());
		
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		donateButton = new JButton(Translate.getInstance().get(TranslateKeys.DONATE));
		
		Dimension buttonSize = new Dimension(Math.max(100, donateButton.getPreferredSize().width), donateButton.getPreferredSize().height);

		okButton.setPreferredSize(buttonSize);
		donateButton.setPreferredSize(buttonSize);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(donateButton);
		buttonPanel.add(okButton);
				
		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append(
				"<html><center><h1>")
				.append(	Translate.getInstance().get(TranslateKeys.BUDDI))
				.append("</h1>");

		StringBuffer sbVersion = new StringBuffer();
		sbVersion.append(
				"<html><h5>")
				.append(Translate.getInstance().get(TranslateKeys.VERSION))
				.append(" ")
				.append(Const.VERSION)
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
				.append(Translate.getInstance().get(TranslateKeys.ABOUT_TEXT))
				.append("<br><br>")
				.append(Translate.getInstance().get(TranslateKeys.ABOUT_COPYRIGHT))
				.append("<br>&lt;")
				.append(Translate.getInstance().get(TranslateKeys.ABOUT_EMAIL))
				.append("&gt;<br><a href='")
				.append(Translate.getInstance().get(Const.PROJECT_URL))
				.append("'>")
				.append(Translate.getInstance().get(Const.PROJECT_URL))
				.append("</a><br><br>")
				.append(Translate.getInstance().get(TranslateKeys.ABOUT_GPL))
				.append("</center></html>");

		text = new JLabel(sbText.toString());
		
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
		
		donateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					BrowserLauncher.openURL(Const.DONATE_URL);
				}
				catch (IOException ioe){
					Log.error(ioe);
				}
			}
		});
		
		text.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					BrowserLauncher.openURL(Const.PROJECT_URL);
				}
				catch (IOException ioe){
					Log.error(ioe);
				}
				super.mouseClicked(e);
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
