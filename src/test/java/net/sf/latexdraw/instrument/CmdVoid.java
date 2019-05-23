package net.sf.latexdraw.instrument;

@FunctionalInterface
public interface CmdVoid extends Cmd<Void> {
	@Override
	default void execute(final Void param) {
		execute();
	}

	default void execute() {
		bodyCmd();
	}

	void bodyCmd();
}
