package test.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;

import org.junit.Before;

public abstract class TestFreeHandStyleGUI extends TestShapePropGUI<ShapeFreeHandCustomiser> {
	protected ComboBox<FreeHandStyle> freeHandType;
	protected Spinner<Integer> gapPoints;
	protected CheckBox open;
	protected TitledPane mainPane;

	protected final GUIVoidCommand selectLineStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.LINES);
	protected final GUIVoidCommand selectCurveStyle = () -> selectGivenComboBoxItem(freeHandType, FreeHandStyle.CURVES);
	protected final GUIVoidCommand incrementgapPoints = () -> incrementSpinner(gapPoints);
	protected final GUIVoidCommand selectOpen = () -> clickOn(open);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/FreehandStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		freeHandType = find("#freeHandType");
		gapPoints = find("#gapPoints");
		open = find("#open");
		mainPane = find("#mainPane");
		ins = (ShapeFreeHandCustomiser)guiceFactory.call(ShapeFreeHandCustomiser.class);
		ins.setActivated(true);
	}
}
