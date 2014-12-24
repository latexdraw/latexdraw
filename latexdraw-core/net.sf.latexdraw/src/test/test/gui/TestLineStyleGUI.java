package test.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;

import org.junit.Before;

public abstract class TestLineStyleGUI extends TestShapePropGUI<ShapeBorderCustomiser> {
	protected Spinner<Double> thicknessField;
	protected ColorPicker lineColButton;
	protected ComboBox<IShape.LineStyle> lineCB;
	protected ComboBox<IShape.BorderPos> bordersPosCB;
	protected Spinner<Double> frameArcField;
	protected CheckBox showPoints;
	
	final protected GUIVoidCommand pickLineCol = () -> pickColour(lineColButton);
	final protected GUIVoidCommand checkShowPts = () -> clickOn(showPoints);
	final protected GUIVoidCommand selectLineStyle = () -> selectComboBoxItem(lineCB);
	final protected GUIVoidCommand selectBorderPos = () -> selectComboBoxItem(bordersPosCB);
	final protected GUIVoidCommand incrementThickness = () -> incrementSpinner(thicknessField);
	final protected GUIVoidCommand incrementFrameArc = () -> incrementSpinner(frameArcField);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/LineStyle.fxml";
	}
	 
	@Override
	@Before
	public void setUp() {
		super.setUp();
		thicknessField 	= find("#thicknessField");
		lineColButton 	= find("#lineColButton");
		lineCB 			= find("#lineCB");
		bordersPosCB 	= find("#bordersPosCB");
		frameArcField 	= find("#frameArcField");
		showPoints 		= find("#showPoints");
		ins				= (ShapeBorderCustomiser)guiceFactory.call(ShapeBorderCustomiser.class);
		ins.setActivated(true);
	}
}
