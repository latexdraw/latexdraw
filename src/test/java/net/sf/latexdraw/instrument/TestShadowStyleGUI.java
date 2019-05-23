package net.sf.latexdraw.instrument;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import org.junit.Before;

public abstract class TestShadowStyleGUI extends TestShapePropGUI<ShapeShadowCustomiser> {
	protected CheckBox shadowCB;
	protected ColorPicker shadowColB;
	protected Spinner<Double> shadowSizeField;
	protected Spinner<Double> shadowAngleField;

	protected final CmdVoid checkShadow = () -> clickOn(shadowCB);
	protected final CmdVoid pickShadCol = () -> pickColour(shadowColB);
	protected final CmdVoid incrementshadowSizeField = () -> incrementSpinner(shadowSizeField);
	protected final CmdVoid incrementshadowAngleField = () -> incrementSpinner(shadowAngleField);

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
