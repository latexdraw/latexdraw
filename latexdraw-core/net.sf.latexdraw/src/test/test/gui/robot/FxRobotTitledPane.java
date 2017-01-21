package test.gui.robot;

import javafx.scene.control.TitledPane;
import org.testfx.util.WaitForAsyncUtils;

/**
 * A robot to interaction with titled panes.
 */
public interface FxRobotTitledPane {
	default void expandPane(final TitledPane pane) {
		pane.setExpanded(true);
		WaitForAsyncUtils.waitForFxEvents();
	}
}
