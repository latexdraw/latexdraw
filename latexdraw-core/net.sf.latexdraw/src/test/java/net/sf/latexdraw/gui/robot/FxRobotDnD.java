package net.sf.latexdraw.gui.robot;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobotInterface;
import org.testfx.util.WaitForAsyncUtils;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * executing DnDs on a GUI.
 */
public interface FxRobotDnD extends FxRobotInterface {
	/**
	 * Executes a DnD from the given position <code>src</code> to the given position
	 * <code>tgt</code> using the given button <code>button</code>.
	 * @param src The source position of the DnD.
	 * @param tgt The target position of the DnD.
	 * @param button The pressed button.
	 */
	default void dndFromPos(final Point2D src, final Point2D tgt, final MouseButton button) {
		moveTo(src);
		press(button);
		moveTo(tgt);
		release(button);
		WaitForAsyncUtils.waitForFxEvents();
	}
}
