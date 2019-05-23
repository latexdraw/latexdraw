package net.sf.latexdraw.instrument;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
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

	protected final Cmd<ArrowStyle> selectArrowLeftCB = style -> selectGivenComboBoxItem(arrowLeftCB, style);
	protected final Cmd<ArrowStyle> selectArrowRightCB = style -> selectGivenComboBoxItem(arrowRightCB, style);
	protected final CmdVoid incrementdotSizeNum = () -> incrementSpinner(dotSizeNum);
	protected final CmdVoid incrementdotSizeDim = () -> incrementSpinner(dotSizeDim);
	protected final CmdVoid incrementbracketNum = () -> incrementSpinner(bracketNum);
	protected final CmdVoid incrementrbracketNum = () -> incrementSpinner(rbracketNum);
	protected final CmdVoid incrementtbarsizeNum = () -> incrementSpinner(tbarsizeNum);
	protected final CmdVoid incrementtbarsizeDim = () -> incrementSpinner(tbarsizeDim);
	protected final CmdVoid incrementarrowSizeDim = () -> incrementSpinner(arrowSizeDim);
	protected final CmdVoid incrementarrowSizeNum = () -> incrementSpinner(arrowSizeNum);
	protected final CmdVoid incrementarrowLength = () -> incrementSpinner(arrowLength);
	protected final CmdVoid incrementarrowInset = () -> incrementSpinner(arrowInset);
	protected final CmdVoid selectArrowStyleArrow = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ARROW);
	protected final CmdVoid selectArrowStyleRBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_ROUND_BRACKET);
	protected final CmdVoid selectArrowStyleSBrack = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.LEFT_SQUARE_BRACKET);
	protected final CmdVoid selectArrowStyleDot = () -> selectGivenComboBoxItem(arrowRightCB, ArrowStyle.DISK_END);

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
		ins = injector.getInstance(ShapeArrowCustomiser.class);
		ins.setActivated(true);
	}
}
