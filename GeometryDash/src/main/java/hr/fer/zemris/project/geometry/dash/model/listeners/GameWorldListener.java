package hr.fer.zemris.project.geometry.dash.model.listeners;

import java.io.IOException;

/**
 * Actions when one instance of GameWorld is finished
 * @author Andi Škrgat
 *
 */
public interface GameWorldListener {

	/**
	 * One instance of {@linkplain GameWorld} is finished
	 * @throws InterruptedException 
	 */
	void instanceFinished(double time) throws IOException, InterruptedException ;
}
