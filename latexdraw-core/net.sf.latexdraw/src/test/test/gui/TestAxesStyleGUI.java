package test.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;

import org.junit.Before;

public abstract class TestAxesStyleGUI extends TestShapePropGUI<ShapeAxesCustomiser> {
	protected ComboBox<AxesStyle> shapeAxes;
	protected ComboBox<TicksStyle> shapeTicks;
	protected ComboBox<PlottingStyle> showTicks;
	protected ComboBox<PlottingStyle> showLabels;
	protected Spinner<Double> incrLabelX;
	protected Spinner<Double> incrLabelY;
	protected Spinner<Double> distLabelsX;
	protected Spinner<Double> distLabelsY;
	protected CheckBox showOrigin;
	protected AnchorPane mainPane;

	final protected GUIVoidCommand selectAxeStyle = () -> selectNextComboBoxItem(shapeAxes);
	final protected GUIVoidCommand selectTicksStyle = () -> selectNextComboBoxItem(shapeTicks);
	final protected GUIVoidCommand selectPlotTicks = () -> selectNextComboBoxItem(showTicks);
	final protected GUIVoidCommand selectPlotLabel = () -> selectNextComboBoxItem(showLabels);
	final protected GUIVoidCommand incrementLabelX = () -> incrementSpinner(incrLabelX);
	final protected GUIVoidCommand incrementLabelY = () -> incrementSpinner(incrLabelY);
	final protected GUIVoidCommand incrementDistLabelX = () -> incrementSpinner(distLabelsX);
	final protected GUIVoidCommand incrementDistLabelY = () -> incrementSpinner(distLabelsY);
	final protected GUIVoidCommand selectShowOrigin = () -> clickOn(showOrigin);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/AxeStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		shapeAxes = find("#shapeAxes");
		shapeTicks = find("#shapeTicks");
		showTicks = find("#showTicks");
		showLabels = find("#showLabels");
		incrLabelX = find("#incrLabelX");
		incrLabelY = find("#incrLabelY");
		distLabelsX = find("#distLabelsX");
		distLabelsY = find("#distLabelsY");
		showOrigin = find("#showOrigin");
		ins = (ShapeAxesCustomiser)guiceFactory.call(ShapeAxesCustomiser.class);
		ins.setActivated(true);
	}
}
