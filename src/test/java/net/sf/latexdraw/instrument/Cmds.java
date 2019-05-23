package net.sf.latexdraw.instrument;

import io.reactivex.annotations.CheckReturnValue;
import java.util.List;
import org.testfx.util.WaitForAsyncUtils;

public class Cmds implements CmdVoid {
	@CheckReturnValue
	public static Cmds of(final CmdVoid... commands) {
		return new Cmds(commands);
	}

	protected final List<CmdVoid> cmds;

	public Cmds(final CmdVoid... commands) {
		super();
		cmds = List.of(commands);
	}

	@Override
	public void execute() {
		bodyCmd();
	}

	@Override
	public void bodyCmd() {
		WaitForAsyncUtils.waitForFxEvents();
		cmds.forEach(cmd -> {
			// null required to call the execute(Void) method
			cmd.execute();
			WaitForAsyncUtils.waitForFxEvents();
		});
	}
}
