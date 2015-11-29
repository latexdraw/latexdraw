package test.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeGUIVoidCommand implements GUIVoidCommand {
	protected final List<GUIVoidCommand> cmds;

	public CompositeGUIVoidCommand(final GUIVoidCommand... commands) {
		super();
		cmds = new ArrayList<>();
		cmds.addAll(Arrays.asList(commands));
	}

	@Override
	public void execute() {
		cmds.forEach(GUIVoidCommand::execute);
	}
}
