package isse;

import java.util.Map;

/**
 * 
 * @author alexander
 *
 * @param <A> Agent type for mechanism
 * @param <T> Outcome selections
 */
public abstract class QuasilinearMechanism<A,T> {
	public abstract T selection(Map<A, Valuator<T>> typeProfile);
	public abstract Map<A, Double> getPayments(Map<A, Valuator<T>> typeProfile);
	
}
