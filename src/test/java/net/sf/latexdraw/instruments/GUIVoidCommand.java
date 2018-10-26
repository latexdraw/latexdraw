package net.sf.latexdraw.instruments;

@FunctionalInterface
public interface GUIVoidCommand extends GUICommand<Void> {
	@Override
	default void execute(final Void param) {
		execute();
	}

	void execute();
}
