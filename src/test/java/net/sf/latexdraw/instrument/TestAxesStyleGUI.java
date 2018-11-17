package net.sf.latexdraw.instrument;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
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
	protected Spinner<Double> ticksSize;
	protected CheckBox showOrigin;
	protected Pane mainPane;

	protected final GUIVoidCommand selectAxeStyle = () -> selectNextComboBoxItem(shapeAxes);
	protected final GUIVoidCommand selectTicksStyle = () -> selectNextComboBoxItem(shapeTicks);
	protected final GUIVoidCommand selectPlotTicks = () -> selectNextComboBoxItem(showTicks);
	protected final GUIVoidCommand selectPlotLabel = () -> selectNextComboBoxItem(showLabels);
	protected final GUIVoidCommand incrementLabelX = () -> incrementSpinner(incrLabelX);
	protected final GUIVoidCommand incrementLabelY = () -> incrementSpinner(incrLabelY);
	protected final GUIVoidCommand incrementTicksSize = () -> incrementSpinner(ticksSize);
	protected final GUIVoidCommand incrementDistLabelX = () -> incrementSpinner(distLabelsX);
	protected final GUIVoidCommand incrementDistLabelY = () -> incrementSpinner(distLabelsY);
	protected final GUIVoidCommand selectShowOrigin = () -> clickOn(showOrigin);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/AxeStyle.fxml";
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
		ticksSize = find("#ticksSize");
		distLabelsX = find("#distLabelsX");
		distLabelsY = find("#distLabelsY");
		showOrigin = find("#showOrigin");
		ins = injector.getInstance(ShapeAxesCustomiser.class);
		ins.setActivated(true);
	}
}
