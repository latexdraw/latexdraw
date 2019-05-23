package net.sf.latexdraw.instrument;

import javafx.application.Platform;

public interface CmdFX<T> extends Cmd<T> {
	@Override
	default void execute(final T param) {
		Platform.runLater(() -> bodyCmd(param));
	}

	void bodyCmd(final T param);
}
