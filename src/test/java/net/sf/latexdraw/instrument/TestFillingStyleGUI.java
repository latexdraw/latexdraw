package net.sf.latexdraw.instrument;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.model.api.shape.FillingStyle;
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

	protected final CmdVoid pickfillCol = () -> pickColour(fillColButton);
	protected final CmdVoid pickhatchCol = () -> pickColour(hatchColButton);
	protected final CmdVoid pickgradStartCol = () -> pickColour(gradStartColButton);
	protected final CmdVoid pickgradEndCol = () -> pickColour(gradEndColButton);
	protected final CmdVoid incrementgradMidPt = () -> incrementSpinner(gradMidPtField);
	protected final CmdVoid incrementgradAngle = () -> incrementSpinner(gradAngleField);
	protected final CmdVoid incrementhatchSep = () -> incrementSpinner(hatchSepField);
	protected final CmdVoid incrementhatchAngle = () -> incrementSpinner(hatchAngleField);
	protected final CmdVoid incrementhatchWidth = () -> incrementSpinner(hatchWidthField);
	protected final CmdVoid selectPlainStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.PLAIN);
	protected final CmdVoid selectHatchingsStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.HLINES_PLAIN);
	protected final CmdVoid selectGradStyle = () -> selectGivenComboBoxItem(fillStyleCB, FillingStyle.GRAD);
	protected final Cmd<FillingStyle> selectStyle = (FillingStyle style) -> selectGivenComboBoxItem(fillStyleCB, style);

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
