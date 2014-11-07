package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;

import javax.swing.SwingUtilities;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp.TextPosition;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;

import org.malai.javafx.instrument.JfxInstrument;
import org.malai.mapping.MappingRegistry;

/**
 * This instrument modifies texts.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 12/27/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeTextCustomiser extends JfxInstrument { //extends ShapePropertyCustomiser {
	/** The button that selects the bottom-left text position. */
	@FXML protected ToggleButton blButton;

	/** The button that selects the bottom text position. */
	@FXML protected ToggleButton bButton;

	/** The button that selects the bottom-right text position. */
	@FXML protected ToggleButton brButton;

	/** The button that selects the top-left text position. */
	@FXML protected ToggleButton tlButton;

	/** The button that selects the top text position. */
	@FXML protected ToggleButton tButton;

	/** The button that selects the top-right text position. */
	@FXML protected ToggleButton trButton;

	/** The button that selects the left text position. */
	@FXML protected ToggleButton lButton;

	/** The button that selects the right text position. */
	@FXML protected ToggleButton rButton;

	/** The button that selects the centre text position. */
	@FXML protected ToggleButton centreButton;

	/** This text field permits to add latex packages that will be used during compilation. */
	@FXML protected TextArea packagesField;

	/** The error log field. */
	@FXML protected TextArea logField;
	
	@FXML protected TitledPane mainPane;


	/**
	 * Creates the instrument.
	 */
	public ShapeTextCustomiser() {
		super();
	}


//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


//	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(ITextProp.class)) {
			final TextPosition tp = shape.getTextPosition();

			bButton.setSelected(tp==TextPosition.BOT);
			brButton.setSelected(tp==TextPosition.BOT_RIGHT);
			blButton.setSelected(tp==TextPosition.BOT_LEFT);
			tButton.setSelected(tp==TextPosition.TOP);
			trButton.setSelected(tp==TextPosition.TOP_RIGHT);
			tlButton.setSelected(tp==TextPosition.TOP_LEFT);
			centreButton.setSelected(tp==TextPosition.CENTER);
			lButton.setSelected(tp==TextPosition.LEFT);
			rButton.setSelected(tp==TextPosition.RIGHT);
			if(!packagesField.isFocused()) // Otherwise it means that this field is currently being edited and must not be updated.
				packagesField.setText(LaTeXGenerator.getPackages());

			// Updating the log field.
			SwingUtilities.invokeLater(() -> {
				shape.getShapes().stream().filter(sh -> sh instanceof IText).findFirst().ifPresent(txt -> {
					final int max = 10;
					final String msg = FlyweightThumbnail.inProgressMsg();
					String log = FlyweightThumbnail.getLog(MappingRegistry.REGISTRY.getTargetFromSource(txt, IViewText.class));
					int i = 0;

					while(i<max && msg.equals(log)) {
						try{ Thread.sleep(100);}
						catch(final InterruptedException e){ BadaboomCollector.INSTANCE.add(e); }
						log = FlyweightThumbnail.getLog(MappingRegistry.REGISTRY.getTargetFromSource(txt, IViewText.class));
						i++;
					}
					if(log==null) log = ""; //$NON-NLS-1$
					logField.setText(log);
				});
			});
		}
		else setActivated(false);
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new KeysTyped2ChangePackages(this));
//			addInteractor(new ButtonPressed2ChangeTextPosition(this));
//			addInteractor(new ButtonPressed2ChangePencil(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}
}



//class KeysTyped2ChangePackages extends InteractorImpl<ModifyLatexProperties, KeysTyped, ShapeTextCustomiser> {
//	protected KeysTyped2ChangePackages(final ShapeTextCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, ModifyLatexProperties.class, KeysTyped.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setProperty(LatexProperties.PACKAGES);
//	}
//
//	@Override
//	public void updateAction() {
//		action.setValue(instrument.getPackagesField().getText());
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getObject()==instrument.packagesField;
//	}
//}
//
//
//
///**
// * Links a button interaction to an action that modifies the pencil.
// */
//class ButtonPressed2ChangePencil extends ButtonPressedForCustomiser<ModifyPencilParameter, ShapeTextCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	ButtonPressed2ChangePencil(final ShapeTextCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		final AbstractButton ab = interaction.getButton();
//
//		action.setProperty(ShapeProperties.TEXT_POSITION);
//		action.setPencil(instrument.pencil);
//
//		if(instrument.blButton==ab) action.setValue(ITextProp.TextPosition.BOT_LEFT);
//		else if(instrument.brButton==ab) action.setValue(ITextProp.TextPosition.BOT_RIGHT);
//		else if(instrument.tButton==ab) action.setValue(ITextProp.TextPosition.TOP);
//		else if(instrument.bButton==ab) action.setValue(ITextProp.TextPosition.BOT);
//		else if(instrument.tlButton==ab) action.setValue(ITextProp.TextPosition.TOP_LEFT);
//		else if(instrument.centreButton==ab) action.setValue(ITextProp.TextPosition.CENTER);
//		else if(instrument.lButton==ab) action.setValue(ITextProp.TextPosition.LEFT);
//		else if(instrument.rButton==ab) action.setValue(ITextProp.TextPosition.RIGHT);
//		else action.setValue(ITextProp.TextPosition.TOP_RIGHT);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final AbstractButton ab = interaction.getButton();
//		return instrument.pencil.isActivated() && (instrument.tButton==ab || instrument.tlButton==ab || instrument.centreButton==ab ||
//				instrument.trButton==ab || instrument.bButton==ab || instrument.blButton==ab || instrument.brButton==ab ||
//				instrument.lButton==ab || instrument.rButton==ab);
//	}
//}
//
//
///**
// * Links a button interaction to an action that modifies the selected shapes.
// */
//class ButtonPressed2ChangeTextPosition extends ButtonPressedForCustomiser<ModifyShapeProperty, ShapeTextCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	ButtonPressed2ChangeTextPosition(final ShapeTextCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		final AbstractButton ab = interaction.getButton();
//
//		action.setGroup(instrument.hand.canvas().getDrawing().getSelection().duplicateDeep(false));
//		action.setProperty(ShapeProperties.TEXT_POSITION);
//
//		if(instrument.bButton==ab) action.setValue(ITextProp.TextPosition.BOT);
//		else if(instrument.blButton==ab) action.setValue(ITextProp.TextPosition.BOT_LEFT);
//		else if(instrument.brButton==ab) action.setValue(ITextProp.TextPosition.BOT_RIGHT);
//		else if(instrument.tButton==ab) action.setValue(ITextProp.TextPosition.TOP);
//		else if(instrument.tlButton==ab) action.setValue(ITextProp.TextPosition.TOP_LEFT);
//		else if(instrument.centreButton==ab) action.setValue(ITextProp.TextPosition.CENTER);
//		else if(instrument.lButton==ab) action.setValue(ITextProp.TextPosition.LEFT);
//		else if(instrument.rButton==ab) action.setValue(ITextProp.TextPosition.RIGHT);
//		else action.setValue(ITextProp.TextPosition.TOP_RIGHT);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final AbstractButton ab = interaction.getButton();
//		return instrument.hand.isActivated() && ( instrument.bButton==ab || instrument.blButton==ab || instrument.centreButton==ab ||
//				instrument.brButton==ab || instrument.tButton==ab || instrument.tlButton==ab || instrument.trButton==ab ||
//				instrument.lButton==ab || instrument.rButton==ab);
//	}
//}
