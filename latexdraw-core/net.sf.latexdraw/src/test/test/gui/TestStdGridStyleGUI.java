package test.gui;

import javafx.scene.control.Spinner;
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

	protected final GUIVoidCommand decrementxStartS = () -> decrementSpinner(xStartS);
	protected final GUIVoidCommand decrementyStartS = () -> decrementSpinner(yStartS);
	protected final GUIVoidCommand incrementxEndS = () -> incrementSpinner(xEndS);
	protected final GUIVoidCommand incrementyEndS = () -> incrementSpinner(yEndS);
	protected final GUIVoidCommand incrementlabelsSizeS = () -> incrementSpinner(labelsSizeS);
	protected final GUIVoidCommand incrementxOriginS = () -> incrementSpinner(xOriginS);
	protected final GUIVoidCommand incrementyOriginS = () -> incrementSpinner(yOriginS);

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
		ins = (ShapeStdGridCustomiser)guiceFactory.call(ShapeStdGridCustomiser.class);
		ins.setActivated(true);
	}
}
