/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller;


public class TransactionsFramePreLoader {
	
//	private final Map<Account, SwingWorker> transactionsFrames;
//	
//	public TransactionsFramePreLoader() {
//		transactionsFrames = new HashMap<Account, SwingWorker>();
//		
//		for (Account a : SourceController.getAccounts()) {
//			add(a);
//		}
//	}
//	
//	public TransactionsFrame get(Account a){
//		if (transactionsFrames.get(a) == null)
//			add(a);
//		return (TransactionsFrame) transactionsFrames.get(a).get();
//	}
//	
//	public Vector<TransactionsFrame> getAll(){
//		Vector<TransactionsFrame> frames = new Vector<TransactionsFrame>();
//		
//		for (SwingWorker w : transactionsFrames.values()) {
//			frames.add((TransactionsFrame) w.get());
//		}
//		
//		return frames;
//	}
//	
//	public void add(final Account a){
//		SwingWorker worker = new SwingWorker(){
//			@Override
//			public Object construct() {
//				WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow();
//				Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
//				Point position = new Point(wa.getX(), wa.getY());
//
//				TransactionsFrame tf = new TransactionsFrame(a);
//				tf.openWindowInvisible(dimension, position);
//				
//				return tf;
//			}
//		};
//		
//		transactionsFrames.put(a, worker);
//		worker.setPriority(Thread.MIN_PRIORITY);
//		worker.start();
//	}
}
