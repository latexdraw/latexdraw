package net.sf.latexdraw.instrument;

@FunctionalInterface
public interface GUIVoidCommand extends GUICommand<Void> {
	@Override
	default void execute(final Void param) {
		execute();
	}

	void execute();
}
