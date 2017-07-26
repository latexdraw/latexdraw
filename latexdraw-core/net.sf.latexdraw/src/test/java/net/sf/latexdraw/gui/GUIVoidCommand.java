package net.sf.latexdraw.gui;

@FunctionalInterface
public interface GUIVoidCommand extends GUICommand<Void> {
	@Override
	default void execute(Void param) {
		execute();
	}

	void execute();
}
