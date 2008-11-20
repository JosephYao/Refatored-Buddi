package org.homeunix.thecave.buddi.plugin.api;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.MossPanel;

/**
 *
 * @author mpeccorini
 */
public abstract class BuddiPanelPlugin extends MossPanel implements MossPlugin {

	private MainFrame parentFrame;

	protected Document document;

	private DocumentChangeListener listener;

	public abstract String getTabLabelKey();

	public abstract void initPanel();

	public abstract void updatePanel();

	@Override
	public void init(){
		super.init();
		initPanel();
	}

	@Override
	public void updateContent(){
		super.updateContent();
		updatePanel();
	}

	public MainFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(MainFrame parentFrame) {
		this.parentFrame = parentFrame;
		listener = new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent arg0) {
				updatePanel();
			}
		};
		this.document = (Document) parentFrame.getDocument();
		parentFrame.getDocument().addDocumentChangeListener(listener);
	}

}
