package net.sf.latexdraw.instruments;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
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

	protected final GUICommand<ArrowStyle> selectArrowLeftCB = style -> selectGivenComboBoxItem(arrowLeftCB, style);
	protected final GUICommand<ArrowStyle> selectArrowRightCB = style -> selectGivenComboBoxItem(arrowRightCB, style);
	protected final GUIVoidCommand incrementdotSizeNum = () -> incrementSpinner(dotSizeNum);
	protected final GUIVoidCommand incrementdotSizeDim = () -> incrementSpinner(dotSizeDim);
	protected final GUIVoidCommand incrementbracketNum = () -> incrementSpinner(bracketNum);
	protected final GUIVoidCommand incrementrbracketNum = () -> incrementSpinner(rbracketNum);
	protected final GUIVoidCommand incrementtbarsizeNum = () -> incrementSpinner(tbarsizeNum);
	protected final GUIVoidCommand incrementtbarsizeDim = () -> incrementSpinner(tbarsizeDim);
	protected final GUIVoidCommand incrementarrowSizeDim = () -> incrementSpinner(arrowSizeDim);
	protected final GUIVoidCommand incrementarrowSizeNum = () -> incrementSpinner(arrowSizeNum);
	protected final GUIVoidCommand incrementarrowLength = () -> incrementSpinner(arrowLength);
	protected final GUIVoidCommand incrementarrowInset = () -> incrementSpinner(arrowInset);
	protected final GUIVoidCommand selectArrowStyleArrow = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ARROW);
	protected final GUIVoidCommand selectArrowStyleRBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ROUND_BRACKET);
	protected final GUIVoidCommand selectArrowStyleSBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_SQUARE_BRACKET);
	protected final GUIVoidCommand selectArrowStyleDot = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.DISK_END);

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/ArrowStyle.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
		ins = (ShapeArrowCustomiser) injectorFactory.call(ShapeArrowCustomiser.class);
		ins.setActivated(true);
	}
}
