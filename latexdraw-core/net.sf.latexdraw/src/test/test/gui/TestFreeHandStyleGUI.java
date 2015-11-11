package test.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;

import org.junit.Before;

public abstract class TestFreeHandStyleGUI extends TestShapePropGUI<ShapeFreeHandCustomiser> {
	protected ComboBox<IFreeHandProp.FreeHandType> freeHandType;
	protected Spinner<Integer> gapPoints;
	protected CheckBox open;
	protected TitledPane mainPane;

	final protected GUIVoidCommand selectLineStyle = () -> selectGivenComboBoxItem(freeHandType, IFreeHandProp.FreeHandType.LINES);
	final protected GUIVoidCommand selectCurveStyle = () -> selectGivenComboBoxItem(freeHandType, IFreeHandProp.FreeHandType.CURVES);
	final protected GUIVoidCommand incrementgapPoints = () -> incrementSpinner(gapPoints);
	final protected GUIVoidCommand selectOpen = () -> clickOn(open);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "view/jfx/ui/FreehandStyle.fxml";
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
