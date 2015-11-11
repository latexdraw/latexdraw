package test.gui;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;

import org.junit.Before;

public abstract class TestFillingStyleGUI extends TestShapePropGUI<ShapeFillingCustomiser> {
	protected ColorPicker fillColButton;
	protected ColorPicker hatchColButton;
	protected ColorPicker gradStartColButton;
	protected ColorPicker gradEndColButton;
	protected ComboBox<FillingStyle> fillStyleCB;
	protected Spinner<Double> gradMidPtField;
	protected Spinner<Double> gradAngleField;
	protected Spinner<Double> hatchSepField;
	protected Spinner<Double> hatchAngleField;
	protected Spinner<Double> hatchWidthField;
	protected TitledPane mainPane;

	final protected GUIVoidCommand pickfillCol = () -> pickColour(fillColButton);
	final protected GUIVoidCommand pickhatchCol = () -> pickColour(hatchColButton);
	final protected GUIVoidCommand pickgradStartCol = () -> pickColour(gradStartColButton);
	final protected GUIVoidCommand pickgradEndCol = () -> pickColour(gradEndColButton);
	final protected GUIVoidCommand incrementgradMidPt = () -> incrementSpinner(gradMidPtField);
	final protected GUIVoidCommand incrementgradAngle = () -> incrementSpinner(gradAngleField);
	final protected GUIVoidCommand incrementhatchSep = () -> incrementSpinner(hatchSepField);
	final protected GUIVoidCommand incrementhatchAngle = () -> incrementSpinner(hatchAngleField);
	final protected GUIVoidCommand incrementhatchWidth = () -> incrementSpinner(hatchWidthField);
	final protected GUIVoidCommand selectPlainStyle = () -> selectGivenComboBoxItem(fillStyleCB, IShape.FillingStyle.PLAIN);
	final protected GUIVoidCommand selectHatchingsStyle = () -> selectGivenComboBoxItem(fillStyleCB, IShape.FillingStyle.HLINES_PLAIN);
	final protected GUIVoidCommand selectGradStyle = () -> selectGivenComboBoxItem(fillStyleCB, IShape.FillingStyle.GRAD);
	final protected GUICommand<IShape.FillingStyle> selectStyle = (IShape.FillingStyle style) -> selectGivenComboBoxItem(fillStyleCB, style);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "view/jfx/ui/FillingStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		fillColButton = find("#fillColButton");
		hatchColButton = find("#hatchColButton");
		gradStartColButton = find("#gradStartColButton");
		gradEndColButton = find("#gradEndColButton");
		fillStyleCB = find("#fillStyleCB");
		gradMidPtField = find("#gradMidPtField");
		gradAngleField = find("#gradAngleField");
		hatchSepField = find("#hatchSepField");
		hatchAngleField = find("#hatchAngleField");
		hatchWidthField = find("#hatchWidthField");
		ins = (ShapeFillingCustomiser)guiceFactory.call(ShapeFillingCustomiser.class);
		ins.setActivated(true);
	}
}
