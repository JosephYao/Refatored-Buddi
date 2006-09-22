/*
 * Created on May 17, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class AboutDialog extends AbstractDialog {
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

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(donateButton);
		buttonPanel.add(okButton);
				
		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append(
				"<html><center><h1>")
				.append(Translate.getInstance().get(TranslateKeys.BUDDI))
				.append("</h1></center></html>");

		StringBuffer sbVersion = new StringBuffer();
		sbVersion.append(
				"<html><center><h5>")
				.append(Translate.getInstance().get(TranslateKeys.VERSION))
				.append(" ")
				.append(Const.VERSION)
				.append("</h5></center></html");
		
		JLabel version = new JLabel(sbVersion.toString());
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		bottomPanel.add(version, BorderLayout.NORTH);
		
		JLabel title = new JLabel(sbTitle.toString());
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = AbstractFrame.class.getClassLoader();
		}

		URL imageResource = cl.getResource("Buddi.png");
		if (null != imageResource) {
			title.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageResource)));
			title.setVerticalTextPosition(JLabel.BOTTOM);
		}
		
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
		inlayPanel.add(text, BorderLayout.CENTER);
		inlayPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		if (Buddi.isMac()){
			inlayPanel.setBorder(BorderFactory.createTitledBorder(""));
		}
		
		this.setLayout(new BorderLayout());
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		this.getRootPane().setDefaultButton(okButton);
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(inlayPanel, BorderLayout.CENTER);
		
		initActions();
		
		openWindow();
	}
	
	@Override
	protected AbstractDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog.this.setVisible(false);
				AboutDialog.this.dispose();
			}
		});
		
		donateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					BrowserLauncher bl = new BrowserLauncher(null);
					bl.openURLinBrowser(Const.DONATE_URL);
				}
				catch (Exception ex){
					Log.error(ex);
				}
			}
		});
		
		text.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					BrowserLauncher bl = new BrowserLauncher(null);
					bl.openURLinBrowser(Const.PROJECT_URL);
				}
				catch (Exception ex){
					Log.error(ex);
				}
				super.mouseClicked(e);
			}
		});
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		return this;
	}

	@Override
	public AbstractDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractDialog updateContent() {
		return this;
	}
}
