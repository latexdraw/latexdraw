package test.gui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;

import org.junit.Before;

public abstract class TestArrowStyleGUI extends TestShapePropGUI<ShapeArrowCustomiser> {
	protected ComboBox<ArrowStyle> arrowLeftCB;
	protected ComboBox<ArrowStyle> arrowRightCB;
	protected Spinner<Double> dotSizeNum;
	protected Spinner<Double> dotSizeDim;
	protected Spinner<Double> bracketNum;
	protected Spinner<Double> rbracketNum;
	protected Spinner<Double> tbarsizeNum;
	protected Spinner<Double> tbarsizeDim;
	protected Spinner<Double> arrowSizeDim;
	protected Spinner<Double> arrowSizeNum;
	protected Spinner<Double> arrowLength;
	protected Spinner<Double> arrowInset;
	protected AnchorPane dotPane;
	protected AnchorPane arrowPane;
	protected AnchorPane barPane;
	protected AnchorPane bracketPane;
	protected AnchorPane rbracketPane;
	protected TitledPane mainPane;

	final protected GUICommand<ArrowStyle> selectArrowLeftCB = (style) -> selectGivenComboBoxItem(arrowLeftCB, style);
	final protected GUICommand<ArrowStyle> selectArrowRightCB = (style) -> selectGivenComboBoxItem(arrowRightCB, style);
	final protected GUIVoidCommand incrementdotSizeNum = () -> incrementSpinner(dotSizeNum);
	final protected GUIVoidCommand incrementdotSizeDim = () -> incrementSpinner(dotSizeDim);
	final protected GUIVoidCommand incrementbracketNum = () -> incrementSpinner(bracketNum);
	final protected GUIVoidCommand incrementrbracketNum = () -> incrementSpinner(rbracketNum);
	final protected GUIVoidCommand incrementtbarsizeNum = () -> incrementSpinner(tbarsizeNum);
	final protected GUIVoidCommand incrementtbarsizeDim = () -> incrementSpinner(tbarsizeDim);
	final protected GUIVoidCommand incrementarrowSizeDim = () -> incrementSpinner(arrowSizeDim);
	final protected GUIVoidCommand incrementarrowSizeNum = () -> incrementSpinner(arrowSizeNum);
	final protected GUIVoidCommand incrementarrowLength = () -> incrementSpinner(arrowLength);
	final protected GUIVoidCommand incrementarrowInset = () -> incrementSpinner(arrowInset);
	final protected GUIVoidCommand selectArrowStyleArrow = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ARROW);
	final protected GUIVoidCommand selectArrowStyleRBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ROUND_BRACKET);
	final protected GUIVoidCommand selectArrowStyleSBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_SQUARE_BRACKET);
	final protected GUIVoidCommand selectArrowStyleDot = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.DISK_END);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/ArrowStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainPane = find("#mainPane");
		arrowLeftCB = find("#arrowLeftCB");
		arrowRightCB = find("#arrowRightCB");
		dotSizeNum = find("#dotSizeNum");
		dotSizeDim = find("#dotSizeDim");
		bracketNum = find("#bracketNum");
		rbracketNum = find("#rbracketNum");
		tbarsizeNum = find("#tbarsizeNum");
		tbarsizeDim = find("#tbarsizeDim");
		arrowSizeDim = find("#arrowSizeDim");
		arrowSizeNum = find("#arrowSizeNum");
		arrowLength = find("#arrowLength");
		arrowInset = find("#arrowInset");
		rbracketPane = find("#rbracketPane");
		dotPane = find("#dotPane");
		arrowPane = find("#arrowPane");
		barPane = find("#barPane");
		bracketPane = find("#bracketPane");
		ins = (ShapeArrowCustomiser)guiceFactory.call(ShapeArrowCustomiser.class);
		ins.setActivated(true);
	}
}
