package test.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeGUICommand implements GUICommand {
	protected final List<GUICommand> cmds;
	
	public CompositeGUICommand(final GUICommand... commands) {
		super();
		cmds = new ArrayList<>();
		cmds.addAll(Arrays.asList(commands));
	}

	@Override
	public void execute() {
		cmds.forEach(GUICommand::execute);
	}
}
