package test.gui.robot;

import static org.junit.Assert.fail;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting items in combo boxes and lists.
 */
public interface FxRobotListSelection extends FxRobotInterface {
	default <T> void selectNextComboBoxItem(final ComboBox<T> combo) {
		clickOn(combo).type(KeyCode.DOWN).type(KeyCode.ENTER);
	}

	default <T> void selectGivenComboBoxItem(final ComboBox<T> combo, final T item) {
		final int index = combo.getItems().indexOf(item);
		final int indexSel = combo.getSelectionModel().getSelectedIndex();

		if(index == -1)
			fail("The item " + item + " is not in the combo box " + combo);

		clickOn(combo);

		if(index > indexSel)
			for(int i = indexSel; i < index; i++)
				type(KeyCode.DOWN);
		else if(index < indexSel)
			for(int i = indexSel; i > index; i--)
				type(KeyCode.UP);

		type(KeyCode.ENTER);
	}
}
