package test.gui.robot;

import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;

import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs.
 * This trait defines routines for picking colours.
 */
public interface FxRobotColourPicker extends FxRobotInterface {
	default void pickColour(final ColorPicker picker) {
		clickOn(picker).type(KeyCode.RIGHT).type(KeyCode.ENTER);
	}
}
