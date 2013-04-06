package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.ItemSelectable;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LabelComboBox;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument customises the arrows of shapes or of the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/05/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArrowCustomiser extends ShapePropertyCustomiser {
	/** Allows to change the style of the left-end of the shape. */
	protected LabelComboBox arrowLeftCB;

	/** Allows to change the style of the right-end of the shape. */
	protected LabelComboBox arrowRightCB;

	/** The field to set the dot size num parameter of arrows. */
	protected MSpinner dotSizeNum;

	/** The field to set the dot size dim parameter of arrows. */
	protected MSpinner dotSizeDim;

	/** The field to set the bracket num parameter of arrows. */
	protected MSpinner bracketNum;

	/** The field to set the rounded bracket num parameter of arrows. */
	protected MSpinner rbracketNum;

	/** The field to set the t bar size num parameter of arrows. */
	protected MSpinner tbarsizeNum;

	/** The field to set the t bar size dim parameter of arrows. */
	protected MSpinner tbarsizeDim;

	/** The field to set the arrows size dim parameter of arrows. */
	protected MSpinner arrowSizeDim;

	/** The field to set the arrow size num parameter of arrows. */
	protected MSpinner arrowSizeNum;

	/** The field to set the arrow length parameter of arrows. */
	protected MSpinner arrowLength;

	/** The field to set the arrow inset parameter of arrows. */
	protected MSpinner arrowInset;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeArrowCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
     	arrowLeftCB = createLeftArrowStyleList();
     	arrowLeftCB.setPreferredSize(new Dimension(80,30));
     	arrowLeftCB.setMaximumSize(new Dimension(80,30));

     	arrowRightCB = createRightArrowStyleList();
     	arrowRightCB.setPreferredSize(new Dimension(80,30));
     	arrowRightCB.setMaximumSize(new Dimension(80,30));

     	dotSizeDim = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0., 1000., 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.24")));
     	dotSizeDim.setEditor(new JSpinner.NumberEditor(dotSizeDim, "0.00"));//$NON-NLS-1$

     	arrowSizeDim = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0., 1000., 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.24")));
     	arrowSizeDim.setEditor(new JSpinner.NumberEditor(arrowSizeDim, "0.00"));//$NON-NLS-1$

     	tbarsizeDim = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0., 1000., 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.24")));
     	tbarsizeDim.setEditor(new JSpinner.NumberEditor(tbarsizeDim, "0.00"));//$NON-NLS-1$

     	dotSizeNum = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0.1, 100., 0.1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.20")));
     	dotSizeNum.setEditor(new JSpinner.NumberEditor(dotSizeNum, "0.00"));//$NON-NLS-1$

     	tbarsizeNum = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0.1, 100., 0.1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.21")));
     	tbarsizeNum.setEditor(new JSpinner.NumberEditor(tbarsizeNum, "0.00"));//$NON-NLS-1$

     	bracketNum = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0.1, 100., 0.01), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.22")));
     	bracketNum.setEditor(new JSpinner.NumberEditor(bracketNum, "0.00"));//$NON-NLS-1$

     	rbracketNum = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0.1, 100., 0.01), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.23")));
     	rbracketNum.setEditor(new JSpinner.NumberEditor(rbracketNum, "0.00"));//$NON-NLS-1$

     	arrowSizeNum = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0.1, 100., 0.01), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.28")));
     	arrowSizeNum.setEditor(new JSpinner.NumberEditor(arrowSizeNum, "0.00"));//$NON-NLS-1$

     	arrowLength = new MSpinner(new MSpinner.MSpinnerNumberModel(10., 0., 100., 0.01), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.26")));
     	arrowLength.setEditor(new JSpinner.NumberEditor(arrowLength, "0.00"));//$NON-NLS-1$

     	arrowInset = new MSpinner(new MSpinner.MSpinnerNumberModel(0., 0., 100., 0.01), new JLabel(LangTool.INSTANCE.getStringDialogFrame("AbstractParametersFrame.27")));
     	arrowInset.setEditor(new JSpinner.NumberEditor(arrowInset, "0.00"));//$NON-NLS-1$
	}


	/**
	 * Creates a list of the different styles of arrowhead (right).
	 * @return The created list.
	 */
	public static LabelComboBox createRightArrowStyleList() {
		final LabelComboBox lineArrowRChoice = new LabelComboBox();

		lineArrowRChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(ArrowStyle.NONE.name());
		label.setIcon(LResources.ARROW_STYLE_NONE_R_ICON);
		lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.BAR_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.BAR_END.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_END.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_END.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_RR_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_R_ICON);
     	lineArrowRChoice.addItem(label);
     	label = new JLabel(ArrowStyle.ROUND_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_R_ICON);
     	lineArrowRChoice.addItem(label);
     	lineArrowRChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowRChoice.setSize(new Dimension(75, 30));
     	lineArrowRChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowRChoice.setMinimumSize(new Dimension(75, 30));

     	return lineArrowRChoice;
	}



	/**
	 * Creates a list of the different styles of arrowhead (left).
	 * @return The created list.
	 */
	public static LabelComboBox createLeftArrowStyleList() {
		final LabelComboBox lineArrowLChoice = new LabelComboBox();

		lineArrowLChoice.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(ArrowStyle.NONE.name());
		label.setIcon(LResources.ARROW_STYLE_NONE_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.BAR_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.BAR_END.name());
     	label.setIcon(LResources.ARROW_STYLE_BAR_END_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_END.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.CIRCLE_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_CIRCLE_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_END.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_END_L_ICON);
     	lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.DISK_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_DISK_IN_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_ARROW_L_ICON);
     	lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_ROUND_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_ARC_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_SQUARE_BRACKET.name());
     	label.setIcon(LResources.ARROW_STYLE_BRACK_LR_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.LEFT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
		label = new JLabel(ArrowStyle.RIGHT_DBLE_ARROW.name());
     	label.setIcon(LResources.ARROW_STYLE_R_DBLE_ARROW_L_ICON);
		lineArrowLChoice.addItem(label);
     	label = new JLabel(ArrowStyle.ROUND_IN.name());
     	label.setIcon(LResources.ARROW_STYLE_ROUND_IN_L_ICON);
     	lineArrowLChoice.addItem(label);
     	lineArrowLChoice.setPreferredSize(new Dimension(75, 30));
     	lineArrowLChoice.setSize(new Dimension(75, 30));
     	lineArrowLChoice.setMaximumSize(new Dimension(75, 30));
     	lineArrowLChoice.setMinimumSize(new Dimension(75, 30));

		return lineArrowLChoice;
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(arrowLeftCB, visible);
		composer.setWidgetVisible(arrowRightCB, visible);
		composer.setWidgetVisible(arrowInset, visible);
		composer.setWidgetVisible(arrowLength, visible);
		composer.setWidgetVisible(arrowSizeDim, visible);
		composer.setWidgetVisible(arrowSizeNum, visible);
		composer.setWidgetVisible(dotSizeNum, visible);
		composer.setWidgetVisible(dotSizeDim, visible);
		composer.setWidgetVisible(tbarsizeDim, visible);
		composer.setWidgetVisible(tbarsizeNum, visible);
		composer.setWidgetVisible(bracketNum, visible);
		composer.setWidgetVisible(rbracketNum, visible);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new List2PencilArrowStyle(this));
			addLink(new List2ShapeArrowStyle(this));
			addLink(new Spinner2SelectionArrowParam(this));
			addLink(new Spinner2PencilArrowParam(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}



	@Override
	protected void update(final IShape shape) {
		if(shape!=null && shape.isArrowable()) {
			final IArrow arr1 = shape.getArrowAt(0);
			final IArrow arr2 = shape.getArrowAt(-1);
			final ArrowStyle arrStyle1 = arr1.getArrowStyle();
			final ArrowStyle arrStyle2 = arr2.getArrowStyle();

			//TODO this code suppose that if arrowable, there are 2 arrows.
			arrowLeftCB.setSelectedItemSafely(arrStyle1.name());
			arrowRightCB.setSelectedItemSafely(arrStyle2.name());

			final boolean isArrow = arrStyle1.isArrow() || arrStyle2.isArrow();
			final boolean isDot = arrStyle1.isCircleDisk() || arrStyle2.isCircleDisk();
			final boolean isBar = arrStyle1.isBar() || arrStyle2.isBar();
			final boolean isSBracket = arrStyle1.isSquareBracket() || arrStyle2.isSquareBracket();
			final boolean isRBracket = arrStyle1.isRoundBracket() || arrStyle2.isRoundBracket();

			// Updating the visibility of the widgets.
			composer.setWidgetVisible(arrowInset, isArrow);
			composer.setWidgetVisible(arrowLength, isArrow);
			composer.setWidgetVisible(arrowSizeDim, isArrow);
			composer.setWidgetVisible(arrowSizeNum, isArrow);
			composer.setWidgetVisible(dotSizeNum, isDot);
			composer.setWidgetVisible(dotSizeDim, isDot);
			composer.setWidgetVisible(tbarsizeDim, isBar || isSBracket || isRBracket);
			composer.setWidgetVisible(tbarsizeNum, isBar || isSBracket || isRBracket);
			composer.setWidgetVisible(bracketNum, isSBracket);
			composer.setWidgetVisible(rbracketNum, isRBracket);

			// Updating the value of the widgets.
			if(isArrow) {
				arrowInset.setValueSafely(arr1.getArrowInset());
				arrowLength.setValueSafely(arr1.getArrowLength());
				arrowSizeDim.setValueSafely(arr1.getArrowSizeDim());
				arrowSizeNum.setValueSafely(arr1.getArrowSizeNum());
			}

			if(isDot) {
				dotSizeNum.setValueSafely(arr1.getDotSizeNum());
				dotSizeDim.setValueSafely(arr1.getDotSizeDim());
			}

			if(isBar || isSBracket || isRBracket) {
				tbarsizeDim.setValueSafely(arr1.getTBarSizeDim());
				tbarsizeNum.setValueSafely(arr1.getTBarSizeNum());
			}

			if(isSBracket)
				bracketNum.setValueSafely(arr1.getBracketNum());

			if(isRBracket)
				rbracketNum.setValueSafely(arr1.getRBracketNum());
		}
		else setActivated(false);
	}


	/**
	 * @return The left arrow style combo box.
	 * @since 3.0
	 */
	public MComboBox<JLabel> getArrowLeftCB() {
		return arrowLeftCB;
	}

	/**
	 * @return The right arrow style combo box.
	 * @since 3.0
	 */
	public MComboBox<JLabel> getArrowRightCB() {
		return arrowRightCB;
	}


	/**
	 * @return the dotSizeNum.
	 * @since 3.0
	 */
	public MSpinner getDotSizeNum() {
		return dotSizeNum;
	}


	/**
	 * @return the dotSizeDim.
	 * @since 3.0
	 */
	public MSpinner getDotSizeDim() {
		return dotSizeDim;
	}


	/**
	 * @return the bracketNum.
	 * @since 3.0
	 */
	public MSpinner getBracketNum() {
		return bracketNum;
	}


	/**
	 * @return the rbracketNum.
	 * @since 3.0
	 */
	public MSpinner getRbracketNum() {
		return rbracketNum;
	}


	/**
	 * @return the tbarsizeNum.
	 * @since 3.0
	 */
	public MSpinner getTbarsizeNum() {
		return tbarsizeNum;
	}


	/**
	 * @return the tbarsizeDim.
	 * @since 3.0
	 */
	public MSpinner getTbarsizeDim() {
		return tbarsizeDim;
	}


	/**
	 * @return the arrowSizeDim.
	 * @since 3.0
	 */
	public MSpinner getArrowSizeDim() {
		return arrowSizeDim;
	}


	/**
	 * @return the arrowSizeNum.
	 * @since 3.0
	 */
	public MSpinner getArrowSizeNum() {
		return arrowSizeNum;
	}


	/**
	 * @return the arrowLength.
	 * @since 3.0
	 */
	public MSpinner getArrowLength() {
		return arrowLength;
	}


	/**
	 * @return the arrowInset.
	 * @since 3.0
	 */
	public MSpinner getArrowInset() {
		return arrowInset;
	}
}


abstract class Spinner2ArrowParam<A  extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeArrowCustomiser> {
	protected Spinner2ArrowParam(final ShapeArrowCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(ins, clazzAction);
	}

	@Override
	public boolean isConditionRespected() {
		final Object obj = interaction.getSpinner();
		return obj==instrument.arrowInset || obj==instrument.arrowLength || obj==instrument.arrowSizeDim || obj==instrument.arrowSizeNum ||
				obj==instrument.bracketNum || obj==instrument.dotSizeDim || obj==instrument.dotSizeNum || obj==instrument.rbracketNum ||
				obj==instrument.tbarsizeDim || obj==instrument.tbarsizeNum;
	}

	@Override
	public void initAction() {
		final Object obj = interaction.getSpinner();
		ShapeProperties prop;

		if(obj==instrument.arrowInset) prop = ShapeProperties.ARROW_INSET;
		else if(obj==instrument.arrowLength) prop = ShapeProperties.ARROW_LENGTH;
		else if(obj==instrument.arrowSizeDim) prop = ShapeProperties.ARROW_SIZE_DIM;
		else if(obj==instrument.arrowSizeNum) prop = ShapeProperties.ARROW_SIZE_NUM;
		else if(obj==instrument.bracketNum) prop = ShapeProperties.ARROW_BRACKET_NUM;
		else if(obj==instrument.dotSizeDim) prop = ShapeProperties.ARROW_DOT_SIZE_DIM;
		else if(obj==instrument.dotSizeNum) prop = ShapeProperties.ARROW_DOT_SIZE_NUM;
		else if(obj==instrument.rbracketNum) prop = ShapeProperties.ARROW_R_BRACKET_NUM;
		else if(obj==instrument.tbarsizeDim) prop = ShapeProperties.ARROW_T_BAR_SIZE_DIM;
		else prop = ShapeProperties.ARROW_T_BAR_SIZE_NUM;

		action.setProperty(prop);
	}
}


/** This link maps spinners to a ModifyShapeProperty action. */
class Spinner2PencilArrowParam extends Spinner2ArrowParam<ModifyPencilParameter> {
	protected Spinner2PencilArrowParam(final ShapeArrowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}


	@Override
	public void initAction() {
		super.initAction();
		action.setPencil(instrument.pencil);
	}
}


/** This link maps spinners to a ModifyShapeProperty action. */
class Spinner2SelectionArrowParam extends Spinner2ArrowParam<ModifyShapeProperty> {
	protected Spinner2SelectionArrowParam(final ShapeArrowCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}


	@Override
	public void initAction() {
		super.initAction();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}
}



/**
 * This link maps a list to a ModifyPencil action.
 */
class List2PencilArrowStyle extends ListForCustomiser<ModifyPencilParameter, ShapeArrowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected List2PencilArrowStyle(final ShapeArrowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		if(getInteraction().getList()==instrument.arrowLeftCB)
			action.setProperty(ShapeProperties.ARROW1_STYLE);
		else
			action.setProperty(ShapeProperties.ARROW2_STYLE);

		action.setPencil(instrument.pencil);
		action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) && instrument.pencil.isActivated();
	}
}


/**
 * This link maps a list to a ModifyShape action.
 */
class List2ShapeArrowStyle extends ListForCustomiser<ModifyShapeProperty, ShapeArrowCustomiser> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected List2ShapeArrowStyle(final ShapeArrowCustomiser instrument) throws InstantiationException, IllegalAccessException {
		super(instrument, ModifyShapeProperty.class);
	}


	@Override
	public void initAction() {
		if(getInteraction().getList()==instrument.arrowLeftCB)
			action.setProperty(ShapeProperties.ARROW1_STYLE);
		else
			action.setProperty(ShapeProperties.ARROW2_STYLE);

		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
	}

	@Override
	public boolean isConditionRespected() {
		final ItemSelectable is	= interaction.getList();
		return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) && instrument.hand.isActivated();
	}
}

