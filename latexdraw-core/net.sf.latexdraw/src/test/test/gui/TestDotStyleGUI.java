package test.gui;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;

import org.junit.Before;

public abstract class TestDotStyleGUI extends TestShapePropGUI<ShapeDotCustomiser> {
	protected Spinner<Double> dotSizeField;
	protected ComboBox<DotStyle> dotCB;
	protected ColorPicker fillingB;
	protected TitledPane mainPane;

	final protected GUIVoidCommand pickFillingColour = () -> pickColour(fillingB);
	final protected GUIVoidCommand selectNextDotStyle = () -> selectNextComboBoxItem(dotCB);
	final protected GUIVoidCommand incrementDotSize = () -> incrementSpinner(dotSizeField);
	final protected GUIVoidCommand setDotStyleFillable = () -> selectGivenComboBoxItem(dotCB, DotStyle.DIAMOND);
	final protected GUICommand<DotStyle> setDotStyle = (style) -> selectGivenComboBoxItem(dotCB, style);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/DotStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		fillingB = find("#fillingB");
		dotCB = find("#dotCB");
		dotSizeField = find("#dotSizeField");
		ins = (ShapeDotCustomiser)guiceFactory.call(ShapeDotCustomiser.class);
		ins.setActivated(true);
	}
}
