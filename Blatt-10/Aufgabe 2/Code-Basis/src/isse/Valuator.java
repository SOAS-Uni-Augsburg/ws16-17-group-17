package isse;

public interface Valuator<T> {
	double valuate(T instance);

	double getAnnouncedSpan(Shift instance);

	double getActualSpan(Shift instance);
}
