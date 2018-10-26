package net.sf.latexdraw.instruments;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
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

	protected final GUIVoidCommand pickfillCol = () -> pickColour(fillColButton);
	protected final GUIVoidCommand pickhatchCol = () -> pickColour(hatchColButton);
	protected final GUIVoidCommand pickgradStartCol = () -> pickColour(gradStartColButton);
	protected final GUIVoidCommand pickgradEndCol = () -> pickColour(gradEndColButton);
	protected final GUIVoidCommand incrementgradMidPt = () -> incrementSpinner(gradMidPtField);
	protected final GUIVoidCommand incrementgradAngle = () -> incrementSpinner(gradAngleField);
	protected final GUIVoidCommand incrementhatchSep = () -> incrementSpinner(hatchSepField);
	protected final GUIVoidCommand incrementhatchAngle = () -> incrementSpinner(hatchAngleField);
	protected final GUIVoidCommand incrementhatchWidth = () -> incrementSpinner(hatchWidthField);
	protected final GUIVoidCommand selectPlainStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.PLAIN);
	protected final GUIVoidCommand selectHatchingsStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.HLINES_PLAIN);
	protected final GUIVoidCommand selectGradStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.GRAD);
	protected final GUICommand<FillingStyle> selectStyle = (FillingStyle style) -> selectGivenComboBoxItem(fillStyleCB, style);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/FillingStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
		ins = injector.getInstance(ShapeFillingCustomiser.class);
		ins.setActivated(true);
	}
}
