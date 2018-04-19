package net.sf.latexdraw.instruments.robot;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.instruments.GUIVoidCommand;
import org.testfx.api.FxRobotInterface;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting items in combo boxes and lists.
 */
public interface FxRobotSpinner extends FxRobotInterface {
	default <T> void incrementSpinner(final Spinner<T> combo) {
		clickOn(combo.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
	}

	default <T> void decrementSpinner(final Spinner<T> combo) {
		clickOn(combo.lookup(".decrement-arrow-button"), MouseButton.PRIMARY);
	}

	default <T> void scrollOnSpinner(final Spinner<T> combo, final int amount) {
		clickOn(combo).scroll(amount);
	}

	default <T extends Number> void doTestSpinner(final GUIVoidCommand cmdsConfig, final Spinner<T> spinner,
												final GUIVoidCommand cmdSpinner, final List<Supplier<T>> oracles) {
		cmdsConfig.execute();
		final T val = spinner.getValue();
		cmdSpinner.execute();
		WaitForAsyncUtils.sleep(700, TimeUnit.MILLISECONDS);
		oracles.forEach(oracle -> assertEquals(spinner.getValue().doubleValue(), oracle.get().doubleValue(), 0.0001));
		assertNotEquals(val.doubleValue(), spinner.getValue().doubleValue(), 0.0001);
	}
}
