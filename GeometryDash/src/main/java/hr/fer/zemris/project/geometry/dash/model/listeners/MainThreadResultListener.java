package hr.fer.zemris.project.geometry.dash.model.listeners;


/**
 * Specific will use variable and store value in it
 * @author Andi Škrgat
 *
 */
public interface MainThreadResultListener<T> {
	
	/**
	 * @param newReference new value for some object
	 */
	void referenceChanged(T newReference);
	
	/**
	 * @return reference
	 */
	T getReference();

}
