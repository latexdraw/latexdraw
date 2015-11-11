package test.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.instruments.ShapeDoubleBorderCustomiser;

import org.junit.Before;

public abstract class TestDoubleLineStyleGUI extends TestShapePropGUI<ShapeDoubleBorderCustomiser> {
	protected CheckBox dbleBoundCB;
	protected ColorPicker dbleBoundColB;
	protected Spinner<Double> dbleSepField;
	protected TitledPane mainPane;

	final protected GUIVoidCommand pickDbleColour = () -> pickColour(dbleBoundColB);
	final protected GUIVoidCommand incrementDbleSep = () -> incrementSpinner(dbleSepField);
	final protected GUIVoidCommand selectdbleLine = () -> clickOn(dbleBoundCB);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "view/jfx/ui/DoubleLineStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		dbleBoundCB = find("#dbleBoundCB");
		dbleBoundColB = find("#dbleBoundColB");
		dbleSepField = find("#dbleSepField");
		ins = (ShapeDoubleBorderCustomiser)guiceFactory.call(ShapeDoubleBorderCustomiser.class);
		ins.setActivated(true);
	}
}
