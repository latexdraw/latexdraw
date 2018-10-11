package net.sf.latexdraw.instruments;

@FunctionalInterface
public interface GUIVoidCommand extends GUICommand<Void> {
	@Override
	default void execute(Void param) {
		execute();
	}

	void execute();
}
