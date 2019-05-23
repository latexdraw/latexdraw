package net.sf.latexdraw.instrument;

public interface Cmd<T> {
	void execute(final T param);
}
