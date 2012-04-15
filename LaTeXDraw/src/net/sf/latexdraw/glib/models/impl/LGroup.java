package net.sf.latexdraw.glib.models.impl;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.Arcable;
import net.sf.latexdraw.glib.models.interfaces.Dottable;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;
import net.sf.latexdraw.glib.models.interfaces.IText;

import org.malai.mapping.ActiveArrayList;

/**
 * A Group is a group of IShape.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LGroup extends LShape implements IGroup {
	/** The set of shapes. */
	protected List<IShape> shapes;


	/**
	 * Creates a group of shapes.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	protected LGroup(final boolean uniqueID) {
		super(uniqueID);

		shapes = new ActiveArrayList<IShape>();
	}



	@Override
	public boolean isTypeOf(final Class<?> clazz) {
		if(clazz==null)
			return false;

		if(clazz.equals(getClass()) || clazz.equals(IShape.class) ||
			clazz.equals(LShape.class) || clazz.equals(IGroup.class))
			return true;

		for(final IShape sh : shapes)
			if(sh.isTypeOf(clazz))
				return true;

		return false;
	}


	@Override
	public void addShape(final IShape sh) {
		if(sh!=null)
			shapes.add(sh);
	}


	@Override
	public void addShape(final IShape s, final int index) {
		if(s!=null && index<=shapes.size() && (index==-1 || index>=0))
			if(index==-1 || index==shapes.size())
				shapes.add(s);
			else
				shapes.add(index, s);
	}


	@Override
	public void clear() {
		if(!shapes.isEmpty())
			shapes.clear();
	}


	@Override
	public boolean contains(final IShape sh) {
		return sh==null ? false : shapes.contains(sh);
	}



	@Override
	public void setThickness(final double thickness) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(thickness))
			for(final IShape shape : shapes)
				shape.setThickness(thickness);
	}


	/**
	 * @return The thickness of the first thicknessable shape. Otherwise NaN is returned.
	 */
	@Override
	public double getThickness() {
		double th = Double.NaN;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(th); i++)
			if(shapes.get(i).isThicknessable())
				th = shapes.get(i).getThickness();

		return th;
	}


	@Override
	public boolean isThicknessable() {
		boolean th = false;

		for(int i=0, size=shapes.size(); i<size && !th; i++)
			th = shapes.get(i).isThicknessable();

		return th;
	}


	@Override
	public Color getLineColour() {
		return isEmpty() ? null : shapes.get(0).getLineColour();
	}


	@Override
	public boolean isLineStylable() {
		boolean stylable = false;

		for(int i=0, size=shapes.size(); i<size && !stylable; i++)
			stylable = shapes.get(i).isLineStylable();

		return stylable;
	}


	@Override
	public LineStyle getLineStyle() {
		LineStyle style = null;

		for(int i=0, size=shapes.size(); i<size && style==null; i++)
			if(shapes.get(i).isLineStylable())
				style = shapes.get(i).getLineStyle();

		return style;
	}


	@Override
	public void setLineStyle(final LineStyle style) {
		if(style!=null)
			for(final IShape shape : shapes)
				if(shape.isLineStylable())
					shape.setLineStyle(style);
	}



	@Override
	public boolean isBordersMovable() {
		boolean moveable = false;

		for(int i=0, size=shapes.size(); i<size && !moveable; i++)
			moveable = shapes.get(i).isBordersMovable();

		return moveable;
	}


	@Override
	public BorderPos getBordersPosition() {
		BorderPos pos = null;

		for(int i=0, size=shapes.size(); i<size && pos==null; i++)
			if(shapes.get(i).isBordersMovable())
				pos = shapes.get(i).getBordersPosition();

		return pos;
	}



	@Override
	public void setBordersPosition(final BorderPos position) {
		if(position!=null)
			for(final IShape shape : shapes)
				if(shape.isBordersMovable())
					shape.setBordersPosition(position);
	}


	/**
	 * @return The line arc of the first shape that supports rounded corners. Otherwise
	 * NaN is returned.
	 */
	@Override
	public double getLineArc() {
		double value = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(value); i++) {
			sh = shapes.get(i);
			if(sh instanceof ILineArcShape)
				value = ((ILineArcShape)sh).getLineArc();
		}

		return value;
	}


	@Override
	public void setLineArc(final double lineArc) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(lineArc))
			for(final IShape shape : shapes)
				if(shape instanceof ILineArcShape)
					((ILineArcShape)shape).setLineArc(lineArc);
	}


	/**
	 * @return True if one of the shape of the group has rounded corners.
	 */
	@Override
	public boolean isRoundCorner() {
		boolean round = false;

		for(int i=0, size=shapes.size(); i<size && !round; i++)
			round = shapes.get(i) instanceof ILineArcShape && ((ILineArcShape)shapes.get(i)).isRoundCorner();

		return round;
	}


	@Override
	public void setLineColour(final Color lineColour) {
		if(lineColour!=null)
			for(final IShape shape : shapes)
				shape.setLineColour(lineColour);
	}


	@Override
	public void setDbleBordCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isDbleBorderable())
					shape.setDbleBordCol(colour);
	}


	@Override
	public Color getDbleBordCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isDbleBorderable() && sh.hasDbleBord())
				color = shapes.get(i).getDbleBordCol();
		}

		return color;
	}


	@Override
	public boolean hasDbleBord() {
		boolean dble = false;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && !dble; i++) {
			sh   = shapes.get(i);
			dble = sh.isDbleBorderable() && sh.hasDbleBord();
		}

		return dble;
	}


	@Override
	public void setHasDbleBord(final boolean dbleBorders) {
		for(final IShape shape : shapes)
			if(shape.isDbleBorderable())
				shape.setHasDbleBord(dbleBorders);
	}


	@Override
	public boolean isDbleBorderable() {
		boolean dbleable = false;

		for(int i=0, size=shapes.size(); i<size && !dbleable; i++)
			dbleable = shapes.get(i).isDbleBorderable();

		return dbleable;
	}


	@Override
	public void setDbleBordSep(final double dbleBorderSep) {
		for(final IShape shape : shapes)
			if(shape.isDbleBorderable())
				shape.setDbleBordSep(dbleBorderSep);
	}


	@Override
	public double getDbleBordSep() {
		double dbleSep = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(dbleSep); i++) {
			sh = shapes.get(i);
			if(sh.isDbleBorderable() && sh.hasDbleBord())
				dbleSep = sh.getDbleBordSep();
		}

		return dbleSep;
	}


	@Override
	public boolean isShadowable() {
		boolean shadowable = false;

		for(int i=0, size=shapes.size(); i<size && !shadowable; i++)
			shadowable = shapes.get(i).isShadowable();

		return shadowable;
	}


	@Override
	public boolean hasShadow() {
		boolean shadow = false;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && !shadow; i++) {
			sh   = shapes.get(i);
			shadow = sh.isShadowable() && sh.hasShadow();
		}

		return shadow;
	}



	@Override
	public void setHasShadow(final boolean shadow) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setHasShadow(shadow);
	}


	@Override
	public void setShadowSize(final double shadSize) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setShadowSize(shadSize);
	}


	@Override
	public double getShadowSize() {
		double shadSize = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(shadSize); i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				shadSize = sh.getShadowSize();
		}

		return shadSize;
	}


	@Override
	public void setShadowAngle(final double shadAngle) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setShadowAngle(shadAngle);
	}


	@Override
	public double getShadowAngle() {
		double shadAngle = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(shadAngle); i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				shadAngle = sh.getShadowAngle();
		}

		return shadAngle;
	}


	@Override
	public void setShadowCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isShadowable())
					shape.setShadowCol(colour);
	}


	@Override
	public Color getShadowCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				color = sh.getShadowCol();
		}

		return color;
	}


	@Override
	public FillingStyle getFillingStyle() {
		FillingStyle style = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && style==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable())
				style = sh.getFillingStyle();
		}

		return style;
	}


	@Override
	public void setFillingStyle(final FillingStyle style) {
		if(style!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setFillingStyle(style);
	}


	@Override
	public boolean isFillable() {
		boolean fillable = false;

		for(int i=0, size=shapes.size(); i<size && !fillable; i++)
			fillable = shapes.get(i).isFillable();

		return fillable;
	}


	@Override
	public boolean isInteriorStylable() {
		boolean interiorStylable = false;

		for(int i=0, size=shapes.size(); i<size && !interiorStylable; i++)
			interiorStylable = shapes.get(i).isInteriorStylable();

		return interiorStylable;
	}


	@Override
	public void setFillingCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isFillable())
					shape.setFillingCol(colour);
	}


	@Override
	public Color getFillingCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isFillable() && sh.isFilled())
				color = sh.getFillingCol();
		}

		return color;
	}


	@Override
	public void setHatchingsCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setHatchingsCol(colour);
	}


	@Override
	public Color getHatchingsCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isHatchings())
				color = sh.getHatchingsCol();
		}

		return color;
	}


	@Override
	public void setGradColStart(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setGradColStart(colour);
	}


	@Override
	public Color getGradColStart() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				color = sh.getGradColStart();
		}

		return color;
	}


	@Override
	public void setGradColEnd(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setGradColEnd(colour);
	}


	@Override
	public Color getGradColEnd() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				color = sh.getGradColEnd();
		}

		return color;
	}


	@Override
	public void setGradAngle(final double gradAngle) {
		for(final IShape shape : shapes)
			if(shape.isInteriorStylable())
				shape.setGradAngle(gradAngle);
	}


	@Override
	public double getGradAngle() {
		double gradientAngle = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(gradientAngle); i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				gradientAngle = sh.getGradAngle();
		}

		return gradientAngle;
	}


	@Override
	public void setGradMidPt(final double gradMidPoint) {
		for(final IShape shape : shapes)
			if(shape.isInteriorStylable())
				shape.setGradMidPt(gradMidPoint);
	}


	@Override
	public double getGradMidPt() {
		double gradientMidPt = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(gradientMidPt); i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				gradientMidPt = sh.getGradMidPt();
		}

		return gradientMidPt;
	}


	@Override
	public IShape getShapeAt(final int i) {
		final int size = shapes.size();
		return shapes.isEmpty() || i<-1 || i>= size ? null : i==-1 ? shapes.get(size-1) : shapes.get(i);
	}


	@Override
	public List<IShape> getShapes() {
		return shapes;
	}


	@Override
	public boolean isEmpty() {
		return shapes.isEmpty();
	}


	@Override
	public boolean removeShape(final IShape sh) {
		return sh==null ? false : shapes.remove(sh);
	}


	@Override
	public IShape removeShape(final int i) {
		return shapes.isEmpty() || i<-1 || i>=shapes.size() ? null : i==-1 ? shapes.remove(shapes.size()-1) : shapes.remove(i);
	}


	@Override
	public int size() {
		return shapes.size();
	}


	/**
	 * Duplicates the group. Does not duplicate the shapes it contains.
	 */
	@Override
	public IGroup duplicate() {
		return duplicate(false);
	}


	@Override
	public IGroup duplicate(final boolean duplicateShapes) {
		final IGroup dup = new LGroup(true);

		if(duplicateShapes)
			for(final IShape sh : shapes)
				dup.addShape(sh.duplicate());
		else
			for(final IShape sh : shapes)
				dup.addShape(sh);

		return dup;
	}


	@Override
	public double getHatchingsAngle() {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				return sh.getHatchingsAngle();

		return Double.NaN;
	}


	@Override
	public double getHatchingsSep() {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				return sh.getHatchingsSep();

		return Double.NaN;
	}


	@Override
	public double getHatchingsWidth() {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				return sh.getHatchingsWidth();

		return Double.NaN;
	}


	@Override
	public void setHatchingsAngle(final double hatchingsAngle) {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				sh.setHatchingsAngle(hatchingsAngle);
	}


	@Override
	public void setHatchingsSep(final double hatchingsSep) {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				sh.setHatchingsSep(hatchingsSep);
	}


	@Override
	public void setHatchingsWidth(final double hatchingsWidth) {
		for(final IShape sh : shapes)
			if(sh.isInteriorStylable())
				sh.setHatchingsWidth(hatchingsWidth);
	}


	@Override
	public boolean isArrowable() {
		boolean arrowable = false;

		for(int i=0, size=shapes.size(); i<size && !arrowable; i++)
			arrowable = shapes.get(i).isArrowable();

		return arrowable;
	}


	@Override
	public IArrow getArrowAt(final int position) {
		IArrow arrow = null;

		for(int i=0, size=shapes.size(); i<size && arrow==null; i++)
			if(shapes.get(i).isArrowable())
				arrow = shapes.get(i).getArrowAt(position);

		return arrow;
	}


	@Override
	public List<IArrow> getArrows() {
		List<IArrow> shapeArrows = null;

		for(int i=0, size=shapes.size(); i<size && shapeArrows==null; i++)
			if(shapes.get(i).isArrowable())
				shapeArrows = shapes.get(i).getArrows();

		return shapeArrows;
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		ILine arrowLine = null;

		for(int i=0, size=shapes.size(); i<size && arrowLine==null; i++)
			if(shapes.get(i).isArrowable())
				arrowLine = shapes.get(i).getArrowLine(arrow);

		return arrowLine;
	}


	@Override
	public void setDotSizeDim(final double dotSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setDotSizeDim(dotSizeDim);
	}

	@Override
	public void setDotSizeNum(final double dotSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setDotSizeNum(dotSizeNum);
	}

	@Override
	public void setTBarSizeNum(final double tbarSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setTBarSizeNum(tbarSizeNum);
	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setTBarSizeDim(tbarSizeDim);
	}

	@Override
	public double getTBarSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getTBarSizeDim();

		return Double.NaN;
	}

	@Override
	public double getTBarSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getTBarSizeNum();

		return Double.NaN;
	}

	@Override
	public void setRBracketNum(final double rBracketNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setRBracketNum(rBracketNum);
	}

	@Override
	public void setBracketNum(final double bracketNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setBracketNum(bracketNum);
	}

	@Override
	public void setArrowLength(final double lgth) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowLength(lgth);
	}

	@Override
	public void setArrowSizeDim(final double arrowSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowSizeDim(arrowSizeDim);
	}

	@Override
	public void setArrowSizeNum(final double arrowSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowSizeNum(arrowSizeNum);
	}

	@Override
	public void setArrowInset(final double inset) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowInset(inset);
	}

	@Override
	public double getDotSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getDotSizeDim();

		return Double.NaN;
	}

	@Override
	public double getDotSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getDotSizeNum();

		return Double.NaN;
	}

	@Override
	public double getBracketNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getBracketNum();

		return Double.NaN;
	}

	@Override
	public double getArrowSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowSizeNum();

		return Double.NaN;
	}

	@Override
	public double getArrowSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowSizeDim();

		return Double.NaN;
	}

	@Override
	public double getArrowInset() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowInset();

		return Double.NaN;
	}

	@Override
	public double getArrowLength() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowLength();

		return Double.NaN;
	}

	@Override
	public double getRBracketNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getRBracketNum();

		return Double.NaN;
	}


	@Override
	public boolean isShowPtsable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void translate(final double tx, final double ty) {
		for(int i=0, size=shapes.size(); i<size; i++)
			shapes.get(i).translate(tx, ty);
	}


	@Override
	public TextPosition getTextPosition() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return ((IText)sh).getTextPosition();
		return null;
	}


	@Override
	public void setTextPosition(final TextPosition textPosition) {
		if(textPosition!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IText)
					((IText)sh).setTextPosition(textPosition);
	}


	@Override
	public String getText() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return ((IText)sh).getText();

		return null;
	}


	@Override
	public void setText(final String text) {
		if(text!=null && text.length()>0)
			for(final IShape sh : shapes)
				if(sh instanceof IText)
					((IText)sh).setText(text);
	}


	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public IPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPosition(final IPoint pt) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setPosition(final double x, final double y) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setX(final double x) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setY(final double y) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean containsTexts() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return true;

		return false;
	}


	@Override
	public void addToRotationAngle(final IPoint gravCentre, final double angle) {
		final IPoint gc = gravCentre==null ? getGravityCentre() : gravCentre;

		for(final IShape sh : shapes)
			sh.addToRotationAngle(gc, angle);
	}


	@Override
	public void setRotationAngle(final double rotationAngle) {
		for(final IShape sh : shapes)
			sh.setRotationAngle(rotationAngle);
	}


	@Override
	public void rotate(final IPoint point, final double angle) {
		for(final IShape sh : shapes)
			sh.rotate(point, angle);
	}


	@Override
	public double getRotationAngle() {
		return shapes.isEmpty() ? 0. : shapes.get(0).getRotationAngle();
	}


	@Override
	public IPoint getGravityCentre() {
		switch(shapes.size()) {
			case 0:
				return new LPoint();
			default:
				return getTopLeftPoint().getMiddlePoint(getBottomRightPoint());
		}
	}


	@Override
	public IPoint getBottomRightPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MIN_VALUE;
			y = Double.MIN_VALUE;
			IPoint br;

			for(final IShape sh : shapes) {
				br = sh.getBottomRightPoint();
				if(br.getX()>x)
					x = br.getX();
				if(br.getY()>y)
					y = br.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getBottomLeftPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MAX_VALUE;
			y = Double.MIN_VALUE;
			IPoint bl;

			for(final IShape sh : shapes) {
				bl = sh.getBottomLeftPoint();
				if(bl.getX()<x)
					x = bl.getX();
				if(bl.getY()>y)
					y = bl.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getTopLeftPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MAX_VALUE;
			y = Double.MAX_VALUE;
			IPoint tl;

			for(final IShape sh : shapes) {
				tl = sh.getTopLeftPoint();
				if(tl.getX()<x)
					x = tl.getX();
				if(tl.getY()<y)
					y = tl.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getTopRightPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MIN_VALUE;
			y = Double.MAX_VALUE;
			IPoint tr;

			for(final IShape sh : shapes) {
				tr = sh.getTopRightPoint();
				if(tr.getX()>x)
					x = tr.getX();
				if(tr.getY()<y)
					y = tr.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public boolean hasHatchings() {
		boolean has = false;

		for(int i=0, size=shapes.size(); i<size && !has; i++)
			has = shapes.get(i).hasHatchings();

		return has;
	}


	@Override
	public boolean hasGradient() {
		boolean has = false;

		for(int i=0, size=shapes.size(); i<size && !has; i++)
			has = shapes.get(i).hasGradient();

		return has;
	}


	@Override
	public void setModified(final boolean modified) {
		for(final IShape shape : shapes)
			shape.setModified(modified);

		super.setModified(modified);
	}

	@Override
	public void setArrowStyle(final ArrowStyle style, final int position) {
		if(style!=null)
			for(final IShape sh : shapes)
				if(sh.isArrowable())
					sh.setArrowStyle(style, position);
	}

	@Override
	public ArrowStyle getArrowStyle(final int position) {
		ArrowStyle style=null;

		for(int i=0, size=shapes.size(); i<size && style==null; i++)
			if(shapes.get(i).isArrowable())
				style = shapes.get(i).getArrowStyle(position);

		return style;
	}


	@Override
	public Color getDotFillingCol() {
		Color color=null;

		for(int i=0, size=shapes.size(); i<size && color==null; i++)
			if(shapes.get(i) instanceof Dottable)
				color = ((Dottable)shapes.get(i)).getDotFillingCol();

		return color;
	}


	@Override
	public void setDotFillingCol(final Color fillingCol) {
		if(fillingCol!=null)
			for(final IShape sh : shapes)
				if(sh instanceof Dottable)
					((Dottable)sh).setDotFillingCol(fillingCol);
	}


	@Override
	public DotStyle getDotStyle() {
		DotStyle style=null;

		for(int i=0, size=shapes.size(); i<size && style==null; i++)
			if(shapes.get(i) instanceof Dottable)
				style = ((Dottable)shapes.get(i)).getDotStyle();

		return style;
	}


	@Override
	public void setDotStyle(final DotStyle style) {
		if(style!=null)
			for(final IShape sh : shapes)
				if(sh instanceof Dottable)
					((Dottable)sh).setDotStyle(style);
	}


	@Override
	public double getRadius() {
		for(final IShape sh : shapes)
			if(sh instanceof Dottable)
				return ((Dottable)sh).getRadius();

		return Double.NaN;
	}


	@Override
	public void setRadius(final double radius) {
		for(final IShape sh : shapes)
			if(sh instanceof Dottable)
				((Dottable)sh).setRadius(radius);
	}


	@Override
	public ArcStyle getArcStyle() {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				return ((Arcable)sh).getArcStyle();

		return null;
	}


	@Override
	public void setArcStyle(final ArcStyle type) {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				((Arcable)sh).setArcStyle(type);
	}


	@Override
	public double getAngleStart() {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				return ((Arcable)sh).getAngleStart();

		return Double.NaN;
	}


	@Override
	public void setAngleStart(final double angleStart) {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				((Arcable)sh).setAngleStart(angleStart);
	}


	@Override
	public double getAngleEnd() {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				return ((Arcable)sh).getAngleEnd();

		return Double.NaN;
	}


	@Override
	public void setAngleEnd(final double angleEnd) {
		for(final IShape sh : shapes)
			if(sh instanceof Arcable)
				((Arcable)sh).setAngleEnd(angleEnd);
	}


	@Override
	public void scale(final double x, final double y, final Position pos, final Rectangle2D bound) {
		for(final IShape sh : shapes)
			sh.scale(x, y, pos, bound);
	}


	@Override
	public boolean hasDot() {
		for(final IShape sh : shapes)
			if(sh instanceof Dottable && ((Dottable)sh).hasDot())
				return true;

		return false;
	}


	@Override
	public List<BorderPos> getBordersPositionList() {
		final List<BorderPos> list = new ArrayList<BorderPos>();

		for(final IShape sh : shapes)
			list.add(sh.isBordersMovable() ? sh.getBordersPosition() : null);

		return list;
	}


	@Override
	public List<Color> getLineColourList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.getLineColour());

		return list;
	}


	@Override
	public void setBordersPositionList(final List<BorderPos> list) {
		if(list!=null && list.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(list.get(i)!=null)
					shapes.get(i).setBordersPosition(list.get(i));
	}


	@Override
	public void setLineColourList(final List<Color> list) {
		if(list!=null && list.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				shapes.get(i).setLineColour(list.get(i));
	}


	@Override
	public List<Double> getAngleStartList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Arcable ? ((Arcable)sh).getAngleStart() : null);

		return list;
	}


	@Override
	public List<Double> getAngleEndList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Arcable ? ((Arcable)sh).getAngleEnd() : null);

		return list;
	}


	@Override
	public List<ArcStyle> getArcStyleList() {
		final List<ArcStyle> list = new ArrayList<ArcStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Arcable ? ((Arcable)sh).getArcStyle() : null);

		return list;
	}


	@Override
	public List<ArrowStyle> getArrowStyleList(final int i) {
		final List<ArrowStyle> list = new ArrayList<ArrowStyle>();

		for(final IShape sh : shapes)
			list.add(sh.isArrowable() ? sh.getArrowStyle(i) : null);

		return list;
	}


	@Override
	public List<Double> getRotationAngleList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.getRotationAngle());

		return list;
	}


	@Override
	public List<TextPosition> getTextPositionList() {
		final List<TextPosition> list = new ArrayList<TextPosition>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IText ? ((IText)sh).getTextPosition() : null);

		return list;
	}


	@Override
	public List<String> getTextList() {
		final List<String> list = new ArrayList<String>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IText ? ((IText)sh).getText() : null);

		return list;
	}


	@Override
	public List<Double> getHatchingsAngleList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getHatchingsAngle() : null);

		return list;
	}


	@Override
	public List<Double> getHatchingsWidthList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getHatchingsWidth() : null);

		return list;
	}


	@Override
	public List<Double> getHatchingsSepList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getHatchingsSep() : null);

		return list;
	}


	@Override
	public List<Double> getGradAngleList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getGradAngle() : null);

		return list;
	}


	@Override
	public List<Double> getGradMidPtList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getGradMidPt() : null);

		return list;
	}


	@Override
	public List<Double> getLineArcList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh instanceof ILineArcShape ? ((ILineArcShape)sh).getLineArc() : null);

		return list;
	}


	@Override
	public List<Color> getFillingColList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getFillingCol() : null);

		return list;
	}


	@Override
	public List<Color> getHatchingsColList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getHatchingsCol() : null);

		return list;
	}


	@Override
	public List<Boolean> hasDbleBordList() {
		final List<Boolean> list = new ArrayList<Boolean>();

		for(final IShape sh : shapes)
			list.add(sh.isDbleBorderable() ? sh.hasDbleBord() : null);

		return list;
	}


	@Override
	public List<Double> getDbleBordSepList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isDbleBorderable() ? sh.getDbleBordSep() : null);

		return list;
	}


	@Override
	public List<Color> getDbleBordColList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isDbleBorderable() ? sh.getDbleBordCol() : null);

		return list;
	}


	@Override
	public List<Boolean> hasShadowList() {
		final List<Boolean> list = new ArrayList<Boolean>();

		for(final IShape sh : shapes)
			list.add(sh.isShadowable() ? sh.hasShadow() : null);

		return list;
	}


	@Override
	public List<Double> getShadowSizeList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isShadowable() ? sh.getShadowSize() : null);

		return list;
	}


	@Override
	public List<Double> getShadowAngleList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isShadowable() ? sh.getShadowAngle() : null);

		return list;
	}


	@Override
	public List<Color> getShadowColList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isShadowable() ? sh.getShadowCol() : null);

		return list;
	}


	@Override
	public List<Color> getGradColStartList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getGradColStart() : null);

		return list;
	}


	@Override
	public List<Color> getGradColEndList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getGradColEnd() : null);

		return list;
	}


	@Override
	public List<Double> getThicknessList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh.isThicknessable() ? sh.getThickness() : null);

		return list;
	}


	@Override
	public List<FillingStyle> getFillingStyleList() {
		final List<FillingStyle> list = new ArrayList<FillingStyle>();

		for(final IShape sh : shapes)
			list.add(sh.isInteriorStylable() ? sh.getFillingStyle() : null);

		return list;
	}


	@Override
	public List<LineStyle> getLineStyleList() {
		final List<LineStyle> list = new ArrayList<LineStyle>();

		for(final IShape sh : shapes)
			list.add(sh.isLineStylable() ? sh.getLineStyle() : null);

		return list;
	}


	@Override
	public List<Color> getDotFillingColList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Dottable ? ((Dottable)sh).getDotFillingCol() : null);

		return list;
	}


	@Override
	public List<DotStyle> getDotStyleList() {
		final List<DotStyle> list = new ArrayList<DotStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Dottable ? ((Dottable)sh).getDotStyle() : null);

		return list;
	}


	@Override
	public List<Double> getDotSizeList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh instanceof Dottable ? ((Dottable)sh).getRadius() : null);

		return list;
	}


	@Override
	public void setAngleStartList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Arcable)
					((Arcable)shapes.get(i)).setAngleStart(values.get(i));
	}


	@Override
	public void setDotStyleList(final List<DotStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Dottable)
					((Dottable)shapes.get(i)).setDotStyle(values.get(i));
	}


	@Override
	public void setAngleEndList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Arcable)
					((Arcable)shapes.get(i)).setAngleEnd(values.get(i));
	}


	@Override
	public void setArcStyleList(final List<ArcStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Arcable)
					((Arcable)shapes.get(i)).setArcStyle(values.get(i));
	}


	@Override
	public void setArrowStyleList(final List<ArrowStyle> values, final int i) {
		if(values!=null && values.size()==shapes.size())
			for(int j=0, size=shapes.size(); j<size; j++)
				if(values.get(j)!=null && shapes.get(j).isArrowable())
					shapes.get(j).setArrowStyle(values.get(j), i);
	}


	@Override
	public void setRotationAngleList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null)
					shapes.get(i).setRotationAngle(values.get(i));
	}


	@Override
	public void setTextPositionList(final List<TextPosition> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IText)
					((IText)shapes.get(i)).setTextPosition(values.get(i));
	}


	@Override
	public void setTextList(final List<String> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IText)
					((IText)shapes.get(i)).setText(values.get(i));
	}


	@Override
	public void setHatchingsAngleList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setHatchingsAngle(values.get(i));
	}


	@Override
	public void setHatchingsWidthList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setHatchingsWidth(values.get(i));
	}


	@Override
	public void setHatchingsSepList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setHatchingsSep(values.get(i));
	}


	@Override
	public void setGradAngleList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setGradAngle(values.get(i));
	}


	@Override
	public void setGradMidPtList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setGradMidPt(values.get(i));
	}


	@Override
	public void setLineArcList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof ILineArcShape)
					((ILineArcShape)shapes.get(i)).setLineArc(values.get(i));
	}


	@Override
	public void setFillingColList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setFillingCol(values.get(i));
	}


	@Override
	public void setHatchingsColList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setHatchingsCol(values.get(i));
	}


	@Override
	public void setHasDbleBordList(final List<Boolean> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isDbleBorderable())
					shapes.get(i).setHasDbleBord(values.get(i));
	}


	@Override
	public void setDbleBordSepList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isDbleBorderable())
					shapes.get(i).setDbleBordSep(values.get(i));
	}


	@Override
	public void setDbleBordColList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isDbleBorderable())
					shapes.get(i).setDbleBordCol(values.get(i));
	}


	@Override
	public void setHasShadowList(final List<Boolean> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isShadowable())
					shapes.get(i).setHasShadow(values.get(i));
	}


	@Override
	public void setShadowSizeList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isShadowable())
					shapes.get(i).setShadowSize(values.get(i));
	}


	@Override
	public void setShadowAngleList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isShadowable())
					shapes.get(i).setShadowAngle(values.get(i));
	}


	@Override
	public void setShadowColList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isShadowable())
					shapes.get(i).setShadowCol(values.get(i));
	}


	@Override
	public void setGradColStartList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setGradColStart(values.get(i));
	}


	@Override
	public void setGradColEndList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setGradColEnd(values.get(i));
	}


	@Override
	public void setThicknessList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isThicknessable())
					shapes.get(i).setThickness(values.get(i));
	}


	@Override
	public void setFillingStyleList(final List<FillingStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isInteriorStylable())
					shapes.get(i).setFillingStyle(values.get(i));
	}


	@Override
	public void setLineStyleList(final List<LineStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i).isLineStylable())
					shapes.get(i).setLineStyle(values.get(i));
	}


	@Override
	public void setDotFillingColList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Dottable)
					((Dottable)shapes.get(i)).setDotFillingCol(values.get(i));
	}


	@Override
	public void setDotSizeList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof Dottable)
					((Dottable)shapes.get(i)).setRadius(values.get(i));
	}


	@Override
	public double getGridMinX() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridMinX();

		return Double.NaN;
	}


	@Override
	public double getGridMaxX() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridMaxX();

		return Double.NaN;
	}


	@Override
	public double getGridMinY() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridMinY();

		return Double.NaN;
	}


	@Override
	public double getGridMaxY() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridMaxY();

		return Double.NaN;
	}


	@Override
	public int getLabelsSize() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getLabelsSize();

		return -1;
	}


	@Override
	public void setLabelsSize(final int labelsSize) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setLabelsSize(labelsSize);
	}


	@Override
	public void setGridEndX(final double x) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridEndX(x);
	}


	@Override
	public void setGridEndY(final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridEndY(y);
	}


	@Override
	public boolean isXLabelSouth() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).isXLabelSouth();

		return false;
	}


	@Override
	public void setXLabelSouth(final boolean isXLabelSouth) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setXLabelSouth(isXLabelSouth);
	}


	@Override
	public boolean isYLabelWest() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).isYLabelWest();

		return false;
	}


	@Override
	public void setYLabelWest(final boolean isYLabelWest) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setYLabelWest(isYLabelWest);
	}


	@Override
	public double getGridStartX() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridStartX();

		return Double.NaN;
	}


	@Override
	public double getGridStartY() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridStartY();

		return Double.NaN;
	}


	@Override
	public void setGridStart(final double x, final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridStart(x, y);
	}


	@Override
	public double getGridEndX() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridEndX();

		return Double.NaN;
	}


	@Override
	public double getGridEndY() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridEndY();

		return Double.NaN;
	}


	@Override
	public void setGridEnd(final double x, final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridEnd(x, y);
	}


	@Override
	public double getOriginX() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getOriginX();

		return Double.NaN;
	}


	@Override
	public double getOriginY() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getOriginY();

		return Double.NaN;
	}


	@Override
	public void setOrigin(final double x, final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setOrigin(x, y);
	}


	@Override
	public void setGridStartY(final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridStartY(y);
	}


	@Override
	public void setGridStartX(final double x) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setGridStartX(x);
	}


	@Override
	public void setOriginX(final double x) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setOriginX(x);
	}


	@Override
	public void setOriginY(final double y) {
		for(final IShape shape : shapes)
			if(shape instanceof IStandardGrid)
				((IStandardGrid)shape).setOriginY(y);
	}


	@Override
	public double getStep() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getStep();

		return Double.NaN;
	}


	@Override
	public void setGridEndList(final List<IPoint> values) {
		IPoint pt;
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++) {
				pt = values.get(i);
				if(pt!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setGridEnd(pt.getX(), pt.getY());
			}
	}

	@Override
	public void setGridStartList(final List<IPoint> values) {
		IPoint pt;
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++) {
				pt = values.get(i);
				if(pt!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setGridStart(pt.getX(), pt.getY());
			}
	}


	@Override
	public List<IPoint> getGridStartList() {
		final List<IPoint> list = new ArrayList<IPoint>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IStandardGrid ? ((IStandardGrid)sh).getGridStart() : null);

		return list;
	}


	@Override
	public List<IPoint> getGridEndList() {
		final List<IPoint> list = new ArrayList<IPoint>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IStandardGrid ? ((IStandardGrid)sh).getGridEnd() : null);

		return list;
	}


	@Override
	public IPoint getGridStart() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridStart();

		return null;
	}


	@Override
	public IPoint getGridEnd() {
		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid)
				return ((IStandardGrid)sh).getGridEnd();

		return null;
	}


	@Override
	public void setGridOriginList(final List<IPoint> values) {
		IPoint pt;
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++) {
				pt = values.get(i);
				if(pt!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setOrigin(pt.getX(), pt.getY());
			}
	}


	@Override
	public List<IPoint> getGridOriginList() {
		final List<IPoint> list = new ArrayList<IPoint>();

		for(final IShape sh : shapes)
			if(sh instanceof IStandardGrid) {
				final IStandardGrid grid = (IStandardGrid)sh;
				list.add(new LPoint(grid.getOriginX(), grid.getOriginY()));
			}
			else list.add(null);

		return list;
	}



	@Override
	public boolean isColourable() {
		for(final IShape sh : shapes)
			if(sh.isColourable())
				return true;

		return false;
	}


	@Override
	public void setGridLabelSizeList(final List<Integer> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setLabelsSize(values.get(i));
	}


	@Override
	public List<Integer> getGridLabelSizeList() {
		final List<Integer> list = new ArrayList<Integer>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IStandardGrid ? ((IStandardGrid)sh).getLabelsSize() : null);

		return list;
	}


	@Override
	public void setGridXLabelSouthList(final List<Boolean> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setXLabelSouth(values.get(i));
	}


	@Override
	public List<Boolean> getGridXLabelSouthList() {
		final List<Boolean> list = new ArrayList<Boolean>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IStandardGrid ? ((IStandardGrid)sh).isXLabelSouth() : null);

		return list;
	}


	@Override
	public void setGridYLabelWestList(final List<Boolean> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IStandardGrid)
					((IStandardGrid)shapes.get(i)).setYLabelWest(values.get(i));
	}


	@Override
	public List<Boolean> getGridYLabelWestList() {
		final List<Boolean> list = new ArrayList<Boolean>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IStandardGrid ? ((IStandardGrid)sh).isYLabelWest() : null);

		return list;
	}


	@Override
	public void setAxesStyleList(final List<AxesStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setAxesStyle(values.get(i));
	}


	@Override
	public List<AxesStyle> getAxesStyleList() {
		final List<AxesStyle> list = new ArrayList<AxesStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getAxesStyle() : null);

		return list;
	}


	@Override
	public double getIncrementX() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getIncrementX();

		return Double.NaN;
	}


	@Override
	public double getIncrementY() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getIncrementY();

		return Double.NaN;
	}


	@Override
	public void setIncrementX(final double increment) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(increment))
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setIncrementX(increment);
	}


	@Override
	public void setIncrementY(final double increment) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(increment))
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setIncrementY(increment);
	}


	@Override
	public double getDistLabelsX() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getDistLabelsX();

		return Double.NaN;
	}


	@Override
	public double getDistLabelsY() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getDistLabelsY();

		return Double.NaN;
	}


	@Override
	public void setDistLabelsX(final double distLabelsX) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(distLabelsX))
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setDistLabelsX(distLabelsX);
	}


	@Override
	public void setDistLabelsY(final double distLabelsY) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(distLabelsY))
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setDistLabelsY(distLabelsY);
	}


	@Override
	public PlottingStyle getLabelsDisplayed() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getLabelsDisplayed();

		return null;
	}


	@Override
	public void setLabelsDisplayed(final PlottingStyle labelsDisplayed) {
		if(labelsDisplayed!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setLabelsDisplayed(labelsDisplayed);
	}


	@Override
	public boolean isShowOrigin() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).isShowOrigin();

		return false;
	}


	@Override
	public void setShowOrigin(final boolean showOrigin) {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				((IAxes)sh).setShowOrigin(showOrigin);
	}


	@Override
	public PlottingStyle getTicksDisplayed() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getTicksDisplayed();

		return null;
	}


	@Override
	public void setTicksDisplayed(final PlottingStyle ticksDisplayed) {
		if(ticksDisplayed!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setTicksDisplayed(ticksDisplayed);
	}


	@Override
	public TicksStyle getTicksStyle() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getTicksStyle();

		return null;
	}


	@Override
	public void setTicksStyle(final TicksStyle ticksStyle) {
		if(ticksStyle!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setTicksStyle(ticksStyle);
	}


	@Override
	public double getTicksSize() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getTicksSize();

		return Double.NaN;
	}


	@Override
	public void setTicksSize(final double ticksSize) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(ticksSize))
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setTicksSize(ticksSize);
	}


	@Override
	public AxesStyle getAxesStyle() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getAxesStyle();

		return null;
	}


	@Override
	public void setAxesStyle(final AxesStyle axesStyle) {
		if(axesStyle!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IAxes)
					((IAxes)sh).setAxesStyle(axesStyle);
	}


	@Override
	public void setAxesTicksStyleList(final List<TicksStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setTicksStyle(values.get(i));
	}


	@Override
	public List<TicksStyle> getAxesTicksStyleList() {
		final List<TicksStyle> list = new ArrayList<TicksStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getTicksStyle() : null);

		return list;
	}


	@Override
	public void setAxesTicksSizeList(final List<Double> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setTicksSize(values.get(i));
	}


	@Override
	public List<Double> getAxesTicksSizeList() {
		final List<Double> list = new ArrayList<Double>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getTicksSize() : null);

		return list;
	}


	@Override
	public void setAxesTicksDisplayedList(final List<PlottingStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setTicksDisplayed(values.get(i));
	}


	@Override
	public List<PlottingStyle> getAxesTicksDisplayedList() {
		final List<PlottingStyle> list = new ArrayList<PlottingStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getTicksDisplayed() : null);

		return list;
	}


	@Override
	public void setAxesIncrementsList(final List<IPoint> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setIncrement(values.get(i));
	}


	@Override
	public List<IPoint> getAxesIncrementsList() {
		final List<IPoint> list = new ArrayList<IPoint>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getIncrement() : null);

		return list;
	}


	@Override
	public IPoint getIncrement() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getIncrement();

		return null;
	}


	@Override
	public void setIncrement(final IPoint increment) {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				((IAxes)sh).setIncrement(increment);
	}


	@Override
	public void setAxesLabelsDisplayedList(final List<PlottingStyle> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setLabelsDisplayed(values.get(i));
	}


	@Override
	public List<PlottingStyle> getAxesLabelsDisplayedList() {
		final List<PlottingStyle> list = new ArrayList<PlottingStyle>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getLabelsDisplayed() : null);

		return list;
	}


	@Override
	public void setAxesShowOriginList(final List<Boolean> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setShowOrigin(values.get(i));
	}


	@Override
	public List<Boolean> getAxesShowOriginList() {
		final List<Boolean> list = new ArrayList<Boolean>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).isShowOrigin() : null);

		return list;
	}


	@Override
	public IPoint getDistLabels() {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				return ((IAxes)sh).getDistLabels();

		return null;
	}


	@Override
	public void setDistLabels(final IPoint distLabels) {
		for(final IShape sh : shapes)
			if(sh instanceof IAxes)
				((IAxes)sh).setDistLabels(distLabels);
	}


	@Override
	public void setAxesDistLabelsList(final List<IPoint> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IAxes)
					((IAxes)shapes.get(i)).setDistLabels(values.get(i));
	}


	@Override
	public List<IPoint> getAxesDistLabelsList() {
		final List<IPoint> list = new ArrayList<IPoint>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IAxes ? ((IAxes)sh).getDistLabels() : null);

		return list;
	}


	@Override
	public int getGridDots() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getGridDots();
		return 0;
	}


	@Override
	public void setGridDots(final int gridDots) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setGridDots(gridDots);
	}


	@Override
	public Color getGridLabelsColour() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getGridLabelsColour();

		return null;
	}


	@Override
	public void setGridLabelsColour(final Color gridLabelsColour) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setGridLabelsColour(gridLabelsColour);
	}


	@Override
	public double getGridWidth() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getGridWidth();
		return Double.NaN;
	}


	@Override
	public void setGridWidth(final double gridWidth) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setGridWidth(gridWidth);
	}


	@Override
	public Color getSubGridColour() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getSubGridColour();
		return null;
	}


	@Override
	public void setSubGridColour(final Color subGridColour) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setSubGridColour(subGridColour);
	}


	@Override
	public int getSubGridDiv() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getSubGridDiv();
		return 0;
	}


	@Override
	public void setSubGridDiv(final int subGridDiv) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setSubGridDiv(subGridDiv);
	}


	@Override
	public int getSubGridDots() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getSubGridDots();
		return 0;
	}


	@Override
	public void setSubGridDots(final int subGridDots) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setSubGridDots(subGridDots);
	}


	@Override
	public double getSubGridWidth() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getSubGridWidth();
		return Double.NaN;
	}


	@Override
	public void setSubGridWidth(final double subGridWidth) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setSubGridWidth(subGridWidth);
	}


	@Override
	public void setUnit(final double unit) {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				((IGrid)sh).setUnit(unit);
	}


	@Override
	public double getUnit() {
		for(final IShape sh : shapes)
			if(sh instanceof IGrid)
				return ((IGrid)sh).getUnit();
		return Double.NaN;
	}


	@Override
	public void setGridLabelsColourList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IGrid)
					((IGrid)shapes.get(i)).setGridLabelsColour(values.get(i));
	}


	@Override
	public List<Color> getGridLabelsColourList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IGrid ? ((IGrid)sh).getGridLabelsColour() : null);

		return list;
	}


	@Override
	public void setSubGridColourList(final List<Color> values) {
		if(values!=null && values.size()==shapes.size())
			for(int i=0, size=shapes.size(); i<size; i++)
				if(values.get(i)!=null && shapes.get(i) instanceof IGrid)
					((IGrid)shapes.get(i)).setSubGridColour(values.get(i));
	}


	@Override
	public List<Color> getSubGridColourList() {
		final List<Color> list = new ArrayList<Color>();

		for(final IShape sh : shapes)
			list.add(sh instanceof IGrid ? ((IGrid)sh).getSubGridColour() : null);

		return list;
	}
}
