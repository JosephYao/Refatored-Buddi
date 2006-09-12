package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;

public class PasswordInputPane extends JPanel {
	private static final long serialVersionUID = 0;

	public static String askForPassword() {
		return askForPassword(
				null, 
				Translate.getInstance().get(TranslateKeys.PASSWORD_MESSAGE), 
				Translate.getInstance().get(TranslateKeys.PASSWORD_TITLE));
	}
	
	public static String askForPassword(Component parentComponent, String message, String title) {
		PasswordInputPane pane = new PasswordInputPane(message);
		Component parent = (null == parentComponent) ?
				JOptionPane.getRootFrame() : parentComponent;
		pane.setComponentOrientation(parent.getComponentOrientation());
		
		JDialog dialog = pane.createDialog(parentComponent, title);
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
	
	public JDialog createDialog(Component parentComponent, String title) throws HeadlessException {
		Window window = getWindowForComponent(parentComponent);
	    
		final JDialog dialog = (window instanceof Frame) ?
				new JDialog((Frame) window, title, true) :
			    new JDialog((Dialog) window, title, true);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		
		JLabel messageLabel = new JLabel(this.message, JLabel.LEFT);
		add(messageLabel, gbc);
		
		gbc.gridy = 1;		
		gbc.gridwidth = 1;
		JLabel pw1Label = new JLabel("Password");
		add(pw1Label, gbc);
		
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		final JPasswordField pw1 = new JPasswordField(32);
		add(pw1, gbc);
		
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		JLabel pw2Label = new JLabel("Confirm Password");
		add(pw2Label, gbc);
		
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		final JPasswordField pw2 = new JPasswordField(32);
		add(pw2, gbc);
		
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		
		// TODO: these should be translated
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pw1Value = new String(pw1.getPassword());
				String pw2Value = new String(pw2.getPassword());
				if ((null == pw1Value) ? (null == pw2Value) : (pw1Value.equals(pw2Value))) {
					PasswordInputPane.this.value = pw1Value;
					dialog.setVisible(false);					
				} 
				else {
					JOptionPane.showMessageDialog(
							dialog,
							Translate.getInstance().get(TranslateKeys.PASSWORDS_DONT_MATCH));					
				}
			}			
		});
		add(ok, gbc);
		
		gbc.gridx = 2;
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PasswordInputPane.this.value = null;
			}			
		});
		add(cancel, gbc);
		
		dialog.setComponentOrientation(this.getComponentOrientation());
		
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
        contentPane.add(this, BorderLayout.CENTER);
        
        dialog.setResizable(false);
        
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        
        WindowAdapter adapter = new WindowAdapter() {            
            public void windowClosing(WindowEvent we) {
                PasswordInputPane.this.value = null;
            }
        };
        dialog.addWindowListener(adapter);
        dialog.addWindowFocusListener(adapter);        
        
		return dialog;
	}

	public String getValue() {
		return this.value;
	}
}
