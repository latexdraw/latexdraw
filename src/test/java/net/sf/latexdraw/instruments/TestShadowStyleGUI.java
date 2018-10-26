package net.sf.latexdraw.instruments;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import org.junit.Before;

public abstract class TestShadowStyleGUI extends TestShapePropGUI<ShapeShadowCustomiser> {
	protected CheckBox shadowCB;
	protected ColorPicker shadowColB;
	protected Spinner<Double> shadowSizeField;
	protected Spinner<Double> shadowAngleField;

	protected final GUIVoidCommand checkShadow = () -> clickOn(shadowCB);
	protected final GUIVoidCommand pickShadCol = () -> pickColour(shadowColB);
	protected final GUIVoidCommand incrementshadowSizeField = () -> incrementSpinner(shadowSizeField);
	protected final GUIVoidCommand incrementshadowAngleField = () -> incrementSpinner(shadowAngleField);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/ShadowStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		shadowCB = find("#shadowCB");
		shadowColB = find("#shadowColB");
		shadowSizeField = find("#shadowSizeField");
		shadowAngleField = find("#shadowAngleField");
		ins = injector.getInstance(ShapeShadowCustomiser.class);
		ins.setActivated(true);
	}
}
