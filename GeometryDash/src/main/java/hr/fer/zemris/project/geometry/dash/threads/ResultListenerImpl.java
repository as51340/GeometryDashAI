package hr.fer.zemris.project.geometry.dash.threads;

import java.util.Optional;

import hr.fer.zemris.project.geometry.dash.model.listeners.MainThreadResultListener;

/**
 * Handles result from JavaFX thread
 * @author Andi Škrgat
 *
 */
public class ResultListenerImpl<T> implements MainThreadResultListener<T> {

	/**
	 * Reference to the result
	 */
	private T result;
	
	public ResultListenerImpl(T result) {
		this.result = result;
	}
	
	@Override
	public void referenceChanged(T newReference) {
		this.result = newReference; 
	}

	@Override
	public T getReference() {
		return this.result;
	}	
}
