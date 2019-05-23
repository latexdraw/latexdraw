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

	protected final CmdVoid selectAxeStyle = () -> selectNextComboBoxItem(shapeAxes);
	protected final CmdVoid selectTicksStyle = () -> selectNextComboBoxItem(shapeTicks);
	protected final CmdVoid selectPlotTicks = () -> selectNextComboBoxItem(showTicks);
	protected final CmdVoid selectPlotLabel = () -> selectNextComboBoxItem(showLabels);
	protected final CmdVoid incrementLabelX = () -> incrementSpinner(incrLabelX);
	protected final CmdVoid incrementLabelY = () -> incrementSpinner(incrLabelY);
	protected final CmdVoid incrementTicksSize = () -> incrementSpinner(ticksSize);
	protected final CmdVoid incrementDistLabelX = () -> incrementSpinner(distLabelsX);
	protected final CmdVoid incrementDistLabelY = () -> incrementSpinner(distLabelsY);
	protected final CmdVoid selectShowOrigin = () -> clickOn(showOrigin);

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
