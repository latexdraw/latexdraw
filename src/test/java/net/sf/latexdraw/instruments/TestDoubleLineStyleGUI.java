package net.sf.latexdraw.instruments;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import org.junit.Before;

public abstract class TestDoubleLineStyleGUI extends TestShapePropGUI<ShapeDoubleBorderCustomiser> {
	protected CheckBox dbleBoundCB;
	protected ColorPicker dbleBoundColB;
	protected Spinner<Double> dbleSepField;

	protected final GUIVoidCommand pickDbleColour = () -> pickColour(dbleBoundColB);
	protected final GUIVoidCommand incrementDbleSep = () -> incrementSpinner(dbleSepField);
	protected final GUIVoidCommand selectdbleLine = () -> clickOn(dbleBoundCB);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/DoubleLineStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		dbleBoundCB = find("#dbleBoundCB");
		dbleBoundColB = find("#dbleBoundColB");
		dbleSepField = find("#dbleSepField");
		ins = injector.getInstance(ShapeDoubleBorderCustomiser.class);
		ins.setActivated(true);
	}
}
