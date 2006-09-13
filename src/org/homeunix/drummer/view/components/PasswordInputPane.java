package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;

public class PasswordInputPane extends JPanel {
	private static final long serialVersionUID = 0;

	public static String askForPassword(boolean showConfirm, boolean isNullPasswordAllowed) {
		return askForPassword(
				MainBuddiFrame.getInstance(), 
				Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD), 
				Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD_TITLE),
				showConfirm,
				isNullPasswordAllowed
		);
	}
	
	public static String askForPassword(Component parentComponent, String message, String title, boolean showConfirm, boolean isNullPasswordAllowed) {
		PasswordInputPane pane = new PasswordInputPane(message);
		Component parent = (null == parentComponent) ?
				JOptionPane.getRootFrame() : parentComponent;
		pane.setComponentOrientation(parent.getComponentOrientation());
		
		JDialog dialog = pane.createDialog(parentComponent, title, showConfirm, isNullPasswordAllowed);
		dialog.setVisible(true);
        dialog.dispose();
        
        return pane.getValue();
	}
		
	private static Window getWindowForComponent(Component component) {
		if (component == null) {
			return JOptionPane.getRootFrame();
		}
        if (component instanceof Frame || component instanceof Dialog) {
        	return (Window) component;
        }
        return getWindowForComponent(component.getParent());
	}
	
	private String message;
	private String value;
	
	private PasswordInputPane(String message) {
		this.message = message;
	}	
	
	public JDialog createDialog(Component parentComponent, String title, final boolean showConfirm, final boolean isNullPasswordAllowed) throws HeadlessException {
		Window window = getWindowForComponent(parentComponent);
	    
		final JDialog dialog = (window instanceof Frame) ?
				new JDialog((Frame) window, title, true) :
			    new JDialog((Dialog) window, title, true);
		
		this.setLayout(new GridLayout(0, 1));
				
		JLabel messageLabel = new JLabel(this.message, JLabel.LEFT);
		this.add(messageLabel);
		
		JPanel passwordPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel pw1Label = new JLabel(Translate.getInstance().get(TranslateKeys.PASSWORD));
		passwordPanel1.add(pw1Label);
		
		final JPasswordField pw1 = new JPasswordField();
		Dimension passwordBoxSize = new Dimension(200, pw1.getPreferredSize().height);
		pw1.setPreferredSize(passwordBoxSize);
		passwordPanel1.add(pw1);
		
		this.add(passwordPanel1);
		
		JPanel passwordPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel pw2Label = new JLabel(Translate.getInstance().get(TranslateKeys.CONFIRM_PASSWORD));
		passwordPanel2.add(pw2Label);
		
		final JPasswordField pw2 = new JPasswordField();
		pw2.setPreferredSize(passwordBoxSize);
		passwordPanel2.add(pw2);
		
		if (showConfirm)
			this.add(passwordPanel2);
				
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		okButton.setPreferredSize(new Dimension(Math.max(100, okButton.getPreferredSize().width), okButton.getPreferredSize().height));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pw1Value = new String(pw1.getPassword());
				String pw2Value = new String(pw2.getPassword());
				if ((pw1Value == null && pw2Value == null)
						|| pw1Value.equals(pw2Value)
						|| !showConfirm) {
					if (pw1Value != null && pw1Value.length() == 0){
						noPasswordEntered(dialog, isNullPasswordAllowed);
					}
					else{
						PasswordInputPane.this.value = pw1Value;
						dialog.setVisible(false);
					}
				} 
				else {
					JOptionPane.showMessageDialog(
							dialog,
							Translate.getInstance().get(TranslateKeys.PASSWORDS_DONT_MATCH));					
				}
			}			
		});
		
		JButton cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
		cancelButton.setPreferredSize(new Dimension(Math.max(100, cancelButton.getPreferredSize().width), cancelButton.getPreferredSize().height));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noPasswordEntered(dialog, isNullPasswordAllowed);
			}			
		});

		this.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		
		//Mac OK buttons should be on the right 
		if (Buddi.isMac()){
			buttonPanel.add(cancelButton);
			buttonPanel.add(okButton);
		}
		else{
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
		}
		
		this.add(buttonPanel);
		
		dialog.getRootPane().setDefaultButton(okButton);
		
//		dialog.setComponentOrientation(this.getComponentOrientation());
		
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
        contentPane.add(this, BorderLayout.CENTER);
        
        dialog.setResizable(false);
        
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        
        WindowAdapter adapter = new WindowAdapter() {            
            public void windowClosing(WindowEvent we) {
            	noPasswordEntered(dialog, isNullPasswordAllowed);
            }
        };
        dialog.addWindowListener(adapter);
        dialog.addWindowFocusListener(adapter);        
        
		return dialog;
	}
	
	private void noPasswordEntered(JDialog dialog, boolean isNullPasswordAllowed){
		if (isNullPasswordAllowed)
			JOptionPane.showMessageDialog(
					MainBuddiFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.NO_PASSWORD_ENTERED), 
					Translate.getInstance().get(TranslateKeys.NO_PASSWORD_ENTERED_TITLE), 
					JOptionPane.INFORMATION_MESSAGE
			);
		PasswordInputPane.this.value = null;
		dialog.setVisible(false);
	}

	public String getValue() {
		return this.value;
	}
}
