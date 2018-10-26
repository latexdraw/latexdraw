package net.sf.latexdraw.instruments;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import org.junit.Before;

public abstract class TestFreeHandStyleGUI extends TestShapePropGUI<ShapeFreeHandCustomiser> {
	protected ComboBox<FreeHandStyle> freeHandType;
	protected Spinner<Integer> gapPoints;

	protected final GUIVoidCommand selectLineStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.LINES);
	protected final GUIVoidCommand selectCurveStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.CURVES);
	protected final GUIVoidCommand incrementgapPoints = () -> incrementSpinner(gapPoints);

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
