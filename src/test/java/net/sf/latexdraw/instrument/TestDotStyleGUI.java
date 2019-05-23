package net.sf.latexdraw.instrument;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.model.api.shape.DotStyle;
import org.junit.Before;

public abstract class TestDotStyleGUI extends TestShapePropGUI<ShapeDotCustomiser> {
	protected Spinner<Double> dotSizeField;
	protected ComboBox<DotStyle> dotCB;
	protected ColorPicker fillingB;

	protected final CmdVoid pickFillingColour = () -> pickColour(fillingB);
	protected final CmdVoid selectNextDotStyle = () -> selectNextComboBoxItem(dotCB);
	protected final CmdVoid incrementDotSize = () -> incrementSpinner(dotSizeField);
	protected final CmdVoid setDotStyleFillable = () -> selectGivenComboBoxItem(dotCB, DotStyle.DIAMOND);
	protected final Cmd<DotStyle> setDotStyle = style -> selectGivenComboBoxItem(dotCB, style);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/DotStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		fillingB = find("#fillingB");
		dotCB = find("#dotCB");
		dotSizeField = find("#dotSizeField");
		ins = injector.getInstance(ShapeDotCustomiser.class);
		ins.setActivated(true);
	}
}
