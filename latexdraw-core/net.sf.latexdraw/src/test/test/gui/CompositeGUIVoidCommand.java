package test.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testfx.util.WaitForAsyncUtils;

public class CompositeGUIVoidCommand implements GUIVoidCommand {
	protected final List<GUIVoidCommand> cmds;

	public CompositeGUIVoidCommand(final GUIVoidCommand... commands) {
		super();
		cmds = new ArrayList<>();
		cmds.addAll(Arrays.asList(commands));
	}

	@Override
	public void execute() {
		WaitForAsyncUtils.waitForFxEvents(12);
		cmds.forEach(cmd -> {
			cmd.execute();
			WaitForAsyncUtils.waitForFxEvents(12);
		});
	}
}
