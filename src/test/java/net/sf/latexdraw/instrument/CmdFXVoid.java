package net.sf.latexdraw.instrument;

import javafx.application.Platform;

@FunctionalInterface
public interface CmdFXVoid extends CmdVoid {
	static CmdFXVoid of(final CmdVoid cmd) {
		return () -> cmd.execute();
	}

	@Override
	default void execute() {
		Platform.runLater(() -> bodyCmd());
	}
}
