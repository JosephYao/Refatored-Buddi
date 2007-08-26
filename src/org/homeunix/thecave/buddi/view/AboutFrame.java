/*
 * Created on May 17, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view;

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

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.AboutFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

import edu.stanford.ejalbert.BrowserLauncher;

public class AboutFrame extends MossFrame implements ActionListener {
	public static final long serialVersionUID = 0;

	private final JButton okButton;
	private final JButton donateButton;
	private final JLabel text;

	public AboutFrame() {
		super(null);
		okButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK));
		donateButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_DONATE));

		Dimension buttonSize = new Dimension(Math.max(100, donateButton.getPreferredSize().width), donateButton.getPreferredSize().height);

		okButton.setPreferredSize(buttonSize);
		donateButton.setPreferredSize(buttonSize);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(donateButton);
		buttonPanel.add(okButton);

		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append(
				"<html><center><h1>")
				.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI))
				.append("</h1></center></html>");

		StringBuffer sbVersion = new StringBuffer();
		sbVersion.append(
		"<html><center><h5>")
		.append(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_VERSION))
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
			cl = MossFrame.class.getClassLoader();
		}

		URL imageResource = cl.getResource("Buddi.png");
		if (imageResource != null) {
			title.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageResource)));
			title.setVerticalTextPosition(JLabel.BOTTOM);
		}

		StringBuffer sbText = new StringBuffer();
		sbText.append(
				"<html><center>")
				.append(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_TEXT))
				.append("<br><br>")
				.append(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_COPYRIGHT))
				.append("<br>&lt;")
				.append(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_EMAIL))
				.append("&gt;<br><a href='")
				.append(PrefsModel.getInstance().getTranslator().get(Const.PROJECT_URL))
				.append("'>")
				.append(PrefsModel.getInstance().getTranslator().get(Const.PROJECT_URL))
				.append("</a><br><br>")
				.append(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_GPL))
				.append("</center></html>");

		text = new JLabel(sbText.toString());

		JPanel inlayPanel = new JPanel(new BorderLayout());
		inlayPanel.add(text, BorderLayout.CENTER);
		inlayPanel.add(bottomPanel, BorderLayout.SOUTH);

		if (OperatingSystemUtil.isMac()){
			inlayPanel.setBorder(BorderFactory.createTitledBorder(""));
		}

		this.setLayout(new BorderLayout());
		this.getRootPane().setDefaultButton(okButton);
		this.setTitle(PrefsModel.getInstance().getTranslator().get(AboutFrameKeys.ABOUT_BUDDI));
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(inlayPanel, BorderLayout.CENTER);
	}

	public void init() {
		super.init();
		
		okButton.addActionListener(this);
		donateButton.addActionListener(this);

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
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			AboutFrame.this.closeWindow();
		}
		else if (e.getSource().equals(donateButton)){		
			try{
				BrowserLauncher bl = new BrowserLauncher(null);
				bl.openURLinBrowser(Const.DONATE_URL);
			}
			catch (Exception ex){
				Log.error(ex);
			}
		}
	}
}
