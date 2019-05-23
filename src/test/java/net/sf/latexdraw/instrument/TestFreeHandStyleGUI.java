package net.sf.latexdraw.instrument;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import org.junit.Before;

public abstract class TestFreeHandStyleGUI extends TestShapePropGUI<ShapeFreeHandCustomiser> {
	protected ComboBox<FreeHandStyle> freeHandType;
	protected Spinner<Integer> gapPoints;

	protected final CmdVoid selectLineStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.LINES);
	protected final CmdVoid selectCurveStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.CURVES);
	protected final CmdVoid incrementgapPoints = () -> incrementSpinner(gapPoints);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/FreehandStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		freeHandType = find("#freeHandType");
		gapPoints = find("#gapPoints");
		ins = injector.getInstance(ShapeFreeHandCustomiser.class);
		ins.setActivated(true);
	}
}
