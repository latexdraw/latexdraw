package net.sf.latexdraw.instruments;

import javafx.scene.control.Spinner;
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
	protected final GUIVoidCommand scrollxStartS = () -> scrollOnSpinner(xStartS, -2);
	protected final GUIVoidCommand scrollyStartS = () -> scrollOnSpinner(yStartS, -2);
	protected final GUIVoidCommand scrollxEndS = () -> scrollOnSpinner(xEndS, -2);
	protected final GUIVoidCommand scrollyEndS = () -> scrollOnSpinner(yEndS, -2);
	protected final GUIVoidCommand scrolllabelsSizeS = () -> scrollOnSpinner(labelsSizeS, -2);
	protected final GUIVoidCommand scrollxOriginS = () -> scrollOnSpinner(xOriginS, -2);
	protected final GUIVoidCommand scrollyOriginS = () -> scrollOnSpinner(yOriginS, -2);

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
		ins = injector.getInstance(ShapeStdGridCustomiser.class);
		ins.setActivated(true);
	}
}
