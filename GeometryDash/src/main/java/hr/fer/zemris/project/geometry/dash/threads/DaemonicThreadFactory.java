package hr.fer.zemris.project.geometry.dash.threads;

import java.util.concurrent.ThreadFactory;

/**
 * Factory for creating daemonic threads. Implements singleton design pattern
 * @author Andi Škrgat
 *
 */
public class DaemonicThreadFactory implements ThreadFactory {
	
	/**
	 * Private instance
	 */
	private static final DaemonicThreadFactory daemonicThreadFactory = new DaemonicThreadFactory();
	
	/**
	 * Private construcotr
	 */
	private DaemonicThreadFactory() {
		
	}
	
	/**
	 * For getting same object
	 * @return
	 */
	public static DaemonicThreadFactory getInstance() {
		return daemonicThreadFactory;
	}
	

	@Override
	public Thread newThread(Runnable r) {
		Thread musicThread = new Thread(r);
		musicThread.setDaemon(true);
		return musicThread;
	}

}