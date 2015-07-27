package test.gui.robot;

import javafx.scene.control.Spinner;

import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs.
 * This trait defines routines for selecting items in combo boxes and lists.
 */
public interface FxRobotSpinner extends FxRobotInterface {
	default <T>void incrementSpinner(final Spinner<T> combo) {
//		clickOn(combo).type(KeyCode.UP);
		combo.getValueFactory().increment(1);
	}
	
	default <T>void decrementSpinner(final Spinner<T> combo) {
		combo.getValueFactory().decrement(1);
	}
}
