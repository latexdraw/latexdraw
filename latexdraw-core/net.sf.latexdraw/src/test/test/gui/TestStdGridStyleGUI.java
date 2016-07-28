package test.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;

import org.junit.Before;

public abstract class TestStdGridStyleGUI extends TestShapePropGUI<ShapeStdGridCustomiser> {
	protected Spinner<Double> xStartS;
	protected Spinner<Double> yStartS;
	protected Spinner<Double> xEndS;
	protected Spinner<Double> yEndS;
	protected Spinner<Integer> labelsSizeS;
	protected Spinner<Double> xOriginS;
	protected Spinner<Double> yOriginS;
	protected TitledPane mainPane;

	final protected GUIVoidCommand decrementxStartS = () -> decrementSpinner(xStartS);
	final protected GUIVoidCommand decrementyStartS = () -> decrementSpinner(yStartS);
	final protected GUIVoidCommand incrementxEndS = () -> incrementSpinner(xEndS);
	final protected GUIVoidCommand incrementyEndS = () -> incrementSpinner(yEndS);
	final protected GUIVoidCommand incrementlabelsSizeS = () -> incrementSpinner(labelsSizeS);
	final protected GUIVoidCommand incrementxOriginS = () -> incrementSpinner(xOriginS);
	final protected GUIVoidCommand incrementyOriginS = () -> incrementSpinner(yOriginS);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/StdGridStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		xStartS = find("#xStartS");
		yStartS = find("#yStartS");
		xEndS = find("#xEndS");
		yEndS = find("#yEndS");
		labelsSizeS = find("#labelsSizeS");
		xOriginS = find("#xOriginS");
		yOriginS = find("#yOriginS");
		mainPane = find("#mainPane");
		ins = (ShapeStdGridCustomiser)guiceFactory.call(ShapeStdGridCustomiser.class);
		ins.setActivated(true);
	}
}
