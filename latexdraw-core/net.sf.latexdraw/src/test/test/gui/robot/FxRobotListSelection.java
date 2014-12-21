package test.gui.robot;

import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs.
 * This trait defines routines for selecting items in combo boxes and lists.
 */
public interface FxRobotListSelection extends FxRobotInterface {
	default <T>void selectComboBoxItem(final ComboBox<T> combo) {
		clickOn(combo).type(KeyCode.DOWN).type(KeyCode.ENTER);
	}
}
