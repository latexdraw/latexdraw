package net.sf.latexdraw.instruments;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import org.junit.Before;

public abstract class TestDotStyleGUI extends TestShapePropGUI<ShapeDotCustomiser> {
	protected Spinner<Double> dotSizeField;
	protected ComboBox<DotStyle> dotCB;
	protected ColorPicker fillingB;

	protected final GUIVoidCommand pickFillingColour = () -> pickColour(fillingB);
	protected final GUIVoidCommand selectNextDotStyle = () -> selectNextComboBoxItem(dotCB);
	protected final GUIVoidCommand incrementDotSize = () -> incrementSpinner(dotSizeField);
	protected final GUIVoidCommand setDotStyleFillable = () -> selectGivenComboBoxItem(dotCB, DotStyle.DIAMOND);
	protected final GUICommand<DotStyle> setDotStyle = style -> selectGivenComboBoxItem(dotCB, style);

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
		ins = (ShapeDotCustomiser) injectorFactory.call(ShapeDotCustomiser.class);
		ins.setActivated(true);
	}
}
