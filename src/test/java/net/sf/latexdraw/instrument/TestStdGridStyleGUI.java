package net.sf.latexdraw.instrument;

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

	protected final CmdVoid decrementxStartS = () -> decrementSpinner(xStartS);
	protected final CmdVoid decrementyStartS = () -> decrementSpinner(yStartS);
	protected final CmdVoid incrementxEndS = () -> incrementSpinner(xEndS);
	protected final CmdVoid incrementyEndS = () -> incrementSpinner(yEndS);
	protected final CmdVoid incrementlabelsSizeS = () -> incrementSpinner(labelsSizeS);
	protected final CmdVoid incrementxOriginS = () -> incrementSpinner(xOriginS);
	protected final CmdVoid incrementyOriginS = () -> incrementSpinner(yOriginS);
	protected final CmdVoid scrollxStartS = () -> scrollOnSpinner(xStartS, -2);
	protected final CmdVoid scrollyStartS = () -> scrollOnSpinner(yStartS, -2);
	protected final CmdVoid scrollxEndS = () -> scrollOnSpinner(xEndS, -2);
	protected final CmdVoid scrollyEndS = () -> scrollOnSpinner(yEndS, -2);
	protected final CmdVoid scrolllabelsSizeS = () -> scrollOnSpinner(labelsSizeS, -2);
	protected final CmdVoid scrollxOriginS = () -> scrollOnSpinner(xOriginS, -2);
	protected final CmdVoid scrollyOriginS = () -> scrollOnSpinner(yOriginS, -2);

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
